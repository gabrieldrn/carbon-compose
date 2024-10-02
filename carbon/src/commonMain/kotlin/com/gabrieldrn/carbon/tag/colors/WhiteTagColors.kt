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

package com.gabrieldrn.carbon.tag.colors

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Color tokens for the tag component in the White theme.
 */
@Immutable
public object WhiteTagColors : TagColors {
    override val backgroundGray: Color = Color(0xFFE0E0E0)
    override val colorGray: Color = Color(0xFF161616)
    override val hoverGray: Color = Color(0xFFD1D1D1)
    override val borderGray: Color = Color(0xFFA8A8A8)

    override val backgroundCoolGray: Color = Color(0xFFDDE1E6)
    override val colorCoolGray: Color = Color(0xFF121619)
    override val hoverCoolGray: Color = Color(0xFFCDD3DA)
    override val borderCoolGray: Color = Color(0xFFA2A9B0)

    override val backgroundWarmGray: Color = Color(0xFFE5E0DF)
    override val colorWarmGray: Color = Color(0xFF171414)
    override val hoverWarmGray: Color = Color(0xFFD8D0CF)
    override val borderWarmGray: Color = Color(0xFFADA8A8)

    override val backgroundRed: Color = Color(0xFFFFD7D9)
    override val colorRed: Color = Color(0xFFA2191F)
    override val hoverRed: Color = Color(0xFFFFC2C5)
    override val borderRed: Color = Color(0xFFFF8389)

    override val backgroundMagenta: Color = Color(0xFFFFD6E8)
    override val colorMagenta: Color = Color(0xFF9F1853)
    override val hoverMagenta: Color = Color(0xFFFFBDDA)
    override val borderMagenta: Color = Color(0xFFFF7EB6)

    override val backgroundPurple: Color = Color(0xFFE8DAFF)
    override val colorPurple: Color = Color(0xFF6929C4)
    override val hoverPurple: Color = Color(0xFFDCC7FF)
    override val borderPurple: Color = Color(0xFFBE95FF)

    override val backgroundBlue: Color = Color(0xFFD0E2FF)
    override val colorBlue: Color = Color(0xFF0043CE)
    override val hoverBlue: Color = Color(0xFFB8D3FF)
    override val borderBlue: Color = Color(0xFF78A9FF)

    override val backgroundCyan: Color = Color(0xFFBAE6FF)
    override val colorCyan: Color = Color(0xFF00539A)
    override val hoverCyan: Color = Color(0xFF99DAFF)
    override val borderCyan: Color = Color(0xFF33B1FF)

    override val backgroundTeal: Color = Color(0xFF9EF0F0)
    override val colorTeal: Color = Color(0xFF005D5D)
    override val hoverTeal: Color = Color(0xFF57E5E5)
    override val borderTeal: Color = Color(0xFF08BDDA)

    override val backgroundGreen: Color = Color(0xFFA7F0BA)
    override val colorGreen: Color = Color(0xFF0E6027)
    override val hoverGreen: Color = Color(0xFF74E792)
    override val borderGreen: Color = Color(0xFF42BE65)
}
