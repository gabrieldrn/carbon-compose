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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertValueEquals
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.swipeRight
import com.gabrieldrn.carbon.CarbonDesignSystem
import kotlin.test.Test
import kotlin.test.assertEquals

class SliderTest {

    @Test
    fun slider_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                Slider(
                    value = 10f,
                    startLabel = "0",
                    endLabel = "100",
                    steps = 10,
                    sliderRange = 0f..100f,
                    onValueChange = {},
                    modifier = Modifier.testTag("slider")
                )
            }
        }

        onNodeWithTag("slider").assertIsDisplayed()
        onNodeWithTag(SliderTestTags.LABEL).assertDoesNotExist()
        onNodeWithTag(SliderTestTags.START_LABEL, useUnmergedTree = true)
            .assertIsDisplayed()
        onNodeWithTag(SliderTestTags.END_LABEL, useUnmergedTree = true)
            .assertIsDisplayed()
        onNodeWithTag(SliderTestTags.TRACK, useUnmergedTree = true)
            .assertIsDisplayed()
        onNodeWithTag(SliderTestTags.HANDLE, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun slider_dragEvent_validateValueChange() = runComposeUiTest {
        var value by mutableStateOf(0f)
        setContent {
            CarbonDesignSystem {
                Slider(
                    value = value,
                    startLabel = "0",
                    endLabel = "100",
                    steps = 9,
                    sliderRange = 0f..100f,
                    onValueChange = { value = it },
                )
            }
        }

        val trackWidth = onNodeWithTag(SliderTestTags.TRACK, useUnmergedTree = true)
            .fetchSemanticsNode()
            .layoutInfo
            .width

        val handle = onNodeWithTag(SliderTestTags.HANDLE, useUnmergedTree = true)

        handle.performTouchInput {
            val oneStepX = trackWidth / 10f
            swipeRight(startX = centerX, endX = oneStepX)
        }
        assertEquals(10f, value)

        value = 0f

        handle.performTouchInput {
            swipeRight(startX = centerX, endX = trackWidth.toFloat())
        }
        assertEquals(100f, value)
    }

    @Test
    fun slider_downEvent_validateValueChange() = runComposeUiTest {
        var value by mutableStateOf(0f)
        setContent {
            CarbonDesignSystem {
                Slider(
                    value = value,
                    startLabel = "0",
                    endLabel = "100",
                    steps = 9,
                    sliderRange = 0f..100f,
                    onValueChange = { value = it },
                    modifier = Modifier.testTag("slider")
                )
            }
        }

        val trackWidth = onNodeWithTag(SliderTestTags.TRACK, useUnmergedTree = true)
            .fetchSemanticsNode()
            .layoutInfo
            .width

        onNodeWithTag(SliderTestTags.HANDLE, useUnmergedTree = true).performTouchInput {
            down(center.copy(x = trackWidth / 10f))
        }
        assertEquals(10f, value)
    }

    @Test
    fun slider_accessibility_validateStateDescription() = runComposeUiTest {
        var value by mutableStateOf(50f)
        setContent {
            CarbonDesignSystem {
                Slider(
                    value = value,
                    startLabel = "0",
                    endLabel = "100",
                    steps = 9,
                    sliderRange = 0f..100f,
                    onValueChange = { value = it },
                    modifier = Modifier.testTag("slider")
                )
            }
        }

        onNodeWithTag("slider").run {
            assertValueEquals("50.0")
            performSemanticsAction(SemanticsActions.SetProgress) { it(70f) }
            assertValueEquals("70.0")
            assert(
                hasProgressBarRangeInfo(
                    ProgressBarRangeInfo(
                        range = 0f..100f,
                        current = 70f,
                        steps = 9
                    )
                )
            )
        }
    }
}
