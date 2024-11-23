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

package com.gabrieldrn.themesmodel.model

import kotlinx.serialization.Serializable

@Serializable
public data class AiColors(
    val aiAuraEnd: String,
    val aiAuraHoverBackground: String,
    val aiAuraHoverEnd: String,
    val aiAuraHoverStart: String,
    val aiAuraStart: String,
    val aiAuraStartSm: String,
    val aiBorderEnd: String,
    val aiBorderStart: String,
    val aiBorderStrong: String,
    val aiDropShadow: String,
    val aiInnerShadow: String,
    val aiOverlay: String,
    val aiPopoverBackground: String,
    val aiPopoverCaretBottom: String,
    val aiPopoverCaretBottomBackground: String,
    val aiPopoverCaretBottomBackgroundActions: String,
    val aiPopoverCaretCenter: String,
    val aiPopoverShadowOuter01: String,
    val aiPopoverShadowOuter02: String,
    val aiSkeletonBackground: String,
    val aiSkeletonElementBackground: String
)