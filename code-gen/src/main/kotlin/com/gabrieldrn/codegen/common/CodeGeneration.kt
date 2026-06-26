/*
 * Copyright 2024 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gabrieldrn.codegen.common

import androidx.compose.runtime.Immutable
import com.gabrieldrn.codegen.CarbonTheme
import com.gabrieldrn.codegen.common.model.RemovedToken
import com.gabrieldrn.codegen.common.model.RenamedToken
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.nio.file.Path
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

internal const val CODE_INDENT = "    "

internal val generatedCodeMessage =
    """
        ----------------------------------
        /!\ Generated code. Do not modify.
        ----------------------------------
    """.trimIndent()

internal val dataClassesSuppressedIssuesAnnotation =
    AnnotationSpec.builder(Suppress::class)
        .addMember("%S, %S", "UndocumentedPublicProperty", "MaxLineLength")
        .build()

internal val topLevelPropertiesSuppressedIssuesAnnotation =
    AnnotationSpec.builder(Suppress::class)
        .addMember("%S, %S, %S", "TopLevelPropertyNaming", "TrailingWhitespace", "MaxLineLength")
        .build()

/**
 * Result of generating a token set with [generateTokenSetClass]: the class it generated and the
 * name of its per-theme top-level instance, so other token sets can reference it (see
 * [Node.ReferenceNode]).
 */
data class TokenSetResult(
    val abstractionClassName: ClassName,
    val instanceNames: Map<CarbonTheme, String>,
)

/**
 * Generates all source files for a token set and writes them to [sourcePath].
 *
 * For the given [abstractionClassName] this function produces:
 * - **One abstraction file** — a `@Immutable data class` containing all token properties.
 * - **One implementation file per theme** — a top-level property named with [instanceName],
 *   instantiating the abstraction class with that theme's token values.
 *
 * Token lifecycle annotations on model properties are honoured:
 * - [`@RenamedToken`][RenamedToken] — emits a computed getter that delegates to the new name,
 *   annotated with `@Deprecated` + `ReplaceWith`. The property is **not** a constructor parameter.
 * - [`@RemovedToken`][RemovedToken] — emits a `@Deprecated` constructor parameter whose
 *   per-theme fallback value is read from [removedTokensData].
 *
 * Properties whose type matches a key of [componentReferences] are not recursed into — they are
 * emitted as a reference to that token set's already-generated per-theme instance instead (see
 * [Node.ReferenceNode]).
 *
 * @param abstractionClassName Fully-qualified [ClassName] of the abstraction class to generate.
 * @param classKdoc KDoc of the generated abstraction class.
 * @param tokenSetData Per-theme deserialized model instances to traverse for token values.
 * @param instanceName Name of the per-theme top-level property.
 * @param instanceKdoc KDoc of the per-theme top-level property, if any.
 * @param visibility Visibility of the per-theme top-level property.
 * @param componentReferences Other already-generated token sets that this one references by
 *   property type, keyed by the model class of that property.
 * @param removedTokensData Per-theme [JsonElement] providing fallback values for [RemovedToken]
 *   properties. Required when any model property carries `@RemovedToken`; may be `null` otherwise.
 * @param sourcePath Directory generated source files are written to.
 */
fun generateTokenSetClass(
    abstractionClassName: ClassName,
    classKdoc: String,
    tokenSetData: Map<CarbonTheme, Any>,
    instanceName: (CarbonTheme) -> String,
    instanceKdoc: (CarbonTheme) -> String? = { null },
    visibility: KModifier = KModifier.PUBLIC,
    componentReferences: Map<KClass<*>, TokenSetResult> = emptyMap(),
    removedTokensData: Map<CarbonTheme, JsonElement>? = null,
    sourcePath: Path,
): TokenSetResult {
    check(tokenSetData.isNotEmpty())

    val tokenSetTrees: Map<CarbonTheme, Node.ClassNode> = tokenSetData.mapValues { (theme, model) ->
        getTokenSetTree(
            nodePropertySpec = PropertySpec
                .builder("root", abstractionClassName)
                .initializer("root")
                .build(),
            model = model,
            theme = theme,
            abstractionName = abstractionClassName,
            classKdoc = classKdoc,
            componentReferences = componentReferences,
            removedTokensJson = removedTokensData?.get(theme),
        )
    }

    // Abstraction class, shared across themes (structurally identical).

    FileSpec.builder(abstractionClassName)
        .indent(CODE_INDENT)
        .addFileComment(generatedCodeMessage)
        .addType(tokenSetTrees.values.first().abstractionTypeSpec)
        .build()
        .writeTo(sourcePath)

    // Per-theme top-level instance.

    tokenSetData.keys.forEach { theme ->
        val tree = tokenSetTrees[theme]!!

        val instanceSpec = PropertySpec
            .builder(instanceName(theme), abstractionClassName, visibility)
            .apply { instanceKdoc(theme)?.let(::addKdoc) }
            .initializer(tree.toCodeBlock())
            .build()

        FileSpec.builder(abstractionClassName.packageName, instanceName(theme))
            .addFileComment(generatedCodeMessage)
            .addProperty(instanceSpec)
            .addAnnotation(topLevelPropertiesSuppressedIssuesAnnotation)
            .build()
            .writeTo(sourcePath)
    }

    return TokenSetResult(
        abstractionClassName = abstractionClassName,
        instanceNames = tokenSetData.keys.associateWith(instanceName),
    )
}

/**
 * Returns a [Node.ClassNode] (a tree structure) based on the provided [model].
 */
private fun getTokenSetTree(
    nodePropertySpec: PropertySpec,
    model: Any,
    theme: CarbonTheme,
    abstractionName: ClassName,
    classKdoc: String,
    componentReferences: Map<KClass<*>, TokenSetResult>,
    removedTokensJson: JsonElement?,
): Node.ClassNode {
    val unorderedSubNodes: List<Node> = model::class.memberProperties.map { property ->
        val propertyClass = property.returnType.jvmErasure

        when {
            property.findAnnotation<RenamedToken>() != null ->
                buildRenamedCompatNode(property, model)

            property.findAnnotation<RemovedToken>() != null ->
                buildRemovedTokenNode(property, removedTokensJson)

            propertyClass == String::class ->
                Node.TokenNode(property, property.getter.call(model) as String)

            componentReferences.containsKey(propertyClass) -> {
                val reference = componentReferences.getValue(propertyClass)
                Node.ReferenceNode(
                    property = property,
                    type = reference.abstractionClassName,
                    referencedInstanceName = reference.instanceNames.getValue(theme),
                )
            }

            else -> error(
                "Unsupported property type '${propertyClass.simpleName}' for '${property.name}'. " +
                    "Register it in `componentReferences` or add support for it in the generator."
            )
        }
    }

    // Plain color tokens are listed before references to other token sets (e.g. `Theme`'s
    // `buttonColors`), matching the model's existing source layout. `sortedBy` is stable, so
    // each group keeps its original (alphabetical) relative order.
    val subNodes: List<Node> = unorderedSubNodes.sortedBy { it is Node.ReferenceNode }

    val realNodes = subNodes.filterNot { it is Node.CompatNode }
    val compatNodes = subNodes.filterIsInstance<Node.CompatNode>()

    val realProperties = realNodes.map { it.propertySpec }
    val compatProperties = compatNodes.map { it.propertySpec }

    val constructor = FunSpec.constructorBuilder()
        .addParameters(realProperties.map { ParameterSpec.builder(it.name, it.type).build() })
        .build()

    val spec = TypeSpec.classBuilder(abstractionName)
        .primaryConstructor(constructor)
        .addKdoc(classKdoc)
        .addModifiers(KModifier.DATA)
        .addAnnotation(Immutable::class)
        .addAnnotation(dataClassesSuppressedIssuesAnnotation)
        .addProperties(realProperties)
        .addProperties(compatProperties)
        .build()

    return Node.ClassNode(
        abstractionClassName = abstractionName,
        subNodes = subNodes,
        abstractionTypeSpec = spec,
        propertySpec = nodePropertySpec,
    )
}

private fun buildRenamedCompatNode(
    property: KProperty1<out Any, *>,
    model: Any,
): Node.CompatNode.Renamed {
    val newName = property.findAnnotation<RenamedToken>()!!.newName
    model::class.memberProperties.firstOrNull { it.name == newName }
        ?: error(
            "Cannot resolve @RenamedToken('$newName') on '${property.name}': " +
                "no sibling property named '$newName' found in model."
        )
    return Node.CompatNode.Renamed(
        property = property,
        newName = newName,
        propertyType = androidx.compose.ui.graphics.Color::class.asTypeName(),
    )
}

private fun buildRemovedTokenNode(
    property: KProperty1<out Any, *>,
    removedTokensJson: JsonElement?,
): Node.TokenNode {
    check(removedTokensJson != null) {
        "Removed token '${property.name}' requires removedTokensData for this token set."
    }
    val fallbackValue = removedTokensJson.jsonObject[property.name]
        ?.jsonPrimitive
        ?.content
        ?: error("No fallback entry for removed token '${property.name}' in removed-tokens.json.")
    return Node.TokenNode(property, fallbackValue)
}
