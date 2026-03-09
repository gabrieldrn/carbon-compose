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
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char

@Stable
public interface SimpleDateInputState {

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

@Composable
public fun rememberSimpleDateInputState(
    initialSelectedDate: LocalDate? = null,
    dateFormat: DateTimeFormat<LocalDate> = LocalDate.Format {
        monthNumber(); char( '/'); year()
    },
    onFieldValidation: (Boolean?) -> Unit = {}
): SimpleDateInputState = rememberSaveable(
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
private class SimpleDateInputStateImpl(
    initialSelectedDate: LocalDate?,
    val dateFormat: DateTimeFormat<LocalDate>,
    val onFieldValidation: (Boolean?) -> Unit
) : SimpleDateInputState {

    private var _selectedDate by mutableStateOf(initialSelectedDate)

    override var selectedDate: LocalDate?
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
         * @param dateFormat The [DateTimeFormat] used to parse and format the date string.
         * @param onFieldValidation Callback invoked when the state tries to parse the field value
         * or format the [SimpleDateInputStateImpl.selectedDate] when one of them is changed.
         */
        fun Saver(
            dateFormat: DateTimeFormat<LocalDate>,
            onFieldValidation: (Boolean?) -> Unit
        ): Saver<SimpleDateInputStateImpl, *> = listSaver(
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
