/*
 * Copyright 2025 Jacob Ras
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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * # Tabs
 *
 * Tabs are used to organize related content. They allow the user to navigate between
 * groups of information that appear within the same context.
 *
 * (From [Tabs documentation](https://carbondesignsystem.com/components/tabs/usage/))
 *
 * @param tabs The items to display as tabs.
 * @param selectedTab The currently selected tab, usually the first one.
 * @param onTabSelected Callback invoked when a tab is selected. The selected [TabItem] is
 * passed as a parameter, and the callback should be used to update a remembered state with
 * the new value.
 * @param variant The variant of the tab list.
 */
@Composable
public fun TabList(
    tabs: List<TabItem>,
    selectedTab: TabItem,
    onTabSelected: (TabItem) -> Unit,
    modifier: Modifier = Modifier,
    variant: TabVariant = TabVariant.Line
) {
    val selectedIndex = tabs.indexOf(selectedTab)

    Row(
        modifier = modifier,
        horizontalArrangement = when (variant) {
            TabVariant.Line -> Arrangement.spacedBy(1.dp)
            TabVariant.Contained -> Arrangement.Start
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                item = tab,
                selected = tab == selectedTab,
                beforeSelected = selectedIndex - index == 1,
                isLast = tabs.lastIndex == index,
                variant = variant,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}