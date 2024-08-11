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

package com.gabrieldrn.carbon.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.BaseColorsTest
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.dropdown.base.DropdownColors
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.foundation.color.Layer
import kotlin.test.Test
import kotlin.test.assertEquals

class DropdownColorsTest : BaseColorsTest() {

    private var dropdownColors by mutableStateOf<DropdownColors?>(null)

    private val interactiveStates = mapOf(
        "enabled" to DropdownInteractiveState.Enabled,
        "warning" to DropdownInteractiveState.Warning("warning"),
        "error" to DropdownInteractiveState.Error("error"),
        "disabled" to DropdownInteractiveState.Disabled,
        "readOnly" to DropdownInteractiveState.ReadOnly
    )

    private fun Map<Any, Any>.getColorByStateAndLayer(
        state: Any,
        layer: Layer
    ) = (this[state] ?: this[layer]!!).let {
        if (it is Map<*, *>) it[layer] as Color
        else it
    }

    private fun ComposeUiTest.checkColorByInteractiveStateAndLayer(
        expectedColors: Map<Any, Any>,
        actual: @Composable (state: DropdownInteractiveState) -> Color
    ) {
        var result by mutableStateOf<Color>(Color.Unspecified)

        forAllLayersAndStates(
            statesUnderTest = interactiveStates.values
        ) { state, layer ->
            result = actual(state)

            assertEquals(
                expected = expectedColors.getColorByStateAndLayer(state, layer),
                actual = result,
                message = "State: $state, Layer: $layer"
            )
        }
    }

    @Test
    fun dropdownColors_staticColorsWithoutContextualLayer_colorsAreCorrect() = runComposeUiTest {
        setContent {
            CarbonDesignSystem(theme = theme) {
                dropdownColors = DropdownColors.colors()
            }
        }

        assertEquals(
            expected = theme.iconPrimary,
            actual = dropdownColors!!.checkmarkIconColor
        )

        assertEquals(
            expected = theme.supportError,
            actual = dropdownColors!!.fieldBorderErrorColor
        )
    }

    @Test
    fun dropdownColors_menuOptionBackgroundColor_colorsAreCorrect() = runComposeUiTest {
        forAllLayers { layer ->
            assertEquals(
                expected = when (layer) {
                    Layer.Layer00 -> theme.layer01
                    Layer.Layer01 -> theme.layer02
                    Layer.Layer02 -> theme.layer03
                    Layer.Layer03 -> theme.layer03
                },
                actual = DropdownColors.colors().menuOptionBackgroundColor,
                message = "Layer: $layer"
            )
        }
    }

    @Test
    fun dropdownColors_menuOptionBorderColor_colorsAreCorrect() = runComposeUiTest {
        forAllLayers { layer ->
            dropdownColors = DropdownColors.colors()

            assertEquals(
                expected = when (layer) {
                    Layer.Layer00 -> theme.borderSubtle01
                    Layer.Layer01 -> theme.borderSubtle02
                    Layer.Layer02 -> theme.borderSubtle03
                    Layer.Layer03 -> theme.borderSubtle03
                },
                actual = dropdownColors!!.menuOptionBorderColor,
                message = "Layer: $layer"
            )
        }
    }

    @Test
    fun dropdownColors_chevronIconColor_colorsAreCorrect() = runComposeUiTest{
        val expectedColors: Map<Any, Any> =
            (interactiveStates.values + Layer.entries).associate {
                if (it is DropdownInteractiveState.Disabled) it to theme.iconDisabled
                else it to theme.iconPrimary
            }

        checkColorByInteractiveStateAndLayer(expectedColors) { state ->
            DropdownColors.colors().chevronIconColor(state).value
        }
    }

    @Test
    fun dropdownColors_fieldBackgroundColor_colorsAreCorrect() = runComposeUiTest {
        val colorsExpected = mapOf<Any, Any>(
            DropdownInteractiveState.ReadOnly to Color.Transparent,
            Layer.Layer00 to theme.field01,
            Layer.Layer01 to theme.field02,
            Layer.Layer02 to theme.field03,
            Layer.Layer03 to theme.field03,
        )

        checkColorByInteractiveStateAndLayer(colorsExpected) { state ->
            DropdownColors.colors().fieldBackgroundColor(state).value
        }
    }

    @Test
    fun dropdownColors_fieldBorderColor_colorsAreCorrect() = runComposeUiTest {
        val colorsExpected = mapOf<Any, Any>(
            interactiveStates["error"]!! to theme.supportError,
            interactiveStates["disabled"]!! to Color.Transparent,
            interactiveStates["readOnly"]!! to mapOf(
                Layer.Layer00 to theme.borderSubtle01,
                Layer.Layer01 to theme.borderSubtle02,
                Layer.Layer02 to theme.borderSubtle03,
                Layer.Layer03 to theme.borderSubtle03
            ),
            Layer.Layer00 to theme.borderStrong01,
            Layer.Layer01 to theme.borderStrong02,
            Layer.Layer02 to theme.borderStrong03,
            Layer.Layer03 to theme.borderStrong03,
        )

        checkColorByInteractiveStateAndLayer(colorsExpected) { state ->
            DropdownColors.colors().fieldBorderColor(state).value
        }
    }

    @Test
    fun dropdownColors_fieldTextColor_colorsAreCorrect() = runComposeUiTest {
        val colorsExpected: Map<Any, Any> = (interactiveStates.values + Layer.entries).associate {
            if (it is DropdownInteractiveState.Disabled) it to theme.textDisabled
            else it to theme.textPrimary
        }

        checkColorByInteractiveStateAndLayer(colorsExpected) { state ->
            DropdownColors.colors().fieldTextColor(state).value
        }
    }

    @Test
    fun dropdownColors_helperTextColor_colorsAreCorrect() = runComposeUiTest {
        val colorsExpected: Map<Any, Any> = (interactiveStates.values + Layer.entries).associate {
            if (it is DropdownInteractiveState.Error) it to theme.textError
            else it to theme.textPrimary
        }

        checkColorByInteractiveStateAndLayer(colorsExpected) { state ->
            DropdownColors.colors().helperTextColor(state).value
        }
    }

    @Test
    fun dropdownColors_labelTextColor_colorsAreCorrect() = runComposeUiTest {
        val colorsExpected: Map<Any, Any> = (interactiveStates.values + Layer.entries).associate {
            if (it is DropdownInteractiveState.Disabled) it to theme.textDisabled
            else it to theme.textSecondary
        }

        checkColorByInteractiveStateAndLayer(colorsExpected) { state ->
            DropdownColors.colors().labelTextColor(state).value
        }
    }

    @Test
    fun dropdownColors_menuOptionBackgroundSelectedColor_colorsAreCorrect() = runComposeUiTest {
        var result by mutableStateOf<Color>(Color.Unspecified)

        forAllLayersAndStates(statesUnderTest = listOf(true, false)) { isSelected, layer ->
            result = DropdownColors
                .colors()
                .menuOptionBackgroundSelectedColor(isSelected = isSelected)
                .value

            assertEquals(
                expected = if (isSelected) {
                    when (layer) {
                        Layer.Layer00 -> theme.layerSelected01
                        Layer.Layer01 -> theme.layerSelected02
                        Layer.Layer02 -> theme.layerSelected03
                        Layer.Layer03 -> theme.layerSelected03
                    }
                } else {
                    Color.Transparent
                },
                actual = result,
                message = "isSelected: $isSelected, Layer: $layer"
            )
        }
    }

    @Test
    fun dropdownColors_menuOptionTextColor_colorsAreCorrect() = runComposeUiTest {
        var result by mutableStateOf<Color>(Color.Unspecified)

        forAllLayersAndStates(
            statesUnderTest1 = listOf(true, false),
            statesUnderTest2 = listOf(true, false)
        ) { isEnabled, isSelected, layer ->
            result = DropdownColors.colors().menuOptionTextColor(
                isEnabled = isEnabled,
                isSelected = isSelected
            ).value

            assertEquals(
                expected = when {
                    !isEnabled -> theme.textDisabled
                    isSelected -> theme.textPrimary
                    else -> theme.textSecondary
                },
                actual = result,
                message = "isEnabled: $isEnabled, " +
                    "isSelected: $isSelected, " +
                    "Layer: $layer"
            )
        }
    }
}
