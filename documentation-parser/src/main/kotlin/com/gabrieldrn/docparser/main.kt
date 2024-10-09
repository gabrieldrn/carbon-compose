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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.gabrieldrn.docparser.color.TokenProperty
import com.gabrieldrn.docparser.color.abstractThemeDoc
import com.gabrieldrn.docparser.color.associateColorTokensWithThemes
import com.gabrieldrn.docparser.color.deserializeColorTokens
import com.gabrieldrn.docparser.color.model.colortokens.ColorTokens
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.nio.file.Paths

fun main() {
    // Load color tokens
    val tokens: ColorTokens = deserializeColorTokens()

    // Associate each token to its theme
    val themesTokens: Map<String, MutableList<TokenProperty>> =
        associateColorTokensWithThemes(tokens)

    // Generate theme classes

    val packageStructure = "com.gabrieldrn.carbon.foundation.color"
    val themeAbstractionName = "Theme"
    val themeAbstraction = TypeSpec.classBuilder(themeAbstractionName)
        .addKdoc(abstractThemeDoc)
        .addModifiers(KModifier.ABSTRACT)
        .addAnnotation(Immutable::class)
        .apply {
            // tokens
            themesTokens.entries.first().value
                .map { token ->
                    PropertySpec.builder(token.name, Color::class)
                        .addKdoc(token.desc)
                        .addModifiers(KModifier.ABSTRACT)
                        .build()
                }
                .let(::addProperties)
        }
        .build()

    FileSpec.builder(packageStructure, themeAbstractionName)
        .addType(themeAbstraction)
        .build()
        .writeTo(Paths.get("carbon/src/commonMain/kotlin"))
}
