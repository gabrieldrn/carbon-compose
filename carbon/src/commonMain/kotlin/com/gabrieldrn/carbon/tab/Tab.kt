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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text

@Composable
internal fun Tab(
    item: TabItem,
    selected: Boolean,
    beforeSelected: Boolean,
    isLast: Boolean,
    variant: TabVariant,
    onClick: () -> Unit
) {
    val colors = TabColors.colors(variant)
    val backgroundColor by colors.backgroundColor(isSelected = selected)
    val textColor by colors.tabLabelTextColor(
        isEnabled = item.enabled,
        isSelected = selected
    )

    Box(
        Modifier
            .height(variant.height)
            .width(IntrinsicSize.Max)
            .background(backgroundColor)
            .drawBehind {
                when (variant) {
                    TabVariant.Line -> drawIndicator(
                        color = if (selected) colors.indicator else colors.bottomBorderUnselected,
                        alignment = Alignment.Bottom
                    )
                    TabVariant.Contained -> {
                        if (selected) {
                            drawIndicator(color = colors.indicator, alignment = Alignment.Top)
                        }
                        if (!selected && !beforeSelected && !isLast) {
                            drawSeparator(colors.verticalBorderUnselected)
                        }
                    }
                }
            }
            .clickable(enabled = item.enabled) { onClick() }
            .testTag(TabListTestTags.TAB_ROOT)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = SpacingScale.spacing05),
            text = item.label,
            style = if (selected) {
                Carbon.typography.headingCompact01.copy(textColor)
            } else {
                Carbon.typography.bodyCompact01.copy(textColor)
            }
        )
    }
}

private fun DrawScope.drawIndicator(
    color: Color,
    alignment: Alignment.Vertical
) {
    val y = when (alignment) {
        Alignment.Top -> 0f
        Alignment.Bottom -> size.height - 2.dp.toPx()
        else -> error("Unsupported alignment: $alignment")
    }
    drawRect(
        color = color,
        topLeft = Offset(0f, y),
        size = Size(width = size.width, height = 2.dp.toPx())
    )
}

private fun DrawScope.drawSeparator(
    color: Color
) {
    drawRect(
        color = color,
        topLeft = Offset(size.width - 1.dp.toPx(), 0f),
        size = Size(width = 1.dp.toPx(), height = size.height)
    )
}