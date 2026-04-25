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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.gabrieldrn.carbon.datepicker.CalendarDatePickerStateImpl.Companion.Saver
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.minusMonth
import kotlinx.datetime.minusYear
import kotlinx.datetime.onDay
import kotlinx.datetime.plusMonth
import kotlinx.datetime.plusYear
import kotlinx.datetime.yearMonth

/**
 * A state holder for the Calendar Date Picker to observe the selected date and synchronize it with
 * a text field.
 *
 * This interface provides the core state management for calendar date pickers, handling date
 * selection, validation, and navigation between months and years.
 *
 * @param T The date type (e.g., [kotlinx.datetime.LocalDate]).
 */
@Stable
public interface CalendarDatePickerState<T> {

    /**
     * Determines which dates are selectable in the calendar.
     *
     * This interface allows customization of which dates can be selected by the user.
     * For example, you can disable weekends, past dates, or specific dates.
     */
    public val selectableDates: SelectableDates<T>

    /**
     * The current day.
     */
    public val today: T

    /**
     * The currently selected date, or `null` if no date is selected.
     */
    public var selectedDate: T?

    /**
     * Provides the calendar menu data structure for rendering the calendar popup.
     *
     * This [State] provides access to the [CalendarMenuData] which contains the days matrix,
     * year name, and month name for the currently displayed month in the calendar menu.
     */
    public val calendarMenuData: State<CalendarMenuData<T>>

    /**
     * A callback invoked by the state to update the text field value, usually when the selected
     * date changes.
     *
     * This is typically set by the UI component (e.g., a DatePicker text field) to receive updates
     * when the [selectedDate] changes programmatically or via calendar interaction.
     * The argument is the formatted date string or the raw input string.
     */
    public var updateFieldCallback: ((String) -> Unit)?

    /**
     * Processes a new string value input (e.g., from a text field).
     *
     * @param newValue The raw string input to process.
     */
    public fun updateFieldValue(newValue: String)

    /**
     * Invoked when the user requests to display the previous month in the calendar popup menu.
     */
    public fun onLoadPreviousMonth()

    /**
     * Invoked when the user requests to display the next month in the calendar popup menu.
     */
    public fun onLoadNextMonth()

    /**
     * Invoked when the user requests to display the previous year in the calendar popup menu.
     */
    public fun onLoadPreviousYear()

    /**
     * Invoked when the user requests to display the next year in the calendar popup menu.
     */
    public fun onLoadNextYear()
}

/**
 * A functional interface to determine which dates are selectable in the calendar.
 *
 * This allows you to customize which dates can be selected by the user. Common use cases include:
 * - Disabling past dates
 * - Disabling future dates
 * - Disabling weekends
 * - Disabling specific dates (e.g., holidays)
 *
 * Example with [LocalDate] date type:
 * ```kotlin
 * // Only allow future dates
 * SelectableDates { date -> date > today }
 *
 * // Only allow weekdays
 * SelectableDates { date ->
 *     date.dayOfWeek != DayOfWeek.SATURDAY && date.dayOfWeek != DayOfWeek.SUNDAY
 * }
 * ```
 *
 * @param T The date type (e.g., [kotlinx.datetime.LocalDate]).
 */
@Stable
public fun interface SelectableDates<T> {

    /**
     * Determines if the given [date] is selectable.
     *
     * @param date The date to check.
     * @return `true` if the date is selectable, `false` otherwise.
     */
    public fun isSelectable(date: T): Boolean
}

/**
 * Creates and provides a [CalendarDatePickerState] implementation using date data types from
 * [`kotlinx.datetime`](https://github.com/Kotlin/kotlinx-datetime).
 *
 * @param today The current day represented by a [LocalDate].
 * @param initialSelectedDate The initial [LocalDate] to be selected, or `null` if no date is
 * selected.
 * @param dateFormat The [DateTimeFormat] used to parse and format the date string. Defaults to
 * `yyyy/MM/dd`.
 * @param yearFormat The [DateTimeFormat] used to format the displayed year in the calendar.
 * @param monthFormat The [DateTimeFormat] used to format the displayed month in the calendar.
 * @param selectableDates A [SelectableDates] instance that determines which dates are selectable.
 * Defaults to all dates being selectable.
 * @param onFieldValidation Callback invoked when the state tries to parse the field value or
 * format the [CalendarDatePickerState.selectedDate] when one of them is changed. Returns `true`
 * when successful, `false` otherwise, or `null` if the field is empty.
 */
@Composable
public fun rememberCalendarDatePickerState(
    today: LocalDate,
    initialSelectedDate: LocalDate? = null,
    dateFormat: DateTimeFormat<LocalDate> = LocalDate.Format {
        year(); char('/'); monthNumber(); char('/'); day()
    },
    yearFormat: DateTimeFormat<YearMonth> = YearMonth.Format {
        year()
    },
    monthFormat: DateTimeFormat<YearMonth> = YearMonth.Format {
        monthName(MonthNames.ENGLISH_FULL)
    },
    selectableDates: SelectableDates<LocalDate> = SelectableDates { true },
    onFieldValidation: (Boolean?) -> Unit = {}
): CalendarDatePickerState<LocalDate> =
    rememberSaveable(
        today,
        dateFormat,
        selectableDates,
        onFieldValidation,
        saver = CalendarDatePickerStateImpl.Saver(
            dateFormat = dateFormat,
            yearFormat = yearFormat,
            monthFormat = monthFormat,
            selectableDates = selectableDates,
            onFieldValidation = onFieldValidation
        )
    ) {
        CalendarDatePickerStateImpl(
            today = today,
            initialSelectedDate = initialSelectedDate,
            dateFormat = dateFormat,
            yearFormat = yearFormat,
            monthFormat = monthFormat,
            selectableDates = selectableDates,
            onFieldValidation = onFieldValidation
        )
    }

@Stable
private class CalendarDatePickerStateImpl(
    initialSelectedDate: LocalDate?,
    override val today: LocalDate,
    val dateFormat: DateTimeFormat<LocalDate>,
    val yearFormat: DateTimeFormat<YearMonth>,
    val monthFormat: DateTimeFormat<YearMonth>,
    override val selectableDates: SelectableDates<LocalDate>,
    val onFieldValidation: (Boolean?) -> Unit
) : CalendarDatePickerState<LocalDate> {

    private var _selectedDate by mutableStateOf(initialSelectedDate)

    override var selectedDate: LocalDate?
        get() = _selectedDate
        set(value) {
            when {
                value == null -> {
                    updateFieldCallback?.invoke("")
                    onFieldValidation(null)
                    _selectedDate = null
                }
                !selectableDates.isSelectable(value) -> onFieldValidation(false)
                else -> try {
                    updateFieldCallback?.invoke(dateFormat.format(value))
                    onFieldValidation(true)
                    _selectedDate = value
                } catch (_: IllegalArgumentException) {
                    onFieldValidation(false)
                }
            }
            updateCalendarMenuData()
        }

    override var calendarMenuData: MutableState<CalendarMenuDataImpl> = mutableStateOf(
        CalendarMenuDataImpl((selectedDate ?: today).yearMonth)
    )

    override var updateFieldCallback: ((String) -> Unit)? = null

    override fun updateFieldValue(newValue: String) {
        if (newValue.isBlank()) {
            onFieldValidation(null)
            _selectedDate = null
        } else {
            try {
                dateFormat.parse(newValue)
                    .takeIf(selectableDates::isSelectable)
                    ?.let { _selectedDate = it }
                onFieldValidation(true)
            } catch (_: IllegalArgumentException) {
                onFieldValidation(false)
            }
        }
        updateCalendarMenuData()
        updateFieldCallback?.invoke(newValue)
    }

    private fun updateCalendarMenuData() {
        val yearMonth = selectedDate?.yearMonth ?: today.yearMonth
        if (calendarMenuData.value.yearMonth == yearMonth) return
        calendarMenuData.value = CalendarMenuDataImpl(yearMonth)
    }

    override fun onLoadPreviousMonth() {
        calendarMenuData.value =
            CalendarMenuDataImpl(calendarMenuData.value.yearMonth.minusMonth())
    }

    override fun onLoadNextMonth() {
        calendarMenuData.value =
            CalendarMenuDataImpl(calendarMenuData.value.yearMonth.plusMonth())
    }

    override fun onLoadPreviousYear() {
        calendarMenuData.value =
            CalendarMenuDataImpl(calendarMenuData.value.yearMonth.minusYear())
    }

    override fun onLoadNextYear() {
        calendarMenuData.value =
            CalendarMenuDataImpl(calendarMenuData.value.yearMonth.plusYear())
    }

    inner class CalendarMenuDataImpl(val yearMonth: YearMonth) : CalendarMenuData<LocalDate> {

        override val daysMatrix: List<List<CalendarMenuData.MonthDay<LocalDate>>>
        override val yearName: String = yearFormat.format(yearMonth)
        override val monthName: String = monthFormat.format(yearMonth)

        init {
            val firstDayCurrentMonth = yearMonth.firstDay
            val previousMonth = yearMonth.minusMonth()
            val nextMonth = yearMonth.plusMonth()
            val previousMonthLastDay = previousMonth.numberOfDays

            var prevMonthDaysCount = firstDayCurrentMonth.dayOfWeek.dayNumber

            var monthDayCount = 1
            var nextMonthDayCount = 1

            daysMatrix = buildList {
                repeat(CALENDAR_WEEKS) {
                    buildList {
                        repeat(DAYS_IN_WEEK) {
                            add(
                                when {
                                    prevMonthDaysCount > 0 -> {
                                        val date = previousMonth.onDay(
                                            previousMonthLastDay - prevMonthDaysCount-- + 1
                                        )
                                        CalendarMenuData.MonthDay(
                                            date = date,
                                            dateString = date.day.toString(),
                                            isOutOfMonth = true,
                                        )
                                    }

                                    monthDayCount <= yearMonth.numberOfDays -> {
                                        val date = yearMonth.onDay(monthDayCount++)
                                        CalendarMenuData.MonthDay(
                                            date = date,
                                            dateString = date.day.toString(),
                                            isOutOfMonth = false,
                                        )
                                    }

                                    else -> {
                                        val date = nextMonth.onDay(nextMonthDayCount++)
                                        CalendarMenuData.MonthDay(
                                            date = date,
                                            dateString = date.day.toString(),
                                            isOutOfMonth = true,
                                        )
                                    }
                                }
                            )
                        }
                    }.let(::add)
                }
            }
        }
    }

    companion object {

        private const val CALENDAR_WEEKS = 6
        private val DAYS_IN_WEEK = DayOfWeek.entries.count()

        private val DayOfWeek.dayNumber
            get() = when (this) {
                DayOfWeek.SUNDAY -> 0
                DayOfWeek.MONDAY -> 1
                DayOfWeek.TUESDAY -> 2
                DayOfWeek.WEDNESDAY -> 3
                DayOfWeek.THURSDAY -> 4
                DayOfWeek.FRIDAY -> 5
                DayOfWeek.SATURDAY -> 6
            }

        /**
         * Default [Saver] implementation for [CalendarDatePickerStateImpl].
         *
         * @param dateFormat The [DateTimeFormat] used to parse and format the date string.
         * @param yearFormat The [DateTimeFormat] used to format the displayed year in the calendar.
         * @param monthFormat The [DateTimeFormat] used to format the displayed month in the
         * calendar.
         * @param selectableDates A [SelectableDates] instance that determines which dates are
         * selectable.
         * @param onFieldValidation Callback invoked when the state tries to parse the field value
         * or format the [CalendarDatePickerState.selectedDate] when one of them is changed.
         */
        fun Saver(
            dateFormat: DateTimeFormat<LocalDate>,
            yearFormat: DateTimeFormat<YearMonth>,
            monthFormat: DateTimeFormat<YearMonth>,
            selectableDates: SelectableDates<LocalDate>,
            onFieldValidation: (Boolean?) -> Unit
        ): Saver<CalendarDatePickerState<LocalDate>, *> = listSaver(
            save = { listOf(it.today, it.selectedDate) },
            restore = {
                CalendarDatePickerStateImpl(
                    today = it[0]!!,
                    initialSelectedDate = it[1],
                    dateFormat = dateFormat,
                    yearFormat = yearFormat,
                    monthFormat = monthFormat,
                    selectableDates = selectableDates,
                    onFieldValidation = onFieldValidation
                )
            }
        )
    }
}
