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

    val backgroundColorUnselected = when {
        variant == TabVariant.Line -> Color.Transparent
        layer == Layer.Layer00 -> theme.layerAccent01
        layer == Layer.Layer01 -> theme.layerAccent02
        else -> theme.layerAccent03
    }

    val backgroundColorSelected = when {
        variant == TabVariant.Line -> Color.Transparent
        layer == Layer.Layer00 -> theme.layer01
        layer == Layer.Layer01 -> theme.layer02
        else -> theme.layer03
    }

    val backgroundColorHovered = when {
        variant == TabVariant.Line -> Color.Transparent
        layer == Layer.Layer00 -> theme.layerAccentHover01
        layer == Layer.Layer01 -> theme.layerAccentHover02
        else -> theme.layerAccentHover03
    }

    val backgroundColorPressed = when {
        variant == TabVariant.Line -> Color.Transparent
        layer == Layer.Layer00 -> theme.layerAccentActive01
        layer == Layer.Layer01 -> theme.layerAccentActive02
        else -> theme.layerAccentActive03
    }

    private val bottomBorderUnselected = when (layer) {
        Layer.Layer00 -> theme.borderSubtle01
        Layer.Layer01 -> theme.borderSubtle02
        Layer.Layer02, Layer.Layer03 -> theme.borderSubtle03
    }

    private val bottomBorderHover = when (layer) {
        Layer.Layer00 -> theme.borderStrong01
        Layer.Layer01 -> theme.borderStrong02
        Layer.Layer02, Layer.Layer03 -> theme.borderStrong03
    }

    val scrollButtonBackgroundLine = when (layer) {
        Layer.Layer00 -> theme.background
        Layer.Layer01 -> theme.layer01
        Layer.Layer02 -> theme.layer02
        Layer.Layer03 -> theme.layer03
    }
    val scrollButtonBackgroundContained = when (layer) {
        Layer.Layer00 -> theme.layerAccent01
        Layer.Layer01 -> theme.layerAccent02
        else -> theme.layerAccent03
    }
    val scrollButtonBackground = when (variant) {
        TabVariant.Line -> scrollButtonBackgroundLine
        TabVariant.Contained -> scrollButtonBackgroundContained
    }

    val topBorder = theme.borderInteractive

    val verticalBorder = when (layer) {
        Layer.Layer00 -> theme.borderStrong01
        Layer.Layer01 -> theme.borderStrong02
        Layer.Layer02, Layer.Layer03 -> theme.borderStrong03
    }

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