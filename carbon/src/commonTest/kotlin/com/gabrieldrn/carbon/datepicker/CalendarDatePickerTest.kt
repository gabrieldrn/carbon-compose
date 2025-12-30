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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.textinput.TextInputState
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char
import kotlinx.datetime.plus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
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
    fun calendarDatePicker_whenExpanded_validateCalendarMenuIsDisplayed() = runComposeUiTest {
        var expanded by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        today = today,
                        dateFormat = dateFormat
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

        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertDoesNotExist()

        expanded = true
        waitForIdle()

        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertExists()
            .assertIsDisplayed()

//        onNodeWithTag(CalendarDatePickerTestTags.CALENDAR_TITLE)
//            .assertExists()
//            .assertIsDisplayed()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_PREV_MONTH_BUTTON)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_NEXT_MONTH_BUTTON)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_PREV_YEAR_BUTTON)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_NEXT_YEAR_BUTTON)
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
                    today = today,
                    initialSelectedDate = null,
                    dateFormat = dateFormat,
                    selectableDates = { true }
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

        val dayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$today"

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
                        today = today,
                        dateFormat = dateFormat
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

        onNodeWithTag(CalendarDatePickerTestTags.MENU_PREV_MONTH_BUTTON)
            .assertExists()
            .performClick()

        waitForIdle()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_MONTH)
            .assertTextEquals("December")
    }

    @Test
    fun calendarDatePicker_navigateToNextMonth_validateMonthChange() = runComposeUiTest {
        var expanded by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        today = today,
                        dateFormat = dateFormat
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

        onNodeWithTag(CalendarDatePickerTestTags.MENU_NEXT_MONTH_BUTTON)
            .assertExists()
            .performClick()

        waitForIdle()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_MONTH)
            .assertTextEquals("February")
    }


    @Test
    fun calendarDatePicker_navigateToPreviousYear_validateYearChange() = runComposeUiTest {
        var expanded by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        today = today,
                        dateFormat = dateFormat
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

        onNodeWithTag(CalendarDatePickerTestTags.MENU_PREV_YEAR_BUTTON)
            .assertExists()
            .performClick()

        waitForIdle()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_YEAR)
            .assertTextEquals("2024")
    }

    @Test
    fun calendarDatePicker_navigateToNextYear_validateYearChange() = runComposeUiTest {
        var expanded by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        today = today,
                        dateFormat = dateFormat
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

        onNodeWithTag(CalendarDatePickerTestTags.MENU_NEXT_YEAR_BUTTON)
            .assertExists()
            .performClick()

        waitForIdle()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_YEAR)
            .assertTextEquals("2026")
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

    @Test
    fun calendarDatePicker_whenDateEntered_validateCalendarMonthUpdates() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    initialSelectedDate = LocalDate(2024, 1, 15), // January
                    dateFormat = dateFormat,
                    selectableDates = { true }
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
            "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_${LocalDate(2024, 1, 15)}"
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
            "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_${LocalDate(2024, 6, 20)}"
        onNodeWithTag(june20Tag, useUnmergedTree = true)
            .assertExists()

        // Verify January 15 is no longer in the calendar (different month)
        onNodeWithTag(january15Tag, useUnmergedTree = true)
            .assertDoesNotExist()
    }

    @Test
    fun calendarDatePicker_whenDaySelected_menuCloses() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    initialSelectedDate = null,
                    dateFormat = dateFormat,
                    selectableDates = { true }
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

        // Open the calendar menu
        expanded = true
        waitForIdle()

        // Verify calendar menu is visible
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertExists()
            .assertIsDisplayed()

        // Click on a day (today)
        val dayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$today"
        onNodeWithTag(dayTag, useUnmergedTree = true)
            .assertExists()
            .performClick()

        waitForIdle()

        // Verify date was selected
        assertNotNull(datePickerState)
        assertEquals(today, datePickerState.selectedDate)

        // Verify calendar menu is now closed
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertDoesNotExist()
    }

    @Test
    fun calendarDatePicker_calendarIconClick_expandsMenu() = runComposeUiTest {
        var expanded by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        today = today,
                        dateFormat = dateFormat
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

        // Verify calendar menu is not visible initially
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertDoesNotExist()

        // Click the calendar icon
        onNodeWithTag(com.gabrieldrn.carbon.textinput.TextInputTestTags.CLICKABLE_TRAILING_ICON)
            .assertExists()
            .assertHasClickAction()
            .performClick()

        waitForIdle()

        // Verify calendar menu is now visible
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun calendarDatePicker_calendarIconWhenDisabled_isNotClickable() = runComposeUiTest {
        var expanded by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        today = today,
                        dateFormat = dateFormat
                    ),
                    label = "Select date",
                    value = "",
                    expanded = expanded,
                    inputState = TextInputState.Disabled,
                    onValueChange = {},
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        // Calendar icon should exist but be disabled
        onNodeWithTag(com.gabrieldrn.carbon.textinput.TextInputTestTags.CLICKABLE_TRAILING_ICON)
            .assertExists()
            .assertIsNotEnabled()

        // Perform click (should not expand the menu)
        onNodeWithTag(com.gabrieldrn.carbon.textinput.TextInputTestTags.CLICKABLE_TRAILING_ICON)
            .performClick()

        waitForIdle()

        // Verify calendar menu is still not visible
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertDoesNotExist()
    }

    @Test
    fun calendarDatePicker_calendarIconWhenReadOnly_isNotClickable() = runComposeUiTest {
        var expanded by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        today = today,
                        dateFormat = dateFormat
                    ),
                    label = "Select date",
                    value = "",
                    expanded = expanded,
                    inputState = TextInputState.ReadOnly,
                    onValueChange = {},
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        // Calendar icon should exist but be disabled
        onNodeWithTag(com.gabrieldrn.carbon.textinput.TextInputTestTags.CLICKABLE_TRAILING_ICON)
            .assertExists()
            .assertIsNotEnabled()

        // Perform click (should not expand the menu)
        onNodeWithTag(com.gabrieldrn.carbon.textinput.TextInputTestTags.CLICKABLE_TRAILING_ICON)
            .performClick()

        waitForIdle()

        // Verify calendar menu is still not visible
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertDoesNotExist()
    }

    @Test
    fun calendarDatePicker_calendarIconWhenEnabled_isClickable() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var inputState by mutableStateOf(TextInputState.Enabled)

        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        today = today,
                        dateFormat = dateFormat
                    ),
                    label = "Select date",
                    value = "",
                    expanded = expanded,
                    inputState = inputState,
                    onValueChange = {},
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        // Test with different enabled states (Warning and Error should still be clickable)
        listOf(
            TextInputState.Enabled,
            TextInputState.Warning,
            TextInputState.Error
        ).forEach { state ->
            inputState = state
            expanded = false
            waitForIdle()

            // Calendar icon should be clickable
            onNodeWithTag(com.gabrieldrn.carbon.textinput.TextInputTestTags.CLICKABLE_TRAILING_ICON)
                .assertExists()
                .assertHasClickAction()
                .performClick()

            waitForIdle()

            // Verify calendar menu is visible
            onNodeWithTag(CalendarDatePickerTestTags.MENU)
                .assertExists()
                .assertIsDisplayed()
        }
    }

    @Test
    fun calendarDatePicker_selectableDates_disabledDateIsNotClickable() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        // Define a date that will be disabled
        val disabledDate = today.plus(1, DateTimeUnit.DAY)

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    initialSelectedDate = null,
                    dateFormat = dateFormat,
                    selectableDates = { date -> date != disabledDate }
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

        // Open the calendar menu
        expanded = true
        waitForIdle()

        // Verify calendar menu is visible
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertExists()
            .assertIsDisplayed()

        // Try to click on the disabled date
        val disabledDayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$disabledDate"
        onNodeWithTag(disabledDayTag, useUnmergedTree = true)
            .assertExists()
            .assertIsNotEnabled()
            .performClick()

        waitForIdle()

        // Verify date was NOT selected
        assertNull(datePickerState?.selectedDate)

        // Verify calendar menu is still open (because date was not selected)
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun calendarDatePicker_selectableDates_enabledDateIsClickable() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        // Define a date that will be disabled
        val disabledDate = today.plus(1, DateTimeUnit.DAY)
        val enabledDate = today.plus(2, DateTimeUnit.DAY)

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    initialSelectedDate = null,
                    dateFormat = dateFormat,
                    selectableDates = { date -> date != disabledDate }
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

        // Open the calendar menu
        expanded = true
        waitForIdle()

        // Click on an enabled date
        val enabledDayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$enabledDate"
        onNodeWithTag(enabledDayTag, useUnmergedTree = true)
            .assertExists()
            .assertHasClickAction()
            .performClick()

        waitForIdle()

        // Verify date was selected
        assertNotNull(datePickerState)
        assertEquals(enabledDate, datePickerState.selectedDate)

        // Verify calendar menu is now closed
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertDoesNotExist()
    }

    @Test
    fun calendarDatePicker_selectableDates_multipleDatesDisabled() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        // Define multiple dates that will be disabled
        val disabledDates = listOf(
            today.plus(1, DateTimeUnit.DAY),
            today.plus(3, DateTimeUnit.DAY),
            today.plus(5, DateTimeUnit.DAY)
        )

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    initialSelectedDate = null,
                    dateFormat = dateFormat,
                    selectableDates = { date -> date !in disabledDates }
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

        // Open the calendar menu
        expanded = true
        waitForIdle()

        // Verify each disabled date is not clickable
        disabledDates.forEach { disabledDate ->
            val disabledDayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$disabledDate"
            onNodeWithTag(disabledDayTag, useUnmergedTree = true)
                .assertExists()
                .assertIsNotEnabled()
        }

        // Verify an enabled date is clickable
        val enabledDate = today.plus(2, DateTimeUnit.DAY)
        val enabledDayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$enabledDate"
        onNodeWithTag(enabledDayTag, useUnmergedTree = true)
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun calendarDatePicker_selectableDates_onlyPastDatesEnabled() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    initialSelectedDate = null,
                    dateFormat = dateFormat,
                    selectableDates = { date -> date <= today }
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

        // Open the calendar menu
        expanded = true
        waitForIdle()

        // Verify future date is not clickable
        val futureDate = today.plus(1, DateTimeUnit.DAY)
        val futureDayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$futureDate"
        onNodeWithTag(futureDayTag, useUnmergedTree = true)
            .assertExists()
            .assertIsNotEnabled()

        // Verify today is clickable
        val todayDayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$today"
        onNodeWithTag(todayDayTag, useUnmergedTree = true)
            .assertExists()
            .assertHasClickAction()
            .performClick()

        waitForIdle()

        // Verify today was selected
        assertNotNull(datePickerState)
        assertEquals(today, datePickerState.selectedDate)
    }

    @Test
    fun calendarDatePicker_selectableDates_onlyFutureDatesEnabled() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState? = null
        var fieldValue by mutableStateOf("")

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    initialSelectedDate = null,
                    dateFormat = dateFormat,
                    selectableDates = { date -> date > today }
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

        // Open the calendar menu
        expanded = true
        waitForIdle()

        // Verify today is not clickable
        val todayDayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$today"
        onNodeWithTag(todayDayTag, useUnmergedTree = true)
            .assertExists()
            .assertIsNotEnabled()

        // Verify future date is clickable
        val futureDate = today.plus(1, DateTimeUnit.DAY)
        val futureDayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$futureDate"
        onNodeWithTag(futureDayTag, useUnmergedTree = true)
            .assertExists()
            .assertHasClickAction()
            .performClick()

        waitForIdle()

        // Verify future date was selected
        assertNotNull(datePickerState)
        assertEquals(futureDate, datePickerState.selectedDate)
    }

    @Test
    fun calendarDatePicker_escapeKey_dismissesCalendarMenu() = runComposeUiTest {
        var expanded by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                CalendarDatePicker(
                    datePickerState = rememberCalendarDatePickerState(
                        today = today,
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

        // Open the calendar menu
        expanded = true
        waitForIdle()

        // Verify calendar menu is visible
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertExists()
            .assertIsDisplayed()

        // Press Escape key on the calendar menu
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .performKeyInput {
                pressKey(Key.Escape)
            }

        waitForIdle()

        // Verify calendar menu is now dismissed
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertDoesNotExist()
    }

    @Test
    fun calendarDatePicker_arrowRightKey_movesFocusToNextDay() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState

        val nextDay = today.plus(1, DateTimeUnit.DAY)

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    dateFormat = dateFormat,
                    onFieldValidation = {}
                )
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = "",
                    expanded = expanded,
                    onValueChange = {},
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        // Open the calendar menu
        expanded = true
        waitForIdle()

        // Get today's date tag
        val todayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$today"
        val nextDayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$nextDay"

        // Focus on today
        onNodeWithTag(todayTag, useUnmergedTree = true)
            .assertExists()
            .requestFocus()
            .performKeyInput {
                pressKey(Key.DirectionRight)
            }

        onNodeWithTag(nextDayTag, useUnmergedTree = false)
            .assertIsFocused()
    }

    @Test
    fun calendarDatePicker_arrowLeftKey_movesFocusToPreviousDay() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState

        // Use a future date to ensure there's a previous day
        val futureDate = today.plus(3, DateTimeUnit.DAY)
        val previousDay = today.plus(2, DateTimeUnit.DAY)

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    dateFormat = dateFormat,
                    onFieldValidation = {}
                )
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = "",
                    expanded = expanded,
                    onValueChange = {},
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        // Open the calendar menu
        expanded = true
        waitForIdle()

        // Get tags for current and previous day
        val futureDateTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$futureDate"
        val previousDayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$previousDay"

        // Focus on future date
        onNodeWithTag(futureDateTag, useUnmergedTree = true)
            .assertExists()
            .requestFocus()
            .performKeyInput {
                pressKey(Key.DirectionLeft)
            }

        onNodeWithTag(previousDayTag, useUnmergedTree = false)
            .assertIsFocused()
    }

    @Test
    fun calendarDatePicker_arrowDownKey_movesFocusToSameDayNextWeek() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState

        val nextWeekDay = today.plus(7, DateTimeUnit.DAY)

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    dateFormat = dateFormat,
                    onFieldValidation = {}
                )
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = "",
                    expanded = expanded,
                    onValueChange = {},
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        // Open the calendar menu
        expanded = true
        waitForIdle()

        // Get tags for today and next week
        val todayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$today"
        val nextWeekTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$nextWeekDay"

        // Focus on today
        onNodeWithTag(todayTag, useUnmergedTree = true)
            .assertExists()
            .requestFocus()
            .performKeyInput {
                pressKey(Key.DirectionDown)
            }

        onNodeWithTag(nextWeekTag, useUnmergedTree = false)
            .assertIsFocused()
    }

    @Test
    fun calendarDatePicker_arrowUpKey_movesFocusToSameDayPreviousWeek() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState

        // Use a date in the second week to ensure there's a week above
        val futureDate = today.plus(10, DateTimeUnit.DAY)
        val previousWeekDay = today.plus(3, DateTimeUnit.DAY)

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    dateFormat = dateFormat,
                    onFieldValidation = {}
                )
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = "",
                    expanded = expanded,
                    onValueChange = {},
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        // Open the calendar menu
        expanded = true
        waitForIdle()

        // Get tags for current date and previous week
        val futureDateTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$futureDate"
        val previousWeekTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$previousWeekDay"

        // Focus on future date
        onNodeWithTag(futureDateTag, useUnmergedTree = true)
            .assertExists()
            .requestFocus()
            .performKeyInput {
                pressKey(Key.DirectionUp)
            }

        onNodeWithTag(previousWeekTag, useUnmergedTree = false)
            .assertIsFocused()
    }

    @Test
    fun calendarDatePicker_arrowKeys_navigationOnDisabledDate() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState

        // Disable some dates
        val disabledDate = today.plus(1, DateTimeUnit.DAY)
        val nextEnabledDate = today.plus(2, DateTimeUnit.DAY)

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    dateFormat = dateFormat,
                    selectableDates = SelectableDates { date ->
                        date != disabledDate
                    },
                    onFieldValidation = {}
                )
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = "",
                    expanded = expanded,
                    onValueChange = {},
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        // Open the calendar menu
        expanded = true
        waitForIdle()

        // Get tags
        val todayDateTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$today"
        val nextEnabledDateTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$nextEnabledDate"

        // Even though the date is disabled, keyboard navigation should still work
        // (focus can move to disabled items, they just can't be clicked)
        onNodeWithTag(todayDateTag, useUnmergedTree = true)
            .requestFocus()
            .performKeyInput {
                pressKey(Key.DirectionRight)
            }

        onNodeWithTag(nextEnabledDateTag, useUnmergedTree = false)
            .assertIsFocused()
    }

    @Test
    fun calendarDatePicker_multipleArrowKeys_sequentialNavigation() = runComposeUiTest {
        var expanded by mutableStateOf(false)
        var datePickerState: CalendarDatePickerState

        // Calculate the expected final position after:
        // Right x2, Down x1, Left x1
        // Starting from today: +2 days, +7 days, -1 day = +8 days
        val expectedFinalDate = today.plus(8, DateTimeUnit.DAY)

        setContent {
            CarbonDesignSystem {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    dateFormat = dateFormat,
                    onFieldValidation = {}
                )
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = "",
                    expanded = expanded,
                    onValueChange = {},
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                )
            }
        }

        // Open the calendar menu
        expanded = true
        waitForIdle()

        // Get tags
        val todayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$today"
        val finalDateTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$expectedFinalDate"

        // Focus on today and perform multiple arrow key presses
        onNodeWithTag(todayTag, useUnmergedTree = true)
            .assertExists()
            .requestFocus()
            .performKeyInput {
                // Navigate right twice
                pressKey(Key.DirectionRight)
                pressKey(Key.DirectionRight)
                // Navigate down once (moves 7 days forward in the grid)
                pressKey(Key.DirectionDown)
                // Navigate left once
                pressKey(Key.DirectionLeft)
            }

        onNodeWithTag(finalDateTag, useUnmergedTree = false)
            .assertIsFocused()
    }
}
