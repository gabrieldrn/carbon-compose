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
    yearFormat: DateTimeFormat<YearMonth> = YearMonth.Format { year() },
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
        mutableStateOf(
            (datePickerState.selectedDate ?: datePickerState.today).yearMonth
        )
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
                        .onFocusEvent { onExpandedChange(it.isFocused) }
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

            // ✅ KEY FIX:
            // Calendar icon is shown ONLY in Enabled state.
            // Warning/Error icons are handled internally by inputDecorator.
            trailingIcon = {
                if (inputState == TextInputState.Enabled) {
                    ClickableTrailingIcon(
                        icon = calendarIcon,
                        isEnabled = true,
                        onClick = { onExpandedChange(true) }
                    )
                }
            },

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
                                .testTag(CalendarDatePickerTestTags.MENU)
                        )
                    }
                }
            }
        )
    )
}
