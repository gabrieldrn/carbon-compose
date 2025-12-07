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

import kotlinx.datetime.Month
import kotlinx.datetime.YearMonth
import kotlinx.datetime.onDay
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CalendarMenuDataTest {

    @Test
    fun givenFebruary2024_whenGetCalendarMenuData_thenMatrixIsCorrect() {
        val yearMonth = YearMonth(2024, Month.FEBRUARY)
        val data = getCalendarMenuData(yearMonth)

        assertEquals(yearMonth, data.yearMonth)
        assertEquals(6, data.daysMatrix.size) // 6 weeks

        // First week
        val firstWeek = data.daysMatrix[0]
        assertEquals(7, firstWeek.size)
        // Jan 28, 29, 30, 31
        assertTrue(firstWeek[0].isOutOfMonth)
        assertEquals(28, firstWeek[0].localDate.day)
        assertEquals(Month.JANUARY, firstWeek[0].localDate.month)
        assertTrue(firstWeek[1].isOutOfMonth)
        assertEquals(29, firstWeek[1].localDate.day)
        assertTrue(firstWeek[2].isOutOfMonth)
        assertEquals(30, firstWeek[2].localDate.day)
        assertTrue(firstWeek[3].isOutOfMonth)
        assertEquals(31, firstWeek[3].localDate.day)
        // Feb 1, 2, 3
        assertFalse(firstWeek[4].isOutOfMonth)
        assertEquals(1, firstWeek[4].localDate.day)
        assertEquals(Month.FEBRUARY, firstWeek[4].localDate.month)
        assertFalse(firstWeek[5].isOutOfMonth)
        assertEquals(2, firstWeek[5].localDate.day)
        assertFalse(firstWeek[6].isOutOfMonth)
        assertEquals(3, firstWeek[6].localDate.day)


        // Last day of month
        val fifthWeek = data.daysMatrix[4]
        val lastDayOfMonth = fifthWeek[4] // Thursday Feb 29
        assertFalse(lastDayOfMonth.isOutOfMonth)
        assertEquals(29, lastDayOfMonth.localDate.day)
        assertEquals(Month.FEBRUARY, lastDayOfMonth.localDate.month)
        assertEquals(yearMonth.onDay(29), lastDayOfMonth.localDate)

        // Day after last day of month
        val firstDayOfNextMonth = fifthWeek[5]
        assertTrue(firstDayOfNextMonth.isOutOfMonth)
        assertEquals(1, firstDayOfNextMonth.localDate.day)
        assertEquals(Month.MARCH, firstDayOfNextMonth.localDate.month)

        // Last day in matrix
        val lastDay = data.daysMatrix.last().last()
        assertTrue(lastDay.isOutOfMonth)
        assertEquals(9, lastDay.localDate.day)
        assertEquals(Month.MARCH, lastDay.localDate.month)
    }
}
