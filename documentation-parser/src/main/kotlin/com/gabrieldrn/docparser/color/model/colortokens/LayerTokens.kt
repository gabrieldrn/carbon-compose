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

package com.gabrieldrn.docparser.color.model.colortokens

import kotlinx.serialization.Serializable

@Serializable
data class LayerTokens(
    val layer01: ColorDefinition,
    val layer02: ColorDefinition,
    val layer03: ColorDefinition,
    val layerActive01: ColorDefinition,
    val layerActive02: ColorDefinition,
    val layerActive03: ColorDefinition,
    val layerHover01: ColorDefinition,
    val layerHover02: ColorDefinition,
    val layerHover03: ColorDefinition,
    val layerSelected01: ColorDefinition,
    val layerSelected02: ColorDefinition,
    val layerSelected03: ColorDefinition,
    val layerSelectedDisabled: ColorDefinition,
    val layerSelectedHover01: ColorDefinition,
    val layerSelectedHover02: ColorDefinition,
    val layerSelectedHover03: ColorDefinition,
    val layerSelectedInverse: ColorDefinition
)
