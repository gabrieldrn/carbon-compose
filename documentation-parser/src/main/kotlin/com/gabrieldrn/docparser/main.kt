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

package com.gabrieldrn.docparser

import androidx.compose.ui.graphics.Color
import com.gabrieldrn.docparser.model.colortokens.ColorDefinition
import com.gabrieldrn.docparser.model.colortokens.ColorToken
import com.gabrieldrn.docparser.model.colortokens.ColorTokens
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlin.reflect.full.memberProperties

@OptIn(ExperimentalSerializationApi::class)
fun main() {
    val tokens = object {}::class.java.getResourceAsStream("/color-tokens.json")
        ?.use { stream -> Json.decodeFromStream<ColorTokens>(stream) }
        ?: error("Could not load color-tokens.json")

    // Associate each token to its theme

    val themes = ColorToken::class.memberProperties.map { it.name }
    val themesTokens = themes.associateWith { mutableMapOf<String, Color>() }

    ColorTokens::class
        .memberProperties
        .forEach { prop ->
            val colorDefinitionCollection = prop.getter.call(tokens)!!
            colorDefinitionCollection::class.memberProperties.forEach { colorDefinition ->
                val colorDefinitionValue = colorDefinition
                    .getter
                    .call(colorDefinitionCollection) as ColorDefinition

                colorDefinitionValue.value::class.memberProperties.forEach { colorToken ->
                    val name = colorToken.name
                    val color = (
                        colorToken
                            .getter
                            .call(colorDefinitionValue.value) as ColorToken.TokenValue
                        )
                        .color

                    themesTokens[name]!![colorDefinition.name] = color
                }
            }
        }

    println("Association of tokens to themes result:")
    themesTokens
        .map { it.key + "\n" + it.value.entries.joinToString("\n") { e -> "\t$e" } }
        .forEach(::println)
}
