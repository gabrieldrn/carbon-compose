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

import com.gabrieldrn.carbon.foundation.color.Theme

/**
 * Colors that can be applied to tags.
 */
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
        Gray -> theme.tagColors.backgroundGray
        CoolGray -> theme.tagColors.backgroundCoolGray
        WarmGray -> theme.tagColors.backgroundWarmGray
        Red -> theme.tagColors.backgroundRed
        Magenta -> theme.tagColors.backgroundMagenta
        Purple -> theme.tagColors.backgroundPurple
        Blue -> theme.tagColors.backgroundBlue
        Cyan -> theme.tagColors.backgroundCyan
        Teal -> theme.tagColors.backgroundTeal
        Green -> theme.tagColors.backgroundGreen
        HighContrast -> theme.backgroundInverse
        Outline -> theme.background
    }

    internal fun contentColor(theme: Theme) = when (this) {
        Gray -> theme.tagColors.colorGray
        CoolGray -> theme.tagColors.colorCoolGray
        WarmGray -> theme.tagColors.colorWarmGray
        Red -> theme.tagColors.colorRed
        Magenta -> theme.tagColors.colorMagenta
        Purple -> theme.tagColors.colorPurple
        Blue -> theme.tagColors.colorBlue
        Cyan -> theme.tagColors.colorCyan
        Teal -> theme.tagColors.colorTeal
        Green -> theme.tagColors.colorGreen
        HighContrast -> theme.textInverse
        Outline -> theme.textPrimary
    }

    internal fun hoverColor(theme: Theme) = when (this) {
        Gray -> theme.tagColors.hoverGray
        CoolGray -> theme.tagColors.hoverCoolGray
        WarmGray -> theme.tagColors.hoverWarmGray
        Red -> theme.tagColors.hoverRed
        Magenta -> theme.tagColors.hoverMagenta
        Purple -> theme.tagColors.hoverPurple
        Blue -> theme.tagColors.hoverBlue
        Cyan -> theme.tagColors.hoverCyan
        Teal -> theme.tagColors.hoverTeal
        Green -> theme.tagColors.hoverGreen
        HighContrast -> theme.backgroundHover
        Outline -> theme.backgroundHover
    }

    internal fun borderColor(theme: Theme) = when (this) {
        Gray -> theme.tagColors.borderGray
        CoolGray -> theme.tagColors.borderCoolGray
        WarmGray -> theme.tagColors.borderWarmGray
        Red -> theme.tagColors.borderRed
        Magenta -> theme.tagColors.borderMagenta
        Purple -> theme.tagColors.borderPurple
        Blue -> theme.tagColors.borderBlue
        Cyan -> theme.tagColors.borderCyan
        Teal -> theme.tagColors.borderTeal
        Green -> theme.tagColors.borderGreen
        HighContrast -> theme.borderInverse
        Outline -> theme.borderInverse
    }
}
