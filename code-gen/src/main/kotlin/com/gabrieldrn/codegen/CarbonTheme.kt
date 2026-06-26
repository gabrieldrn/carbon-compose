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

/**
 * One of Carbon's color themes. [name] is reused verbatim as the prefix of every generated
 * top-level property (e.g. [Gray10] generates `Gray10Theme`, `Gray10ButtonColors`, ...).
 *
 * @param colorTokensFilePath Path of the theme's color tokens JSON resource.
 * @param removedTokensFilePath Path of the theme's removed-tokens fallback JSON resource, if any
 *   model property is annotated with `@RemovedToken`. `null` until the first token is removed.
 */
enum class CarbonTheme(
    val colorTokensFilePath: String,
    val removedTokensFilePath: String? = null,
) {
    White("/white.json"),
    Gray10("/g10.json"),
    Gray90("/g90.json"),
    Gray100("/g100.json"),
}
