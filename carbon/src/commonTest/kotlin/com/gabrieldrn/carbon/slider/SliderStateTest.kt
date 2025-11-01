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
import kotlin.test.assertTrue

class SliderStateTest {

    @Test
    fun `Given initial value When slider is created Then value is set correctly`() {
        val sliderState = SliderState(value = 0.5f, steps = 0.1f, valueRange = 0f..1f)
        assertEquals(0.5f, sliderState.value)
    }

    @Test
    fun `Given initial value out of range When slider is created Then value is clamped correctly`() {
        val sliderState = SliderState(value = 0f, steps = 1f, valueRange = 1f..10f)
        assertEquals(1f, sliderState.value)
    }

    @Test
    fun `Given value out of range When value is set Then value is clamped within range`() {
        val sliderState = SliderState(value = 0.5f, steps = 0.1f, valueRange = 0f..1f)
        sliderState.value = 1.5f
        assertEquals(1f, sliderState.value)

        sliderState.value = -0.5f
        assertEquals(0f, sliderState.value)
    }

    @Test
    fun `Given slider state When valueAsFraction is calculated Then result is correct`() {
        val sliderState = SliderState(value = 0.5f, steps = 0.1f, valueRange = 0f..1f)
        assertEquals(0.5f, sliderState.valueAsFraction)
    }

    @Test
    fun `Given slider width When offset is updated Then value is set based on input`() {
        val sliderState = SliderState(value = 0.5f, steps = 0.1f, valueRange = 0f..1f)
        sliderState.updateWidth(100f)

        sliderState.update(Offset(50f, 0f))
        assertEquals(0.5f, sliderState.value)

        sliderState.update(Offset(100f, 0f))
        assertEquals(1f, sliderState.value)

        sliderState.update(Offset(0f, 0f))
        assertEquals(0f, sliderState.value)
    }

    @Test
    fun `Given slider with steps When offset is updated Then value snaps to nearest division`() {
        val sliderState = SliderState(value = 0.45f, steps = 0.1f, valueRange = 0f..1f)
        sliderState.updateWidth(100f)

        sliderState.update(Offset(47f, 0f)) // Close to 0.5
        assertEquals(0.5f, sliderState.value)

        sliderState.update(Offset(22f, 0f)) // Close to 0.2
        assertEquals(0.2f, sliderState.value)
    }

    @Test
    fun `Given slider width When width is updated Then range is adjusted`() {
        val sliderState = SliderState(value = 0.5f, steps = 0.1f, valueRange = 0f..1f)
        sliderState.updateWidth(200f)

        sliderState.update(Offset(100f, 0f))
        assertEquals(0.5f, sliderState.value)
    }

    @Test
    fun `Given value change listener When value changes Then listener is triggered`() {
        var callbackTriggered = false
        val sliderState = SliderState(value = 0.5f, steps = 0.1f, valueRange = 0f..1f)
        sliderState.onValueChange = { callbackTriggered = true }

        sliderState.value = 0.6f
        assertTrue(callbackTriggered)
    }
}
