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

package com.gabrieldrn.carbon.popover

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.tooltip.getTooltipContentPaddingByPosition

internal open class PopoverShape(
    private val placement: PopoverPlacement,
    private val alignment: PopoverCaretTipAlignment,
    private val popoverContentPaddingValues: PaddingValues,
) : Shape {

    open val caretSize: Dp = Companion.caretSize

    private fun Path.addTooltip(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) {
        val caretSizePx = with(density) { caretSize.toPx() }
        val contentPadding = with(density) {
            popoverContentPaddingValues
                .getTooltipContentPaddingByPosition(
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
            PopoverPlacement.Right -> {
                moveTo(0f, verticalCaretYPosition())
                relativeLineTo(-caretSizePx, caretSizePx)
                relativeLineTo(caretSizePx, caretSizePx)
            }
            PopoverPlacement.Left -> {
                moveTo(size.width, verticalCaretYPosition())
                relativeLineTo(caretSizePx, caretSizePx)
                relativeLineTo(-caretSizePx, caretSizePx)
            }
            PopoverPlacement.Top -> {
                moveTo(horizontalCaretXPosition(), size.height)
                relativeLineTo(caretSizePx, caretSizePx)
                relativeLineTo(caretSizePx, -caretSizePx)
            }
            PopoverPlacement.Bottom -> {
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
                    RoundRect(
                        rect = size.toRect(),
                        topLeft = cornerRadius,
                        topRight = cornerRadius,
                        bottomRight = cornerRadius,
                        bottomLeft = cornerRadius
                    )
                )

                addTooltip(
                    size = size,
                    layoutDirection = layoutDirection,
                    density = density
                )
            }
        )
    }

    internal companion object {
        val cornerSize = CornerSize(2.dp)
        val caretSize = 8.dp
    }
}

@Composable
internal fun rememberPopupShape(
    placement: PopoverPlacement,
    alignment: PopoverCaretTipAlignment,
    tooltipContentPaddingValues: PaddingValues,
): PopoverShape = remember(
    placement, alignment, tooltipContentPaddingValues
) {
    PopoverShape(placement, alignment, tooltipContentPaddingValues)
}
