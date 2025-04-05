/*
 * Copyright 2024 Gabriel Derrien
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

package com.gabrieldrn.carbon.foundation.interaction

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.snap
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.node.DelegatableNode
import com.gabrieldrn.carbon.foundation.color.Theme

/**
 * Implements the basic focus indication from Carbon, consisting of a border (of the theme's focus
 * color) with sharp corners and an inset that animates in and out when focus is gained or lost.
 */
internal open class FocusIndication(private val theme: Theme) : IndicationNodeFactory {

    internal open class DefaultFocusIndicationInstance(
        interactionSource: InteractionSource,
        theme: Theme,
        useInverseColor: Boolean = false
    ) : FocusIndicationInstance(interactionSource, theme, useInverseColor) {

        override val focusAnimationSpec: FiniteAnimationSpec<Float> = snap()

        override fun ContentDrawScope.draw() {
            drawContent()
            clipRect { drawFocus() }
        }

        private fun DrawScope.drawFocus() {
            val borderStrokeWidthPx = (borderFocusWidth * focusAnimation.value).toPx()
            val insetStrokeWidthPx = (insetFocusWidth * focusAnimation.value).toPx()

            val borderHalfStroke = borderStrokeWidthPx * .5f
            val insetOffset = borderStrokeWidthPx + borderHalfStroke * .5f

            val topLeft = Offset(borderHalfStroke, borderHalfStroke)
            val topLeftInset = Offset(insetOffset, insetOffset)

            val borderSize = Size(
                size.width - borderStrokeWidthPx,
                size.height - borderStrokeWidthPx
            )
            val insetSize = Size(
                size.width - insetOffset * 2,
                size.height - insetOffset * 2
            )

            // The inset is drawn first to make it appear below the border while animating
            drawRect(
                brush = SolidColor(insetFocusColorState.value),
                topLeft = topLeftInset,
                size = insetSize,
                style = Stroke(insetStrokeWidthPx)
            )
            drawRect(
                brush = SolidColor(borderFocusColorState.value),
                topLeft = topLeft,
                size = borderSize,
                style = Stroke(borderStrokeWidthPx)
            )
        }
    }

    override fun create(interactionSource: InteractionSource): DelegatableNode =
        DefaultFocusIndicationInstance(interactionSource, theme)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FocusIndication) return false

        if (theme != other.theme) return false

        return true
    }

    override fun hashCode(): Int = theme.hashCode()
}
