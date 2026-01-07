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

actual class CalendarDatePickerMenuTest {
    actual fun calendarDatePickerMenu_whenExpanded_validateCalendarMenuIsDisplayed() {}
    actual fun calendarDatePickerMenu_whenDayClicked_validateDateSelection() {}
    actual fun calendarDatePickerMenu_navigateToPreviousMonth_validateMonthChange() {}
    actual fun calendarDatePickerMenu_navigateToNextMonth_validateMonthChange() {}
    actual fun calendarDatePickerMenu_navigateToPreviousYear_validateYearChange() {}
    actual fun calendarDatePickerMenu_navigateToNextYear_validateYearChange() {}
    actual fun calendarDatePickerMenu_whenDateEntered_validateCalendarMonthUpdates() {}
    actual fun calendarDatePickerMenu_whenDaySelected_menuCloses() {}
    actual fun calendarDatePickerMenu_calendarIconClick_expandsMenu() {}
    actual fun calendarDatePickerMenu_calendarIconWhenDisabled_isNotClickable() {}
    actual fun calendarDatePickerMenu_calendarIconWhenReadOnly_isNotClickable() {}
    actual fun calendarDatePickerMenu_calendarIconWhenEnabled_isClickable() {}
    actual fun calendarDatePickerMenu_selectableDates_disabledDateIsNotClickable() {}
    actual fun calendarDatePickerMenu_selectableDates_enabledDateIsClickable() {}
    actual fun calendarDatePickerMenu_selectableDates_multipleDatesDisabled() {}
    actual fun calendarDatePickerMenu_selectableDates_onlyPastDatesEnabled() {}
    actual fun calendarDatePickerMenu_selectableDates_onlyFutureDatesEnabled() {}
    actual fun calendarDatePickerMenu_escapeKey_dismissesCalendarMenu() {}
    actual fun calendarDatePickerMenu_arrowRightKey_movesFocusToNextDay() {}
    actual fun calendarDatePickerMenu_arrowLeftKey_movesFocusToPreviousDay() {}
    actual fun calendarDatePickerMenu_arrowDownKey_movesFocusToSameDayNextWeek() {}
    actual fun calendarDatePickerMenu_arrowUpKey_movesFocusToSameDayPreviousWeek() {}
    actual fun calendarDatePickerMenu_arrowKeys_navigationOnDisabledDate() {}
    actual fun calendarDatePickerMenu_multipleArrowKeys_sequentialNavigation() {}
}
