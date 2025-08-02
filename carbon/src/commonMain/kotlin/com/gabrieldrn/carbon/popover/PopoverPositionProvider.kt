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
import com.gabrieldrn.carbon.tooltip.getTooltipContentPaddingByPosition

internal class PopoverPositionProvider
@VisibleForTesting(otherwise = VisibleForTesting.Companion.PACKAGE_PRIVATE) constructor(
    private val placement: PopoverPlacement,
    private val alignment: PopoverCaretTipAlignment,
    private val caretSize: Dp,
    private val tooltipContentPaddingValues: PaddingValues,
    density: Density,
) : PopupPositionProvider {

    private val caretSizeInt = with(density) {
        caretSize.roundToPx()
    }

    // Spacing around the actual popup. This allows to draw the caret.
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

    // Distance between the edge of the content (excluding the margin around) and the caret tip.
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
                PopoverCaretTipAlignment.Start ->
                    anchorBounds.left + -edgeToCaretTipOffset + uiTriggerHalfWidth

                PopoverCaretTipAlignment.Center ->
                    anchorBounds.left + (anchorBounds.width - popupContentSize.width) / 2

                PopoverCaretTipAlignment.End ->
                    anchorBounds.right -
                        popupContentSize.width +
                        edgeToCaretTipOffset -
                        uiTriggerHalfWidth
            }
        }

        fun verticalYOffset(): Int {
            val uiTriggerHalfHeight = anchorBounds.height / 2

            val adjustedAlignment = if (popupContentSize.height < anchorBounds.height) {
                PopoverCaretTipAlignment.Center
            } else {
                alignment
            }

            return when (adjustedAlignment) {
                PopoverCaretTipAlignment.Start ->
                    anchorBounds.top + -edgeToCaretTipOffset + uiTriggerHalfHeight

                PopoverCaretTipAlignment.Center ->
                    anchorBounds.top + (anchorBounds.height - popupContentSize.height) / 2

                PopoverCaretTipAlignment.End ->
                    anchorBounds.bottom -
                        popupContentSize.height +
                        edgeToCaretTipOffset -
                        uiTriggerHalfHeight
            }
        }

        return when (placement) {
            PopoverPlacement.Right -> IntOffset(
                x = anchorBounds.right + popupPadding,
                y = verticalYOffset()
            )

            PopoverPlacement.Left -> IntOffset(
                x = anchorBounds.left - popupContentSize.width - popupPadding,
                y = verticalYOffset()
            )

            PopoverPlacement.Bottom -> IntOffset(
                x = horizontalXOffset(),
                y = anchorBounds.bottom + popupPadding
            )

            PopoverPlacement.Top -> IntOffset(
                x = horizontalXOffset(),
                y = anchorBounds.top - popupContentSize.height - popupPadding
            )
        }
    }
}
