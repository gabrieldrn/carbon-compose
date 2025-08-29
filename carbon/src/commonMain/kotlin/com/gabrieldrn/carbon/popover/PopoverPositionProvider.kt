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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

internal class PopoverPositionProvider
@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE) constructor(
    private val placement: PopoverPlacement,
    private val alignment: PopoverAlignment,
    density: Density,
) : PopupPositionProvider {

    // Spacing around the actual popup. This allows to draw the caret.
    private val popupPadding = with(density) {
        SpacingScale.spacing02.roundToPx()
    }

    // Distance between the edge of the content (excluding the margin around) and the caret tip.
    private val edgeToCaretTipOffset = 0

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {

        fun horizontalXOffset(): Int {
            return when (alignment) {
                PopoverAlignment.Start ->
                    anchorBounds.left + -edgeToCaretTipOffset - popupPadding

                PopoverAlignment.End ->
                    anchorBounds.right -
                        popupContentSize.width +
                        edgeToCaretTipOffset +
                        popupPadding
            }
        }

        fun verticalYOffset(): Int {
            return when (alignment) {
                PopoverAlignment.Start ->
                    anchorBounds.top + -edgeToCaretTipOffset - popupPadding

                PopoverAlignment.End ->
                    anchorBounds.bottom -
                        popupContentSize.height +
                        edgeToCaretTipOffset +
                        popupPadding
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

@Composable
internal fun rememberPopoverPositionProvider(
    alignment: PopoverAlignment,
    placement: PopoverPlacement,
): PopoverPositionProvider {
    val density = LocalDensity.current

    return remember(placement, alignment, density) {
        PopoverPositionProvider(
            placement = placement,
            alignment = alignment,
            density = density,
        )
    }
}
