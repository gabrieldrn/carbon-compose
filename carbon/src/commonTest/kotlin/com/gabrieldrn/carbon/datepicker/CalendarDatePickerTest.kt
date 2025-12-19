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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.textinput.TextInputState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CalendarDatePickerTest {

    private val dateFormat = LocalDate.Format {
        year(); char('/'); monthNumber(); char('/'); day()
    }

    @Test
    fun calendarDatePicker_default_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        dateFormat = dateFormat,
                        onFieldValidation = {}
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

        onNodeWithTag(CalendarDatePickerTestTags.CALENDAR_MENU)
            .assertDoesNotExist()
    }

    @Test
    fun calendarDatePicker_whenExpanded_validateCalendarMenuIsDisplayed() = runComposeUiTest {
        var expanded by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        dateFormat = dateFormat,
                        onFieldValidation = {}
                    ),
                    label = "Select date",
                    value = "",
                    expanded = expanded,
                    onValueChange = {},
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        onNodeWithTag(CalendarDatePickerTestTags.CALENDAR_MENU)
            .assertDoesNotExist()

        expanded = true
        waitForIdle()

        onNodeWithTag(CalendarDatePickerTestTags.CALENDAR_MENU)
            .assertExists()
            .assertIsDisplayed()

        onNodeWithTag(CalendarDatePickerTestTags.CALENDAR_TITLE)
            .assertExists()
            .assertIsDisplayed()

        onNodeWithTag(CalendarDatePickerTestTags.CALENDAR_PREVIOUS_BUTTON)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()

        onNodeWithTag(CalendarDatePickerTestTags.CALENDAR_NEXT_BUTTON)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun calendarDatePicker_whenDayClicked_validateDateSelection() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    initialSelectedDate = null,
                    dateFormat = dateFormat,
                    confirmDateChange = { true },
                    onFieldValidation = {}
                )
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = fieldValue,
                    expanded = expanded,
                    onValueChange = { fieldValue = it },
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        expanded = true

        // Get current month/year to create a valid date tag

        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        val dayTag = "${CalendarDatePickerTestTags.CALENDAR_DAY_ITEM}_$today"

        // Try to find and click a day in current month
        onNodeWithTag(dayTag, useUnmergedTree = true)
            .assertExists()
            .performClick()

        waitForIdle()

        // Verify date was selected
        assertNotNull(datePickerState?.selectedDate)
        assertEquals(today, datePickerState.selectedDate)
    }

    @Test
    fun calendarDatePicker_navigateToPreviousMonth_validateMonthChange() = runComposeUiTest {
        var expanded by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        dateFormat = dateFormat,
                        onFieldValidation = {}
                    ),
                    label = "Select date",
                    value = "",
                    expanded = expanded,
                    onValueChange = {},
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        expanded = true
        waitForIdle()

        // Verify previous button exists and click it
        onNodeWithTag(CalendarDatePickerTestTags.CALENDAR_PREVIOUS_BUTTON)
            .assertExists()
            .performClick()

        waitForIdle()

        // Calendar should still be displayed after navigation
        onNodeWithTag(CalendarDatePickerTestTags.CALENDAR_MENU)
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun calendarDatePicker_navigateToNextMonth_validateMonthChange() = runComposeUiTest {
        var expanded by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        dateFormat = dateFormat,
                        onFieldValidation = {}
                    ),
                    label = "Select date",
                    value = "",
                    expanded = expanded,
                    onValueChange = {},
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        expanded = true

        // Verify next button exists and click it
        onNodeWithTag(CalendarDatePickerTestTags.CALENDAR_NEXT_BUTTON)
            .assertExists()
            .performClick()

        waitForIdle()

        // Calendar should still be displayed after navigation
        onNodeWithTag(CalendarDatePickerTestTags.CALENDAR_MENU)
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun calendarDatePicker_whenValidDateEntered_validateParsing() = runComposeUiTest {
        var fieldValidationResult: Boolean? = null
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    initialSelectedDate = null,
                    dateFormat = dateFormat,
                    confirmDateChange = { true },
                    onFieldValidation = { fieldValidationResult = it }
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

        runOnIdle {
            datePickerState?.updateFieldValue(validDateString)
        }

        waitForIdle()

        assertTrue(fieldValidationResult == true, "Valid date should be parsed successfully")
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
                    initialSelectedDate = null,
                    dateFormat = dateFormat,
                    confirmDateChange = { true },
                    onFieldValidation = { fieldValidationResult = it }
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

        assertFalse(fieldValidationResult == true, "Invalid date should fail validation")
        assertNull(datePickerState?.selectedDate, "Selected date should remain null for invalid input")
        assertEquals(invalidDateString, fieldValue, "Field value should still be updated")
    }

    @Test
    fun calendarDatePicker_withInitialDate_validateDisplay() = runComposeUiTest {
        val initialDate = LocalDate(2024, 3, 20)
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    initialSelectedDate = initialDate,
                    dateFormat = dateFormat,
                    confirmDateChange = { true },
                    onFieldValidation = {}
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
                        dateFormat = dateFormat,
                        onFieldValidation = {}
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
                    initialSelectedDate = initialDate,
                    dateFormat = dateFormat,
                    confirmDateChange = { false }, // Never confirm date changes
                    onFieldValidation = {}
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
                    initialSelectedDate = initialDate,
                    dateFormat = dateFormat,
                    confirmDateChange = { true },
                    onFieldValidation = {}
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

    @Test
    fun calendarDatePicker_whenDateEntered_validateCalendarMonthUpdates() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    initialSelectedDate = LocalDate(2024, 1, 15), // January
                    dateFormat = dateFormat,
                    confirmDateChange = { true },
                    onFieldValidation = {}
                )
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = fieldValue,
                    expanded = expanded,
                    onValueChange = { fieldValue = it },
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        // Open calendar to verify it shows January initially
        expanded = true
        waitForIdle()

        // Verify January 15 is in the calendar
        val january15Tag =
            "${CalendarDatePickerTestTags.CALENDAR_DAY_ITEM}_${LocalDate(2024, 1, 15)}"
        onNodeWithTag(january15Tag, useUnmergedTree = true)
            .assertExists()

        // Enter a date in a different month (June)
        runOnIdle {
            datePickerState?.updateFieldValue("2024/06/20")
        }
        waitForIdle()

        // Verify the date was updated
        assertEquals(LocalDate(2024, 6, 20), datePickerState?.selectedDate)
        assertEquals("2024/06/20", fieldValue)

        // Close and reopen calendar to verify it now shows June
        expanded = false
        waitForIdle()
        expanded = true
        waitForIdle()

        // Verify June 20 is now in the calendar
        val june20Tag =
            "${CalendarDatePickerTestTags.CALENDAR_DAY_ITEM}_${LocalDate(2024, 6, 20)}"
        onNodeWithTag(june20Tag, useUnmergedTree = true)
            .assertExists()

        // Verify January 15 is no longer in the calendar (different month)
        onNodeWithTag(january15Tag, useUnmergedTree = true)
            .assertDoesNotExist()
    }
}
