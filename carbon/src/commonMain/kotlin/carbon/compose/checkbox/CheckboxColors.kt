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

package carbon.compose.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import carbon.compose.Carbon
import carbon.compose.foundation.color.Theme
import carbon.compose.common.selectable.SelectableColors
import carbon.compose.common.selectable.SelectableInteractiveState

/**
 * The set of colors used to style a [Checkbox].
 */
@Immutable
@Suppress("LongParameterList")
internal class CheckboxColors private constructor(theme: Theme) : SelectableColors(theme) {

    @Composable
    fun checkmarkColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): State<Color> =
        rememberUpdatedState(
            newValue = when {
                state == ToggleableState.Off -> Color.Transparent
                interactiveState == SelectableInteractiveState.ReadOnly -> theme.iconPrimary
                else -> theme.iconInverse
            }
        )

    @Composable
    fun backgroundColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): State<Color> =
        rememberUpdatedState(
            newValue = when (interactiveState) {
                is SelectableInteractiveState.Default,
                is SelectableInteractiveState.Error,
                is SelectableInteractiveState.Warning -> if (state == ToggleableState.Off) {
                    Color.Transparent
                } else {
                    theme.iconPrimary
                }

                is SelectableInteractiveState.Disabled -> if (state == ToggleableState.Off) {
                    Color.Transparent
                } else {
                    theme.iconDisabled
                }

                is SelectableInteractiveState.ReadOnly -> Color.Transparent
            }
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CheckboxColors) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun hashCode(): Int = super.hashCode()

    internal companion object {

        @Composable
        fun colors(): CheckboxColors = CheckboxColors(Carbon.theme)
    }
}
