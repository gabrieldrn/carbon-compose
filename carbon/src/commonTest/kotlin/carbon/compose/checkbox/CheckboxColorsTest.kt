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

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.runComposeUiTest
import carbon.compose.common.BaseSelectableColorsTest
import carbon.compose.common.selectable.SelectableInteractiveState
import kotlin.test.Test
import kotlin.test.assertEquals

class CheckboxColorsTest : BaseSelectableColorsTest() {

    @Test
    fun checkboxColors_checkmarkColor_colorsAreCorrect() = runComposeUiTest {
        forAllLayersAndStates(
            interactiveStates.values,
            ToggleableState.entries
        ) { interactiveState, toggleableState, _ ->
            assertEquals(
                expected = when {
                    toggleableState == ToggleableState.Off -> Color.Transparent
                    interactiveState == SelectableInteractiveState.ReadOnly -> theme.iconPrimary
                    else -> theme.iconInverse
                },
                actual = CheckboxColors.colors()
                    .checkmarkColor(interactiveState, toggleableState)
                    .value,
                message = "Interactive state: $interactiveState, " +
                    "toggleable state: $toggleableState"
            )
        }
    }

    @Test
    fun checkboxColors_backgroundColor_colorsAreCorrect() = runComposeUiTest {
        val expectedColors: Map<SelectableInteractiveState, Any> = mapOf(
            interactiveStates["default"]!! to mapOf(
                ToggleableState.Off to Color.Transparent,
                ToggleableState.On to theme.iconPrimary,
                ToggleableState.Indeterminate to theme.iconPrimary,
            ),
            interactiveStates["disabled"]!! to mapOf(
                ToggleableState.Off to Color.Transparent,
                ToggleableState.On to theme.iconDisabled,
                ToggleableState.Indeterminate to theme.iconDisabled,
            ),
            interactiveStates["readOnly"]!! to Color.Transparent,
            interactiveStates["error"]!! to mapOf(
                ToggleableState.Off to Color.Transparent,
                ToggleableState.On to theme.iconPrimary,
                ToggleableState.Indeterminate to theme.iconPrimary,
            ),
            interactiveStates["warning"]!! to mapOf(
                ToggleableState.Off to Color.Transparent,
                ToggleableState.On to theme.iconPrimary,
                ToggleableState.Indeterminate to theme.iconPrimary,
            ),
        )

        forAllLayersAndStates(
            interactiveStates.values,
            ToggleableState.entries
        ) { interactiveState, toggleableState, _ ->
            assertEquals(
                expected = expectedColors.getColor(
                    interactiveState = interactiveState,
                    toggleableState = toggleableState
                ),
                actual = CheckboxColors.colors()
                    .backgroundColor(
                        interactiveState = interactiveState,
                        state = toggleableState
                    )
                    .value,
                message = "Interactive state: $interactiveState, " +
                    "toggleable state: $toggleableState"
            )
        }
    }
}
