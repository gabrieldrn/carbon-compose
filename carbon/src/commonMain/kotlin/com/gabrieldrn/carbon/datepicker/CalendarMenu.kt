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

import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.Res
import com.gabrieldrn.carbon.button.ButtonSize
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.carbon_datepicker_calendar_loadNextMonth_description
import com.gabrieldrn.carbon.carbon_datepicker_calendar_loadPreviousMonth_description
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.layerBackground
import com.gabrieldrn.carbon.foundation.interaction.FocusIndication
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.icons.chevronLeftIcon
import com.gabrieldrn.carbon.icons.chevronRightIcon
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.minusMonth
import kotlinx.datetime.onDay
import kotlinx.datetime.plusMonth
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.yearMonth
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

internal val calendarMenuWidth = 288.dp

private const val CALENDAR_WEEKS = 6
private val DAYS_IN_WEEK = DayOfWeek.entries.count()

private val DayOfWeek.dayNumber
    get() = when (this) {
        DayOfWeek.SUNDAY -> 0
        DayOfWeek.MONDAY -> 1
        DayOfWeek.TUESDAY -> 2
        DayOfWeek.WEDNESDAY -> 3
        DayOfWeek.THURSDAY -> 4
        DayOfWeek.FRIDAY -> 5
        DayOfWeek.SATURDAY -> 6
    }

@Stable
internal data class CalendarMenuData(
    val yearMonth: YearMonth,
    val daysMatrix: List<List<MonthDay>>
)

@Stable
internal data class MonthDay(
    val localDate: LocalDate,
    val isOutOfMonth: Boolean,
)

internal fun getCalendarMenuData(yearMonth: YearMonth): CalendarMenuData {
    val firstDayCurrentMonth = yearMonth.firstDay
    val previousMonth = yearMonth.minusMonth()
    val nextMonth = yearMonth.plusMonth()
    val previousMonthLastDay = previousMonth.numberOfDays

    var prevMonthDaysCount = firstDayCurrentMonth.dayOfWeek.dayNumber

    var monthDayCount = 1
    var nextMonthDayCount = 1

    val matrix = buildList {
        repeat(CALENDAR_WEEKS) {
            buildList {
                repeat(DAYS_IN_WEEK) {
                    add(
                        when {
                            prevMonthDaysCount > 0 -> MonthDay(
                                localDate = previousMonth
                                    .onDay(previousMonthLastDay - prevMonthDaysCount-- + 1),
                                isOutOfMonth = true,
                            )

                            monthDayCount <= yearMonth.numberOfDays -> MonthDay(
                                localDate = yearMonth
                                    .onDay(monthDayCount++),
                                isOutOfMonth = false,
                            )

                            else -> MonthDay(
                                localDate = nextMonth
                                    .onDay(nextMonthDayCount++),
                                isOutOfMonth = true,
                            )
                        }
                    )
                }
            }.let(::add)
        }
    }

    return CalendarMenuData(
        yearMonth = yearMonth,
        daysMatrix = matrix
    )
}

@Composable
internal fun CalendarMenu(
    calendar: CalendarMenuData,
    selectedDate: LocalDate?,
    onDayClicked: (LocalDate) -> Unit,
    onLoadPreviousMonth: () -> Unit,
    onLoadNextMonth: () -> Unit,
    modifier: Modifier = Modifier,
    dayOfWeekNames: DayOfWeekNames = DayOfWeekNames.ENGLISH_ABBREVIATED,
    titleYearMonthFormat: DateTimeFormat<YearMonth> = YearMonth.Format {
        monthName(MonthNames.ENGLISH_FULL); char(' '); year()
    }
) {
    val regularTextStyle = Carbon.typography.bodyCompact01.copy(
        color = Carbon.theme.textPrimary
    )

    val outOfMonthTextStyle = Carbon.typography.bodyCompact01.copy(
        color = Carbon.theme.textSecondary
    )

    val selectedDayTextStyle = Carbon.typography.bodyCompact01.copy(
        color = Carbon.theme.linkPrimary,
        fontWeight = FontWeight.SemiBold
    )

    val theme = Carbon.theme

    val dayItemIndication = remember(theme) { FocusIndication(theme) }

    val rotatedDayOfWeekNames = remember(dayOfWeekNames) {
        with(dayOfWeekNames.names) { listOf(last()) + dropLast(1) }
    }

    CarbonLayer {
        Column(
            modifier = modifier
                .width(calendarMenuWidth)
                .layerBackground()
                .padding(SpacingScale.spacing02),
            verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing02)
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(SpacingScale.spacing09)) {
                IconButton(
                    iconPainter = rememberVectorPainter(chevronLeftIcon),
                    buttonType = ButtonType.Ghost,
                    buttonSize = ButtonSize.Medium,
                    contentDescription = stringResource(
                        Res.string.carbon_datepicker_calendar_loadPreviousMonth_description
                    ),
                    onClick = onLoadPreviousMonth
                )

                IconButton(
                    iconPainter = rememberVectorPainter(chevronRightIcon),
                    buttonType = ButtonType.Ghost,
                    buttonSize = ButtonSize.Medium,
                    contentDescription = stringResource(
                        Res.string.carbon_datepicker_calendar_loadNextMonth_description
                    ),
                    onClick = onLoadNextMonth,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )

                BasicText(
                    text = calendar.yearMonth.format(titleYearMonthFormat),
                    style = Carbon.typography.headingCompact01.copy(
                        color = Carbon.theme.textPrimary,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = SpacingScale.spacing09),
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
                                day = day.localDate.day.toString(),
                                isSelected = day.localDate == selectedDate,
                                isOutOfMonth = day.isOutOfMonth,
                                regularTextStyle = regularTextStyle,
                                outOfMonthTextStyle = outOfMonthTextStyle,
                                selectedDayTextStyle = selectedDayTextStyle,
                                indication = dayItemIndication,
                                onClick = { onDayClicked(day.localDate) },
                                modifier = Modifier.fillMaxHeight().weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarDayItem(
    day: String,
    isSelected: Boolean,
    isOutOfMonth: Boolean,
    regularTextStyle: TextStyle,
    outOfMonthTextStyle: TextStyle,
    selectedDayTextStyle: TextStyle,
    indication: Indication,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable(
                onClick = onClick,
                enabled = !isOutOfMonth,
                interactionSource = remember { MutableInteractionSource() },
                indication = indication
            )
    ) {
        BasicText(
            text = day,
            style = when {
                isOutOfMonth -> outOfMonthTextStyle
                isSelected -> selectedDayTextStyle
                else -> regularTextStyle
            },
        )

        if (isSelected) {
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

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun CalendarMenuPreview() {
    CarbonDesignSystem {
        val today = remember {
            Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date
        }

        val calendar = remember { getCalendarMenuData(today.yearMonth) }

        CalendarMenu(
            calendar = calendar,
            selectedDate = today,
            onDayClicked = {},
            onLoadPreviousMonth = {},
            onLoadNextMonth = {},
        )
    }
}
