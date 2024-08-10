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

package carbon.compose.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.runComposeUiTest
import carbon.compose.common.selectable.SelectableColors
import carbon.compose.common.selectable.SelectableInteractiveState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SelectableColorsTest : BaseSelectableColorsTest() {

    @Test
    fun abstractSelectableColors_static_colorsAreCorrect() = runComposeUiTest {
        val colors = object : SelectableColors(theme) {}
        assertNotNull(colors)

        assertEquals(
            expected = theme.textError,
            actual = colors.errorMessageTextColor
        )

        assertEquals(
            expected = theme.textPrimary,
            actual = colors.warningMessageTextColor
        )
    }

    @Test
    fun abstractSelectableColors_borderColor_colorsAreCorrect() = runComposeUiTest {
        val expectedColors: Map<SelectableInteractiveState, Any> = mapOf(
            interactiveStates["default"]!! to mapOf(
                ToggleableState.Off to theme.iconPrimary,
                ToggleableState.On to Color.Transparent,
                ToggleableState.Indeterminate to Color.Transparent,
            ),
            interactiveStates["warning"]!! to mapOf(
                ToggleableState.Off to theme.iconPrimary,
                ToggleableState.On to Color.Transparent,
                ToggleableState.Indeterminate to Color.Transparent,
            ),
            interactiveStates["disabled"]!! to mapOf(
                ToggleableState.Off to theme.iconDisabled,
                ToggleableState.On to Color.Transparent,
                ToggleableState.Indeterminate to Color.Transparent,
            ),
            interactiveStates["readOnly"]!! to theme.iconDisabled,
            interactiveStates["error"]!! to theme.supportError,
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
                actual = object : SelectableColors(theme) {}
                    .borderColor(
                        interactiveState = interactiveState,
                        state = toggleableState
                    )
                    .value,
                message = "Interactive state: $interactiveState, " +
                    "toggleable state: $toggleableState"
            )
        }
    }

    @Test
    fun abstractSelectableColors_labelColor_colorsAreCorrect() = runComposeUiTest {
        forAllLayersAndStates(interactiveStates.values) { interactiveState, _ ->
            assertEquals(
                expected = if (interactiveState == SelectableInteractiveState.Disabled) {
                    theme.textDisabled
                } else {
                    theme.textPrimary
                },
                actual = object : SelectableColors(theme) {}
                    .labelColor(interactiveState)
                    .value,
                message = "Interactive state: $interactiveState"
            )
        }
    }
}
