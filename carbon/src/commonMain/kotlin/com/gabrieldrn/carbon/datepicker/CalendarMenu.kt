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

import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.Res
import com.gabrieldrn.carbon.button.ButtonSize
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.carbon_datepicker_calendar_loadNextMonth_description
import com.gabrieldrn.carbon.carbon_datepicker_calendar_loadNextYear_description
import com.gabrieldrn.carbon.carbon_datepicker_calendar_loadPreviousMonth_description
import com.gabrieldrn.carbon.carbon_datepicker_calendar_loadPreviousYear_description
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.layerBackground
import com.gabrieldrn.carbon.foundation.color.layerHoverColor
import com.gabrieldrn.carbon.foundation.interaction.FocusIndication
import com.gabrieldrn.carbon.foundation.misc.Adaptation
import com.gabrieldrn.carbon.foundation.misc.LocalCarbonAdaptation
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.icons.chevronLeftIcon
import com.gabrieldrn.carbon.icons.chevronRightIcon
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.plus
import org.jetbrains.compose.resources.stringResource

internal val calendarMenuWidth = 288.dp

/**
 * Provides the calendar data structure for displaying a month view in a date picker calendar menu.
 *
 * This interface encapsulates all the information needed to render a calendar grid for a specific
 * month, including the dates to display and their formatting.
 *
 * @param T The date type (e.g., [kotlinx.datetime.LocalDate]).
 */
@Stable
public interface CalendarMenuData<T> {

    /**
     * A 6x7 matrix representing the calendar grid for a month.
     *
     * Each list represents a week (row), and each [MonthDay] within represents a day (column).
     * The matrix includes days from the previous and next months to fill out the grid.
     */
    public val daysMatrix: List<List<MonthDay<T>>>

    /**
     * The formatted year name to be displayed in the calendar header (e.g., "2026").
     */
    public val yearName: String

    /**
     * The formatted month name to be displayed in the calendar header (e.g., "March").
     */
    public val monthName: String

    /**
     * Represents a single day in the calendar grid.
     *
     * @param T The date type (e.g., [kotlinx.datetime.LocalDate]).
     * @property date The actual date value.
     * @property dateString The formatted string representation of the day (typically just the day
     * number, e.g., "15").
     * @property isOutOfMonth `true` if this day belongs to the previous or next month (appears
     * dimmed in the UI), `false` if it belongs to the current displayed month.
     */
    @Stable
    public data class MonthDay<T>(
        val date: T,
        val dateString: String,
        val isOutOfMonth: Boolean
    )
}

private fun Modifier.menuKeyboardNavigation(
    focusManager: FocusManager,
) = onPreviewKeyEvent {
    if (it.type == KeyEventType.KeyDown) when (it.key) {
        Key.DirectionLeft -> focusManager.moveFocus(FocusDirection.Previous)
        Key.DirectionRight -> focusManager.moveFocus(FocusDirection.Next)
        Key.DirectionUp -> focusManager.moveFocus(FocusDirection.Up)
        Key.DirectionDown -> focusManager.moveFocus(FocusDirection.Down)
        else -> false
    } else false
}

@Composable
internal fun <T> CalendarMenu(
    datePickerState: CalendarDatePickerState<T>,
    onDayClicked: (T) -> Unit,
    modifier: Modifier = Modifier,
    dayOfWeekNames: DayOfWeekNames = DayOfWeekNames.ENGLISH_ABBREVIATED,
) {
    val theme = Carbon.theme
    val focusManager = LocalFocusManager.current
    val adaptation = LocalCarbonAdaptation.current

    val regularTextStyle = Carbon.typography.bodyCompact01.copy(color = theme.textPrimary)

    val calendar by datePickerState.calendarMenuData

    val outOfMonthTextStyle = regularTextStyle.copy(color = theme.textSecondary)

    val todayDayTextStyle = regularTextStyle.copy(
        color = theme.linkPrimary,
        fontWeight = FontWeight.SemiBold
    )

    val disabledDayTextStyle = regularTextStyle.copy(color = theme.textDisabled)

    val selectedDayTextStyle = regularTextStyle.copy(
        color = theme.textOnColor,
        fontWeight = FontWeight.SemiBold
    )

    val dayItemIndication = remember(theme) { FocusIndication(theme) }

    val rotatedDayOfWeekNames = remember(dayOfWeekNames) {
        with(dayOfWeekNames.names) { listOf(last()) + dropLast(1) }
    }

    CarbonLayer {
        Column(
            modifier = modifier
                .width(calendarMenuWidth)
                .dropShadow(
                    shape = RectangleShape,
                    shadow = Shadow(
                        radius = 3.dp,
                        spread = 0.dp,
                        color = Color.Black.copy(alpha = .2f),
                        offset = DpOffset(0.dp, 2.dp)
                    )
                )
                .layerBackground()
                .padding(SpacingScale.spacing02),
        ) {
            if (adaptation == Adaptation.Touchscreens) {
                TouchscreenYearMonthSelector(
                    calendar = calendar,
                    onLoadPreviousMonth = datePickerState::onLoadPreviousMonth,
                    onLoadNextMonth = datePickerState::onLoadNextMonth,
                    onLoadPreviousYear = datePickerState::onLoadPreviousYear,
                    onLoadNextYear = datePickerState::onLoadNextYear,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(CalendarDatePickerTestTags.MENU_TOUCHSCREEN_YEARMONTH_SELECTOR)
                )
            } else {
                DefaultYearMonthSelector(
                    calendar = calendar,
                    onLoadPreviousMonth = datePickerState::onLoadPreviousMonth,
                    onLoadNextMonth = datePickerState::onLoadNextMonth,
                    onLoadPreviousYear = datePickerState::onLoadPreviousYear,
                    onLoadNextYear = datePickerState::onLoadNextYear,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(CalendarDatePickerTestTags.MENU_DEFAULT_YEARMONTH_SELECTOR)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(SpacingScale.spacing08)
            ) {
                rotatedDayOfWeekNames.forEach { dayName ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.weight(1f)
                    ) {
                        BasicText(
                            text = dayName,
                            style = regularTextStyle,
                        )
                    }
                }
            }

            Column {
                calendar.daysMatrix.forEach { week ->
                    Row(
                        modifier = Modifier.height(SpacingScale.spacing08),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        week.forEach { day ->
                            CalendarDayItem(
                                day = day.dateString,
                                isEnabled = datePickerState.selectableDates
                                    .isSelectable(day.date),
                                isToday = day.date == datePickerState.today,
                                isSelected = day.date == datePickerState.selectedDate,
                                isOutOfMonth = day.isOutOfMonth,
                                regularTextStyle = regularTextStyle,
                                disabledDayTextStyle = disabledDayTextStyle,
                                outOfMonthTextStyle = outOfMonthTextStyle,
                                todayDayTextStyle = todayDayTextStyle,
                                selectedDayTextStyle = selectedDayTextStyle,
                                indication = dayItemIndication,
                                onClick = { onDayClicked(day.date) },
                                modifier = Modifier
                                    .menuKeyboardNavigation(focusManager = focusManager)
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .testTag(
                                        CalendarDatePickerTestTags.MENU_DAY_ITEM +
                                            "_${day.date}"
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun <T> DefaultYearMonthSelector(
    calendar: CalendarMenuData<T>,
    onLoadPreviousMonth: () -> Unit,
    onLoadNextMonth: () -> Unit,
    onLoadPreviousYear: () -> Unit,
    onLoadNextYear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .testTag(CalendarDatePickerTestTags.MENU_DEFAULT_YEARMONTH_SELECTOR)
    ) {
        IconButton(
            iconPainter = rememberVectorPainter(chevronLeftIcon),
            buttonType = ButtonType.Ghost,
            buttonSize = ButtonSize.Medium,
            contentDescription = stringResource(
                Res.string.carbon_datepicker_calendar_loadPreviousMonth_description
            ),
            onClick = onLoadPreviousMonth,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .testTag(CalendarDatePickerTestTags.MENU_PREV_MONTH_BUTTON)
        )

        IconButton(
            iconPainter = rememberVectorPainter(chevronRightIcon),
            buttonType = ButtonType.Ghost,
            buttonSize = ButtonSize.Medium,
            contentDescription = stringResource(
                Res.string.carbon_datepicker_calendar_loadNextMonth_description
            ),
            onClick = onLoadNextMonth,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .testTag(CalendarDatePickerTestTags.MENU_NEXT_MONTH_BUTTON)
        )

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center)
                .padding(horizontal = SpacingScale.spacing09),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText(
                text = calendar.monthName,
                style = Carbon.typography.headingCompact01.copy(
                    color = Carbon.theme.textPrimary,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.testTag(CalendarDatePickerTestTags.MENU_MONTH)
            )

            DefaultYearSelector(
                calendar = calendar,
                onLoadNextYear = onLoadNextYear,
                onLoadPreviousYear = onLoadPreviousYear
            )
        }
    }
}

@Composable
private fun <T> DefaultYearSelector(
    calendar: CalendarMenuData<T>,
    onLoadNextYear: () -> Unit,
    onLoadPreviousYear: () -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = Carbon.theme
    val isInPreview = LocalInspectionMode.current
    val yearSelectorInteractionSource = remember { MutableInteractionSource() }
    val isYearSelectorHovered by yearSelectorInteractionSource.collectIsHoveredAsState()

    Row(
        modifier = modifier.hoverable(interactionSource = yearSelectorInteractionSource),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(
            text = calendar.yearName,
            style = Carbon.typography.headingCompact01.copy(
                color = Carbon.theme.textPrimary,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(start = SpacingScale.spacing03)
                .testTag(CalendarDatePickerTestTags.MENU_YEAR)
        )

        val nextYearInteractionSource = remember { MutableInteractionSource() }
        val prevYearInteractionSource = remember { MutableInteractionSource() }

        val nextYearIsFocused by nextYearInteractionSource.collectIsFocusedAsState()
        val prevYearIsFocused by prevYearInteractionSource.collectIsFocusedAsState()

        Column(
            modifier = Modifier
                .padding(start = SpacingScale.spacing02)
                .graphicsLayer {
                    alpha =
                        if (isYearSelectorHovered ||
                            nextYearIsFocused ||
                            prevYearIsFocused ||
                            isInPreview
                        ) 1f else 0f
                }
        ) {
            @Composable
            fun CaretButton(
                icon: ImageVector,
                onClick: () -> Unit,
                contentDescription: String,
                modifier: Modifier = Modifier,
                interactionSource: MutableInteractionSource =
                    remember { MutableInteractionSource() },
            ) {
                val isHovered by interactionSource.collectIsHoveredAsState()
                val isFocused by interactionSource.collectIsFocusedAsState()

                Box(
                    modifier = modifier
                        .pointerHoverIcon(PointerIcon.Hand)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = onClick
                        )
                        .width(16.dp)
                        .padding(vertical = SpacingScale.spacing01)
                        .semantics(mergeDescendants = true) {
                            this.contentDescription = contentDescription
                        }
                ) {
                    Image(
                        painter = rememberVectorPainter(icon),
                        colorFilter = ColorFilter.tint(
                            if (isHovered || isFocused) theme.buttonColors.buttonPrimary
                            else theme.iconPrimary
                        ),
                        contentDescription = null
                    )
                }
            }

            CaretButton(
                icon = CaretIncreaseYear,
                onClick = onLoadNextYear,
                contentDescription = stringResource(
                    Res.string.carbon_datepicker_calendar_loadNextYear_description
                ),
                interactionSource = nextYearInteractionSource,
                modifier = Modifier
                    .testTag(CalendarDatePickerTestTags.MENU_NEXT_YEAR_BUTTON)
            )

            CaretButton(
                icon = CaretDecreaseYear,
                onClick = onLoadPreviousYear,
                contentDescription = stringResource(
                    Res.string.carbon_datepicker_calendar_loadPreviousYear_description
                ),
                interactionSource = prevYearInteractionSource,
                modifier = Modifier
                    .testTag(CalendarDatePickerTestTags.MENU_PREV_YEAR_BUTTON)
            )
        }
    }
}

@Composable
private fun <T> TouchscreenYearMonthSelector(
    calendar: CalendarMenuData<T>,
    onLoadPreviousMonth: () -> Unit,
    onLoadNextMonth: () -> Unit,
    onLoadPreviousYear: () -> Unit,
    onLoadNextYear: () -> Unit,
    modifier: Modifier = Modifier
) {
    @Composable
    fun ValueSelector(
        value: String,
        loadPrevDescription: String,
        loadNextDescription: String,
        loadPrevTestTag: String,
        loadNextTestTag: String,
        valueTestTag: String,
        onLoadPrev: () -> Unit,
        onLoadNext: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            IconButton(
                iconPainter = rememberVectorPainter(chevronLeftIcon),
                buttonType = ButtonType.Ghost,
                buttonSize = ButtonSize.Medium,
                contentDescription = loadPrevDescription,
                onClick = onLoadPrev,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .testTag(loadPrevTestTag)
            )

            IconButton(
                iconPainter = rememberVectorPainter(chevronRightIcon),
                buttonType = ButtonType.Ghost,
                buttonSize = ButtonSize.Medium,
                contentDescription = loadNextDescription,
                onClick = onLoadNext,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .testTag(loadNextTestTag)
            )

            BasicText(
                text = value,
                style = Carbon.typography.headingCompact01.copy(
                    color = Carbon.theme.textPrimary,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag(valueTestTag)
            )
        }
    }

    Column(modifier = modifier) {
        ValueSelector(
            value = calendar.yearName,
            loadPrevDescription = stringResource(
                Res.string.carbon_datepicker_calendar_loadPreviousYear_description
            ),
            loadNextDescription = stringResource(
                Res.string.carbon_datepicker_calendar_loadNextYear_description
            ),
            loadPrevTestTag = CalendarDatePickerTestTags.MENU_PREV_YEAR_BUTTON,
            loadNextTestTag = CalendarDatePickerTestTags.MENU_NEXT_YEAR_BUTTON,
            valueTestTag = CalendarDatePickerTestTags.MENU_YEAR,
            onLoadPrev = onLoadPreviousYear,
            onLoadNext = onLoadNextYear,
        )
        ValueSelector(
            value = calendar.monthName,
            loadPrevDescription = stringResource(
                Res.string.carbon_datepicker_calendar_loadPreviousMonth_description
            ),
            loadNextDescription = stringResource(
                Res.string.carbon_datepicker_calendar_loadNextMonth_description
            ),
            loadPrevTestTag = CalendarDatePickerTestTags.MENU_PREV_MONTH_BUTTON,
            loadNextTestTag = CalendarDatePickerTestTags.MENU_NEXT_MONTH_BUTTON,
            valueTestTag = CalendarDatePickerTestTags.MENU_MONTH,
            onLoadPrev = onLoadPreviousMonth,
            onLoadNext = onLoadNextMonth,
        )
    }
}

@Composable
private fun CalendarDayItem(
    day: String,
    isEnabled: Boolean,
    isToday: Boolean,
    isSelected: Boolean,
    isOutOfMonth: Boolean,
    regularTextStyle: TextStyle,
    disabledDayTextStyle: TextStyle,
    outOfMonthTextStyle: TextStyle,
    todayDayTextStyle: TextStyle,
    selectedDayTextStyle: TextStyle,
    indication: Indication,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isHovered by interactionSource.collectIsHoveredAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable(
                onClick = onClick,
                enabled = isEnabled,
                interactionSource = interactionSource,
                indication = indication
            )
            .pointerHoverIcon(PointerIcon.Hand)
            .background(
                when {
                    isSelected -> Carbon.theme.backgroundBrand
                    isHovered -> Carbon.theme.layerHoverColor(Carbon.layer)
                    else -> Color.Transparent
                }
            )
    ) {
        BasicText(
            text = day,
            style = when {
                !isEnabled && isToday -> todayDayTextStyle.merge(disabledDayTextStyle)
                !isEnabled -> disabledDayTextStyle
                isSelected -> selectedDayTextStyle
                isToday -> todayDayTextStyle
                isOutOfMonth -> outOfMonthTextStyle
                else -> regularTextStyle
            },
        )

        if (isToday && !isSelected) {
            Box(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(4.dp)
                    .background(Carbon.theme.linkPrimary)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview
@Composable
private fun CalendarMenuPreview() {
    CarbonDesignSystem {
        val today = remember {
            LocalDate(2025, 12, 1)
        }

        val pickerState = rememberCalendarDatePickerState(
            today = today,
            selectableDates = { it != today.plus(1, DateTimeUnit.DAY) },
        )

        pickerState.selectedDate = today.plus(2, DateTimeUnit.DAY)

        CalendarMenu(
            datePickerState = pickerState,
            onDayClicked = {},
            modifier = Modifier.padding(SpacingScale.spacing04)
        )
    }
}

@Preview
@Composable
private fun TouchscreenCalendarMenuPreview() {
    CarbonDesignSystem(adaptation = Adaptation.Touchscreens) {
        val today = remember {
            LocalDate(2025, 12, 1)
        }

        val pickerState = rememberCalendarDatePickerState(
            today = today,
            selectableDates = { it != today.plus(1, DateTimeUnit.DAY) },
        )

        pickerState.selectedDate = today.plus(2, DateTimeUnit.DAY)

        CalendarMenu(
            datePickerState = pickerState,
            onDayClicked = {},
            modifier = Modifier.padding(SpacingScale.spacing04)
        )
    }
}
