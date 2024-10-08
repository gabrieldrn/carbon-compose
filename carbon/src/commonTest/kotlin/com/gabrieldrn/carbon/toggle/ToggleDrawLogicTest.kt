/*
 * Copyright 2024 Gabriel Derrien
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

package com.gabrieldrn.carbon.toggle

import androidx.compose.ui.unit.Density
import com.gabrieldrn.carbon.toggle.domain.ToggleDrawValues
import kotlin.test.Test
import kotlin.test.assertEquals

class ToggleDrawLogicTest {

    @Test
    fun toggleDrawValues_buildDefaultToggleValues_computeCorrectValues() {
        val density = Density(density = 1.5f)

        val values = ToggleDrawValues.buildValues(
            toggleType = ToggleType.Default,
            density = density
        )

        assertEquals(
            expected = 36f,
            actual = values.toggleHeight
        )
        assertEquals(
            expected = 72f,
            actual = values.toggleWidth
        )
        assertEquals(
            expected = 27f,
            actual = values.handleSize
        )
        assertEquals(
            expected = 9f,
            actual = values.handleCheckmarkOffset.x
        )
        assertEquals(
            expected = 9.75f,
            actual = values.handleCheckmarkOffset.y
        )
        assertEquals(
            expected = 4.5f,
            actual = values.handleYOffPos
        )
        assertEquals(
            expected = 40.5f,
            actual = values.handleXOnPos
        )
    }

    @Test
    fun toggleDrawValues_buildSmallToggleValues_computeCorrectValues() {
        val density = Density(density = 1.5f)

        val values = ToggleDrawValues.buildValues(
            toggleType = ToggleType.Small,
            density = density
        )

        assertEquals(
            expected = 24f,
            actual = values.toggleHeight
        )
        assertEquals(
            expected = 48f,
            actual = values.toggleWidth
        )
        assertEquals(
            expected = 15f,
            actual = values.handleSize
        )
        assertEquals(
            expected = 3f,
            actual = values.handleCheckmarkOffset.x
        )
        assertEquals(
            expected = 3.75f,
            actual = values.handleCheckmarkOffset.y
        )
        assertEquals(
            expected = 4.5f,
            actual = values.handleYOffPos
        )
        assertEquals(
            expected = 28.5f,
            actual = values.handleXOnPos
        )
    }
}
