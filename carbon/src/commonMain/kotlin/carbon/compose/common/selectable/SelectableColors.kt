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

package carbon.compose.common.selectable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import carbon.compose.foundation.color.Theme

/**
 * The set of colors used to style a selectable.
 */
@Immutable
@Suppress("LongParameterList")
internal abstract class SelectableColors(val theme: Theme) {

    protected val borderColor = theme.iconPrimary
    protected val borderDisabledColor = theme.iconDisabled
    protected val borderErrorColor = theme.supportError
    val errorMessageTextColor = theme.textError
    val warningMessageTextColor = theme.textPrimary

    @Composable
    open fun borderColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): State<Color> =
        rememberUpdatedState(
            newValue = when (interactiveState) {
                is SelectableInteractiveState.Default,
                is SelectableInteractiveState.Warning -> if (state == ToggleableState.Off) {
                    borderColor
                } else {
                    Color.Transparent
                }

                is SelectableInteractiveState.Disabled -> if (state == ToggleableState.Off) {
                    borderDisabledColor
                } else {
                    Color.Transparent
                }

                is SelectableInteractiveState.ReadOnly -> theme.iconDisabled
                is SelectableInteractiveState.Error -> borderErrorColor
            }
        )

    @Composable
    fun labelColor(interactiveState: SelectableInteractiveState): State<Color> =
        rememberUpdatedState(
            newValue = if (interactiveState == SelectableInteractiveState.Disabled) {
                theme.textDisabled
            } else {
                theme.textPrimary
            }
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SelectableColors) return false

        if (theme != other.theme) return false

        return true
    }

    override fun hashCode(): Int = theme.hashCode()
}
