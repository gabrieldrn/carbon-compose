/*
 * Copyright 2026 Gabriel Derrien
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

package com.gabrieldrn.carbon.datepicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.forEachParameter
import com.gabrieldrn.carbon.test.semantics.isReadOnly
import com.gabrieldrn.carbon.textinput.TextInputState
import com.gabrieldrn.carbon.textinput.TextInputTestTags
import com.gabrieldrn.carbon.textinput.runGlobalTextInputLayoutAssertions
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SimpleDateInputTest {

    private val dateFormat = LocalDate.Format {
        monthNumber(); char('/'); day(); char('/'); year()
    }

    private var _value by mutableStateOf("")
    private var _placeholderText by mutableStateOf("")
    private var _helperText by mutableStateOf("")
    private var _inputState by mutableStateOf(TextInputState.Enabled)

    @Test
    fun simpleDateInput_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                SimpleDateInput(
                    state = rememberSimpleDateInputState(dateFormat = dateFormat),
                    label = "Date",
                    value = _value,
                    onValueChange = {},
                    placeholderText = _placeholderText,
                    helperText = _helperText,
                    inputState = _inputState,
                )
            }
        }

        forEachParameter(
            aValues = arrayOf("", "06/15/2024"),
            bValues = arrayOf("", "mm/dd/yyyy"),
            cValues = arrayOf("", "Helper text"),
            dValues = TextInputState.entries.toTypedArray()
        ) { value, placeholderText, helperText, inputState ->

            _value = value
            _placeholderText = placeholderText
            _helperText = helperText
            _inputState = inputState

            waitForIdle()

            runGlobalTextInputLayoutAssertions(
                label = "Date",
                helperText = helperText,
                state = inputState,
            )
        }
    }

    @Test
    fun simpleDateInput_validateSemantics() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                SimpleDateInput(
                    state = rememberSimpleDateInputState(dateFormat = dateFormat),
                    label = "Date",
                    value = _value,
                    onValueChange = {},
                    inputState = _inputState,
                )
            }
        }

        TextInputState.entries.forEach { inputState ->
            _inputState = inputState

            waitForIdle()

            onNodeWithTag(TextInputTestTags.FIELD, useUnmergedTree = true).run {
                when (inputState) {
                    TextInputState.Disabled -> assertIsNotEnabled()
                    TextInputState.ReadOnly -> {
                        assert(isFocusable())
                        assert(isReadOnly())
                    }
                    else -> assertIsEnabled()
                }
            }
        }
    }

    @Test
    fun simpleDateInput_whenValidDateEntered_validateParsing() = runComposeUiTest {
        var validationResult: Boolean? = null
        var state: SimpleDateInputState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                state = rememberSimpleDateInputState(
                    dateFormat = dateFormat,
                    onFieldValidation = remember { { validationResult = it } }
                )
                SimpleDateInput(
                    state = state,
                    label = "Date",
                    value = fieldValue,
                    onValueChange = { fieldValue = it },
                )
            }
        }

        val validDateString = "06/15/2024"

        runOnIdle {
            state?.updateFieldValue(validDateString)
        }

        waitForIdle()

        assertEquals(true, validationResult, "Valid date should be parsed successfully")
        assertEquals(LocalDate(2024, 6, 15), state?.selectedDate)
        assertEquals(validDateString, fieldValue)
    }

    @Test
    fun simpleDateInput_whenInvalidDateEntered_validateError() = runComposeUiTest {
        var validationResult: Boolean? = null
        var state: SimpleDateInputState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                state = rememberSimpleDateInputState(
                    dateFormat = dateFormat,
                    onFieldValidation = remember { { validationResult = it } }
                )
                SimpleDateInput(
                    state = state,
                    label = "Date",
                    value = fieldValue,
                    onValueChange = { fieldValue = it },
                )
            }
        }

        val invalidDateString = "not-a-date"

        runOnIdle {
            state?.updateFieldValue(invalidDateString)
        }

        waitForIdle()

        assertEquals(false, validationResult, "Invalid date should fail validation")
        assertNull(state?.selectedDate, "Selected date should remain null for invalid input")
        assertEquals(invalidDateString, fieldValue, "Field value should still be updated")
    }

    @Test
    fun simpleDateInput_whenBlankEntered_validateNullValidation() = runComposeUiTest {
        var validationResult: Boolean? = true
        var state: SimpleDateInputState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                state = rememberSimpleDateInputState(
                    initialSelectedDate = LocalDate(2024, 6, 15),
                    dateFormat = dateFormat,
                    onFieldValidation = remember { { validationResult = it } }
                )
                SimpleDateInput(
                    state = state,
                    label = "Date",
                    value = fieldValue,
                    onValueChange = { fieldValue = it },
                )
            }
        }

        runOnIdle {
            state?.updateFieldValue("   ")
        }

        waitForIdle()

        assertNull(validationResult, "Blank input should yield null validation result")
        assertNull(state?.selectedDate, "Selected date should be cleared on blank input")
    }

    @Test
    fun simpleDateInput_withInitialDate_validateDisplay() = runComposeUiTest {
        val initialDate = LocalDate(2024, 3, 20)
        var state: SimpleDateInputState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                state = rememberSimpleDateInputState(
                    initialSelectedDate = initialDate,
                    dateFormat = dateFormat,
                )
                fieldValue = state.selectedDate?.let { dateFormat.format(it) } ?: ""
                SimpleDateInput(
                    state = state,
                    label = "Date",
                    value = fieldValue,
                    onValueChange = { fieldValue = it },
                )
            }
        }

        waitForIdle()

        assertEquals(initialDate, state?.selectedDate)
        assertEquals("03/20/2024", fieldValue)
    }

    @Test
    fun simpleDateInput_clearDate_validateEmptyField() = runComposeUiTest {
        val initialDate = LocalDate(2024, 5, 10)
        var state: SimpleDateInputState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                state = rememberSimpleDateInputState(
                    initialSelectedDate = initialDate,
                    dateFormat = dateFormat,
                )
                fieldValue = state.selectedDate?.let { dateFormat.format(it) } ?: ""
                SimpleDateInput(
                    state = state,
                    label = "Date",
                    value = fieldValue,
                    onValueChange = { fieldValue = it },
                )
            }
        }

        runOnIdle {
            state?.selectedDate = null
        }

        waitForIdle()

        assertNull(state?.selectedDate)
        assertEquals("", fieldValue)
    }

    @Test
    fun simpleDateInput_whenDateSetProgrammatically_validateFieldUpdated() = runComposeUiTest {
        var validationResult: Boolean? = null
        var state: SimpleDateInputState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                state = rememberSimpleDateInputState(
                    dateFormat = dateFormat,
                    onFieldValidation = remember { { validationResult = it } }
                )
                SimpleDateInput(
                    state = state,
                    label = "Date",
                    value = fieldValue,
                    onValueChange = { fieldValue = it },
                )
            }
        }

        val newDate = LocalDate(2025, 12, 1)

        runOnIdle {
            state?.selectedDate = newDate
        }

        waitForIdle()

        assertEquals(true, validationResult)
        assertEquals(newDate, state?.selectedDate)
        assertEquals("12/01/2025", fieldValue)
    }
}
