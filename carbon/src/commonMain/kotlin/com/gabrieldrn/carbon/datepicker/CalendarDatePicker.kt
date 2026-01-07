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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.requestFocus
import androidx.compose.ui.semantics.role
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
import com.gabrieldrn.carbon.icons.calendarIcon
import com.gabrieldrn.carbon.textinput.ClickableTrailingIcon
import com.gabrieldrn.carbon.textinput.TextInputColors
import com.gabrieldrn.carbon.textinput.TextInputState
import com.gabrieldrn.carbon.textinput.inputDecorator
import kotlinx.datetime.YearMonth
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.minusMonth
import kotlinx.datetime.minusYear
import kotlinx.datetime.plusMonth
import kotlinx.datetime.plusYear
import kotlinx.datetime.yearMonth

/**
 * # Date picker - Calendar - Single date
 *
 * Calendar pickers default to showing today’s date when opened and only one month is shown at a
 * time. Calendar pickers allow users to navigate through months and years, however they work best
 * when used for recent or near future dates. If a user needs to input a far distant or future date
 * consider having the calendar default open to a more convenient day.
 *
 * ## About the variant
 * In a single date picker a user has the option to either manually input a date in the text field
 * or select one specific date from the menu calendar. It requires a day, month, and year to be
 * selected.
 *
 * ## Compose implementation
 * This composable uses [kotlinx.datetime](https://github.com/Kotlin/kotlinx-datetime) for date
 * handling and formatting.
 *
 * (From [Date picker documentation](https://carbondesignsystem.com/components/date-picker/usage/#calendar-pickers))
 *
 * @param datePickerState A [CalendarDatePickerState] that is used to control the state of the
 * date picker.
 * @param label Text that informs the user about the content they need to type in the field.
 * @param value The input [String] text to be shown in the text input.
 * @param expanded Whether the calendar menu is currently expanded.
 * @param onValueChange Callback triggered when the input service updates the text. An updated text
 * comes as a parameter of the callback.
 * @param onExpandedChange Callback triggered when the expanded state of the calendar menu should be
 * changed.
 * @param onDismissRequest Callback triggered when the calendar menu should be dismissed.
 * @param modifier Optional [Modifier] for this text input.
 * @param placeholderText Optional text that provides hints or examples of what to type.
 * @param helperText Optional helper text is pertinent information that assists the user in
 * correctly completing a field. It is often used to explain the correct data format.
 * @param inputState The interactive state of the text input.
 * @param keyboardOptions Software keyboard options that contains configuration such as
 * [androidx.compose.ui.text.input.KeyboardType] and [androidx.compose.ui.text.input.ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param dayOfWeekNames Object providing the names of the days of the week to be displayed in the
 * calendar.
 * @param yearFormat The [DateTimeFormat] used to format the displayed year in the calendar.
 * @param monthFormat The [DateTimeFormat] used to format the displayed month in the calendar.
 * @param interactionSource The [MutableInteractionSource] representing the stream of
 * [androidx.compose.foundation.interaction.Interaction]s for this TextField. You can create and
 * pass in your own remembered [MutableInteractionSource] if you want to observe
 * [androidx.compose.foundation.interaction.Interaction]s and customize the appearance / behavior of
 * this TextField in different [androidx.compose.foundation.interaction.Interaction]s.
 */
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
    yearFormat: DateTimeFormat<YearMonth> = YearMonth.Format {
        year()
    },
    monthFormat: DateTimeFormat<YearMonth> = YearMonth.Format {
        monthName(MonthNames.ENGLISH_FULL)
    },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val theme = Carbon.theme
    val typography = Carbon.typography
    val colors = TextInputColors.colors()

    datePickerState.updateFieldCallback = onValueChange

    val fieldTextColor by colors.fieldTextColor(state = inputState)
    val fieldTextStyle by remember(fieldTextColor) {
        mutableStateOf(typography.bodyCompact01.copy(color = fieldTextColor))
    }

    var calendarYearMonth by remember(datePickerState.selectedDate) {
        mutableStateOf((datePickerState.selectedDate ?: datePickerState.today).yearMonth)
    }

    val calendar = remember(calendarYearMonth) {
        getCalendarMenuData(calendarYearMonth)
    }

    BasicTextField(
        value = value,
        onValueChange = datePickerState::updateFieldValue,
        modifier = modifier
            .testTag(CalendarDatePickerTestTags.TEXT_FIELD)
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
                    TextInputState.Disabled -> Modifier.semantics {
                        role = Role.ValuePicker
                        disabled()
                    }
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
            trailingIcon = {
                ClickableTrailingIcon(
                    icon = calendarIcon,
                    isEnabled = inputState != TextInputState.Disabled &&
                        inputState != TextInputState.ReadOnly,
                    onClick = { onExpandedChange(true) }
                )
            },
            stateIcon = {},
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
                            datePickerState = datePickerState,
                            onDayClicked = {
                                datePickerState.selectedDate = it
                                onExpandedChange(false)
                            },
                            onLoadPreviousMonth = {
                                calendarYearMonth = calendarYearMonth.minusMonth()
                            },
                            onLoadNextMonth = {
                                calendarYearMonth = calendarYearMonth.plusMonth()
                            },
                            onLoadPreviousYear = {
                                calendarYearMonth = calendarYearMonth.minusYear()
                            },
                            onLoadNextYear = {
                                calendarYearMonth = calendarYearMonth.plusYear()
                            },
                            dayOfWeekNames = dayOfWeekNames,
                            yearFormat = yearFormat,
                            monthFormat = monthFormat,
                            modifier = Modifier
                                .onPreviewKeyEvent {
                                    if (it.key == Key.Escape && it.type == KeyEventType.KeyDown) {
                                        onDismissRequest()
                                        true
                                    } else {
                                        false
                                    }
                                }
                                .testTag(CalendarDatePickerTestTags.MENU)
                        )
                    }
                }
            }
        )
    )
}
