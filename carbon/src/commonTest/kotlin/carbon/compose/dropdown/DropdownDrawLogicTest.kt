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

package carbon.compose.dropdown

import androidx.compose.ui.unit.dp
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.domain.getChevronStartSpacing
import carbon.compose.dropdown.domain.getOptionsPopupHeightRatio
import carbon.compose.foundation.spacing.SpacingScale
import kotlin.test.Test
import kotlin.test.assertEquals

class DropdownDrawLogicTest {

    @Test
    fun dropdownDrawLogic_getOptionsPopupHeightRatio_returnsCorrectValue() {
        mapOf<Pair<Int, Int>, Float>(
            1 to 0 to 1f,
            1 to 1 to 1f,
            1 to 2 to 1f,
            2 to 0 to 1.5f,
            2 to 1 to 1.5f,
            2 to 2 to 2f,
            3 to 0 to 1.5f,
            3 to 1 to 1.5f,
            3 to 2 to 2.5f,
            3 to 3 to 3f,
            4 to 3 to 3.5f,
        ).forEach {
            val (optionsSize, visibleItems) = it.key
            val expectedRatio = it.value
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
