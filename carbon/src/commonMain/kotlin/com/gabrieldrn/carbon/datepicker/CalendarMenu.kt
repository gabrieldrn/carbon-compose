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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.Res
import com.gabrieldrn.carbon.button.ButtonSize
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.carbon_datepicker_calendar_friday_label
import com.gabrieldrn.carbon.carbon_datepicker_calendar_loadNextMonth_description
import com.gabrieldrn.carbon.carbon_datepicker_calendar_loadPreviousMonth_description
import com.gabrieldrn.carbon.carbon_datepicker_calendar_monday_label
import com.gabrieldrn.carbon.carbon_datepicker_calendar_saturday_label
import com.gabrieldrn.carbon.carbon_datepicker_calendar_sunday_label
import com.gabrieldrn.carbon.carbon_datepicker_calendar_thursday_label
import com.gabrieldrn.carbon.carbon_datepicker_calendar_tuesday_label
import com.gabrieldrn.carbon.carbon_datepicker_calendar_wednesday_label
import com.gabrieldrn.carbon.foundation.color.layerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.icons.chevronLeftIcon
import com.gabrieldrn.carbon.icons.chevronRightIcon
import kotlinx.datetime.DayOfWeek
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val CALENDAR_WEEKS = 6
private val DAYS_IN_WEEK = DayOfWeek.entries.count()

private val daysOfWeekLetters = listOf(
    Res.string.carbon_datepicker_calendar_sunday_label,
    Res.string.carbon_datepicker_calendar_monday_label,
    Res.string.carbon_datepicker_calendar_tuesday_label,
    Res.string.carbon_datepicker_calendar_wednesday_label,
    Res.string.carbon_datepicker_calendar_thursday_label,
    Res.string.carbon_datepicker_calendar_friday_label,
    Res.string.carbon_datepicker_calendar_saturday_label,
)

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

internal data class MonthDay(
    val number: Int,
    val isOutOfMonth: Boolean,
    val isSelected: Boolean
)

private fun getCalendarMatrix(
    firstDayOfWeek: DayOfWeek,
    previousMonthLastDay: Int,
    monthSelectedDay: Int?,
    monthLastDay: Int
): List<List<MonthDay>> {
    val prevMonthDays = firstDayOfWeek.dayNumber
    var prevMonthDaysCount = prevMonthDays

    var monthDayCount = 1
    var nextMonthDayCount = 1

    return buildList {
        repeat(CALENDAR_WEEKS) {
            buildList {
                repeat(DAYS_IN_WEEK) {
                    when {
                        prevMonthDaysCount > 0 -> add(
                            MonthDay(
                                number = previousMonthLastDay - prevMonthDaysCount-- + 1,
                                isOutOfMonth = true,
                                isSelected = false
                            )
                        )
                        monthDayCount <= monthLastDay -> {
                            add(
                                MonthDay(
                                    number = monthDayCount,
                                    isOutOfMonth = false,
                                    isSelected = monthDayCount == monthSelectedDay
                                )
                            )
                            monthDayCount++
                        }
                        else -> add(
                            MonthDay(
                                number = nextMonthDayCount++,
                                isOutOfMonth = true,
                                isSelected = false
                            )
                        )
                    }
                }
            }.let(::add)
        }
    }
}

@Composable
internal fun CalendarMenu(
    calendar: List<List<MonthDay>>,
    onLoadPreviousMonth: () -> Unit,
    onLoadNextMonth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .layerBackground()
            .padding(SpacingScale.spacing02)
            .requiredWidth(SpacingScale.spacing12 * 3),
        verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing02)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                iconPainter = rememberVectorPainter(chevronLeftIcon),
                buttonType = ButtonType.Ghost,
                buttonSize = ButtonSize.Medium,
                contentDescription = stringResource(
                    Res.string.carbon_datepicker_calendar_loadPreviousMonth_description
                ),
                onClick = onLoadPreviousMonth
            )

            BasicText(
                text = "January 1970",
                style = Carbon.typography.headingCompact01.copy(textAlign = TextAlign.Center),
                modifier = Modifier.weight(1f),
            )

            IconButton(
                iconPainter = rememberVectorPainter(chevronRightIcon),
                buttonType = ButtonType.Ghost,
                buttonSize = ButtonSize.Medium,
                contentDescription = stringResource(
                    Res.string.carbon_datepicker_calendar_loadNextMonth_description
                ),
                onClick = onLoadNextMonth
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(SpacingScale.spacing08)
        ) {
            daysOfWeekLetters.forEach { dayOfWeekLetterRes ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    BasicText(
                        text = stringResource(dayOfWeekLetterRes),
                        style = Carbon.typography.bodyCompact01,
                    )
                }
            }
        }

        val outOfMonthTextStyle = Carbon.typography.bodyCompact01.copy(
            color = Carbon.theme.textSecondary
        )

        val selectedDayTextStyle = Carbon.typography.bodyCompact01.copy(
            color = Carbon.theme.linkPrimary,
            fontWeight = FontWeight.SemiBold
        )

        Column {
            calendar.forEach { week ->
                Row(
                    modifier = Modifier.height(SpacingScale.spacing08),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    week.forEach { day ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        ) {
                            BasicText(
                                text = day.number.toString(),
                                style = when {
                                    day.isOutOfMonth -> outOfMonthTextStyle
                                    day.isSelected -> selectedDayTextStyle
                                    else -> Carbon.typography.bodyCompact01
                                },
                            )

                            if (day.isSelected) {
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
                }
            }
        }
    }
}

@Preview
@Composable
private fun CalendarMenuPreview() {
    CarbonDesignSystem {
        val calendar = remember {
            getCalendarMatrix(
                firstDayOfWeek = DayOfWeek.THURSDAY,
                previousMonthLastDay = 31,
                monthSelectedDay = 7,
                monthLastDay = 31
            )
        }
        CalendarMenu(
            calendar = calendar,
            onLoadPreviousMonth = {},
            onLoadNextMonth = {},
        )
    }
}
