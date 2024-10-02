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
 * Color tokens for the tag component in the Gray 100 theme.
 */
@Immutable
public object Gray100TagColors : TagColors {
    override val backgroundGray: Color = Color(0xFF525252)
    override val colorGray: Color = Color(0xFFE0E0E0)
    override val hoverGray: Color = Color(0xFF636363)
    override val borderGray: Color = Color(0xFF8D8D8D)

    override val backgroundCoolGray: Color = Color(0xFF4D5358)
    override val colorCoolGray: Color = Color(0xFFDDE1E6)
    override val hoverCoolGray: Color = Color(0xFF5D646A)
    override val borderCoolGray: Color = Color(0xFF878D96)

    override val backgroundWarmGray: Color = Color(0xFF565151)
    override val colorWarmGray: Color = Color(0xFFE5E0DF)
    override val hoverWarmGray: Color = Color(0xFF696364)
    override val borderWarmGray: Color = Color(0xFF8F8B8B)

    override val backgroundRed: Color = Color(0xFFA2191F)
    override val colorRed: Color = Color(0xFFFFD7D9)
    override val hoverRed: Color = Color(0xFFC21E25)
    override val borderRed: Color = Color(0xFFFA4D56)

    override val backgroundMagenta: Color = Color(0xFF9F1853)
    override val colorMagenta: Color = Color(0xFFFFD6E8)
    override val hoverMagenta: Color = Color(0xFFBF1D63)
    override val borderMagenta: Color = Color(0xFFEE5396)

    override val backgroundPurple: Color = Color(0xFF6929C4)
    override val colorPurple: Color = Color(0xFFE8DAFF)
    override val hoverPurple: Color = Color(0xFF7C3DD6)
    override val borderPurple: Color = Color(0xFFA56EFF)

    override val backgroundBlue: Color = Color(0xFF0043CE)
    override val colorBlue: Color = Color(0xFFD0E2FF)
    override val hoverBlue: Color = Color(0xFF0053FF)
    override val borderBlue: Color = Color(0xFF4589FF)

    override val backgroundCyan: Color = Color(0xFF00539A)
    override val colorCyan: Color = Color(0xFFBAE6FF)
    override val hoverCyan: Color = Color(0xFF0066BD)
    override val borderCyan: Color = Color(0xFF1192E8)

    override val backgroundTeal: Color = Color(0xFF005D5D)
    override val colorTeal: Color = Color(0xFF9EF0F0)
    override val hoverTeal: Color = Color(0xFF007070)
    override val borderTeal: Color = Color(0xFF009D9A)

    override val backgroundGreen: Color = Color(0xFF0E6027)
    override val colorGreen: Color = Color(0xFFA7F0BA)
    override val hoverGreen: Color = Color(0xFF11742F)
    override val borderGreen: Color = Color(0xFF24A148)
}