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

package com.gabrieldrn.carbon.tab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.isNotFocusable
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.toList
import kotlin.test.Test

class TabListTest {

    private var _variant by mutableStateOf(TabVariant.Line)

    private val rootTag = "root"
    private val testTabItems = listOf(
        TabItem(label = "Dashboard"),
        TabItem(label = "Monitoring"),
        TabItem(label = "Activity"),
        TabItem(label = "Disabled", enabled = false)
    )

    @Test
    fun tabList_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                TabList(
                    tabs = testTabItems,
                    selectedTab = testTabItems[0],
                    onTabSelected = {},
                    modifier = Modifier.testTag(rootTag),
                    variant = _variant
                )
            }
        }

        forEachParameter {
            onNodeWithTag(rootTag)
                .assertHeightIsEqualTo(_variant.height)

            onAllNodesWithTag(TabListTestTags.TAB_ROOT)
                .toList()
                .onEachIndexed { index, node ->
                    val testTabItem = testTabItems[index]
                    if (testTabItem.enabled) {
                        node.assert(isFocusable())
                    } else {
                        node.assert(isNotFocusable())
                    }
                }
        }
    }

    private fun forEachParameter(
        testBlock: () -> Unit
    ) {
        TabVariant.entries.forEach { variant ->
            println("Running test with variant = $variant")
            _variant = variant

            testBlock()
        }
    }
}