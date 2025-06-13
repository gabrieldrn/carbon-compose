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

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.BasicTooltipBox
import androidx.compose.foundation.BasicTooltipState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
private val tooltipMultiLineMaxWidth = 288.dp
private val tooltipSingleLinePaddingValues = PaddingValues(
    horizontal = SpacingScale.spacing05,
    vertical = SpacingScale.spacing01
)
private val tooltipMultiLinePaddingValues = PaddingValues(SpacingScale.spacing05)
private val tooltipMargin = SpacingScale.spacing02

/**
 * # TooltipBox
 *
 * Composable wrapping a UI trigger (e.g. a button) and displaying a tooltip when the user hovers
 * over it.
 *
 * Tooltips display additional information upon hover or focus that is contextual, helpful, and
 * nonessential while providing the ability to communicate and give clarity to a user.
 *
 * From [Tooltip documentation](https://carbondesignsystem.com/components/tooltip/usage/)
 *
 * @param tooltipText Text to display in the tooltip.
 * @param modifier Default Modifier to be applied to the whole composable.
 * @param singleLine Either the text in the tooltip should be displayed in a single line or not.
 * Note that the tooltip width is limited to a maximum width depending on this parameter and the
 * text might be truncated if it exceeds the maximum width.
 * @param placement Placement of the tooltip relative to the UI trigger. Defaults to
 * [TooltipPlacement.Bottom].
 * @param content UI trigger content that will be wrapped by the tooltip. This is typically a button
 * or an icon that the user can hover over to see the tooltip.
 */
@Composable
@ExperimentalFoundationApi
public fun TooltipBox(
    tooltipText: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    placement: TooltipPlacement = TooltipPlacement.Bottom,
    content: @Composable () -> Unit
) {
    TooltipBox(
        tooltipText = tooltipText,
        modifier = modifier,
        state = rememberBasicTooltipState(),
        singleLine = singleLine,
        placement = placement,
        content = content
    )
}

@Composable
@ExperimentalFoundationApi
internal fun TooltipBox(
    tooltipText: String,
    modifier: Modifier = Modifier,
    state: BasicTooltipState = rememberBasicTooltipState(),
    singleLine: Boolean = false,
    placement: TooltipPlacement = TooltipPlacement.Bottom,
    content: @Composable () -> Unit
) {
    BasicTooltipBox(
        positionProvider = TooltipPositionProvider(
            density = LocalDensity.current,
            placement = placement
        ),
        tooltip = {
            SingleLineTooltipPopup(
                text = tooltipText,
                singleLine = singleLine,
                placement = placement
            )
        },
        state = state,
        modifier = modifier,
        focusable = false
    ) {
        content()
    }
}

@Composable
private fun SingleLineTooltipPopup(
    text: String,
    singleLine: Boolean,
    placement: TooltipPlacement,
    modifier: Modifier = Modifier,
) {
    val shape = remember(placement, singleLine) {
        TooltipShape(placement, singleLine)
    }

    BasicText(
        text = text,
        style = Carbon.typography.body01.copy(color = Carbon.theme.textInverse),
        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .padding(shape.caretSize + tooltipMargin)
            .background(
                color = Carbon.theme.backgroundInverse,
                shape = shape
            )
            .widthIn(
                max = if (singleLine) tooltipSingleLineMaxWidth else tooltipMultiLineMaxWidth
            )
            .padding(
                if (singleLine) tooltipSingleLinePaddingValues
                else tooltipMultiLinePaddingValues
            ),
    )
}

internal class TooltipPositionProvider
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE) constructor(
    private val density: Density,
    private val placement: TooltipPlacement
) : PopupPositionProvider {

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val popupPadding = with(density) {
            SpacingScale.spacing02.roundToPx()
        }

        return when (placement) {
            TooltipPlacement.Right -> IntOffset(
                x = anchorBounds.right + popupPadding,
                y = anchorBounds.top + (anchorBounds.height - popupContentSize.height) / 2
            )

            TooltipPlacement.Left -> IntOffset(
                x = anchorBounds.left - popupContentSize.width - popupPadding,
                y = anchorBounds.top + (anchorBounds.height - popupContentSize.height) / 2
            )

            TooltipPlacement.Bottom -> IntOffset(
                x = anchorBounds.left + (anchorBounds.width - popupContentSize.width) / 2,
                y = anchorBounds.bottom + popupPadding
            )

            TooltipPlacement.Top -> IntOffset(
                x = anchorBounds.left + (anchorBounds.width - popupContentSize.width) / 2,
                y = anchorBounds.top - popupContentSize.height - popupPadding
            )
        }
    }
}
