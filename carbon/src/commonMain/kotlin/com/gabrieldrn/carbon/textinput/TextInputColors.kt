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

package com.gabrieldrn.carbon.textinput

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.foundation.color.layerBorderStrong
import com.gabrieldrn.carbon.foundation.color.layerBorderSubtle

@Immutable
internal class TextInputColors private constructor(
    private val theme: Theme,
    private val layer: Layer
) {

    @Composable
    fun fieldBorderColor(state: TextInputState): State<Color> =
        rememberUpdatedState(
            newValue =
                if (state == TextInputState.ReadOnly) theme.layerBorderSubtle(layer)
                else theme.layerBorderStrong(layer)
        )

    @Composable
    fun fieldBackgroundColor(state: TextInputState): State<Color> =
        rememberUpdatedState(
            newValue = if (state == TextInputState.ReadOnly) {
                Color.Transparent
            } else {
                when (layer) {
                    Layer.Layer00 -> theme.field01
                    Layer.Layer01 -> theme.field02
                    else -> theme.field03
                }
            }
        )

    @Composable
    fun labelTextColor(state: TextInputState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == TextInputState.Disabled) textDisabled
                else textSecondary
            }
        )

    @Composable
    fun fieldTextColor(state: TextInputState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == TextInputState.Disabled) textDisabled
                else textPrimary
            }
        )

    @Composable
    fun placeholderTextColor(state: TextInputState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == TextInputState.Disabled) textDisabled
                else textPlaceholder
            }
        )

    @Composable
    fun helperTextColor(state: TextInputState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                when (state) {
                    TextInputState.Error -> textError
                    TextInputState.Disabled -> textDisabled
                    else -> textPrimary
                }
            }
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TextInputColors) return false

        if (theme != other.theme) return false
        if (layer != other.layer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = theme.hashCode()
        result = 31 * result + layer.hashCode()
        return result
    }

    companion object {
        @Composable
        fun colors() = TextInputColors(Carbon.theme, Carbon.layer)
    }
}
