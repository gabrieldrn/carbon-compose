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
data class Tag(
    val tagBackgroundBlue: ColorDefinition,
    val tagBackgroundCoolGray: ColorDefinition,
    val tagBackgroundCyan: ColorDefinition,
    val tagBackgroundGray: ColorDefinition,
    val tagBackgroundGreen: ColorDefinition,
    val tagBackgroundMagenta: ColorDefinition,
    val tagBackgroundPurple: ColorDefinition,
    val tagBackgroundRed: ColorDefinition,
    val tagBackgroundTeal: ColorDefinition,
    val tagBackgroundWarmGray: ColorDefinition,
    val tagBorderBlue: ColorDefinition,
    val tagBorderCoolGray: ColorDefinition,
    val tagBorderCyan: ColorDefinition,
    val tagBorderGray: ColorDefinition,
    val tagBorderGreen: ColorDefinition,
    val tagBorderMagenta: ColorDefinition,
    val tagBorderPurple: ColorDefinition,
    val tagBorderRed: ColorDefinition,
    val tagBorderTeal: ColorDefinition,
    val tagBorderWarmGray: ColorDefinition,
    val tagColorBlue: ColorDefinition,
    val tagColorCoolGray: ColorDefinition,
    val tagColorCyan: ColorDefinition,
    val tagColorGray: ColorDefinition,
    val tagColorGreen: ColorDefinition,
    val tagColorMagenta: ColorDefinition,
    val tagColorPurple: ColorDefinition,
    val tagColorRed: ColorDefinition,
    val tagColorTeal: ColorDefinition,
    val tagColorWarmGray: ColorDefinition,
    val tagHoverBlue: ColorDefinition,
    val tagHoverCoolGray: ColorDefinition,
    val tagHoverCyan: ColorDefinition,
    val tagHoverGray: ColorDefinition,
    val tagHoverGreen: ColorDefinition,
    val tagHoverMagenta: ColorDefinition,
    val tagHoverPurple: ColorDefinition,
    val tagHoverRed: ColorDefinition,
    val tagHoverTeal: ColorDefinition,
    val tagHoverWarmGray: ColorDefinition
)
