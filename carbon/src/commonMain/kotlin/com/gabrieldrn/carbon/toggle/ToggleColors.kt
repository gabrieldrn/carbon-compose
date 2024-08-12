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

package com.gabrieldrn.carbon.toggle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.Theme

/**
 * Colors to be used by a [Toggle] based on its state.
 */
@Immutable
internal data class ToggleColors private constructor(
    val theme: Theme,
    val layer: Layer
) {

    @Composable
    fun backgroundColor(
        isEnabled: Boolean,
        isReadOnly: Boolean,
        isToggled: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = when {
                !isEnabled -> theme.buttonDisabled
                isReadOnly -> Color.Transparent
                isToggled -> theme.supportSuccess
                else -> theme.toggleOff
            }
        )

    @Composable
    fun borderColor(
        isEnabled: Boolean,
        isReadOnly: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = if (isReadOnly && isEnabled) {
                when (layer) {
                    Layer.Layer00 -> theme.borderSubtle01
                    Layer.Layer01 -> theme.borderSubtle02
                    else -> theme.borderSubtle03
                }
            } else {
                Color.Transparent
            }
        )

    @Composable
    fun handleColor(
        isEnabled: Boolean,
        isReadOnly: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = when {
                !isEnabled -> theme.iconOnColorDisabled
                isReadOnly -> theme.iconPrimary
                else -> theme.iconOnColor
            }
        )

    @Composable
    fun handleCheckmarkColor(
        isEnabled: Boolean,
        isReadOnly: Boolean,
        isToggled: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = when {
                !isToggled || isReadOnly -> Color.Transparent
                !isEnabled -> theme.buttonDisabled
                else -> theme.supportSuccess
            }
        )

    @Composable
    fun textColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(
            newValue = if (isEnabled) theme.textPrimary else theme.textDisabled
        )

    companion object {

        @Composable
        fun colors() = ToggleColors(Carbon.theme, Carbon.layer)
    }
}
