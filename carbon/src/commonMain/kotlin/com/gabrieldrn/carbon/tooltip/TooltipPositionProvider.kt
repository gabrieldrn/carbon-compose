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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

internal class TooltipPositionProvider
@VisibleForTesting(otherwise = VisibleForTesting.Companion.PRIVATE) constructor(
    private val placement: TooltipPlacement,
    private val alignment: TooltipAlignment,
    private val caretSize: Dp,
    private val tooltipContentPaddingValues: PaddingValues,
    private val isSingleLine: Boolean,
    density: Density,
) : PopupPositionProvider {

    private val caretSizeInt = with(density) {
        caretSize.roundToPx()
    }

    private val popupPadding = with(density) {
        SpacingScale.spacing02.roundToPx()
    }

    private val contentPadding = with(density) {
        tooltipContentPaddingValues
            .getTooltipContentPaddingByPosition(
                placement = placement,
                alignment = alignment,
                layoutDirection = LayoutDirection.Ltr // Assuming LTR for simplicity
            )
            .roundToPx()
    }

    private val edgeToCaretTipOffset =
        popupPadding + contentPadding + 2 * caretSizeInt

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {

        fun horizontalXOffset(): Int {
            val uiTriggerHalfWidth = anchorBounds.width / 2

            return when (alignment) {
                TooltipAlignment.Start -> anchorBounds.left +
                    -edgeToCaretTipOffset +
                    uiTriggerHalfWidth
                TooltipAlignment.Center -> anchorBounds.left +
                    (anchorBounds.width - popupContentSize.width) / 2
                TooltipAlignment.End -> anchorBounds.right -
                    popupContentSize.width +
                    edgeToCaretTipOffset -
                    uiTriggerHalfWidth
            }
        }

        fun verticalYOffset(): Int {
            val uiTriggerHalfEight = anchorBounds.height / 2

            return if (isSingleLine) {
                anchorBounds.top + (anchorBounds.height - popupContentSize.height) / 2
            } else {
                when (alignment) {
                    TooltipAlignment.Start -> anchorBounds.top +
                        -edgeToCaretTipOffset +
                        uiTriggerHalfEight
                    TooltipAlignment.Center -> anchorBounds.top +
                        (anchorBounds.height - popupContentSize.height) / 2
                    TooltipAlignment.End -> anchorBounds.bottom -
                        popupContentSize.height +
                        edgeToCaretTipOffset -
                        uiTriggerHalfEight
                }
            }
        }

        return when (placement) {
            TooltipPlacement.Right -> IntOffset(
                x = anchorBounds.right + popupPadding,
                y = verticalYOffset()
            )

            TooltipPlacement.Left -> IntOffset(
                x = anchorBounds.left - popupContentSize.width - popupPadding,
                y = verticalYOffset()
            )

            TooltipPlacement.Bottom -> IntOffset(
                x = horizontalXOffset(),
                y = anchorBounds.bottom + popupPadding
            )

            TooltipPlacement.Top -> IntOffset(
                x = horizontalXOffset(),
                y = anchorBounds.top - popupContentSize.height - popupPadding
            )
        }
    }
}
