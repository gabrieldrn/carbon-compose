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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.gabrieldrn.carbon.datepicker.SimpleDateInputStateImpl.Companion.Saver
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char

/**
 * A state holder for [SimpleDateInput] that manages the currently selected date and synchronizes
 * it with the text field value.
 *
 * This interface provides the core state management for simple date input fields, handling date
 * parsing, validation, and formatting without a calendar popup menu.
 *
 * @param T The date type (e.g., [kotlinx.datetime.LocalDate] or [kotlinx.datetime.YearMonth]).
 */
@Stable
public interface SimpleDateInputState<T> {

    /**
     * The currently selected date, or `null` if no date is selected.
     */
    public var selectedDate: T?

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
 * Creates a [SimpleDateInputState] that is remembered across compositions for precise dates
 * (day, month and year). Uses `kotlinx.datetime`'s [LocalDate] as the date type.
 *
 * @param initialSelectedDate The initial [LocalDate] to be selected, or `null` if no date is
 * selected.
 * @param dateFormat The [DateTimeFormat] used to parse typed input and format the date when
 * [SimpleDateInputState.selectedDate] is set programmatically. Defaults to `dd/MM/yyyy`.
 * @param onFieldValidation Callback invoked whenever the state attempts to parse typed input or
 * to format [SimpleDateInputState.selectedDate]. Receives `true` when parsing or formatting
 * succeeds, `false` when it fails, or `null` when the field is empty.
 */
@Composable
public fun rememberMemorableSimpleDateInputState(
    initialSelectedDate: LocalDate? = null,
    dateFormat: DateTimeFormat<LocalDate> = LocalDate.Format {
        day(); char('/'); monthNumber(); char( '/'); year()
    },
    onFieldValidation: (Boolean?) -> Unit = {}
): SimpleDateInputState<LocalDate> = rememberSaveable(
    dateFormat,
    onFieldValidation,
    saver = SimpleDateInputStateImpl.Saver(
        dateFormat = dateFormat,
        onFieldValidation = onFieldValidation
    )
) {
    SimpleDateInputStateImpl(
        initialSelectedDate = initialSelectedDate,
        dateFormat = dateFormat,
        onFieldValidation = onFieldValidation
    )
}

/**
 * Creates a [SimpleDateInputState] that is remembered across compositions for approximate dates
 * (month and year only). Uses `kotlinx.datetime`'s [YearMonth] as the date type.
 *
 * @param initialSelectedDate The initial [YearMonth] to be selected, or `null` if no date is
 * selected.
 * @param dateFormat The [DateTimeFormat] used to parse typed input and format the date when
 * [SimpleDateInputState.selectedDate] is set programmatically. Defaults to `dd/MM/yyyy`.
 * @param onFieldValidation Callback invoked whenever the state attempts to parse typed input or
 * to format [SimpleDateInputState.selectedDate]. Receives `true` when parsing or formatting
 * succeeds, `false` when it fails, or `null` when the field is empty.
 */
@Composable
public fun rememberApproximateSimpleDateInputState(
    initialSelectedDate: YearMonth? = null,
    dateFormat: DateTimeFormat<YearMonth> = YearMonth.Format {
        monthNumber(); char( '/'); year()
    },
    onFieldValidation: (Boolean?) -> Unit = {}
): SimpleDateInputState<YearMonth> = rememberSaveable(
    dateFormat,
    onFieldValidation,
    saver = SimpleDateInputStateImpl.Saver(
        dateFormat = dateFormat,
        onFieldValidation = onFieldValidation
    )
) {
    SimpleDateInputStateImpl(
        initialSelectedDate = initialSelectedDate,
        dateFormat = dateFormat,
        onFieldValidation = onFieldValidation
    )
}

@Stable
private class SimpleDateInputStateImpl<T>(
    initialSelectedDate: T?,
    val dateFormat: DateTimeFormat<T>,
    val onFieldValidation: (Boolean?) -> Unit
) : SimpleDateInputState<T> {

    private var _selectedDate by mutableStateOf(initialSelectedDate)

    override var selectedDate: T?
        get() = _selectedDate
        set(value) {
            if (value == null) {
                updateFieldCallback?.invoke("")
                onFieldValidation(null)
                _selectedDate = null
            } else {
                try {
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
                _selectedDate = dateFormat.parse(newValue)
                onFieldValidation(true)
            } catch (_: IllegalArgumentException) {
                onFieldValidation(false)
            }
        }
        updateFieldCallback?.invoke(newValue)
    }

    companion object {

        /**
         * Default [Saver] implementation for [SimpleDateInputStateImpl].
         *
         * @param T The type of date being managed (e.g., [LocalDate] or [YearMonth]).
         * @param dateFormat The [DateTimeFormat] used to parse and format the date string.
         * @param onFieldValidation Callback invoked when the state tries to parse the field value
         * or format the [SimpleDateInputStateImpl.selectedDate] when one of them is changed.
         */
        fun <T> Saver(
            dateFormat: DateTimeFormat<T>,
            onFieldValidation: (Boolean?) -> Unit
        ): Saver<SimpleDateInputStateImpl<T>, *> = listSaver(
            save = { listOf(it.selectedDate) },
            restore = {
                SimpleDateInputStateImpl(
                    initialSelectedDate = it[0],
                    dateFormat = dateFormat,
                    onFieldValidation = onFieldValidation
                )
            }
        )
    }
}
