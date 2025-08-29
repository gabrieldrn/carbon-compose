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

package com.gabrieldrn.carbon.popover.carettip

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.popover.PopoverShape
import com.gabrieldrn.carbon.popover.getPopoverContentPaddingByPosition

internal open class PopoverCaretTipShape(
    private val placement: PopoverCaretTipPlacement,
    private val alignment: PopoverCaretTipAlignment,
    private val popoverContentPaddingValues: PaddingValues,
) : PopoverShape() {

    override val tipSize: Dp = caretTipSize

    private fun Path.addCaretTip(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) {
        val caretSizePx = with(density) { tipSize.toPx() }
        val contentPadding = with(density) {
            popoverContentPaddingValues
                .getPopoverContentPaddingByPosition(
                    placement = placement,
                    alignment = alignment,
                    layoutDirection = layoutDirection
                )
                .toPx()
        }

        fun horizontalCaretXPosition(): Float {
            return when (alignment) {
                PopoverCaretTipAlignment.Start -> contentPadding
                PopoverCaretTipAlignment.Center -> size.width / 2 - caretSizePx
                PopoverCaretTipAlignment.End -> size.width - contentPadding - caretSizePx * 2
            }
        }

        fun verticalCaretYPosition(): Float {
            return when (alignment) {
                PopoverCaretTipAlignment.Start -> contentPadding
                PopoverCaretTipAlignment.Center -> size.height / 2 - caretSizePx
                PopoverCaretTipAlignment.End -> size.height - contentPadding - caretSizePx * 2
            }
        }

        when (placement) {
            PopoverCaretTipPlacement.Right -> {
                moveTo(0f, verticalCaretYPosition())
                relativeLineTo(-caretSizePx, caretSizePx)
                relativeLineTo(caretSizePx, caretSizePx)
            }
            PopoverCaretTipPlacement.Left -> {
                moveTo(size.width, verticalCaretYPosition())
                relativeLineTo(caretSizePx, caretSizePx)
                relativeLineTo(-caretSizePx, caretSizePx)
            }
            PopoverCaretTipPlacement.Top -> {
                moveTo(horizontalCaretXPosition(), size.height)
                relativeLineTo(caretSizePx, caretSizePx)
                relativeLineTo(caretSizePx, -caretSizePx)
            }
            PopoverCaretTipPlacement.Bottom -> {
                moveTo(horizontalCaretXPosition(), 0f)
                relativeLineTo(caretSizePx, -caretSizePx)
                relativeLineTo(caretSizePx, caretSizePx)
            }
        }

        close()
    }

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val cornerRadius = CornerRadius(cornerSize.toPx(size, density))

        return Outline.Generic(
            Path().apply {
                addRoundRect(
                    RoundRect(rect = size.toRect(), cornerRadius = cornerRadius)
                )

                addCaretTip(
                    size = size,
                    layoutDirection = layoutDirection,
                    density = density
                )
            }
        )
    }

    internal companion object {
        val caretTipSize = 8.dp
    }
}

@Composable
internal fun rememberPopoverCaretTipShape(
    placement: PopoverCaretTipPlacement,
    alignment: PopoverCaretTipAlignment,
    tooltipContentPaddingValues: PaddingValues,
): PopoverCaretTipShape = remember(
    placement, alignment, tooltipContentPaddingValues
) {
    PopoverCaretTipShape(placement, alignment, tooltipContentPaddingValues)
}
