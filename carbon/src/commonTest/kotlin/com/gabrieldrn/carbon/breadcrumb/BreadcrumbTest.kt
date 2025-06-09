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
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.toList
import kotlin.test.Test
import kotlin.test.assertEquals

class BreadcrumbTest {

    @Test
    fun breadcrumb_noItems_rendersCorrectly() = runComposeUiTest {
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
    fun breadcrumb_oneItem_rendersCorrectly() = runComposeUiTest {
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
    fun breadcrumb_manyItems_rendersCorrectly() = runComposeUiTest {
        var displayTrailingSeparator by mutableStateOf(false)

        setContent {
            CarbonDesignSystem {
                Breadcrumb(
                    breadcrumbs = listOf(
                        Breadcrumb(label = "Item 1"),
                        Breadcrumb(label = "Item 2"),
                        Breadcrumb(label = "Item 3"),
                    ),
                    onBreadcrumbClick = {},
                    displayTrailingSeparator = displayTrailingSeparator
                )
            }
        }

        listOf(true, false).forEach {
            displayTrailingSeparator = it

            onAllNodesWithTag(BreadcrumbTestTags.ITEM)
                .assertCountEquals(3)

            onAllNodesWithTag(BreadcrumbTestTags.SEPARATOR)
                .assertCountEquals(if (displayTrailingSeparator) 3 else 2)
        }
    }


    @Test
    fun breadcrumb_item_validateClickableAndCallbackWithCorrectItem() = runComposeUiTest {
        var isItemEnabled by mutableStateOf(false)
        var returnedItem: Breadcrumb? = null

        setContent {
            CarbonDesignSystem {
                Breadcrumb(
                    breadcrumbs = listOf(
                        Breadcrumb(
                            label = "Item 1",
                            isEnabled = isItemEnabled
                        ),
                        Breadcrumb(
                            label = "Item 2",
                            isEnabled = isItemEnabled
                        ),
                        Breadcrumb(
                            label = "Item 3",
                            isEnabled = isItemEnabled
                        ),
                    ),
                    onBreadcrumbClick = { returnedItem = it }
                )
            }
        }

        listOf(true, false).forEach { isEnabled ->
            isItemEnabled = isEnabled

            onAllNodesWithTag(BreadcrumbTestTags.ITEM)
                .toList()
                .forEachIndexed { index, node ->
                    node.assertHasClickAction()
                    if (isItemEnabled) {
                        node.assertIsEnabled()

                        node.performClick()
                        assertEquals("Item ${index + 1}", returnedItem?.label)
                    } else {
                        node.assertIsNotEnabled()
                    }
                }
        }
    }
}
