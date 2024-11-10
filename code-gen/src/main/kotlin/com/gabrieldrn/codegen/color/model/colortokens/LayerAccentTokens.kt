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
data class LayerAccentTokens(
    val layerAccent01: ColorDefinition,
    val layerAccent02: ColorDefinition,
    val layerAccent03: ColorDefinition,
    val layerAccentActive01: ColorDefinition,
    val layerAccentActive02: ColorDefinition,
    val layerAccentActive03: ColorDefinition,
    val layerAccentHover01: ColorDefinition,
    val layerAccentHover02: ColorDefinition,
    val layerAccentHover03: ColorDefinition
)
