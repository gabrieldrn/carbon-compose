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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.catalog.Res
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.catalog.ic_checkmark_filled
import com.gabrieldrn.carbon.catalog.ic_unknown_filled
import com.gabrieldrn.carbon.catalog.ic_warning_filled
import com.gabrieldrn.carbon.datepicker.CalendarDatePicker
import com.gabrieldrn.carbon.datepicker.rememberCalendarDatePickerState
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.layerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.tag.ReadOnlyTag
import com.gabrieldrn.carbon.tag.TagSize
import com.gabrieldrn.carbon.tag.TagType
import com.gabrieldrn.carbon.textinput.TextInputState
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

private val textInputStateOptions = TextInputState.entries.toDropdownOptions()

@OptIn(ExperimentalTime::class)
@Composable
fun DatePickerDemoScreen(
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isFieldValid by remember { mutableStateOf<Boolean?>(null) }

    val dateFormat = remember {
        LocalDate.Format {
            year(); char('/'); monthNumber(); char('/'); day()
        }
    }

    val today = remember {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    val pickerState = rememberCalendarDatePickerState(
        today = today,
        dateFormat = dateFormat,
        selectableDates = { it != today.plus(1, DateTimeUnit.DAY) },
        onFieldValidation = { isFieldValid = it }
    )

    var fieldValue by remember {
        mutableStateOf(pickerState.selectedDate?.let(dateFormat::format) ?: "")
    }

    var inputState by remember { mutableStateOf(TextInputState.Enabled) }

    val effectiveInputState = remember(inputState, isFieldValid) {
        when (inputState) {
            TextInputState.Warning if isFieldValid == false -> TextInputState.Error
            TextInputState.Enabled if isFieldValid == false -> TextInputState.Error
            else -> inputState
        }
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
                inputState = effectiveInputState,
                onValueChange = { fieldValue = it },
                onExpandedChange = { isExpanded = it },
                onDismissRequest = { isExpanded = false },
            )
        },
        demoParametersContent = {
            Dropdown(
                label = "Input state",
                placeholder = "Choose an option",
                options = textInputStateOptions,
                selectedOption = inputState,
                onOptionSelected = { inputState = it }
            )

            Text(
                text = "Picker state data",
                style = Carbon.typography.heading01.copy(color = Carbon.theme.textPrimary),
            )

            CarbonLayer {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .layerBackground()
                        .padding(SpacingScale.spacing05),
                    verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing03)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Latest field validation event",
                            style = Carbon.typography.body01.copy(color = Carbon.theme.textHelper),
                            modifier = Modifier.padding(end = SpacingScale.spacing03)
                        )

                        when (isFieldValid) {
                            true -> ReadOnlyTag(
                                text = "OK",
                                type = TagType.Green,
                                icon = { painterResource(Res.drawable.ic_checkmark_filled) },
                                size = TagSize.Small
                            )
                            false -> ReadOnlyTag(
                                text = "ERROR",
                                type = TagType.Red,
                                icon = { painterResource(Res.drawable.ic_warning_filled) },
                                size = TagSize.Small
                            )
                            null -> ReadOnlyTag(
                                text = "EMPTY",
                                type = TagType.Gray,
                                icon = { painterResource(Res.drawable.ic_unknown_filled) },
                                size = TagSize.Small
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Selected date",
                            style = Carbon.typography.body01.copy(color = Carbon.theme.textHelper),
                            modifier = Modifier.padding(end = SpacingScale.spacing03)
                        )

                        Text(
                            text = pickerState.selectedDate.toString().uppercase(),
                            style = Carbon.typography.code02.copy(color = Carbon.theme.textPrimary)
                        )
                    }
                }
            }
        },
        modifier = modifier
    )
}
