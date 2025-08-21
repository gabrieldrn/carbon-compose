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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.gabrieldrn.carbon.button.ButtonSize
import com.gabrieldrn.carbon.button.heightDp
import com.gabrieldrn.carbon.popover.PopoverAlignment
import com.gabrieldrn.carbon.popover.PopoverShape

internal open class PopoverTabTipShape(
    private val alignment: PopoverAlignment,
    private val buttonSize: ButtonSize,
) : PopoverShape() {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val cornerRadius = CornerRadius(cornerSize.toPx(size, density))
        val tabSizePx = with(density) { buttonSize.heightDp().toPx() }

        return Outline.Generic(
            Path().apply {
                addRect(
                    when (alignment) {
                        PopoverAlignment.Start ->
                            Rect(
                                Offset(0f, 0f),
                                Size(tabSizePx, tabSizePx)
                            )
                        PopoverAlignment.End ->
                            Rect(
                                Offset(size.width, 0f),
                                Size(-tabSizePx, tabSizePx)
                            )
                    }
                )

                addRoundRect(
                    RoundRect(
                        rect = size.toRect().translate(translateX = 0f, translateY = tabSizePx),
                        topLeft = if (alignment == PopoverAlignment.Start) {
                            CornerRadius.Zero
                        } else {
                            cornerRadius
                        },
                        topRight = if (alignment == PopoverAlignment.End) {
                            CornerRadius.Zero
                        } else {
                            cornerRadius
                        },
                        bottomRight = cornerRadius,
                        bottomLeft = cornerRadius
                    )
                )
            }
        )
    }
}

@Composable
internal fun rememberPopoverTabTipShape(
    alignment: PopoverAlignment,
    buttonSize: ButtonSize
): PopoverTabTipShape = remember(alignment, buttonSize) {
    PopoverTabTipShape(alignment, buttonSize)
}
