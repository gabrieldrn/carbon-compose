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

package com.gabrieldrn.carbon.slider

import androidx.compose.ui.geometry.Offset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SliderStateTest {

    @Test
    fun givenValidParameters_whenConstructed_thenCreatesInstance() {
        val state = SliderState(value = 50f, steps = 4, valueRange = 0f..100f)
        assertEquals(50f, state.value)
        assertEquals(4, state.steps)
        assertEquals(0f..100f, state.valueRange)
    }

    @Test
    fun givenValueOutsideRange_whenConstructed_thenValueIsCoerced() {
        val state = SliderState(value = 150f, steps = 4, valueRange = 0f..100f)
        assertEquals(100f, state.value)
    }

    @Test
    fun givenStateWithCallback_whenValueIsSet_thenValueIsUpdatedAndCallbackIsInvoked() {
        var onValueChangeCalled = false
        val state = SliderState(value = 50f, steps = 4, valueRange = 0f..100f)
        state.onValueChange = {
            onValueChangeCalled = true
            assertEquals(75f, it)
        }
        state.value = 75f
        assertEquals(75f, state.value)
        assertTrue(onValueChangeCalled)
    }

    @Test
    fun givenStateWithCallback_whenValueIsSetToSameValue_thenCallbackIsNotInvoked() {
        var onValueChangeCalled = false
        val state = SliderState(value = 50f, steps = 4, valueRange = 0f..100f)
        state.onValueChange = { onValueChangeCalled = true }
        state.value = 50f
        assertFalse(onValueChangeCalled)
    }

    @Test
    fun givenState_whenValueAsFractionIsAccessed_thenReturnsCorrectFraction() {
        val state = SliderState(value = 50f, steps = 4, valueRange = 0f..100f)
        assertEquals(0.5f, state.valueAsFraction)
    }

    @Test
    fun givenStateWithDifferentRange_whenValueAsFractionIsAccessed_thenReturnsCorrectFraction() {
        val state = SliderState(value = 100f, steps = 4, valueRange = 50f..150f)
        assertEquals(0.5f, state.valueAsFraction)
    }

    @Test
    fun givenStateWithSteps_whenGetNearestDivisionIsCalled_thenReturnsNearestDivision() {
        val state = SliderState(value = 0f, steps = 4, valueRange = 0f..100f)
        // (100-0)/(4+1) = 20. Divisions at 0, 20, 40, 60, 80, 100
        assertEquals(20f, state.getNearestDivision(23f))
        assertEquals(40f, state.getNearestDivision(38f))
        assertEquals(100f, state.getNearestDivision(100f))
    }

    @Test
    fun givenStateWithZeroSteps_whenGetNearestDivisionIsCalled_thenReturnsCoercedValue() {
        val state = SliderState(value = 0f, steps = 0, valueRange = 0f..100f)
        assertEquals(23f, state.getNearestDivision(23f))
        assertEquals(38f, state.getNearestDivision(38f))
        assertEquals(100f, state.getNearestDivision(110f))
    }

    @Test
    fun givenStateWithRangeNotStartingAtZero_whenGetNearestDivisionIsCalled_thenReturnsCorrectDivision() {
        val state = SliderState(value = 50f, steps = 4, valueRange = 50f..150f)
        // Expected step size: (150 - 50) / (4 + 1) = 20.
        // Expected divisions: 50, 70, 90, 110, 130, 150.
        // For input 74f, nearest is 70f.
        assertEquals(70f, state.getNearestDivision(74f))
    }

    @Test
    fun givenStateWithSteps_whenUpdateIsCalled_thenValueSnapsToNearestDivision() {
        val state = SliderState(value = 0f, steps = 4, valueRange = 0f..100f)
        state.updateWidth(200f)
        state.update(Offset(x = 46f, y = 0f))
        assertEquals(20f, state.value)
    }

    @Test
    fun givenStateWithZeroSteps_whenUpdateIsCalled_thenValueIsUpdated() {
        val state = SliderState(value = 0f, steps = 0, valueRange = 0f..100f)
        state.updateWidth(200f)
        state.update(Offset(x = 50f, y = 0f))
        assertEquals(25f, state.value)
    }

    @Test
    fun givenStateWithCallback_whenUpdateIsCalled_thenOnValueChangeIsInvoked() {
        var onValueChangeCalled = false
        val state = SliderState(value = 0f, steps = 4, valueRange = 0f..100f)
        state.updateWidth(200f)
        state.onValueChange = {
            onValueChangeCalled = true
            assertEquals(20f, it)
        }
        state.update(Offset(x = 46f, y = 0f))
        assertTrue(onValueChangeCalled)
    }

    @Test
    fun givenState_whenUpdateWidthIsCalled_thenPixelToValueMappingIsUpdated() {
        val state = SliderState(value = 0f, steps = 0, valueRange = 0f..100f)
        state.updateWidth(300f)
        state.update(Offset(x = 150f, y = 0f))
        assertEquals(50f, state.value)
    }
}
