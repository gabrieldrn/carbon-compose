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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
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

internal class TooltipShape(
    private val placement: TooltipPlacement,
    private val alignment: TooltipAlignment,
    private val tooltipContentPaddingValues: PaddingValues,
    isSingleLine: Boolean
) : Shape {

    val caretSize: Dp = if (isSingleLine) singleSizeCaretSize else multiLineCaretSize

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val cornerRadius = CornerRadius(cornerSize.toPx(size, density))
        val caretSizePx = with(density) { caretSize.toPx() }
        val horizontalContentPadding = with(density) {
            tooltipContentPaddingValues
                .calculateStartPadding(LayoutDirection.Ltr)
                .toPx()
        }

        fun horizontalCaretXPosition(): Float {
            return when (alignment) {
                TooltipAlignment.Start -> horizontalContentPadding
                TooltipAlignment.Center -> size.width / 2 - caretSizePx
                TooltipAlignment.End -> size.width - horizontalContentPadding - caretSizePx * 2
            }
        }

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

                when (placement) {
                    TooltipPlacement.Right -> {
                        moveTo(0f, size.height / 2 - caretSizePx)
                        relativeLineTo(-caretSizePx, caretSizePx)
                        relativeLineTo(caretSizePx, caretSizePx)
                    }
                    TooltipPlacement.Left -> {
                        moveTo(size.width, size.height / 2 - caretSizePx)
                        relativeLineTo(caretSizePx, caretSizePx)
                        relativeLineTo(-caretSizePx, caretSizePx)
                    }
                    TooltipPlacement.Top -> {
                        moveTo(horizontalCaretXPosition(), size.height)
                        relativeLineTo(caretSizePx, caretSizePx)
                        relativeLineTo(caretSizePx, -caretSizePx)
                    }
                    TooltipPlacement.Bottom -> {
                        moveTo(horizontalCaretXPosition(), 0f)
                        relativeLineTo(caretSizePx, -caretSizePx)
                        relativeLineTo(caretSizePx, caretSizePx)
                    }
                }

                close()
            }
        )
    }

    companion object {
        val cornerSize = CornerSize(2.dp)
        private val singleSizeCaretSize = 6.dp
        private val multiLineCaretSize = 8.dp
    }
}

@Composable
internal fun rememberTooltipShape(
    placement: TooltipPlacement,
    alignment: TooltipAlignment,
    tooltipContentPaddingValues: PaddingValues,
    isSingleLine: Boolean
): TooltipShape = remember(
    placement, alignment, tooltipContentPaddingValues, isSingleLine
) {
    TooltipShape(placement, alignment, tooltipContentPaddingValues, isSingleLine)
}
