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

package com.gabrieldrn.carbon.accordion

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.toList
import kotlin.test.Test
import kotlin.test.assertEquals

class AccordionTest {

    @Test
    fun accordion_validateMarginRight() = runComposeUiTest {
        var widthConstraint by mutableStateOf(641.dp)

        var containerWidth = 0.dp

        setContent {
            CarbonDesignSystem {
                BoxWithConstraints(
                    modifier = Modifier.sizeIn(maxWidth = widthConstraint)
                ) {
                    containerWidth = maxWidth

                    Accordion(
                        sections = listOf(
                            "Title" to "Content",
                            "Title" to "Content",
                        ),
                        size = AccordionSize.Medium
                    )
                }
            }
        }

        fun assertMarginWidth(expected: Float, absoluteTolerance: Float = 0f) {
            onAllNodesWithTag(AccordionTestTags.MARGIN_RIGHT)
                .toList()
                .onEach { node ->
                    val marginWidth = node.fetchSemanticsNode()
                        .size
                        .width
                        .let { with(density) { it.toDp() } }

                    assertEquals(
                        expected = expected,
                        actual = marginWidth.value,
                        absoluteTolerance = absoluteTolerance
                    )
                }
        }

        // This condition can be false on mobile targets if their maximum screen width is smaller.
        // The following assertions will then be ignored for those targets but others must validate
        // them.
        if (containerWidth >= 641.dp) {
            assertMarginWidth(
                expected = (containerWidth * .25f).value,
                absoluteTolerance = .5f
            )
        }

        arrayOf(420.dp, 640.dp).onEach { constrainedWidth ->
            widthConstraint = constrainedWidth

            assertMarginWidth(expected = SpacingScale.spacing10.value)
        }

        widthConstraint = 419.dp

        assertMarginWidth(expected = SpacingScale.spacing05.value)
    }
}
