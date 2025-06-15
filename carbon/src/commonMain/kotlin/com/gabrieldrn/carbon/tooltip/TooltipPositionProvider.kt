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
import androidx.compose.foundation.layout.calculateStartPadding
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
    density: Density,
) : PopupPositionProvider {

    private val caretSizeInt = with(density) {
        caretSize.roundToPx()
    }

    private val popupPadding = with(density) {
        SpacingScale.spacing02.roundToPx()
    }

    private val horizontalContentPadding = with(density) {
        tooltipContentPaddingValues
            .calculateStartPadding(LayoutDirection.Ltr)
            .roundToPx()
    }

//    private val verticalContentPadding = with(density) {
//        when (placement) {
//            TooltipPlacement.Top -> tooltipContentPaddingValues.calculateTopPadding()
//            TooltipPlacement.Bottom -> tooltipContentPaddingValues.calculateBottomPadding()
//            else -> 0.dp
//        }
//            .roundToPx()
//    }

    private val horizontalEdgeToCaretTipOffset =
        popupPadding + horizontalContentPadding + 2 * caretSizeInt

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {

        val uiTriggerHalfWidth = anchorBounds.width / 2

        fun horizontalXOffset(): Int {
            return when (alignment) {
                TooltipAlignment.Start -> anchorBounds.left +
                    -horizontalEdgeToCaretTipOffset +
                    uiTriggerHalfWidth
                TooltipAlignment.Center -> anchorBounds.left +
                    (anchorBounds.width - popupContentSize.width) / 2
                TooltipAlignment.End -> anchorBounds.right -
                    popupContentSize.width +
                    horizontalEdgeToCaretTipOffset -
                    uiTriggerHalfWidth
            }
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
