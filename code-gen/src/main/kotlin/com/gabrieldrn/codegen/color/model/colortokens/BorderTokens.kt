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

package com.gabrieldrn.codegen.color.model.colortokens

import kotlinx.serialization.Serializable

@Serializable
data class BorderTokens(
    val borderDisabled: ColorDefinition,
    val borderInteractive: ColorDefinition,
    val borderInverse: ColorDefinition,
    val borderStrong01: ColorDefinition,
    val borderStrong02: ColorDefinition,
    val borderStrong03: ColorDefinition,
    val borderSubtle00: ColorDefinition,
    val borderSubtle01: ColorDefinition,
    val borderSubtle02: ColorDefinition,
    val borderSubtle03: ColorDefinition,
    val borderSubtleSelected01: ColorDefinition,
    val borderSubtleSelected02: ColorDefinition,
    val borderSubtleSelected03: ColorDefinition,
    val borderTile01: ColorDefinition,
    val borderTile02: ColorDefinition,
    val borderTile03: ColorDefinition
)
