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

package com.gabrieldrn.carbon.breadcrumb

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.click
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.text.TextLayoutResult
import com.gabrieldrn.carbon.CarbonDesignSystem
import kotlin.test.Test
import kotlin.test.assertTrue

class BreadcrumbTest {

    @Test
    fun breadcrumb_noItems() = runComposeUiTest {
        var displayTrailingSeparator by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                Breadcrumb(
                    breadcrumbs = listOf(),
                    onBreadcrumbClick = {},
                    displayTrailingSeparator = displayTrailingSeparator
                )
            }
        }

        listOf(true, false).forEach {
            @Suppress("AssignedValueIsNeverRead")
            displayTrailingSeparator = it

            onAllNodesWithTag(BreadcrumbTestTags.ITEM)
                .assertCountEquals(0)

            onAllNodesWithTag(BreadcrumbTestTags.SEPARATOR)
                .assertCountEquals(0)
        }
    }

    @Test
    fun breadcrumb_oneItem() = runComposeUiTest {
        var displayTrailingSeparator by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                Breadcrumb(
                    breadcrumbs = listOf(
                        Breadcrumb(label = "Item 1")
                    ),
                    onBreadcrumbClick = {},
                    displayTrailingSeparator = displayTrailingSeparator
                )
            }
        }

        listOf(true, false).forEach {
            displayTrailingSeparator = it

            onAllNodesWithTag(BreadcrumbTestTags.ITEM)
                .assertCountEquals(1)

            onAllNodesWithTag(BreadcrumbTestTags.SEPARATOR)
                .assertCountEquals(if (displayTrailingSeparator) 1 else 0)
        }
    }

    @Test
    fun breadcrumb_item_validateLinkAnnotation() = runComposeUiTest {
        var isItemEnabled by mutableStateOf(false)
        var clickAnswer = false

        setContent {
            CarbonDesignSystem {
                Breadcrumb(
                    breadcrumbs = listOf(
                        Breadcrumb(
                            label = "Item",
                            isEnabled = isItemEnabled
                        ),
                    ),
                    onBreadcrumbClick = { clickAnswer = true }
                )
            }
        }

        listOf(true, false).forEach { isEnabled ->
            isItemEnabled = isEnabled

            onNodeWithTag(BreadcrumbTestTags.ITEM).run {
                performSemanticsAction(SemanticsActions.GetTextLayoutResult) {
                    val result = mutableListOf<TextLayoutResult>().apply { it(this) }.first()
                    val text = result.layoutInput.text
                    val linkAnnotations = text.getLinkAnnotations(0, text.length)

                    if (isItemEnabled) {
                        val linkBounds = result.getBoundingBox(linkAnnotations.first().start)
                        performTouchInput { click(linkBounds.center) }
                        assertTrue(clickAnswer)
                        clickAnswer = false
                    } else {
                        assertTrue(linkAnnotations.isEmpty())
                    }
                }
            }
        }
    }
}
