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

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.requestFocus
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.common.semantics.readOnly
import com.gabrieldrn.carbon.icons.CalendarIcon
import com.gabrieldrn.carbon.textinput.TextInputColors
import com.gabrieldrn.carbon.textinput.TextInputState
import com.gabrieldrn.carbon.textinput.TextInputState.Companion.isEnabled
import com.gabrieldrn.carbon.textinput.inputDecorator
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.minusMonth
import kotlinx.datetime.plusMonth
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.yearMonth
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

// TODO UI tests
// TODO KDoc
// TODO GH Pages documentation + mention usage of kotlinx.datetime
@ExperimentalTime
@Composable
public fun CalendarDatePicker(
    datePickerState: CalendarDatePickerState,
    label: String,
    value: String,
    expanded: Boolean,
    onValueChange: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    helperText: String = "",
    inputState: TextInputState = TextInputState.Enabled,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    dayOfWeekNames: DayOfWeekNames = DayOfWeekNames.ENGLISH_ABBREVIATED,
    titleYearMonthFormat: DateTimeFormat<YearMonth> = YearMonth.Format {
        monthName(MonthNames.ENGLISH_FULL); char(' '); year()
    },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val theme = Carbon.theme
    val typography = Carbon.typography
    val colors = TextInputColors.colors()

    datePickerState.onUpdateFieldCallback = onValueChange

    val fieldTextColor by colors.fieldTextColor(state = inputState)
    val fieldTextStyle by remember(fieldTextColor) {
        mutableStateOf(typography.bodyCompact01.copy(color = fieldTextColor))
    }

    val today by remember {
        mutableStateOf(
            Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date
        )
    }

    var calendarYearMonth by remember {
        mutableStateOf(today.yearMonth)
    }

    val calendar = remember(calendarYearMonth) { getCalendarMenuData(calendarYearMonth) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is PressInteraction.Press && inputState.isEnabled) {
                onExpandedChange(true)
            }
        }
    }

    BasicTextField(
        value = value,
        onValueChange = datePickerState::updateFieldValue,
        modifier = modifier
            .width(calendarMenuWidth)
            .then(
                when (inputState) {
                    TextInputState.Enabled,
                    TextInputState.Warning,
                    TextInputState.Error -> Modifier
                        .onFocusEvent {
                            onExpandedChange(it.isFocused)
                        }
                        .semantics(mergeDescendants = true) {
                            requestFocus {
                                onExpandedChange(true)
                                true
                            }
                        }
                    TextInputState.Disabled -> Modifier.semantics { disabled() }
                    TextInputState.ReadOnly -> Modifier.readOnly(
                        role = Role.ValuePicker,
                        interactionSource = interactionSource,
                        mergeDescendants = true
                    )
                }
            ),
        enabled = inputState != TextInputState.Disabled,
        readOnly = inputState == TextInputState.ReadOnly,
        textStyle = fieldTextStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        maxLines = 1,
        minLines = 1,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(colors.fieldTextColor(state = inputState).value),
        decorationBox = inputDecorator(
            label = label,
            value = value,
            placeholderText = placeholderText,
            helperText = helperText,
            state = inputState,
            theme = theme,
            colors = colors,
            singleLine = true,
            interactionSource = interactionSource,
            counter = null,
            trailingIcon = null,
            stateIcon = { CalendarIcon() },
            popup = {
                if (expanded) {
                    Popup(
                        popupPositionProvider = object : PopupPositionProvider {
                            override fun calculatePosition(
                                anchorBounds: IntRect,
                                windowSize: IntSize,
                                layoutDirection: LayoutDirection,
                                popupContentSize: IntSize
                            ): IntOffset = IntOffset(
                                x = anchorBounds.left,
                                y = anchorBounds.top + anchorBounds.height
                            )
                        },
                        onDismissRequest = onDismissRequest,
                        properties = PopupProperties(focusable = true),
                    ) {
                        CalendarMenu(
                            calendar = calendar,
                            selectedDate = datePickerState.selectedDate,
                            onDayClicked = { datePickerState.selectedDate = it },
                            onLoadPreviousMonth = {
                                calendarYearMonth = calendarYearMonth.minusMonth()
                            },
                            onLoadNextMonth = {
                                calendarYearMonth = calendarYearMonth.plusMonth()
                            },
                            dayOfWeekNames = dayOfWeekNames,
                            titleYearMonthFormat = titleYearMonthFormat
                        )
                    }
                }
            }
        )
    )
}
