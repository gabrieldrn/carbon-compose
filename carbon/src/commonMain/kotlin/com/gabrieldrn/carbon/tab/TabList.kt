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

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Res
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.ic_arrow_left
import com.gabrieldrn.carbon.ic_arrow_right
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

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
 * @param modifier The modifier to apply to the composable.
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
    val scrollState = rememberScrollState()
    val colors = TabColors.colors(variant)
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        Row(
            modifier = Modifier.horizontalScroll(scrollState),
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
                    colors = colors,
                    onClick = { onTabSelected(tab) }
                )
            }
        }

        if (scrollState.canScrollBackward) {
            IconButton(
                modifier = Modifier
                    .background(colors.scrollButtonBackground)
                    .align(Alignment.CenterStart),
                iconPainter = painterResource(Res.drawable.ic_arrow_left),
                buttonType = ButtonType.Ghost,
                onClick = { scope.launch { scrollState.animateScrollBy(-SCROLL_DISTANCE) } }
            )
        }
        if (scrollState.canScrollForward) {
            IconButton(
                modifier = Modifier
                    .background(colors.scrollButtonBackground)
                    .align(Alignment.CenterEnd),
                iconPainter = painterResource(Res.drawable.ic_arrow_right),
                buttonType = ButtonType.Ghost,
                onClick = { scope.launch { scrollState.animateScrollBy(SCROLL_DISTANCE) } }
            )
        }
    }
}

private const val SCROLL_DISTANCE = 100f