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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.catalog.Res
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.catalog.ic_checkmark_filled
import com.gabrieldrn.carbon.catalog.ic_unknown_filled
import com.gabrieldrn.carbon.catalog.ic_warning_filled
import com.gabrieldrn.carbon.datepicker.CalendarDatePicker
import com.gabrieldrn.carbon.datepicker.SimpleDateInput
import com.gabrieldrn.carbon.datepicker.rememberApproximateSimpleDateInputState
import com.gabrieldrn.carbon.datepicker.rememberCalendarDatePickerState
import com.gabrieldrn.carbon.datepicker.rememberMemorableSimpleDateInputState
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.layerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.tab.TabItem
import com.gabrieldrn.carbon.tag.ReadOnlyTag
import com.gabrieldrn.carbon.tag.TagSize
import com.gabrieldrn.carbon.tag.TagType
import com.gabrieldrn.carbon.textinput.TextInputState
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.format.char
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.yearMonth
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

private enum class DatePickerVariant(val label: String) {
    DateInput("Date input"),
    CalendarDatePicker("Calendar date picker");

    companion object {
        fun fromLabel(label: String) = DatePickerVariant.entries.first { it.label == label }
    }
}

private enum class SimpleDateInputType { Memorable, Approximate }

private val variants = DatePickerVariant.entries.map { TabItem(it.label) }

private val textInputStateOptions = TextInputState.entries.toDropdownOptions()
private val simpleDateInputOptions = SimpleDateInputType.entries.toDropdownOptions()

private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

private val localDateFormat =
    LocalDate.Format { year(); char('/'); monthNumber(); char('/'); day() }

private val yearMonthFormat =
    YearMonth.Format { monthNumber(); char('/'); year() }

@OptIn(ExperimentalTime::class)
@Composable
fun DatePickerDemoScreen(
    modifier: Modifier = Modifier
) {
    var isFieldValid by remember { mutableStateOf<Boolean?>(null) }

    var inputState by remember { mutableStateOf(TextInputState.Enabled) }
    var simpleDateInputType by remember { mutableStateOf(SimpleDateInputType.Memorable) }

    val effectiveInputState = remember(inputState, isFieldValid) {
        when (inputState) {
            TextInputState.Warning if isFieldValid == false -> TextInputState.Error
            TextInputState.Enabled if isFieldValid == false -> TextInputState.Error
            else -> inputState
        }
    }

    DemoScreen(
        variants = variants,
        demoContent = { tab ->
            val datePickerVariant = DatePickerVariant.fromLabel(tab.label)

            LaunchedEffect(datePickerVariant) {
                inputState = TextInputState.Enabled
                isFieldValid = null
            }

            when (datePickerVariant) {
                DatePickerVariant.DateInput -> SimpleDateInputDemo(
                    simpleDateInputType = simpleDateInputType,
                    effectiveInputState = effectiveInputState,
                    isFieldValid = isFieldValid,
                    onFieldValidation = remember { { isFieldValid = it } }
                )
                DatePickerVariant.CalendarDatePicker -> CalendarDatePickerDemo(
                    variant = datePickerVariant,
                    effectiveInputState = effectiveInputState,
                    isFieldValid = isFieldValid,
                    onFieldValidation = remember { { isFieldValid = it } }
                )
            }
        },
        demoParametersContent = { tab ->
            val variant = DatePickerVariant.fromLabel(tab.label)

            Dropdown(
                label = "Input state",
                placeholder = "Choose an option",
                options = textInputStateOptions,
                selectedOption = inputState,
                onOptionSelected = { inputState = it }
            )

            if (variant == DatePickerVariant.DateInput) {
                Dropdown(
                    label = "Date type",
                    placeholder = "Choose an option",
                    options = simpleDateInputOptions,
                    selectedOption = simpleDateInputType,
                    onOptionSelected = { simpleDateInputType = it }
                )
            }
        },
        modifier = modifier
    )
}

@Composable
private fun SimpleDateInputDemo(
    simpleDateInputType: SimpleDateInputType,
    effectiveInputState: TextInputState,
    isFieldValid: Boolean?,
    onFieldValidation: (Boolean?) -> Unit
) {
    val state = when (simpleDateInputType) {
        SimpleDateInputType.Memorable -> rememberMemorableSimpleDateInputState(
            today,
            localDateFormat,
            onFieldValidation = onFieldValidation
        )
        SimpleDateInputType.Approximate -> rememberApproximateSimpleDateInputState(
            today.yearMonth,
            yearMonthFormat,

            )
    }

    var fieldValue by remember(state) {
        mutableStateOf(
            state
                .selectedDate
                ?.let {
                    when (it) {
                        is LocalDate -> localDateFormat.format(it)
                        is YearMonth -> yearMonthFormat.format(it)
                        else -> ""
                    }
                }
                ?: ""
        )
    }

    SimpleDateInput(
        state = state,
        label = "Label",
        placeholderText = when (simpleDateInputType) {
            SimpleDateInputType.Memorable -> "yyyy/mm/dd"
            SimpleDateInputType.Approximate -> "mm/yyyy"
        },
        helperText = when (simpleDateInputType) {
            SimpleDateInputType.Memorable -> "year/month/day"
            SimpleDateInputType.Approximate -> "month/year"
        },
        value = fieldValue,
        inputState = effectiveInputState,
        onValueChange = { fieldValue = it },
        modifier = Modifier
            .width(288.dp)
            .padding(vertical = SpacingScale.spacing07)
    )

    CarbonLayer {
        FieldDebugSection(
            isFieldValid = isFieldValid,
            inputValue = state.selectedDate.toString().uppercase(),
            modifier = Modifier.padding(top = SpacingScale.spacing05)
        )
    }
}

@Composable
private fun CalendarDatePickerDemo(
    variant: DatePickerVariant,
    effectiveInputState: TextInputState,
    isFieldValid: Boolean?,
    onFieldValidation: (Boolean?) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(variant) {
        isExpanded = false
    }

    val state = rememberCalendarDatePickerState(
        today = today,
        dateFormat = localDateFormat,
        selectableDates = { it != today.plus(1, DateTimeUnit.DAY) },
        onFieldValidation = onFieldValidation
    )

    var fieldValue by remember {
        mutableStateOf(
            state
                .selectedDate
                ?.let(localDateFormat::format)
                ?: ""
        )
    }

    CalendarDatePicker(
        datePickerState = state,
        label = "Label",
        value = fieldValue,
        expanded = isExpanded,
        placeholderText = "yyyy/mm/dd",
        helperText = "year/month/day",
        inputState = effectiveInputState,
        onValueChange = { fieldValue = it },
        onExpandedChange = { isExpanded = it },
        onDismissRequest = { isExpanded = false },
        modifier = Modifier.padding(vertical = SpacingScale.spacing07)
    )

    CarbonLayer {
        FieldDebugSection(
            isFieldValid = isFieldValid,
            inputValue = state.selectedDate.toString().uppercase(),
            modifier = Modifier.padding(top = SpacingScale.spacing05)
        )
    }
}

@Composable
private fun FieldDebugSection(
    isFieldValid: Boolean?,
    inputValue: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .layerBackground()
            .padding(SpacingScale.spacing05),
        verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing03)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BasicText(
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
            BasicText(
                text = "Selected date",
                style = Carbon.typography.body01.copy(color = Carbon.theme.textHelper),
                modifier = Modifier.padding(end = SpacingScale.spacing03)
            )

            BasicText(
                text = inputValue,
                style = Carbon.typography.code02.copy(color = Carbon.theme.textPrimary)
            )
        }
    }
}
