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

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.interaction.FocusIndication
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
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor by colors.background(
        enabled = item.enabled,
        selected = selected,
        hovered = isHovered,
        pressed = isPressed
    )
    val backgroundColorAnimated by animateColorAsState(backgroundColor)
    val textColor by colors.labelText(
        enabled = item.enabled,
        selected = selected,
        hovered = isHovered
    )
    val textColorAnimated by animateColorAsState(textColor)
    val bottomBorderColor by colors.bottomBorder(
        enabled = item.enabled,
        selected = selected,
        hovered = isHovered
    )
    val bottomBorderColorAnimated by animateColorAsState(bottomBorderColor)

    Box(
        Modifier
            .height(variant.height)
            .width(IntrinsicSize.Max)
            .background(backgroundColorAnimated)
            .drawBehind {
                when (variant) {
                    TabVariant.Line -> drawIndicator(
                        color = bottomBorderColorAnimated,
                        position = IndicatorPosition.Bottom
                    )
                    TabVariant.Contained -> {
                        if (selected) {
                            drawIndicator(
                                color = colors.topBorder,
                                position = IndicatorPosition.Top
                            )
                        }
                        if (!selected && !beforeSelected && !isLast) {
                            drawSeparator(colors.verticalBorder)
                        }
                    }
                }
            }
            .clickable(
                enabled = item.enabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = FocusIndication(Carbon.theme),
                role = Role.Tab
            )
            .testTag(TabListTestTags.TAB_ROOT)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = SpacingScale.spacing05),
            text = item.label,
            style = if (selected) {
                Carbon.typography.headingCompact01.copy(textColorAnimated)
            } else {
                Carbon.typography.bodyCompact01.copy(textColorAnimated)
            }
        )
    }
}

private enum class IndicatorPosition { Top, Bottom }

private fun DrawScope.drawIndicator(
    color: Color,
    position: IndicatorPosition
) {
    val y = when (position) {
        IndicatorPosition.Top -> 0f
        IndicatorPosition.Bottom -> size.height - 2.dp.toPx()
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