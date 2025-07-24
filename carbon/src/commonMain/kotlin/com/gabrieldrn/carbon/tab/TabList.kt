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

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.button.ButtonSize
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.button.heightDp
import com.gabrieldrn.carbon.foundation.color.containerColor
import com.gabrieldrn.carbon.icons.chevronLeftIcon
import com.gabrieldrn.carbon.icons.chevronRightIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val SCROLL_DISTANCE_RATIO = .8f

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
    val density = LocalDensity.current
    val selectedIndex = tabs.indexOf(selectedTab)
    val scrollState = rememberScrollState()
    val colors = TabColors.colors(variant)
    val scope = rememberCoroutineScope()
    var visibleRowWidth by remember { mutableStateOf(0) }
    val buttonSize = remember(variant) {
        when (variant) {
            TabVariant.Line -> ButtonSize.Medium
            TabVariant.Contained -> ButtonSize.LargeProductive
        }
    }
    val buttonSizePx = remember(density, buttonSize) {
        with(density) { buttonSize.heightDp().toPx() }
    }

    BoxWithConstraints(modifier = modifier) {
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .onGloballyPositioned { visibleRowWidth = it.boundsInParent().width.toInt() },
            horizontalArrangement = when (variant) {
                TabVariant.Line -> Arrangement.spacedBy(1.dp)
                TabVariant.Contained -> Arrangement.Start
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                var tabXStart by remember { mutableStateOf(0) }
                var tabXEnd by remember { mutableStateOf(0) }

                Tab(
                    item = tab,
                    selected = tab == selectedTab,
                    beforeSelected = selectedIndex - index == 1,
                    isLast = tabs.lastIndex == index,
                    variant = variant,
                    colors = colors,
                    modifier = Modifier.onGloballyPositioned {
                        tabXStart = it.positionInParent().x.toInt()
                        tabXEnd = tabXStart + it.boundsInParent().width.toInt()
                    },
                    onClick = {
                        scope.scrollToTab(
                            scrollState = scrollState,
                            buttonSizePx = buttonSizePx,
                            visibleRowWidth = visibleRowWidth,
                            tabXStart = tabXStart,
                            tabXEnd = tabXEnd
                        )
                        onTabSelected(tab)
                    }
                )
            }
        }

        if (scrollState.canScrollBackward) {
            LeftScrollButton(
                buttonSize = buttonSize,
                variant = variant,
                colors = colors,
                onClick = {
                    scope.launch {
                        scrollState.animateScrollBy(
                            -this@BoxWithConstraints.maxWidth.value * SCROLL_DISTANCE_RATIO
                        )
                    }
                },
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
        if (scrollState.canScrollForward) {
            RightScrollButton(
                buttonSize = buttonSize,
                variant = variant,
                colors = colors,
                onClick = {
                    scope.launch {
                        scrollState.animateScrollBy(
                            this@BoxWithConstraints.maxWidth.value * SCROLL_DISTANCE_RATIO
                        )
                    }
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
private fun LeftScrollButton(
    buttonSize: ButtonSize,
    variant: TabVariant,
    colors: TabColors,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        IconButton(
            iconPainter = rememberVectorPainter(chevronLeftIcon),
            buttonType = ButtonType.Ghost,
            buttonSize = buttonSize,
            onClick = onClick,
            modifier = Modifier.background(colors.scrollButtonBackground)
        )
        if (variant == TabVariant.Line) {
            FadingEdge(height = variant.height)
        }
    }
}

@Composable
private fun RightScrollButton(
    buttonSize: ButtonSize,
    variant: TabVariant,
    colors: TabColors,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        if (variant == TabVariant.Line) {
            FadingEdge(height = variant.height, inverse = true)
        }
        IconButton(
            iconPainter = rememberVectorPainter(chevronRightIcon),
            buttonType = ButtonType.Ghost,
            buttonSize = buttonSize,
            onClick = onClick,
            modifier = Modifier.background(colors.scrollButtonBackground)
        )
    }
}

@Composable
private fun FadingEdge(height: Dp, inverse: Boolean = false) {
    val containerColor = Carbon.theme.containerColor(Carbon.layer)
    val brush = if (inverse) {
        Brush.horizontalGradient(0f to Color.Transparent, 1f to containerColor)
    } else {
        Brush.horizontalGradient(0f to containerColor, 1f to Color.Transparent)
    }
    Spacer(
        modifier = Modifier
            .height(height)
            .width(8.dp)
            .background(brush)
    )
}

private fun CoroutineScope.scrollToTab(
    scrollState: ScrollState,
    buttonSizePx: Float,
    visibleRowWidth: Int,
    tabXStart: Int,
    tabXEnd: Int
) {
    val backButtonOffset = if (scrollState.canScrollBackward) buttonSizePx else 0f
    val forwardButtonOffset = if (scrollState.canScrollForward) buttonSizePx else 0f

    // TabList is currently showing:
    val visibleStart = scrollState.value + backButtonOffset
    val visibleEnd = scrollState.value + visibleRowWidth - forwardButtonOffset

    // Tab starts before visible range?
    if (tabXStart < visibleStart) {
        // Then scroll a bit.
        launch {
            val scrollOffset = (tabXStart - visibleStart).toFloat()
            scrollState.animateScrollBy(scrollOffset)
        }
    }
    // Tab ends after visible range?
    else if (tabXEnd > visibleEnd) {
        // Then scroll a bit.
        launch {
            val scrollOffset = (tabXEnd - visibleEnd).toFloat()
            scrollState.animateScrollBy(scrollOffset)
        }
    }
}
