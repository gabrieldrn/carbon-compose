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

package com.gabrieldrn.carbon.popover.tabtip

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
import com.gabrieldrn.carbon.button.ButtonSize
import com.gabrieldrn.carbon.popover.PopoverAlignment

internal class PopoverTapTipPositionProvider
@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE) constructor(
    private val iconButtonSize: ButtonSize,
    private val alignment: PopoverAlignment,
    density: Density,
) : PopupPositionProvider {

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset = IntOffset(
        x = when (alignment) {
            PopoverAlignment.Start ->
                anchorBounds.left

            PopoverAlignment.End ->
                anchorBounds.right - popupContentSize.width
        },
        y = anchorBounds.top
    )
}

@Composable
internal fun rememberPopoverTapTipPositionProvider(
    buttonSize: ButtonSize,
    alignment: PopoverAlignment,
): PopoverTapTipPositionProvider {
    val density = LocalDensity.current

    return remember(buttonSize, alignment, density) {
        PopoverTapTipPositionProvider(
            iconButtonSize = buttonSize,
            alignment = alignment,
            density = density,
        )
    }
}
