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

import kotlin.test.BeforeTest
import kotlin.test.Test

actual class CalendarDatePickerMenuTest {

    val commonImpl = CalendarDatePickerMenuTestCommonImpl()

    @BeforeTest
    fun setup() {
        commonImpl.setup()
    }

    @Test
    actual fun calendarDatePickerMenu_whenExpanded_validateCalendarMenuIsDisplayed() =
        commonImpl.calendarDatePickerMenu_whenExpanded_validateCalendarMenuIsDisplayed()

    @Test
    actual fun calendarDatePickerMenu_whenDayClicked_validateDateSelection() =
        commonImpl.calendarDatePickerMenu_whenDayClicked_validateDateSelection()

    @Test
    actual fun calendarDatePickerMenu_navigateToPreviousMonth_validateMonthChange() =
        commonImpl.calendarDatePickerMenu_navigateToPreviousMonth_validateMonthChange()

    @Test
    actual fun calendarDatePickerMenu_navigateToNextMonth_validateMonthChange() =
        commonImpl.calendarDatePickerMenu_navigateToNextMonth_validateMonthChange()

    @Test
    actual fun calendarDatePickerMenu_navigateToPreviousYear_validateYearChange() =
        commonImpl.calendarDatePickerMenu_navigateToPreviousYear_validateYearChange()

    @Test
    actual fun calendarDatePickerMenu_navigateToNextYear_validateYearChange() =
        commonImpl.calendarDatePickerMenu_navigateToNextYear_validateYearChange()

    @Test
    actual fun calendarDatePickerMenu_whenDateEntered_validateCalendarMonthUpdates() =
        commonImpl.calendarDatePickerMenu_whenDateEntered_validateCalendarMonthUpdates()

    @Test
    actual fun calendarDatePickerMenu_whenDaySelected_menuCloses() =
        commonImpl.calendarDatePickerMenu_whenDaySelected_menuCloses()

    @Test
    actual fun calendarDatePickerMenu_calendarIconClick_expandsMenu() =
        commonImpl.calendarDatePickerMenu_calendarIconClick_expandsMenu()

    @Test
    actual fun calendarDatePickerMenu_calendarIconWhenDisabled_isNotClickable() =
        commonImpl.calendarDatePickerMenu_calendarIconWhenDisabled_isNotClickable()

    @Test
    actual fun calendarDatePickerMenu_calendarIconWhenReadOnly_isNotClickable() =
        commonImpl.calendarDatePickerMenu_calendarIconWhenReadOnly_isNotClickable()

    @Test
    actual fun calendarDatePickerMenu_calendarIconWhenEnabled_isClickable() =
        commonImpl.calendarDatePickerMenu_calendarIconWhenEnabled_isClickable()

    @Test
    actual fun calendarDatePickerMenu_selectableDates_disabledDateIsNotClickable() =
        commonImpl.calendarDatePickerMenu_selectableDates_disabledDateIsNotClickable()

    @Test
    actual fun calendarDatePickerMenu_selectableDates_enabledDateIsClickable() =
        commonImpl.calendarDatePickerMenu_selectableDates_enabledDateIsClickable()

    @Test
    actual fun calendarDatePickerMenu_selectableDates_multipleDatesDisabled() =
        commonImpl.calendarDatePickerMenu_selectableDates_multipleDatesDisabled()

    @Test
    actual fun calendarDatePickerMenu_selectableDates_onlyPastDatesEnabled() =
        commonImpl.calendarDatePickerMenu_selectableDates_onlyPastDatesEnabled()

    @Test
    actual fun calendarDatePickerMenu_selectableDates_onlyFutureDatesEnabled() =
        commonImpl.calendarDatePickerMenu_selectableDates_onlyFutureDatesEnabled()

    @Test
    actual fun calendarDatePickerMenu_escapeKey_dismissesCalendarMenu() =
        commonImpl.calendarDatePickerMenu_escapeKey_dismissesCalendarMenu()

    @Test
    actual fun calendarDatePickerMenu_arrowRightKey_movesFocusToNextDay() =
        commonImpl.calendarDatePickerMenu_arrowRightKey_movesFocusToNextDay()

    @Test
    actual fun calendarDatePickerMenu_arrowLeftKey_movesFocusToPreviousDay() =
        commonImpl.calendarDatePickerMenu_arrowLeftKey_movesFocusToPreviousDay()

    @Test
    actual fun calendarDatePickerMenu_arrowDownKey_movesFocusToSameDayNextWeek() =
        commonImpl.calendarDatePickerMenu_arrowDownKey_movesFocusToSameDayNextWeek()

    @Test
    actual fun calendarDatePickerMenu_arrowUpKey_movesFocusToSameDayPreviousWeek() =
        commonImpl.calendarDatePickerMenu_arrowUpKey_movesFocusToSameDayPreviousWeek()

    @Test
    actual fun calendarDatePickerMenu_arrowKeys_navigationOnDisabledDate() =
        commonImpl.calendarDatePickerMenu_arrowKeys_navigationOnDisabledDate()

    @Test
    actual fun calendarDatePickerMenu_multipleArrowKeys_sequentialNavigation() =
        commonImpl.calendarDatePickerMenu_multipleArrowKeys_sequentialNavigation()
}
