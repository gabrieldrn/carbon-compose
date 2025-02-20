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

package com.gabrieldrn.carbon.dropdown.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.foundation.color.layerActiveColor
import com.gabrieldrn.carbon.foundation.color.layerHoverColor
import com.gabrieldrn.carbon.foundation.color.layerSelectedColor
import com.gabrieldrn.carbon.foundation.color.layerSelectedHoverColor

/**
 * The colors used by the dropdown composable based on the current [theme].
 *
 * @param theme The theme to use for the colors.
 * @param layer Current layer where the dropdown is placed on.
 */
@Immutable
internal class DropdownColors private constructor(
    private val theme: Theme,
    private val layer: Layer
) {

    val checkmarkIconColor = theme.iconPrimary
    val fieldBorderErrorColor = theme.supportError
    val menuOptionBackgroundColor = when (layer) {
        Layer.Layer00 -> theme.layer01
        Layer.Layer01 -> theme.layer02
        else -> theme.layer03
    }
    val menuOptionBorderColor = when (layer) {
        Layer.Layer00 -> theme.borderSubtle01
        Layer.Layer01 -> theme.borderSubtle02
        else -> theme.borderSubtle03
    }

    @Composable
    fun chevronIconColor(state: DropdownInteractiveState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == DropdownInteractiveState.Disabled) iconDisabled
                else iconPrimary
            }
        )

    @Composable
    fun fieldBackgroundColor(state: DropdownInteractiveState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == DropdownInteractiveState.ReadOnly) Color.Transparent
                else when (layer) {
                    Layer.Layer00 -> field01
                    Layer.Layer01 -> field02
                    else -> field03
                }
            }
        )

    @Composable
    fun fieldBorderColor(state: DropdownInteractiveState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                when (state) {
                    is DropdownInteractiveState.Error -> supportError
                    is DropdownInteractiveState.Disabled -> Color.Transparent
                    is DropdownInteractiveState.ReadOnly -> when (layer) {
                        Layer.Layer00 -> borderSubtle01
                        Layer.Layer01 -> borderSubtle02
                        else -> borderSubtle03
                    }
                    else -> when (layer) {
                        Layer.Layer00 -> borderStrong01
                        Layer.Layer01 -> borderStrong02
                        else -> borderStrong03
                    }
                }
            }
        )

    @Composable
    fun fieldTextColor(state: DropdownInteractiveState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == DropdownInteractiveState.Disabled) textDisabled
                else textPrimary
            }
        )

    @Composable
    fun helperTextColor(state: DropdownInteractiveState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state is DropdownInteractiveState.Error) textError
                else textPrimary
            }
        )

    @Composable
    fun labelTextColor(state: DropdownInteractiveState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == DropdownInteractiveState.Disabled) textDisabled
                else textSecondary
            }
        )

    @Composable
    fun menuOptionBackground(
        isSelected: Boolean,
        isHovered: Boolean,
        isActive: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = when {
                isActive -> theme.layerActiveColor(layer)
                !isSelected && isHovered -> theme.layerHoverColor(layer)
                isSelected && isHovered -> theme.layerSelectedHoverColor(layer)
                isSelected -> theme.layerSelectedColor(layer)
                else -> Color.Transparent
            }
        )

    @Composable
    fun menuOptionTextColor(isEnabled: Boolean, isSelected: Boolean): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                when {
                    !isEnabled -> textDisabled
                    isSelected -> textPrimary
                    else -> textSecondary
                }
            }
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DropdownColors) return false

        if (theme != other.theme) return false
        if (layer != other.layer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = theme.hashCode()
        result = 31 * result + layer.hashCode()
        return result
    }

    internal companion object {

        @Composable
        fun colors() = DropdownColors(Carbon.theme, Carbon.layer)
    }
}
