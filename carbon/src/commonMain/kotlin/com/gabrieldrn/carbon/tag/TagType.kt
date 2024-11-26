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

package com.gabrieldrn.carbon.tag

import androidx.compose.runtime.Immutable
import com.gabrieldrn.carbon.foundation.color.Theme

/**
 * Colors that can be applied to tags.
 */
@Immutable
@Suppress("UndocumentedPublicProperty")
public enum class TagType {

    Gray,
    CoolGray,
    WarmGray,
    Red,
    Magenta,
    Purple,
    Blue,
    Cyan,
    Teal,
    Green,
    HighContrast,
    Outline;

    internal fun backgroundColor(theme: Theme) = when (this) {
        Gray -> theme.tagColors.tagBackgroundGray
        CoolGray -> theme.tagColors.tagBackgroundCoolGray
        WarmGray -> theme.tagColors.tagBackgroundWarmGray
        Red -> theme.tagColors.tagBackgroundRed
        Magenta -> theme.tagColors.tagBackgroundMagenta
        Purple -> theme.tagColors.tagBackgroundPurple
        Blue -> theme.tagColors.tagBackgroundBlue
        Cyan -> theme.tagColors.tagBackgroundCyan
        Teal -> theme.tagColors.tagBackgroundTeal
        Green -> theme.tagColors.tagBackgroundGreen
        HighContrast -> theme.backgroundInverse
        Outline -> theme.background
    }

    internal fun contentColor(theme: Theme) = when (this) {
        Gray -> theme.tagColors.tagColorGray
        CoolGray -> theme.tagColors.tagColorCoolGray
        WarmGray -> theme.tagColors.tagColorWarmGray
        Red -> theme.tagColors.tagColorRed
        Magenta -> theme.tagColors.tagColorMagenta
        Purple -> theme.tagColors.tagColorPurple
        Blue -> theme.tagColors.tagColorBlue
        Cyan -> theme.tagColors.tagColorCyan
        Teal -> theme.tagColors.tagColorTeal
        Green -> theme.tagColors.tagColorGreen
        HighContrast -> theme.textInverse
        Outline -> theme.textPrimary
    }

    internal fun hoverColor(theme: Theme) = when (this) {
        Gray -> theme.tagColors.tagHoverGray
        CoolGray -> theme.tagColors.tagHoverCoolGray
        WarmGray -> theme.tagColors.tagHoverWarmGray
        Red -> theme.tagColors.tagHoverRed
        Magenta -> theme.tagColors.tagHoverMagenta
        Purple -> theme.tagColors.tagHoverPurple
        Blue -> theme.tagColors.tagHoverBlue
        Cyan -> theme.tagColors.tagHoverCyan
        Teal -> theme.tagColors.tagHoverTeal
        Green -> theme.tagColors.tagHoverGreen
        HighContrast -> theme.backgroundHover
        Outline -> theme.backgroundHover
    }

    internal fun borderColor(theme: Theme) = when (this) {
        Gray -> theme.tagColors.tagBorderGray
        CoolGray -> theme.tagColors.tagBorderCoolGray
        WarmGray -> theme.tagColors.tagBorderWarmGray
        Red -> theme.tagColors.tagBorderRed
        Magenta -> theme.tagColors.tagBorderMagenta
        Purple -> theme.tagColors.tagBorderPurple
        Blue -> theme.tagColors.tagBorderBlue
        Cyan -> theme.tagColors.tagBorderCyan
        Teal -> theme.tagColors.tagBorderTeal
        Green -> theme.tagColors.tagBorderGreen
        HighContrast -> theme.borderInverse
        Outline -> theme.borderInverse
    }
}
