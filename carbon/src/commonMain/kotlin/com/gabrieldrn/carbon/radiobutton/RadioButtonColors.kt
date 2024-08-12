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

package com.gabrieldrn.carbon.radiobutton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.common.selectable.SelectableColors
import com.gabrieldrn.carbon.common.selectable.SelectableInteractiveState

/**
 * The set of colors used to style a [RadioButton].
 */
@Immutable
@Suppress("LongParameterList")
internal class RadioButtonColors private constructor(theme: Theme) : SelectableColors(theme) {

    @Composable
    fun borderColor(
        interactiveState: SelectableInteractiveState,
        selected: Boolean
    ): State<Color> =
        borderColor(
            interactiveState,
            ToggleableState(selected)
        )

    @Composable
    override fun borderColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): State<Color> =
        rememberUpdatedState(
            newValue = when (interactiveState) {
                is SelectableInteractiveState.Default,
                is SelectableInteractiveState.Warning -> borderColor
                is SelectableInteractiveState.Disabled,
                is SelectableInteractiveState.ReadOnly -> borderDisabledColor
                is SelectableInteractiveState.Error -> borderErrorColor
            }
        )

    @Composable
    fun dotColor(
        interactiveState: SelectableInteractiveState,
        selected: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = when {
                !selected -> Color.Transparent
                interactiveState == SelectableInteractiveState.Disabled -> theme.iconDisabled
                else -> theme.iconPrimary
            }
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RadioButtonColors) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun hashCode(): Int = super.hashCode()

    internal companion object {

        @Composable
        fun colors(): RadioButtonColors = RadioButtonColors(Carbon.theme)
    }
}
