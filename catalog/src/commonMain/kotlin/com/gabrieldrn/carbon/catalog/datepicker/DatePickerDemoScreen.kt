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

package com.gabrieldrn.carbon.catalog.datepicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.datepicker.CalendarDatePicker
import com.gabrieldrn.carbon.datepicker.rememberCalendarDatePickerState
import com.gabrieldrn.carbon.textinput.TextInputState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun DatePickerDemoScreen(
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var inputState by remember { mutableStateOf(TextInputState.Enabled) }
    val dateFormat = remember {
        LocalDate.Format {
            year(); char('/'); monthNumber(); char('/'); day()
        }
    }
    val pickerState = rememberCalendarDatePickerState(
        dateFormat = dateFormat,
        onFieldValidation = {
            inputState = if (it) TextInputState.Enabled else TextInputState.Error
        }
    )
    var fieldValue by remember {
        mutableStateOf(pickerState.selectedDate?.let(dateFormat::format) ?: "")
    }

    DemoScreen(
        demoContent = {
            CalendarDatePicker(
                datePickerState = pickerState,
                label = "Label",
                value = fieldValue,
                expanded = isExpanded,
                placeholderText = "yyyy/mm/dd",
                helperText = "year/month/day",
                inputState = inputState,
                onValueChange = { fieldValue = it },
                onExpandedChange = { isExpanded = it },
                onDismissRequest = { isExpanded = false },
            )
        },
        modifier = modifier
    )
}
