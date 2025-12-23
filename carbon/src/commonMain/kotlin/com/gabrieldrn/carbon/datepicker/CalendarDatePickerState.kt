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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.gabrieldrn.carbon.datepicker.CalendarDatePickerStateImpl.Companion.Saver
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char

/**
 * A state holder for the Calendar Date Picker to observe the selected date and synchronize it with
 * a text field.
 */
@Stable
public interface CalendarDatePickerState {

    /**
     * Determines which dates are selectable in the calendar.
     *
     * This interface allows customization of which dates can be selected by the user.
     * For example, you can disable weekends, past dates, or specific dates.
     */
    public val selectableDates: SelectableDates

    /**
     * The current day represented by a [LocalDate].
     */
    public val today: LocalDate

    /**
     * The currently selected [LocalDate], or `null` if no date is selected.
     */
    public var selectedDate: LocalDate?

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
 * Example:
 * ```kotlin
 * // Only allow future dates
 * SelectableDates { date -> date > today }
 *
 * // Only allow weekdays
 * SelectableDates { date ->
 *     date.dayOfWeek != DayOfWeek.SATURDAY && date.dayOfWeek != DayOfWeek.SUNDAY
 * }
 * ```
 */
public fun interface SelectableDates {

    /**
     * Determines if the given [date] is selectable.
     *
     * @param date The date to check.
     * @return `true` if the date is selectable, `false` otherwise.
     */
    public fun isSelectable(date: LocalDate): Boolean
}

/**
 * Creates a [CalendarDatePickerState] that is remembered across compositions.
 *
 * @param today The current day represented by a [LocalDate].
 * @param initialSelectedDate The initial [LocalDate] to be selected, or `null` if no date is
 * selected.
 * @param dateFormat The [DateTimeFormat] used to parse and format the date string. Defaults to
 * `yyyy/MM/dd`.
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
    selectableDates: SelectableDates = SelectableDates { true },
    onFieldValidation: (Boolean?) -> Unit
): CalendarDatePickerState =
    rememberSaveable(
        saver = CalendarDatePickerStateImpl.Saver(
            dateFormat = dateFormat,
            selectableDates = selectableDates,
            onFieldValidation = onFieldValidation
        )
    ) {
        CalendarDatePickerStateImpl(
            today = today,
            initialSelectedDate = initialSelectedDate,
            dateFormat = dateFormat,
            selectableDates = selectableDates,
            onFieldValidation = onFieldValidation
        )
    }

@Stable
private class CalendarDatePickerStateImpl(
    initialSelectedDate: LocalDate?,
    override val today: LocalDate,
    val dateFormat: DateTimeFormat<LocalDate>,
    override val selectableDates: SelectableDates,
    val onFieldValidation: (Boolean?) -> Unit
) : CalendarDatePickerState {

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
        }

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
        updateFieldCallback?.invoke(newValue)
    }

    companion object {

        /**
         * Default [Saver] implementation for [CalendarDatePickerStateImpl].
         *
         * @param dateFormat The [DateTimeFormat] used to parse and format the date string.
         * @param selectableDates A [SelectableDates] instance that determines which dates are
         * selectable.
         * @param onFieldValidation Callback invoked when the state tries to parse the field value
         * or format the [CalendarDatePickerState.selectedDate] when one of them is changed.
         */
        fun Saver(
            dateFormat: DateTimeFormat<LocalDate>,
            selectableDates: SelectableDates,
            onFieldValidation: (Boolean?) -> Unit
        ): Saver<CalendarDatePickerState, *> = listSaver(
            save = { listOf(it.today, it.selectedDate) },
            restore = {
                CalendarDatePickerStateImpl(
                    today = it[0]!!,
                    initialSelectedDate = it[1],
                    dateFormat = dateFormat,
                    selectableDates = selectableDates,
                    onFieldValidation = onFieldValidation
                )
            }
        )
    }
}
