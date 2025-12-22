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
 * Creates a [CalendarDatePickerState] that is remembered across compositions.
 *
 * @param today The current day represented by a [LocalDate].
 * @param initialSelectedDate The initial [LocalDate] to be selected, or `null` if no date is
 * selected.
 * @param dateFormat The [DateTimeFormat] used to parse and format the date string. Defaults to
 * `yyyy/MM/dd`.
 * @param confirmDateChange Callback invoked to confirm if a date change is allowed. Return `true`
 * to allow the change, `false` otherwise.
 * @param onFieldValidation Callback invoked when the state tries to parse the field value or
 * format the [CalendarDatePickerState.selectedDate] when one of them is changed. Returns `true`
 * when successful, `false` otherwise, or `null` is the field is empty.
 */
@Composable
public fun rememberCalendarDatePickerState(
    today: LocalDate,
    initialSelectedDate: LocalDate? = null,
    dateFormat: DateTimeFormat<LocalDate> = LocalDate.Format {
        year(); char('/'); monthNumber(); char('/'); day()
    },
    confirmDateChange: (LocalDate?) -> Boolean = { true },
    onFieldValidation: (Boolean?) -> Unit
): CalendarDatePickerState =
    rememberSaveable(
        saver = CalendarDatePickerStateImpl.Saver(
            dateFormat = dateFormat,
            confirmValueChange = confirmDateChange,
            onFieldValidation = onFieldValidation
        )
    ) {
        CalendarDatePickerStateImpl(
            today = today,
            initialSelectedDate = initialSelectedDate,
            dateFormat = dateFormat,
            confirmDateChange = confirmDateChange,
            onFieldValidation = onFieldValidation
        )
    }

@Stable
private class CalendarDatePickerStateImpl(
    initialSelectedDate: LocalDate?,
    override val today: LocalDate,
    val dateFormat: DateTimeFormat<LocalDate>,
    val confirmDateChange: (LocalDate?) -> Boolean,
    val onFieldValidation: (Boolean?) -> Unit
) : CalendarDatePickerState {

    private var _selectedDate by mutableStateOf(initialSelectedDate)

    override var selectedDate: LocalDate?
        get() = _selectedDate
        set(value) {
            if (confirmDateChange(value)) {
                if (value == null) {
                    updateFieldCallback?.invoke("")
                    onFieldValidation(true)
                } else {
                    try {
                        updateFieldCallback?.invoke(dateFormat.format(value))
                        onFieldValidation(true)
                    } catch (_: IllegalArgumentException) {
                        onFieldValidation(false)
                    }
                }
                _selectedDate = value
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
                    .takeIf(confirmDateChange)
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
         * @param confirmValueChange Callback invoked to confirm if a date change is allowed.
         * @param onFieldValidation Callback invoked when the state tries to parse the field value
         * or format the [CalendarDatePickerState.selectedDate] when one of them is changed.
         */
        fun Saver(
            dateFormat: DateTimeFormat<LocalDate>,
            confirmValueChange: (LocalDate?) -> Boolean,
            onFieldValidation: (Boolean?) -> Unit
        ): Saver<CalendarDatePickerState, *> = listSaver(
            save = { listOf(it.today, it.selectedDate) },
            restore = {
                CalendarDatePickerStateImpl(
                    today = it[0]!!,
                    initialSelectedDate = it[1],
                    dateFormat = dateFormat,
                    confirmDateChange = confirmValueChange,
                    onFieldValidation = onFieldValidation
                )
            }
        )
    }
}
