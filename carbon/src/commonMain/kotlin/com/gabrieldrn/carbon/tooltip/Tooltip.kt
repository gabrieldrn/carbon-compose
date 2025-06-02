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

package com.gabrieldrn.carbon.tooltip

import androidx.compose.foundation.BasicTooltipBox
import androidx.compose.foundation.BasicTooltipState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

// TODO Default placement on mobile is Bottom

private val tooltipSingleLineMaxWidth = 208.dp

@Composable
@ExperimentalFoundationApi
public fun TooltipBox(
    tooltipText: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    TooltipBox(
        tooltipText = tooltipText,
        modifier = modifier,
        tooltipState = rememberBasicTooltipState(),
        content = content
    )
}

@Composable
@ExperimentalFoundationApi
internal fun TooltipBox(
    tooltipText: String,
    modifier: Modifier = Modifier,
    tooltipState: BasicTooltipState = rememberBasicTooltipState(),
    content: @Composable () -> Unit
) {
    BasicTooltipBox(
        positionProvider = TooltipPositionProvider(LocalDensity.current),
        tooltip = {
            SingleLineTooltipPopup(
                text = tooltipText,
            )
        },
        state = tooltipState,
        modifier = modifier,
        focusable = false
    ) {
        content()
    }
}

@Composable
private fun SingleLineTooltipPopup(
    text: String,
    modifier: Modifier = Modifier,
) {
    BasicText(
        text = text,
        style = Carbon.typography.body01.copy(color = Carbon.theme.textInverse),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .padding(6.dp)
            .background(
                color = Carbon.theme.backgroundInverse,
                shape = TooltipShape()
            )
            .widthIn(max = tooltipSingleLineMaxWidth)
            .padding(horizontal = SpacingScale.spacing05, vertical = SpacingScale.spacing01),
    )

}

private class TooltipPositionProvider(
    private val density: Density
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        // Bottom center position for the tooltip.
        return IntOffset(
            x = anchorBounds.left + (anchorBounds.width - popupContentSize.width) / 2,
            y = anchorBounds.bottom + with(density) { SpacingScale.spacing02.roundToPx() }
        )
    }
}
