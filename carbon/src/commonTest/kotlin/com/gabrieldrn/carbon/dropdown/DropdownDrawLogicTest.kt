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

package com.gabrieldrn.carbon.dropdown

import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.domain.getChevronStartSpacing
import com.gabrieldrn.carbon.dropdown.domain.getOptionsPopupHeightRatio
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import kotlin.test.Test
import kotlin.test.assertEquals

class DropdownDrawLogicTest {

    @Test
    fun dropdownDrawLogic_getOptionsPopupHeightRatio_returnsCorrectValue() {
        // optionsSize, visibleItemsBeforeScroll, expectedRatio
        listOf<Triple<Int, Int, Float>>(
            Triple(0, 0, 0f),
            Triple(1, 0, 1f),
            Triple(1, 1, 1f),
            Triple(1, 2, 1f),
            Triple(2, 0, 1.5f),
            Triple(2, 1, 1.5f),
            Triple(2, 2, 2f),
            Triple(3, 0, 1.5f),
            Triple(3, 1, 1.5f),
            Triple(3, 2, 2.5f),
            Triple(3, 3, 3f),
            Triple(4, 3, 3.5f),
        ).forEach {
            val (optionsSize, visibleItems, expectedRatio) = it
            val actualRatio = getOptionsPopupHeightRatio(
                optionsSize = optionsSize,
                visibleItemsBeforeScroll = visibleItems
            )
            assertEquals(
                expected = expectedRatio,
                actual = actualRatio,
                message = "For optionsSize=$optionsSize " +
                    "and visibleItemsBeforeScroll=$visibleItems, " +
                    "expected $expectedRatio " +
                    "but was $actualRatio"
            )
        }
    }

    @Test
    fun dropdownDrawLogic_getChevronStartSpacing_returnsCorrectValue() {
        mapOf(
            DropdownInteractiveState.Enabled to SpacingScale.spacing05,
            DropdownInteractiveState.Warning("") to 0.dp,
            DropdownInteractiveState.Error("") to 0.dp,
            DropdownInteractiveState.Disabled to SpacingScale.spacing05,
            DropdownInteractiveState.ReadOnly to SpacingScale.spacing05,
        ).forEach { (state, expectedSpacing) ->
            val actualSpacing = getChevronStartSpacing(state)
            assertEquals(
                expected = expectedSpacing,
                actual = actualSpacing,
                message = "For state=$state, expected $expectedSpacing but was $actualSpacing"
            )
        }
    }
}
