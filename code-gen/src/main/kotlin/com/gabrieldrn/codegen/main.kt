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

package com.gabrieldrn.codegen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.gabrieldrn.codegen.color.abstractThemeDoc
import com.gabrieldrn.codegen.color.deserializeColorTokens
import com.gabrieldrn.codegen.color.g100ThemeDoc
import com.gabrieldrn.codegen.color.g10ThemeDoc
import com.gabrieldrn.codegen.color.g90ThemeDoc
import com.gabrieldrn.codegen.color.model.colortokens.Theme
import com.gabrieldrn.codegen.color.whiteThemeDoc
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.typeNameOf
import java.nio.file.Paths
import java.util.Locale
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

private const val PACKAGE_ROOT = "com.gabrieldrn.carbon.foundation.color"

private val sourcePath = Paths.get("carbon/src/commonMain/kotlin")

private val composeColorMemberName = MemberName(
    "androidx.compose.ui.graphics",
    "Color"
)

//private val sourcePath = Paths.get("build/generated/kotlin")
private val themeAbstractionName = ClassName(PACKAGE_ROOT, "Theme")
private const val codeIndent = "    "
private val generatedCodeMessage =
    """
        ----------------------------------
        /!\ Generated code. Do not modify.
        ----------------------------------
    """.trimIndent()

private const val componentInterfaceKdoc = "Color tokens for the %s component."
private const val componentImplementationKdoc = "Color tokens for the %s component in the %s theme."

private val dataClassesSuppressedIssuesAnnotation =
    AnnotationSpec.builder(Suppress::class)
        .addMember("%S", "UndocumentedPublicProperty")
        .build()

private val topLevelPropertiesSuppressedIssuesAnnotation =
    AnnotationSpec.builder(Suppress::class)
        .addMember("%S, %S", "TopLevelPropertyNaming", "TrailingWhitespace")
        .build()

private fun String.capitalize() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
}

typealias ComponentName = String
typealias ThemeName = String

fun main() {
    // Load color tokens
    val themes: Map<ThemeName, Theme> = deserializeColorTokens()
        .mapKeys { (name, _) ->
            if (name.startsWith('g')) name.replaceFirstChar { "gray" } else name
        }

    val componentsNames: List<ComponentName> = Theme::class.memberProperties
        .filterNot { it.returnType.jvmErasure == String::class }
        .map { it.name }

    val componentsDataClasses = generateComponentsDataClasses(componentsNames)

    val themeAbstractionSpec = generateThemeDataClass(componentsDataClasses.values.toList())

    val componentsThemesInstances: Map<ThemeName, Map<ComponentName, ClassName>> =
        themes.entries.associate { (name, theme) ->
            name to generateComponentsThemesTopLevelProperties(
                name.capitalize(),
                theme,
                componentsDataClasses
            )
        }

    themes.forEach { (name, theme) ->
        val components = componentsThemesInstances[name]!!

        generateThemeTopLevelProperty(
            themeAbstractionSpec,
            name,
            theme,
            components,
        )
    }
}

private fun generateComponentsDataClasses(
    components: List<ComponentName>
): Map<String, ClassName> {

    val themeMembers = Theme::class.memberProperties.map { it.returnType.jvmErasure }

    return components.associateWith { component ->
        val componentType = themeMembers
            .first { it.simpleName.equals(component, ignoreCase = true) }

        val componentName = componentType.simpleName!!

        val componentProperties = componentType.declaredMemberProperties
            .filter { it.returnType.jvmErasure == String::class }
            .map { token ->
                PropertySpec.builder(token.name, Color::class)
                    .initializer(token.name)
                    .build()
            }

        val constructor = FunSpec.constructorBuilder()
            .addParameters(
                componentProperties.map {
                    ParameterSpec.builder(it.name, it.type).build()
                }
            )
            .build()

        ClassName(
            "$PACKAGE_ROOT." +
                componentName
                    .substringBefore("Colors")
                    .replaceFirstChar { it.lowercase() },
            componentName
        ).apply {
            val componentSpec = TypeSpec.classBuilder(this)
                .primaryConstructor(constructor)
                .addKdoc(
                    componentInterfaceKdoc.format(
                        componentName.substringBefore("Colors")
                    )
                )
                .addModifiers(KModifier.DATA)
                .addAnnotation(Immutable::class)
                .addAnnotation(dataClassesSuppressedIssuesAnnotation)
                .addProperties(componentProperties)
                .build()

            FileSpec.builder(this)
                .indent(codeIndent)
                .addFileComment(generatedCodeMessage)
                .addType(componentSpec)
                .build()
                .writeTo(sourcePath)

            println("$simpleName data class generated.")
        }
    }
}

/**
 * Generates the data class representing the theme abstraction.
 */
private fun generateThemeDataClass(componentsDataClasses: List<ClassName>): TypeSpec {
    val coreTokens = Theme::class.memberProperties
        .filter { it.returnType == String::class.createType() }
        .map { token ->
            PropertySpec.builder(token.name, Color::class)
                .initializer(token.name)
                .build()
        }

    val components = componentsDataClasses
        .associateBy {
            it.simpleName.replaceFirstChar { c -> c.lowercase() }
        }
        .map { (name, className) ->
            PropertySpec.builder(name, className)
                .initializer(name)
                .build()
        }

    val constructor = FunSpec.constructorBuilder()
        .addParameters(
            (coreTokens + components).map {
                ParameterSpec.builder(it.name, it.type).build()
            }
        )
        .build()

    val themeSpec = TypeSpec.classBuilder(themeAbstractionName)
        .primaryConstructor(constructor)
        .addKdoc(abstractThemeDoc)
        .addModifiers(KModifier.DATA)
        .addAnnotation(Immutable::class)
        .addAnnotation(dataClassesSuppressedIssuesAnnotation)
        .addProperties(coreTokens)
        .addProperties(components)
        .build()

    FileSpec.builder(PACKAGE_ROOT, themeAbstractionName.simpleName)
        .indent(codeIndent)
        .addFileComment(generatedCodeMessage)
        .addType(themeSpec)
        .build()
        .writeTo(sourcePath)

    println("Theme data class generated.")

    return themeSpec
}

/**
 * Generates the top-level properties instantiating the colors of each component for the given
 * theme.
 */
private fun generateComponentsThemesTopLevelProperties(
    themeName: String,
    theme: Theme,
    componentsAbstractions: Map<String, ClassName>
): Map<ComponentName, ClassName> {

    val implementations = mutableMapOf<String, ClassName>()

    val componentsNames = componentsAbstractions.keys

    Theme::class.memberProperties
        .filter { it.name in componentsNames }
        .forEach { component ->
            val componentName: ComponentName = component.name
            val componentType = component.returnType.jvmErasure
            val componentTokensInstance = component.getter.call(theme)
            val componentSpec =
                componentsAbstractions[componentName] ?: error("Component not found.")

            val componentInterfaceName = ClassName(
                "$PACKAGE_ROOT." +
                    componentName
                        .substringBefore("Colors")
                        .replaceFirstChar { it.lowercase() },
                themeName + componentName.capitalize()
            )

            val componentProperties = componentType.declaredMemberProperties
                .filter { it.returnType.jvmErasure == String::class }
                .map { token ->
                    val color = token.getter.call(componentTokensInstance) as String
                    val colorValue = "0x%s".format(color.removePrefix("#"))
                    CodeBlock.builder()
                        .add("${token.name} = %M($colorValue)", composeColorMemberName)
                        .build()
                }

            val themedComponentSpec = PropertySpec
                .builder(componentInterfaceName.simpleName, componentSpec, KModifier.PUBLIC)
                .addKdoc(
                    componentImplementationKdoc.format(
                        componentName.substringBefore("Colors").capitalize(), themeName
                    )
                )
                .initializer(
                    buildCodeBlock {
                        add("\n%T(\n", componentSpec)
                        // Weird behavior with the indentation
                        indent()
                        indent()
                        componentProperties.forEachIndexed { index, property ->
                            add(property)
                            if (index < componentProperties.size - 1) add(",")
                            add("\n")
                        }
                        unindent()
                        unindent()
                        add(")")
                    }
                )
                .build()

            FileSpec.builder(componentInterfaceName.packageName, componentInterfaceName.simpleName)
                .addFileComment(generatedCodeMessage)
                .addProperty(themedComponentSpec)
                .addAnnotation(topLevelPropertiesSuppressedIssuesAnnotation)
                .build()
                .writeTo(sourcePath)

            println("${componentInterfaceName.simpleName} top level property generated.")

            implementations[componentName] = componentInterfaceName
        }

    return implementations
}

/**
 * Generates the top-level property instantiating the colors of the given theme.
 */
private fun generateThemeTopLevelProperty(
    abstractionSpec: TypeSpec,
    name: ThemeName,
    tokens: Theme,
    componentsImplementations: Map<ComponentName, ClassName>
) {
    val themeMemberProperties = Theme::class.memberProperties
        .associateBy { it.name }

    val colorTypeName = typeNameOf<Color>()

    val coreTokens = abstractionSpec.propertySpecs
        .filter { it.type == colorTypeName }
        .map { token ->
            val color = themeMemberProperties[token.name]!!.getter.call(tokens) as String
            val colorValue = "0x%s".format(color.removePrefix("#"))
            CodeBlock.builder()
                .add("${token.name} = %M($colorValue)", composeColorMemberName)
                .build()
        }

    val components = componentsImplementations.map { (name, implementation) ->
        CodeBlock.builder()
            .add("$name = %T", implementation)
            .build()
    }

    val initializer = buildCodeBlock {
        add("\n%T(\n", themeAbstractionName)
        // Weird behavior with the indentation
        indent()
        indent()
        (coreTokens + components).let { blocks ->
            blocks.forEachIndexed { index, property ->
                add(property)
                if (index < blocks.size - 1) add(",")
                add("\n")
            }
        }
        unindent()
        unindent()
        add(")")
    }

    val themeSpec = PropertySpec
        .builder(
            name.capitalize() + themeAbstractionName.simpleName,
            themeAbstractionName,
            KModifier.PUBLIC
        )
        .apply {
            when {
                name.startsWith("white") -> whiteThemeDoc
                name.startsWith("gray10") -> g10ThemeDoc
                name.startsWith("gray90") -> g90ThemeDoc
                name.startsWith("gray100") -> g100ThemeDoc
                else -> null
            }?.let(::addKdoc)
        }
        .initializer(initializer)
        .build()

    FileSpec.builder(PACKAGE_ROOT, name.capitalize() + themeAbstractionName.simpleName)
        .addFileComment(generatedCodeMessage)
        .addProperty(themeSpec)
        .addAnnotation(topLevelPropertiesSuppressedIssuesAnnotation)
        .build()
        .writeTo(sourcePath)

    println("${name.capitalize()} theme top level property generated.")
}
