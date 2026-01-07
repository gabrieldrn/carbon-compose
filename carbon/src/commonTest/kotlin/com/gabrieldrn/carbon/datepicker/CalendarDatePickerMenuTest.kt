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
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ComposeUiTest
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
import co.touchlab.kermit.Logger
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.foundation.misc.Adaptation
import com.gabrieldrn.carbon.textinput.TextInputState
import com.gabrieldrn.carbon.textinput.TextInputTestTags
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char
import kotlinx.datetime.plus
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

// FIXME The actual implementation for keyboard navigation on Android is empty due to an unknown
//  situation where the focus doesn't work. Manually testing, to be working, the device must be in a
//  "keyboard mode" for example when pressing the Tab key in an emulator before executing the tests.
expect class CalendarDatePickerMenuTest {
    fun calendarDatePickerMenu_whenExpanded_validateCalendarMenuIsDisplayed()
    fun calendarDatePickerMenu_whenDayClicked_validateDateSelection()
    fun calendarDatePickerMenu_navigateToPreviousMonth_validateMonthChange()
    fun calendarDatePickerMenu_navigateToNextMonth_validateMonthChange()
    fun calendarDatePickerMenu_navigateToPreviousYear_validateYearChange()
    fun calendarDatePickerMenu_navigateToNextYear_validateYearChange()
    fun calendarDatePickerMenu_whenDateEntered_validateCalendarMonthUpdates()
    fun calendarDatePickerMenu_whenDaySelected_menuCloses()
    fun calendarDatePickerMenu_calendarIconClick_expandsMenu()
    fun calendarDatePickerMenu_calendarIconWhenDisabled_isNotClickable()
    fun calendarDatePickerMenu_calendarIconWhenReadOnly_isNotClickable()
    fun calendarDatePickerMenu_calendarIconWhenEnabled_isClickable()
    fun calendarDatePickerMenu_selectableDates_disabledDateIsNotClickable()
    fun calendarDatePickerMenu_selectableDates_enabledDateIsClickable()
    fun calendarDatePickerMenu_selectableDates_multipleDatesDisabled()
    fun calendarDatePickerMenu_selectableDates_onlyPastDatesEnabled()
    fun calendarDatePickerMenu_selectableDates_onlyFutureDatesEnabled()
    fun calendarDatePickerMenu_escapeKey_dismissesCalendarMenu()
    fun calendarDatePickerMenu_arrowRightKey_movesFocusToNextDay()
    fun calendarDatePickerMenu_arrowLeftKey_movesFocusToPreviousDay()
    fun calendarDatePickerMenu_arrowDownKey_movesFocusToSameDayNextWeek()
    fun calendarDatePickerMenu_arrowUpKey_movesFocusToSameDayPreviousWeek()
    fun calendarDatePickerMenu_arrowKeys_navigationOnDisabledDate()
    fun calendarDatePickerMenu_multipleArrowKeys_sequentialNavigation()
}

class CalendarDatePickerMenuTestCommonImpl() {

    private var adaptation by mutableStateOf(Adaptation.None)
    lateinit var datePickerState: CalendarDatePickerState
    private var today by mutableStateOf(LocalDate(2025, 1, 1))
    private var initialSelectedDate: LocalDate? by mutableStateOf(null)
    private var dateFormat by mutableStateOf(
        LocalDate.Format {
            year(); char('/'); monthNumber(); char('/'); day()
        }
    )
    private var selectableDates by mutableStateOf(SelectableDates { true })
    private var onFieldValidation by mutableStateOf<(Boolean?) -> Unit>({})
    private var onDismissRequest by mutableStateOf({ expanded = false })
    private var expanded by mutableStateOf(false)
    private var fieldValue by mutableStateOf("")
    private var inputState by mutableStateOf(TextInputState.Enabled)

    fun setup() {
        adaptation = Adaptation.None
        today = LocalDate(2025, 1, 1)
        initialSelectedDate = null
        dateFormat = LocalDate.Format {
            year(); char('/'); monthNumber(); char('/'); day()
        }
        selectableDates = SelectableDates { true }
        onFieldValidation = {}
        onDismissRequest = { expanded = false }
        expanded = false
        fieldValue = ""
    }

    private fun ComposeUiTest.setupUi() {
        setContent {
            CarbonDesignSystem(adaptation = adaptation) {
                datePickerState = rememberCalendarDatePickerState(
                    today = today,
                    initialSelectedDate = initialSelectedDate,
                    dateFormat = dateFormat,
                    selectableDates = selectableDates,
                    onFieldValidation = onFieldValidation
                )
                CalendarDatePicker(
                    datePickerState = datePickerState,
                    label = "Select date",
                    value = fieldValue,
                    expanded = expanded,
                    inputState = inputState,
                    onValueChange = { fieldValue = it },
                    onExpandedChange = { expanded = it },
                    onDismissRequest = onDismissRequest,
                )
            }
        }
    }

    private fun runWithAdaptation(
        effectContext: CoroutineContext = EmptyCoroutineContext,
        runTestContext: CoroutineContext = EmptyCoroutineContext,
        testTimeout: Duration = 60.seconds,
        block: suspend ComposeUiTest.() -> Unit,
    ) {
        Adaptation.entries.forEach {
            Logger.d("Applying adaptation $it")
            adaptation = it
            runComposeUiTest(
                effectContext = effectContext,
                runTestContext = runTestContext,
                testTimeout = testTimeout,
                block = block,
            )
        }
    }

    fun calendarDatePickerMenu_whenExpanded_validateCalendarMenuIsDisplayed() = runWithAdaptation {
        setupUi()

        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertDoesNotExist()

        expanded = true
        waitForIdle()

        onNodeWithTag(
            when (adaptation) {
                Adaptation.None ->
                    CalendarDatePickerTestTags.MENU_DEFAULT_YEARMONTH_SELECTOR
                Adaptation.Touchscreens ->
                    CalendarDatePickerTestTags.MENU_TOUCHSCREEN_YEARMONTH_SELECTOR
            }
        )
            .assertExists()
            .assertIsDisplayed()

        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertExists()
            .assertIsDisplayed()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_MONTH)
            .assertExists()
            .assertIsDisplayed()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_YEAR)
            .assertExists()
            .assertIsDisplayed()

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

    fun calendarDatePickerMenu_whenDayClicked_validateDateSelection() = runWithAdaptation {
        setupUi()

        expanded = true

        // Get current month/year to create a valid date tag

        val dayTag = "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_$today"

        // Try to find and click a day in current month
        onNodeWithTag(dayTag, useUnmergedTree = true)
            .assertExists()
            .performClick()

        waitForIdle()

        // Verify date was selected
        assertNotNull(datePickerState.selectedDate)
        assertEquals(today, datePickerState.selectedDate)
    }


    fun calendarDatePickerMenu_navigateToPreviousMonth_validateMonthChange() = runWithAdaptation {
        setupUi()

        expanded = true
        waitForIdle()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_PREV_MONTH_BUTTON)
            .assertExists()
            .performClick()

        waitForIdle()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_MONTH)
            .assertTextEquals("December")
    }


    fun calendarDatePickerMenu_navigateToNextMonth_validateMonthChange() = runWithAdaptation {
        setupUi()

        expanded = true

        onNodeWithTag(CalendarDatePickerTestTags.MENU_NEXT_MONTH_BUTTON)
            .assertExists()
            .performClick()

        waitForIdle()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_MONTH)
            .assertTextEquals("February")
    }

    fun calendarDatePickerMenu_navigateToPreviousYear_validateYearChange() = runWithAdaptation {
        setupUi()

        expanded = true
        waitForIdle()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_PREV_YEAR_BUTTON)
            .assertExists()
            .performClick()

        waitForIdle()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_YEAR)
            .assertTextEquals("2024")
    }

    fun calendarDatePickerMenu_navigateToNextYear_validateYearChange() = runWithAdaptation {
        setupUi()

        expanded = true

        onNodeWithTag(CalendarDatePickerTestTags.MENU_NEXT_YEAR_BUTTON)
            .assertExists()
            .performClick()

        waitForIdle()

        onNodeWithTag(CalendarDatePickerTestTags.MENU_YEAR)
            .assertTextEquals("2026")
    }

    fun calendarDatePickerMenu_whenDateEntered_validateCalendarMonthUpdates() = runWithAdaptation {
        initialSelectedDate = LocalDate(2024, 1, 15)

        setupUi()

        // Open calendar to verify it shows January initially
        expanded = true
        waitForIdle()

        // Verify January 15 is in the calendar
        val january15Tag =
            "${CalendarDatePickerTestTags.MENU_DAY_ITEM}_${LocalDate(2024, 1, 15)}"
        onNodeWithTag(january15Tag, useUnmergedTree = true)
            .assertExists()

        // Enter a date in a different month (June)
        runOnIdle { datePickerState.updateFieldValue("2024/06/20") }
        waitForIdle()

        // Verify the date was updated
        assertEquals(LocalDate(2024, 6, 20), datePickerState.selectedDate)
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

    fun calendarDatePickerMenu_whenDaySelected_menuCloses() = runWithAdaptation {
        setupUi()

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

    fun calendarDatePickerMenu_calendarIconClick_expandsMenu() = runWithAdaptation {
        setupUi()

        // Verify calendar menu is not visible initially
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertDoesNotExist()

        // Click the calendar icon
        onNodeWithTag(TextInputTestTags.CLICKABLE_TRAILING_ICON)
            .assertExists()
            .assertHasClickAction()
            .performClick()

        waitForIdle()

        // Verify calendar menu is now visible
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertExists()
            .assertIsDisplayed()
    }

    fun calendarDatePickerMenu_calendarIconWhenDisabled_isNotClickable() =
        runWithAdaptation {
            inputState = TextInputState.Disabled

            setupUi()

            // Calendar icon should exist but be disabled
            onNodeWithTag(TextInputTestTags.CLICKABLE_TRAILING_ICON)
                .assertExists()
                .assertIsNotEnabled()

            // Perform click (should not expand the menu)
            onNodeWithTag(TextInputTestTags.CLICKABLE_TRAILING_ICON)
                .performClick()

            waitForIdle()

            // Verify calendar menu is still not visible
            onNodeWithTag(CalendarDatePickerTestTags.MENU)
                .assertDoesNotExist()
        }

    fun calendarDatePickerMenu_calendarIconWhenReadOnly_isNotClickable() = runWithAdaptation {
        inputState = TextInputState.ReadOnly

        setupUi()

        // Calendar icon should exist but be disabled
        onNodeWithTag(TextInputTestTags.CLICKABLE_TRAILING_ICON)
            .assertExists()
            .assertIsNotEnabled()

        // Perform click (should not expand the menu)
        onNodeWithTag(TextInputTestTags.CLICKABLE_TRAILING_ICON)
            .performClick()

        waitForIdle()

        // Verify calendar menu is still not visible
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertDoesNotExist()
    }

    fun calendarDatePickerMenu_calendarIconWhenEnabled_isClickable() = runWithAdaptation {
        setupUi()

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
            onNodeWithTag(TextInputTestTags.CLICKABLE_TRAILING_ICON)
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

    fun calendarDatePickerMenu_selectableDates_disabledDateIsNotClickable() = runWithAdaptation {
        // Define a date that will be disabled
        val disabledDate = today.plus(1, DateTimeUnit.DAY)

        selectableDates = SelectableDates { date -> date != disabledDate }

        setupUi()

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
        assertNull(datePickerState.selectedDate)

        // Verify calendar menu is still open (because date was not selected)
        onNodeWithTag(CalendarDatePickerTestTags.MENU)
            .assertExists()
            .assertIsDisplayed()
    }

    fun calendarDatePickerMenu_selectableDates_enabledDateIsClickable() = runWithAdaptation {
        // Define a date that will be disabled
        val disabledDate = today.plus(1, DateTimeUnit.DAY)
        val enabledDate = today.plus(2, DateTimeUnit.DAY)

        selectableDates = SelectableDates { date -> date != disabledDate }

        setupUi()

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

    fun calendarDatePickerMenu_selectableDates_multipleDatesDisabled() = runWithAdaptation {
        // Define multiple dates that will be disabled
        val disabledDates = listOf(
            today.plus(1, DateTimeUnit.DAY),
            today.plus(3, DateTimeUnit.DAY),
            today.plus(5, DateTimeUnit.DAY)
        )

        selectableDates = SelectableDates { date -> date !in disabledDates }

        setupUi()

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

    fun calendarDatePickerMenu_selectableDates_onlyPastDatesEnabled() = runWithAdaptation {
        selectableDates = SelectableDates { date -> date <= today }

        setupUi()

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

    fun calendarDatePickerMenu_selectableDates_onlyFutureDatesEnabled() = runWithAdaptation {
        selectableDates = SelectableDates { date -> date > today }

        setupUi()

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

    fun calendarDatePickerMenu_escapeKey_dismissesCalendarMenu() = runWithAdaptation {
        setupUi()

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

    fun calendarDatePickerMenu_arrowRightKey_movesFocusToNextDay() = runWithAdaptation {
        val nextDay = today.plus(1, DateTimeUnit.DAY)

        setupUi()

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

        onNodeWithTag(nextDayTag, useUnmergedTree = true)
            .assertIsFocused()
    }

    fun calendarDatePickerMenu_arrowLeftKey_movesFocusToPreviousDay() = runWithAdaptation {
        // Use a future date to ensure there's a previous day
        val futureDate = today.plus(3, DateTimeUnit.DAY)
        val previousDay = today.plus(2, DateTimeUnit.DAY)

        setupUi()

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

        onNodeWithTag(previousDayTag, useUnmergedTree = true)
            .assertIsFocused()
    }

    fun calendarDatePickerMenu_arrowDownKey_movesFocusToSameDayNextWeek() = runWithAdaptation {
        val nextWeekDay = today.plus(7, DateTimeUnit.DAY)

        setupUi()

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
            .assertIsFocused()
            .performKeyInput {
                pressKey(Key.DirectionDown)
            }

        onNodeWithTag(nextWeekTag, useUnmergedTree = true)
            .assertIsFocused()
    }

    fun calendarDatePickerMenu_arrowUpKey_movesFocusToSameDayPreviousWeek() = runWithAdaptation {
        // Use a date in the second week to ensure there's a week above
        val futureDate = today.plus(10, DateTimeUnit.DAY)
        val previousWeekDay = today.plus(3, DateTimeUnit.DAY)

        setupUi()

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

        onNodeWithTag(previousWeekTag, useUnmergedTree = true)
            .assertIsFocused()
    }

    fun calendarDatePickerMenu_arrowKeys_navigationOnDisabledDate() = runWithAdaptation {
        // Disable some dates
        val disabledDate = today.plus(1, DateTimeUnit.DAY)
        val nextEnabledDate = today.plus(2, DateTimeUnit.DAY)

        selectableDates = SelectableDates { date -> date != disabledDate }

        setupUi()

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

        onNodeWithTag(nextEnabledDateTag, useUnmergedTree = true)
            .assertIsFocused()
    }

    fun calendarDatePickerMenu_multipleArrowKeys_sequentialNavigation() = runWithAdaptation {
        // Calculate the expected final position after:
        // Right x2, Down x1, Left x1
        // Starting from today: +2 days, +7 days, -1 day = +8 days
        val expectedFinalDate = today.plus(8, DateTimeUnit.DAY)

        setupUi()

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

        onNodeWithTag(finalDateTag, useUnmergedTree = true)
            .assertIsFocused()
    }
}
