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
data class TagColors(
    val tagBackgroundBlue: String,
    val tagBackgroundCoolGray: String,
    val tagBackgroundCyan: String,
    val tagBackgroundGray: String,
    val tagBackgroundGreen: String,
    val tagBackgroundMagenta: String,
    val tagBackgroundPurple: String,
    val tagBackgroundRed: String,
    val tagBackgroundTeal: String,
    val tagBackgroundWarmGray: String,
    val tagBorderBlue: String,
    val tagBorderCoolGray: String,
    val tagBorderCyan: String,
    val tagBorderGray: String,
    val tagBorderGreen: String,
    val tagBorderMagenta: String,
    val tagBorderPurple: String,
    val tagBorderRed: String,
    val tagBorderTeal: String,
    val tagBorderWarmGray: String,
    val tagColorBlue: String,
    val tagColorCoolGray: String,
    val tagColorCyan: String,
    val tagColorGray: String,
    val tagColorGreen: String,
    val tagColorMagenta: String,
    val tagColorPurple: String,
    val tagColorRed: String,
    val tagColorTeal: String,
    val tagColorWarmGray: String,
    val tagHoverBlue: String,
    val tagHoverCoolGray: String,
    val tagHoverCyan: String,
    val tagHoverGray: String,
    val tagHoverGreen: String,
    val tagHoverMagenta: String,
    val tagHoverPurple: String,
    val tagHoverRed: String,
    val tagHoverTeal: String,
    val tagHoverWarmGray: String
)