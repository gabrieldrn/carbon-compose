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
import java.nio.file.Paths
import java.util.Locale

private const val PACKAGE_ROOT = "com.gabrieldrn.carbon.foundation.color"
private const val PACKAGE_COMPONENT_TAG = "com.gabrieldrn.carbon.foundation.color.tag"

private val sourcePath = Paths.get("carbon/src/commonMain/kotlin")
private val layerClass = ClassName(PACKAGE_ROOT, "Layer")
private val tagColorsAbstractionName = ClassName(PACKAGE_COMPONENT_TAG, "TagColors")
private val themeAbstractionName = ClassName(PACKAGE_ROOT, "Theme")

fun generateThemeImplementations(themesTokens: Map<String, MutableList<TokenProperty>>) {

    val (tagsProperties, tokenProperties) = themesTokens.entries.first().value
        .partition { it.name.startsWith("tag") }

    generateTagColorsAbstraction(
        tagsProperties = tagsProperties,
    )

    generateThemeAbstraction(
        tokenProperties = tokenProperties,
    )

    generateImplementations(
        themesTokens = themesTokens,
    )
}

private fun generateTagColorsAbstraction(
    tagsProperties: List<TokenProperty>
) {
    val tagsPropertiesSpecs = tagsProperties
        .map { token ->
            val name = token.name
                .removePrefix("tag")
                .replaceFirstChar { it.lowercase(Locale.getDefault()) }
            PropertySpec.builder(name, Color::class)
                .addKdoc(token.desc)
                .build()
        }

    val tagColorsAbstraction = TypeSpec.interfaceBuilder(tagColorsAbstractionName)
        .addKdoc("Tag colors for the Carbon Design System.")
        .addProperties(tagsPropertiesSpecs)
        .build()

    FileSpec.builder(PACKAGE_COMPONENT_TAG, "TagColors")
        .addFileComment("Generated code. Do not modify.")
        .addType(tagColorsAbstraction)
        .build()
        .writeTo(sourcePath)

    println("TagColors interface generated.")
}

/**
 * Generates the base theme abstract class.
 */
private fun generateThemeAbstraction(
    tokenProperties: List<TokenProperty>,
) {
    val tokenPropertiesSpecs = tokenProperties
        .map { token ->
            PropertySpec.builder(token.name, Color::class)
                .addKdoc(token.desc)
                .addModifiers(KModifier.ABSTRACT)
                .build()
        }

    val tagColorsPropertySpec = PropertySpec.builder("tagColors", tagColorsAbstractionName)
        .addModifiers(KModifier.ABSTRACT)
        .build()

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

    val copyThemeFuncSpec = FunSpec.builder("copy")
        .returns(themeAbstractionName)
        .addModifiers(KModifier.INTERNAL)
        .addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "LongMethod")
                .build()
        )
        .apply {
            tokenProperties.forEach { token ->
                addParameter(
                    ParameterSpec.builder(token.name, Color::class)
                        .defaultValue("this.${token.name}")
                        .build()
                )
            }
        }
        .addParameter(
            ParameterSpec.builder("tagColors", tagColorsAbstractionName)
                .defaultValue("this.tagColors")
                .build()
        )
        .addStatement(
            buildString {
                appendLine("return object : ${themeAbstractionName.simpleName}() {")
                tokenProperties.forEach { token ->
                    appendLine("  override val ${token.name}: Color = ${token.name}")
                }
                append("  override val tagColors: ${tagColorsAbstractionName.simpleName} = ")
                appendLine(tagColorsPropertySpec.name)
                append("}")
            }
        )
        .build()

    val themeAbstraction = TypeSpec.classBuilder(themeAbstractionName)
        .addKdoc(abstractThemeDoc)
        .addModifiers(KModifier.ABSTRACT)
        .addAnnotation(Immutable::class)
        .addProperties(tokenPropertiesSpecs)
        .addProperty(tagColorsPropertySpec)
        .addFunctions(
            listOf(containerColorFuncSpec, copyThemeFuncSpec)
        )
        .build()

    FileSpec.builder(PACKAGE_ROOT, themeAbstractionName.simpleName)
        .addFileComment("Generated code. Do not modify.")
        .addType(themeAbstraction)
        .build()
        .writeTo(sourcePath)

    println("Theme interface generated.")
}

private fun generateTagColorsImplementation(
    theme: String,
    tagsProperties: List<TokenProperty>
): ClassName {
    val tagColorsImplementationName = ClassName(
        PACKAGE_COMPONENT_TAG,
        "${theme}TagColors"
    )

    val classSpec = TypeSpec.objectBuilder(tagColorsImplementationName)
        .addSuperinterface(tagColorsAbstractionName)
        .addKdoc("Color tokens for the tag component in the $theme theme.")
        .addAnnotation(Immutable::class)
        .apply {
            tagsProperties
                .map { prop ->
                    val propName = prop.name
                        .removePrefix("tag")
                        .replaceFirstChar { it.lowercase(Locale.getDefault()) }

                    PropertySpec.builder(propName, Color::class)
                        .addModifiers(KModifier.OVERRIDE)
                        .initializer(
                            prop.color
                                .replace("#", "0x")
                                .let { "Color($it)" }
                        )
                        .build()
                }
                .let(::addProperties)
        }
        .build()

    FileSpec.builder(PACKAGE_COMPONENT_TAG, tagColorsImplementationName.simpleName)
        .addFileComment("Generated code. Do not modify.")
        .addType(classSpec)
        .build()
        .writeTo(sourcePath)

    println("${tagColorsImplementationName.simpleName} generated.")

    return tagColorsImplementationName
}

/**
 * Generates all themes implementations.
 */
private fun generateImplementations(
    themesTokens: Map<String, List<TokenProperty>>
) {
    themesTokens.keys.forEach { theme ->
        val themeBaseName = getThemeName(theme)
        val themeName = themeBaseName
            .plus(themeAbstractionName.simpleName)

        val themeClassName = ClassName(PACKAGE_ROOT, themeName)

        val kdoc = when (theme) {
            "g10" -> g10ThemeDoc
            "g90" -> g90ThemeDoc
            "g100" -> g100ThemeDoc
            else -> whiteThemeDoc
        }

        val tagColorsClass = generateTagColorsImplementation(
            theme = themeBaseName,
            tagsProperties = themesTokens[theme]!!
                .filter { it.name.startsWith("tag") }
        )

        val classSpec = TypeSpec.objectBuilder(themeClassName)
            .superclass(themeAbstractionName)
            .addAnnotation(Immutable::class)
            .addKdoc(kdoc)
            .apply {
                themesTokens[theme]!!
                    .filterNot { it.name.startsWith("tag") }
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
                    .let(::addProperties)
            }
            .addProperty(
                PropertySpec.builder("tagColors", tagColorsAbstractionName)
                    .addModifiers(KModifier.OVERRIDE)
                    .initializer("%T", tagColorsClass)
                    .build()
            )
            .build()

        FileSpec.builder(PACKAGE_ROOT, themeName)
            .addFileComment("Generated code. Do not modify.")
            .addType(classSpec)
            .build()
            .writeTo(sourcePath)

        println("$themeName generated.")
    }
}

private fun getThemeName(theme: String) = theme
    .let {
        if (it.startsWith("g")) it.replaceFirst("g", "gray")
        else it
    }
    .replaceFirstChar { it.titlecase(Locale.getDefault()) }
