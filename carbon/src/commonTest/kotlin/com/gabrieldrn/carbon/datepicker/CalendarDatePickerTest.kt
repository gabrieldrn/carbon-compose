/*
 * Copyright 2025 Gabriel Derrien
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.textinput.TextInputState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class CalendarDatePickerTest {

    private val today = LocalDate(2025, 1, 1)

    private val dateFormat = LocalDate.Format {
        year(); char('/'); monthNumber(); char('/'); day()
    }

    @Test
    fun calendarDatePicker_default_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        today = today,
                        dateFormat = dateFormat
                    ),
                    label = "Select date",
                    value = "",
                    expanded = false,
                    onValueChange = {},
                    onExpandedChange = {},
                    onDismissRequest = {},
                    modifier = Modifier.testTag("DatePicker")
                )
            }
        }

        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertDoesNotExist()
    }

    @Test
    fun calendarDatePicker_whenValidDateEntered_validateParsing() = runComposeUiTest {
        var fieldValidationResult: Boolean? = null
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    initialSelectedDate = null,
                    dateFormat = dateFormat,
                    selectableDates = { true },
                    onFieldValidation = remember { { fieldValidationResult = it } }
                )
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = fieldValue,
                    expanded = false,
                    onValueChange = { fieldValue = it },
                    onExpandedChange = {},
                    onDismissRequest = {},
                )
            }
        }

        val validDateString = "2024/06/15"

        datePickerState?.updateFieldValue(validDateString)

        waitForIdle()

        assertEquals(
            fieldValidationResult,
            true,
            "Valid date should be parsed successfully")
        assertEquals(LocalDate(2024, 6, 15), datePickerState?.selectedDate)
        assertEquals(validDateString, fieldValue)
    }

    @Test
    fun calendarDatePicker_whenInvalidDateEntered_validateError() = runComposeUiTest {
        var fieldValidationResult: Boolean? = null
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    initialSelectedDate = null,
                    dateFormat = dateFormat,
                    selectableDates = { true },
                    onFieldValidation = remember { { fieldValidationResult = it } }
                )
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = fieldValue,
                    expanded = false,
                    onValueChange = { fieldValue = it },
                    onExpandedChange = {},
                    onDismissRequest = {},
                )
            }
        }

        val invalidDateString = "invalid/date"

        runOnIdle {
            datePickerState?.updateFieldValue(invalidDateString)
        }

        waitForIdle()

        assertNotEquals(
            fieldValidationResult,
            true,
            "Invalid date should fail validation"
        )
        assertNull(
            datePickerState?.selectedDate,
            "Selected date should remain null for invalid input"
        )
        assertEquals(
            invalidDateString,
            fieldValue,
            "Field value should still be updated")
    }

    @Test
    fun calendarDatePicker_withInitialDate_validateDisplay() = runComposeUiTest {
        val initialDate = LocalDate(2024, 3, 20)
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    initialSelectedDate = initialDate,
                    dateFormat = dateFormat,
                    selectableDates = { true }
                )
                fieldValue = datePickerState.selectedDate?.let { dateFormat.format(it) } ?: ""
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = fieldValue,
                    expanded = false,
                    onValueChange = { fieldValue = it },
                    onExpandedChange = {},
                    onDismissRequest = {},
                )
            }
        }

        waitForIdle()

        assertEquals(initialDate, datePickerState?.selectedDate)
        assertEquals("2024/03/20", fieldValue)
    }

    @Test
    fun calendarDatePicker_disabled_validateState() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        today = today,
                        dateFormat = dateFormat
                    ),
                    label = "Select date",
                    value = "",
                    expanded = false,
                    inputState = TextInputState.Disabled,
                    onValueChange = {},
                    onExpandedChange = {},
                    onDismissRequest = {},
                )
            }
        }

        onNodeWithTag(CalendarDatePickerTestTags.TEXT_FIELD)
            .assertExists()
            .assertIsNotEnabled()
    }

    @Test
    fun calendarDatePicker_whenConfirmDateChangeReturnsFalse_validateDateNotChanged() = runComposeUiTest {
        val initialDate = LocalDate(2024, 1, 1)
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    initialSelectedDate = initialDate,
                    dateFormat = dateFormat,
                    selectableDates = { false } // Never confirm date changes
                )
                fieldValue = datePickerState.selectedDate?.let { dateFormat.format(it) } ?: ""
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = fieldValue,
                    expanded = false,
                    onValueChange = { fieldValue = it },
                    onExpandedChange = {},
                    onDismissRequest = {},
                )
            }
        }

        runOnIdle {
            datePickerState?.updateFieldValue("2024/12/31")
        }

        waitForIdle()

        // Date should not change because confirmDateChange returns false
        assertEquals(initialDate, datePickerState?.selectedDate)
    }

    @Test
    fun calendarDatePicker_clearDate_validateEmptyField() = runComposeUiTest {
        val initialDate = LocalDate(2024, 5, 10)
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    initialSelectedDate = initialDate,
                    dateFormat = dateFormat,
                    selectableDates = { true }
                )
                fieldValue = datePickerState.selectedDate?.let { dateFormat.format(it) } ?: ""
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = fieldValue,
                    expanded = false,
                    onValueChange = { fieldValue = it },
                    onExpandedChange = {},
                    onDismissRequest = {},
                )
            }
        }

        runOnIdle {
            datePickerState?.selectedDate = null
        }

        waitForIdle()

        assertNull(datePickerState?.selectedDate)
        assertEquals("", fieldValue)
    }
}
