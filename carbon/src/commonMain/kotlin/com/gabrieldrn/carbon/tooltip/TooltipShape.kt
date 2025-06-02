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

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

internal class TooltipShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val cornerRadius = CornerRadius(cornerSize.toPx(size, density))
        val caretSizePx = with(density) { caretSize.toPx() }

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

                moveTo(size.width / 2 - caretSizePx, 0f)
                relativeLineTo(caretSizePx, -caretSizePx)
                relativeLineTo(caretSizePx, caretSizePx)
                close()
            }
        )
    }

    companion object {
        val cornerSize = CornerSize(2.dp)
        val caretSize = 6.dp
    }
}
