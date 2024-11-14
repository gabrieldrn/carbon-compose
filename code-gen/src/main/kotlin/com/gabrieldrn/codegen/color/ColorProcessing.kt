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

package com.gabrieldrn.codegen.color

import com.gabrieldrn.codegen.color.model.colortokens.ColorDefinition
import com.gabrieldrn.codegen.color.model.colortokens.ColorToken
import com.gabrieldrn.codegen.color.model.colortokens.ColorTokens
import com.gabrieldrn.codegen.color.model.colortokens2.Theme
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.util.Locale
import kotlin.reflect.full.memberProperties

const val file_path_format = "/%s.json"

private val themes = listOf(
    "white",
    "g10",
    "g90",
    "g100",
)

data class TokenProperty(val name: String, val desc: String, val color: String)

@OptIn(ExperimentalSerializationApi::class)
fun deserializeColorTokens(): Map<String, Theme> = themes.associateWith { theme ->
    object {}::class.java
        .getResourceAsStream(file_path_format.format(theme))
        .use { stream ->
            try {
                Json.decodeFromStream<Theme>(stream)
            } catch (e: Exception) {
                error("Could not load theme $theme." + e.message)
            }
        }
}

/**
 * Transforms the color tokens into a map associating them to their respective themes.
 */
fun associateColorTokensWithThemes(tokens: ColorTokens): Map<String, MutableList<TokenProperty>> {
    val themes =
        ColorToken::class.memberProperties.map { it.name }

    val themesTokens: Map<String, MutableList<TokenProperty>> =
        themes.associateWith { mutableListOf() }

    ColorTokens::class
        .memberProperties
        .forEach { prop ->
            val colorDefinitionCollection = prop.getter.call(tokens)!!
            colorDefinitionCollection::class.memberProperties.forEach { colorDefinition ->
                val colorDefinitionValue = colorDefinition
                    .getter
                    .call(colorDefinitionCollection) as ColorDefinition

                colorDefinitionValue.value::class.memberProperties.forEach { colorToken ->
                    val themeName = colorToken.name
                    val color = (
                        colorToken
                            .getter
                            .call(colorDefinitionValue.value) as ColorToken.TokenValue
                        )
                        .color

                    themesTokens[themeName]!!.add(
                        TokenProperty(
                            colorDefinition.name,
                            colorDefinitionValue
                                .role
                                .joinToString("\n") { "$it." }
                                .formatCodeReferences(),
                            color
                        )
                    )
                }
            }
        }

    themesTokens
        .map { it.key + "\n" + it.value.joinToString("\n") { e -> "\t${e.name} -> ${e.color}" } }

    return themesTokens
}

private fun String.formatCodeReferences() =
    replace(Regex("\\\$([A-Z]|[a-z]|[0-9]|-)*")) { result ->
        result.value
            .removePrefix("$")
            // transform string from kebab-case to camelCase
            .split("-")
            .let {
                it.first().plus(
                    it
                        .drop(1)
                        .joinToString(separator = "") { part ->
                            part.replaceFirstChar { c -> c.titlecase(Locale.getDefault()) }
                        }
                )
            }
            .let { "[$it]" }
    }
