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

import com.gabrieldrn.docparser.color.TokenProperty
import com.gabrieldrn.docparser.color.associateColorTokensWithThemes
import com.gabrieldrn.docparser.color.deserializeColorTokens
import com.gabrieldrn.docparser.color.generateThemeImplementations
import com.gabrieldrn.docparser.color.model.colortokens.ColorTokens

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    // Load color tokens
    val tokens: ColorTokens = deserializeColorTokens()

    // Associate each token to its theme
    val themesTokens: Map<String, MutableList<TokenProperty>> =
        associateColorTokensWithThemes(tokens)

    // Generate theme classes
    generateThemeImplementations(themesTokens)
}
