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

package com.gabrieldrn.docparser.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import java.nio.file.Paths
import java.util.Locale

private const val PACKAGE_ROOT = "com.gabrieldrn.carbon.foundation.color"

private val sourcePath = Paths.get("carbon/src/commonMain/kotlin")

//private val sourcePath = Paths.get("build/generated/kotlin")
private val layerClass = ClassName(PACKAGE_ROOT, "Layer")
private val themeAbstractionName = ClassName(PACKAGE_ROOT, "Theme")
private val codeIndent = "    "
private val generatedCodeMessage =
    """
        ----------------------------------
        /!\ Generated code. Do not modify.
        ----------------------------------
    """.trimIndent()


enum class Component {
    AI,
    TAG,
    CHAT,
    NOTIFICATION,
    CORE
}

fun generateThemeImplementations(themesTokens: Map<String, MutableList<TokenProperty>>) {

    //  Theme ------v
    val tokens: Map<String, Map<Component, MutableList<TokenProperty>>> =
        themesTokens.mapValues { (_, tokens) ->
            val map: Map<Component, MutableList<TokenProperty>> =
                Component.entries.associateWith { mutableListOf() }

            tokens.forEach { token ->
                val component = when {
                    token.name.startsWith("tag") -> Component.TAG
                    token.name.startsWith("ai") -> Component.AI
                    token.name.startsWith("chat") -> Component.CHAT
                    token.name.startsWith("notification") -> Component.NOTIFICATION
                    else -> Component.CORE
                }

                map[component]!!.add(token)
            }

            map
        }

    // Generate components abstractions
    val componentAbstractions = mutableMapOf<Component, ClassName>()

    tokens.values
        .distinctBy { it.keys }
        .forEach { componentsWithTokens ->
            componentsWithTokens
                .filterNot { it.key == Component.CORE }
                .mapValues {
                    generateComponentAbstraction(
                        component = it.key,
                        tokens = it.value
                    )
                }
                .let { componentAbstractions.putAll(it) }
        }

    // Generate components implementations
    val componentsImplementations = mutableMapOf<String, MutableMap<Component, ClassName>>()

    tokens.forEach { (theme, components) ->
        components
            .filterNot { it.key == Component.CORE }
            .forEach { (component, tokens) ->
                val implementation = generateComponentImplementation(
                    theme = theme,
                    component = component,
                    abstraction = componentAbstractions[component]!!,
                    tokens = tokens
                )

                componentsImplementations
                    .getOrPut(theme) { mutableMapOf() }[component] = implementation
            }
    }


    // Generate theme abstraction
    generateThemeAbstraction(
        tokenProperties = tokens.values.first()[Component.CORE]!!,
        componentsAbstractions = componentAbstractions
    )

    tokens.forEach { (theme, componentTokens) ->
        generateThemeImplementation(
            theme = theme,
            tokenProperties = componentTokens[Component.CORE]!!,
            componentsAbstractions = componentAbstractions,
            componentsImplementations = componentsImplementations[theme]!!
        )
    }
}

private fun generateComponentAbstraction(
    component: Component,
    tokens: List<TokenProperty>
): ClassName {
    val componentName = component.name.lowercase()

    val formattedComponentName = componentName
        .replaceFirstChar { it.titlecase(Locale.getDefault()) }

    val packagePath = "$PACKAGE_ROOT.$componentName"

    val interfaceName = ClassName(packagePath, "${formattedComponentName}Colors")

    val interfaceSpec = TypeSpec.interfaceBuilder(interfaceName)
        .addKdoc("Color tokens for the $formattedComponentName component.")
        .addAnnotation(Immutable::class)
        .apply {
            tokens
                .map { token ->
                    PropertySpec.builder(token.getPropertyName(componentName), Color::class)
                        .addKdoc(token.desc)
                        .build()
                }
                .let(::addProperties)
        }
        .build()

    FileSpec.builder(interfaceName)
        .indent(codeIndent)
        .addFileComment(generatedCodeMessage)
        .addType(interfaceSpec)
        .build()
        .writeTo(sourcePath)

    println("${interfaceName.simpleName} interface generated.")

    return interfaceName
}

private fun generateComponentImplementation(
    theme: String,
    component: Component,
    abstraction: ClassName,
    tokens: List<TokenProperty>
): ClassName {
    val componentName = component.name.lowercase()

    val formattedThemeName = getThemeName(theme)

    val formattedComponentName = componentName
        .replaceFirstChar { it.titlecase() }

    val packagePath = "$PACKAGE_ROOT.$componentName"

    val implementationName = ClassName(
        packagePath,
        "${formattedThemeName}${formattedComponentName}Colors"
    )

    val classSpec = TypeSpec.objectBuilder(implementationName)
        .addSuperinterface(abstraction)
        .addKdoc(
            "Color tokens for the $formattedComponentName component " +
                "in the $formattedThemeName theme."
        )
        .addAnnotation(Immutable::class)
        .apply {
            tokens
                .map { token ->
                    PropertySpec.builder(token.getPropertyName(componentName), Color::class)
                        .addModifiers(KModifier.OVERRIDE)
                        .initializer(
                            token.color
                                .replace("#", "0x")
                                .let { "Color($it)" }
                        )
                        .build()
                }
                .let(::addProperties)
        }
        .build()

    FileSpec.builder(implementationName.packageName, implementationName.simpleName)
        .indent(codeIndent)
        .addFileComment(generatedCodeMessage)
        .addType(classSpec)
        .build()
        .writeTo(sourcePath)

    println("${implementationName.simpleName} generated.")

    return implementationName
}

/**
 * Generates the base theme abstract class.
 */
private fun generateThemeAbstraction(
    tokenProperties: List<TokenProperty>,
    componentsAbstractions: Map<Component, ClassName>
) {
    val tokenPropertiesSpecs = tokenProperties
        .map { token ->
            PropertySpec.builder(token.name, Color::class)
                .addKdoc(token.desc)
                .addModifiers(KModifier.ABSTRACT)
                .build()
        }

    val componentsPropertySpecs = componentsAbstractions
        .map { (component, abstraction) ->
            val componentName = component.name.lowercase().replaceFirstChar { it.titlecase() }
            PropertySpec
                .builder(
                    abstraction.simpleName.replaceFirstChar { it.lowercase() },
                    abstraction
                )
                .addKdoc("Color tokens for the $componentName component.")
                .addModifiers(KModifier.ABSTRACT)
                .build()
        }

    val containerColorFuncSpec = FunSpec.builder("containerColor")
        .addKdoc(containerColorMemberDoc)
        .returns(Color::class)
        .addParameter(
            ParameterSpec.builder("layer", layerClass)
                .defaultValue("Layer.Layer00")
                .build()
        )
        .addStatement(
            """
                return when (layer) {
                  Layer.Layer00 -> background
                  Layer.Layer01 -> layer01
                  Layer.Layer02 -> layer02
                  Layer.Layer03 -> layer03
                }
            """.trimIndent()
        )
        .build()

    val copyAnonymousClass = TypeSpec.anonymousClassBuilder()
        .superclass(themeAbstractionName)
        .apply {
            tokenProperties.forEach { token ->
                addProperty(
                    PropertySpec.builder(token.name, Color::class)
                        .initializer(token.name)
                        .addModifiers(KModifier.OVERRIDE)
                        .build()
                )
            }
            componentsPropertySpecs.forEach { component ->
                addProperty(
                    PropertySpec.builder(component.name, component.type)
                        .initializer(component.name)
                        .addModifiers(KModifier.OVERRIDE)
                        .build()
                )
            }
        }
        .build()

    val copyThemeFuncSpec = FunSpec.builder("copy")
        .returns(themeAbstractionName)
        .addModifiers(KModifier.INTERNAL)
        .addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "LongMethod")
                .build()
        )
        .addParameters(
            (tokenProperties + componentsPropertySpecs).mapIndexed { index, spec ->
                if (spec is TokenProperty) {
                    ParameterSpec.builder(spec.name, Color::class)
                        .defaultValue("this.${spec.name}")
                        .build()
                } else if (spec is PropertySpec) {
                    ParameterSpec.builder(spec.name, spec.type)
                        .defaultValue("this.${spec.name}")
                        .build()
                } else {
                    error("Type ${spec::class.simpleName} at index $index unrecognized.")
                }
            }
        )
        .addStatement("return %L", copyAnonymousClass)
        .build()

    val equalsFunSpec = FunSpec.builder("equals")
        .addModifiers(KModifier.OVERRIDE)
        .addParameter("other", Any::class.asTypeName().copy(nullable = true))
        .returns(Boolean::class)
        .addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "CognitiveComplexMethod")
                .addMember("%S", "CyclomaticComplexMethod")
                .addMember("%S", "LongMethod")
                .build()
        )
        .addStatement("if (this === other) return true")
        .addStatement("if (other !is %T) return false", themeAbstractionName)
        .apply {
            (tokenProperties + componentsPropertySpecs).forEachIndexed { index, spec ->
                val name = (spec as? PropertySpec)?.name
                    ?: (spec as? TokenProperty)?.name
                    ?: error("Type ${spec::class.simpleName} at index $index unrecognized.")

                addStatement("if (%N != other.%N) return false", name, name)
            }
        }
        .addStatement("return true")
        .build()

    val hashCodeFunSpec = FunSpec.builder("hashCode")
        .addModifiers(KModifier.OVERRIDE)
        .returns(Int::class)
        .addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "LongMethod")
                .build()
        )
        .apply {
            (tokenProperties + componentsPropertySpecs).forEachIndexed { index, spec ->
                val name = (spec as? PropertySpec)?.name
                    ?: (spec as? TokenProperty)?.name
                    ?: error("Type ${spec::class.simpleName} at index $index unrecognized.")

                if (index == 0) {
                    addStatement("var result = %N.hashCode()", name)
                } else {
                    addStatement("result = 31 * result + %N.hashCode()", name)
                }
            }
        }
        .addStatement("return result")
        .build()

    val themeAbstraction = TypeSpec.classBuilder(themeAbstractionName)
        .addKdoc(abstractThemeDoc)
        .addModifiers(KModifier.ABSTRACT)
        .addAnnotation(Immutable::class)
        .addProperties(tokenPropertiesSpecs)
        .addProperties(componentsPropertySpecs)
        .addFunctions(
            listOf(containerColorFuncSpec, copyThemeFuncSpec, equalsFunSpec, hashCodeFunSpec)
        )
        .build()

    FileSpec.builder(PACKAGE_ROOT, themeAbstractionName.simpleName)
        .indent(codeIndent)
        .addFileComment(generatedCodeMessage)
        .addType(themeAbstraction)
        .build()
        .writeTo(sourcePath)

    println("Theme interface generated.")
}

/**
 * Generates the implementation of a theme.
 */
private fun generateThemeImplementation(
    theme: String,
    tokenProperties: List<TokenProperty>,
    componentsAbstractions: Map<Component, ClassName>,
    componentsImplementations: Map<Component, ClassName>
) {
    val themeName = getThemeName(theme)
        .plus(themeAbstractionName.simpleName)

    val className = ClassName(PACKAGE_ROOT, themeName)

    val kdoc = when (theme) {
        "g10" -> g10ThemeDoc
        "g90" -> g90ThemeDoc
        "g100" -> g100ThemeDoc
        else -> whiteThemeDoc
    }

    val classSpec = TypeSpec.objectBuilder(className)
        .superclass(themeAbstractionName)
        .addAnnotation(Immutable::class)
        .addKdoc(kdoc)
        .addProperties(
            tokenProperties
                .map { prop ->
                    PropertySpec.builder(prop.name, Color::class)
                        .addModifiers(KModifier.OVERRIDE)
                        .initializer(
                            prop.color
                                .replace("#", "0x")
                                .let { "Color($it)" }
                        )
                        .build()
                }
        )
        .addProperties(
            componentsImplementations
                .map { (component, implementation) ->
                    PropertySpec.builder(
                        name = component.name.lowercase(Locale.getDefault()) + "Colors",
                        type = componentsAbstractions[component]!!
                    )
                        .addModifiers(KModifier.OVERRIDE)
                        .initializer("%T", implementation)
                        .build()
                }
        )
        .build()

    FileSpec.builder(PACKAGE_ROOT, themeName)
        .indent(codeIndent)
        .addFileComment(generatedCodeMessage)
        .addType(classSpec)
        .build()
        .writeTo(sourcePath)

    println("$themeName generated.")
}

fun TokenProperty.getPropertyName(prefix: String = ""): String {
    return this.name
        .removePrefix(prefix)
        .replaceFirstChar { it.lowercase() }
}

private fun getThemeName(theme: String) = theme
    .let {
        if (it.startsWith("g")) it.replaceFirst("g", "gray")
        else it
    }
    .replaceFirstChar { it.titlecase(Locale.getDefault()) }
