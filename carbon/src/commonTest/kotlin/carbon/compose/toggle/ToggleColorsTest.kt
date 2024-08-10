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

package carbon.compose.toggle

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.runComposeUiTest
import carbon.compose.BaseColorsTest
import carbon.compose.foundation.color.Layer
import kotlin.test.Test
import kotlin.test.assertEquals

class ToggleColorsTest : BaseColorsTest() {

    @Test
    fun toggleColors_backgroundColorByState_returnsCorrectColor() = runComposeUiTest {
        val expectedColors = mapOf(
            Triple(
                /*isEnabled=*/false, /*isReadOnly=*/false, /*isToggled=*/false
            ) to theme.buttonDisabled,
            Triple(
                true, false, false
            ) to theme.toggleOff,
            Triple(
                true, true, false
            ) to Color.Transparent,
            Triple(
                true, false, true
            ) to theme.supportSuccess,
        )

        forAllLayersAndStates(expectedColors.keys) { state, _ ->
            assertEquals(
                expected = expectedColors[state],
                actual = ToggleColors.colors()
                    .backgroundColor(state.first, state.second, state.third)
                    .value,
                message = "For state (" +
                    "isEnabled = ${state.first}, " +
                    "isReadOnly = ${state.second}, " +
                    "isToggled = ${state.third})"
            )
        }
    }

    @Test
    fun toggleColors_borderColorByState_returnsCorrectColor() = runComposeUiTest {
        val expectedColors = mapOf(
            /*isEnabled=*/true to /*isReadOnly=*/true to mapOf(
                Layer.Layer00 to theme.borderSubtle01,
                Layer.Layer01 to theme.borderSubtle02,
                Layer.Layer02 to theme.borderSubtle03,
                Layer.Layer03 to theme.borderSubtle03
            ),
            false to false to Color.Transparent,
            true to false to Color.Transparent,
        )

        forAllLayersAndStates(expectedColors.keys) { state, layer ->
            val expected = expectedColors[state]
            assertEquals(
                expected = if (expected is Map<*, *>) {
                    expected[layer]
                } else {
                    expected
                },
                actual = ToggleColors.colors().borderColor(state.first, state.second).value,
                message = "For state (isEnabled = ${state.first}, isReadOnly = ${state.second}), " +
                    "layer $layer"
            )
        }
    }

    @Test
    fun toggleColors_handleColorByState_returnsCorrectColor() = runComposeUiTest {
        val expectedColors = mapOf(
            /*isEnabled = */ false to /*isReadOnly = */ false to theme.iconOnColorDisabled,
            false to true to theme.iconOnColorDisabled,
            true to false to theme.iconOnColor,
            true to true to theme.iconPrimary,
        )

        forAllLayersAndStates(expectedColors.keys) { state, _ ->
            assertEquals(
                expected = expectedColors[state],
                actual = ToggleColors.colors().handleColor(state.first, state.second).value,
                message = "For state (isEnabled = ${state.first}, isReadOnly = ${state.second})"
            )
        }
    }

    @Test
    fun toggleColors_handleCheckmarkColorByState_returnsCorrectColor() = runComposeUiTest {
        val expectedColors = mapOf(
            Triple(
                /*isEnabled=*/false, /*isReadOnly=*/false, /*isToggled=*/false
            ) to Color.Transparent,
            Triple(
                false, true, false
            ) to Color.Transparent,
            Triple(
                true, true, false
            ) to Color.Transparent,
            Triple(
                false, false, true
            ) to theme.buttonDisabled,
            Triple(
                true, false, true
            ) to theme.supportSuccess,
            Triple(
                true, true, true
            ) to Color.Transparent,
        )

        forAllLayersAndStates(expectedColors.keys) { state, _ ->
            assertEquals(
                expected = expectedColors[state],
                actual = ToggleColors.colors()
                    .handleCheckmarkColor(state.first, state.second, state.third)
                    .value,
                message = "For state $state"
            )
        }
    }
}
