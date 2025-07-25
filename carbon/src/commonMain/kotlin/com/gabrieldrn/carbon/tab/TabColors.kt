/*
 * Copyright 2025 Gabriel Derrien
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

package com.gabrieldrn.carbon.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.foundation.color.borderStrongColor
import com.gabrieldrn.carbon.foundation.color.borderSubtleColor
import com.gabrieldrn.carbon.foundation.color.layerAccentActiveColor
import com.gabrieldrn.carbon.foundation.color.layerAccentColor
import com.gabrieldrn.carbon.foundation.color.layerAccentHoverColor
import com.gabrieldrn.carbon.foundation.color.layerBackgroundColor
import com.gabrieldrn.carbon.foundation.color.layerColor

/**
 * The colors used by the [TabList] composable based on the current [theme].
 *
 * @param theme The theme to use for the colors.
 * @param layer Current layer where the [TabList] is placed on.
 * @param variant The visual variant of the tab.
 */
@Immutable
internal class TabColors private constructor(
    private val theme: Theme,
    layer: Layer,
    private val variant: TabVariant
) {

    private val backgroundColorUnselected =
        if (variant == TabVariant.Line) Color.Transparent
        else theme.layerAccentColor(layer)

    private val backgroundColorSelected =
        if (variant == TabVariant.Line) Color.Transparent
        else theme.layerColor(layer)

    private val backgroundColorHovered =
        if (variant == TabVariant.Line) Color.Transparent
        else theme.layerAccentHoverColor(layer)

    private val backgroundColorPressed =
        if (variant == TabVariant.Line) Color.Transparent
        else theme.layerAccentActiveColor(layer)

    private val bottomBorderUnselected = theme.borderSubtleColor(layer)

    private val bottomBorderHover = theme.borderStrongColor(layer)

    private val scrollButtonBackgroundLine = theme.layerBackgroundColor(layer)

    private val scrollButtonBackgroundContained = theme.layerAccentColor(layer)

    val scrollButtonBackground = when (variant) {
        TabVariant.Line -> scrollButtonBackgroundLine
        TabVariant.Contained -> scrollButtonBackgroundContained
    }

    val topBorder = theme.borderInteractive

    val verticalBorder = theme.borderStrongColor(layer)

    @Composable
    fun background(
        enabled: Boolean,
        selected: Boolean,
        hovered: Boolean,
        pressed: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = when {
                variant == TabVariant.Line -> Color.Transparent
                !enabled -> theme.buttonColors.buttonDisabled
                selected -> backgroundColorSelected
                hovered -> backgroundColorHovered
                pressed -> backgroundColorPressed
                else -> backgroundColorUnselected
            }
        )

    @Composable
    fun bottomBorder(
        enabled: Boolean,
        selected: Boolean,
        hovered: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = when {
                !enabled -> theme.borderDisabled
                selected -> theme.borderInteractive
                hovered -> bottomBorderHover
                else -> bottomBorderUnselected
            }
        )

    @Composable
    fun labelText(
        enabled: Boolean,
        selected: Boolean,
        hovered: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = when {
                !enabled -> when (variant) {
                    TabVariant.Line -> theme.textDisabled
                    TabVariant.Contained -> theme.textOnColorDisabled
                }
                hovered -> theme.textPrimary
                selected -> theme.textPrimary
                else -> theme.textSecondary
            }
        )

    internal companion object {

        @Composable
        fun colors(variant: TabVariant) = TabColors(Carbon.theme, Carbon.layer, variant)
    }
}
