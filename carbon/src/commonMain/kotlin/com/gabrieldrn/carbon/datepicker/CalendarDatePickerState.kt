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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char

// TODO KDoc
public interface CalendarDatePickerState {
    public var selectedDate: LocalDate?
    public var onUpdateFieldCallback: ((String) -> Unit)?
    public fun updateFieldValue(newValue: String)
}

// TODO Unit tests
@Composable
public fun rememberCalendarDatePickerState(
    initialSelectedDate: LocalDate? = null,
    dateFormat: DateTimeFormat<LocalDate> = LocalDate.Format {
        year(); char('/'); monthNumber(); char('/'); day()
    },
    confirmValueChange: (LocalDate?) -> Boolean = { true },
    onFieldValidityChanged: (Boolean) -> Unit
): CalendarDatePickerState =
    rememberSaveable(
        saver = CalendarDatePickerStateImpl.Saver(
            dateFormat = dateFormat,
            confirmValueChange = confirmValueChange,
            onFieldValidityChanged = onFieldValidityChanged
        )
    ) {
        CalendarDatePickerStateImpl(
            initialSelectedDate = initialSelectedDate,
            dateFormat = dateFormat,
            confirmValueChange = confirmValueChange,
            onFieldValidityChanged = onFieldValidityChanged
        )
    }

private class CalendarDatePickerStateImpl(
    initialSelectedDate: LocalDate?,
    val dateFormat: DateTimeFormat<LocalDate>,
    val confirmValueChange: (LocalDate?) -> Boolean,
    val onFieldValidityChanged: (Boolean) -> Unit
) : CalendarDatePickerState {

    private var _selectedDate by mutableStateOf(initialSelectedDate)

    override var selectedDate: LocalDate?
        get() = _selectedDate
        set(value) {
            if (confirmValueChange(value)) {
                if (value == null) {
                    onUpdateFieldCallback?.invoke("")
                    onFieldValidityChanged(true)
                } else {
                    try {
                        onUpdateFieldCallback?.invoke(dateFormat.format(value))
                        onFieldValidityChanged(true)
                    } catch (_: IllegalArgumentException) {
                        onFieldValidityChanged(false)
                    }
                }
                _selectedDate = value
            }
        }

    override var onUpdateFieldCallback: ((String) -> Unit)? = null

    override fun updateFieldValue(newValue: String) {
        try {
            dateFormat.parse(newValue)
                .takeIf(confirmValueChange)
                .let { _selectedDate = it }
            onFieldValidityChanged(true)
        } catch (_: IllegalArgumentException) {
            onFieldValidityChanged(false)
        }
        onUpdateFieldCallback?.invoke(newValue)
    }

    companion object {
        fun Saver(
            dateFormat: DateTimeFormat<LocalDate>,
            confirmValueChange: (LocalDate?) -> Boolean,
            onFieldValidityChanged: (Boolean) -> Unit
        ): Saver<CalendarDatePickerState, *> = listSaver(
            save = { listOf(it.selectedDate) },
            restore = {
                CalendarDatePickerStateImpl(
                    initialSelectedDate = it.first(),
                    dateFormat = dateFormat,
                    confirmValueChange = confirmValueChange,
                    onFieldValidityChanged = onFieldValidityChanged
                )
            }
        )
    }
}
