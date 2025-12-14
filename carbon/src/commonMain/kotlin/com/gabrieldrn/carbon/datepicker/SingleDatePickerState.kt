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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char

public class SingleDatePickerState(
    initialSelectedDate: LocalDate? = null,
    override val dateFormat: DateTimeFormat<LocalDate> = LocalDate.Format {
        year(); char('/'); monthNumber(); char('/'); day()
    },
    private val confirmValueChange: (LocalDate?) -> Boolean = { true }
) : DatePickerState {

    private var _selectedDate by mutableStateOf(initialSelectedDate)

    public var selectedDate: LocalDate?
        get() = _selectedDate
        set(value) {
            if (confirmValueChange(value)) {
                if (value == null) {
                    onUpdateFieldCallback?.invoke("")
                    _isFieldValueInvalid.value = false
                } else {
                    try {
                        onUpdateFieldCallback?.invoke(dateFormat.format(value))
                        _isFieldValueInvalid.value = false
                    } catch (_: IllegalArgumentException) {
                        _isFieldValueInvalid.value = true
                    }
                }
                _selectedDate = value
            }
        }

    private var _isFieldValueInvalid = mutableStateOf(false)

    public val isFieldValueInvalid: Boolean
        get() = _isFieldValueInvalid.value

    internal var onUpdateFieldCallback: ((String) -> Unit)? = null

    internal fun updateFieldValue(newValue: String) {
        try {
            dateFormat.parse(newValue)
                .takeIf(confirmValueChange)
                .let { _selectedDate = it }
            _isFieldValueInvalid.value = false
        } catch (_: IllegalArgumentException) {
            _isFieldValueInvalid.value = true
        }
        onUpdateFieldCallback?.invoke(newValue)
    }

// May be not implementable
//    internal fun onReleased() {
//        if (_isFieldValueInvalid.value) {
//            val rectifiedValue = _selectedDate?.let(dateFormat::format) ?: ""
//            onUpdateFieldCallback?.invoke(rectifiedValue)
//        }
//    }
}
