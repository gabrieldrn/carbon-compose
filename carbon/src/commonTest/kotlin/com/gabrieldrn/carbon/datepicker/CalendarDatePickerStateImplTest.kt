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

import androidx.compose.ui.test.runComposeUiTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CalendarDatePickerStateImplTest {

    private val dateFormat = LocalDate.Format {
        year(); char('/'); monthNumber(); char('/'); day()
    }

    @Test
    fun givenInitialDate_whenStateIsCreated_thenSelectedDateIsInitialized() = runComposeUiTest {
        // Given
        val initialDate = LocalDate(2024, 1, 1)
        var state: CalendarDatePickerState? = null

        // When
        setContent {
            state = rememberCalendarDatePickerState(
                initialSelectedDate = initialDate,
                dateFormat = dateFormat,
                onFieldValidation = {}
            )
        }

        // Then
        assertEquals(initialDate, state?.selectedDate)
    }

    @Test
    fun givenNewDate_whenSelectedDateIsSetAndConfirmed_thenStateIsUpdated() = runComposeUiTest {
        // Given
        var state: CalendarDatePickerState? = null
        setContent {
            state = rememberCalendarDatePickerState(
                confirmDateChange = { true },
                dateFormat = dateFormat,
                onFieldValidation = {}
            )
        }
        val newDate = LocalDate(2024, 1, 1)
        var fieldUpdate: String? = null
        state?.updateFieldCallback = { fieldUpdate = it }

        // When
        runOnIdle {
            state?.selectedDate = newDate
        }

        // Then
        assertEquals(newDate, state?.selectedDate)
        assertEquals("2024/01/01", fieldUpdate)
    }

    @Test
    fun givenNewDate_whenSelectedDateIsSetAndNotConfirmed_thenStateIsNotUpdated() =
        runComposeUiTest {
            // Given
            val initialDate = LocalDate(2024, 1, 1)
            var state: CalendarDatePickerState? = null
            setContent {
                state = rememberCalendarDatePickerState(
                    initialSelectedDate = initialDate,
                    confirmDateChange = { false },
                    dateFormat = dateFormat,
                    onFieldValidation = {}
                )
            }
            val newDate = LocalDate(2024, 2, 2)
            var fieldUpdate: String? = null
            state?.updateFieldCallback = { fieldUpdate = it }

            // When
            runOnIdle {
                state?.selectedDate = newDate
            }

            // Then
            assertEquals(initialDate, state?.selectedDate)
            assertNull(fieldUpdate)
        }

    @Test
    fun givenNullDate_whenSelectedDateIsSet_thenFieldIsEmpty() = runComposeUiTest {
        // Given
        var state: CalendarDatePickerState? = null
        setContent {
            state = rememberCalendarDatePickerState(
                initialSelectedDate = LocalDate(2024, 1, 1),
                confirmDateChange = { true },
                dateFormat = dateFormat,
                onFieldValidation = {}
            )
        }
        var fieldUpdate: String? = "initial"
        state?.updateFieldCallback = { fieldUpdate = it }

        // When
        runOnIdle {
            state?.selectedDate = null
        }

        // Then
        assertNull(state?.selectedDate)
        assertEquals("", fieldUpdate)
    }

    @Test
    fun givenValidFieldValue_whenUpdateFieldValueIsCalledAndConfirmed_thenSelectedDateIsUpdated() =
        runComposeUiTest {
            // Given
            var validationResult: Boolean? = null
            var state: CalendarDatePickerState? = null
            setContent {
                state = rememberCalendarDatePickerState(
                    confirmDateChange = { true },
                    dateFormat = dateFormat,
                    onFieldValidation = { validationResult = it }
                )
            }
            val dateString = "2024/01/01"
            var fieldUpdate: String? = null
            state?.updateFieldCallback = { fieldUpdate = it }

            // When
            runOnIdle {
                state?.updateFieldValue(dateString)
            }

            // Then
            assertEquals(LocalDate(2024, 1, 1), state?.selectedDate)
            assertTrue(validationResult ?: false)
            assertEquals(dateString, fieldUpdate)
        }

    @Suppress("MaxLineLength")
    @Test
    fun givenValidFieldValue_whenUpdateFieldValueIsCalledAndNotConfirmed_thenSelectedDateIsNotUpdated() =
        runComposeUiTest {
            // Given
            var validationResult: Boolean? = null
            var state: CalendarDatePickerState? = null
            setContent {
                state = rememberCalendarDatePickerState(
                    confirmDateChange = { false },
                    dateFormat = dateFormat,
                    onFieldValidation = { validationResult = it }
                )
            }
            val dateString = "2024/01/01"
            var fieldUpdate: String? = null
            state?.updateFieldCallback = { fieldUpdate = it }

            // When
            runOnIdle {
                state?.updateFieldValue(dateString)
            }

            // Then
            assertNull(state?.selectedDate)
            assertTrue(validationResult ?: false)
            assertEquals(dateString, fieldUpdate)
        }

    @Test
    fun givenInvalidFieldValue_whenUpdateFieldValueIsCalled_thenValidationFails() =
        runComposeUiTest {
            // Given
            var validationResult: Boolean? = null
            var state: CalendarDatePickerState? = null
            setContent {
                state = rememberCalendarDatePickerState(
                    confirmDateChange = { true },
                    dateFormat = dateFormat,
                    onFieldValidation = { validationResult = it }
                )
            }
            val invalidDateString = "not a date"
            var fieldUpdate: String? = null
            state?.updateFieldCallback = { fieldUpdate = it }

            // When
            runOnIdle {
                state?.updateFieldValue(invalidDateString)
            }

            // Then
            assertNull(state?.selectedDate)
            assertFalse(validationResult ?: true)
            assertEquals(invalidDateString, fieldUpdate)
        }
}
