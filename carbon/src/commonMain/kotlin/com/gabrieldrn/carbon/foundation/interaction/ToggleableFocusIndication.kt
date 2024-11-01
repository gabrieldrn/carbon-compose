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

import androidx.compose.animation.core.snap
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.unit.Dp
import com.gabrieldrn.carbon.foundation.color.Theme

internal class ToggleableFocusIndication(
    private val theme: Theme,
    val indicationCornerRadius: Dp
) : IndicationNodeFactory {

    private inner class ToggleableIndicationInstance(
        interactionSource: InteractionSource,
        theme: Theme
    ) : FocusIndicationInstance(interactionSource, theme) {

        // From React implementation, focus animation is immediate on toggleable components.
        // e.g. https://react.carbondesignsystem.com/?path=/docs/components-toggle--overview
        // This also applies to checkboxes and radio buttons.
        override val focusAnimationSpec = snap<Float>()

        override fun ContentDrawScope.draw() {
            val borderStrokeWidthPx = borderFocusWidth.toPx()
            val insetWidthPx = insetFocusWidth.toPx()

            val borderSize = Size(
                size.width + borderStrokeWidthPx + insetWidthPx * 2,
                size.height + borderStrokeWidthPx + insetWidthPx * 2
            )

            drawContent()

            inset(-borderStrokeWidthPx * .5f - insetWidthPx) {
                drawRoundRect(
                    brush = SolidColor(borderFocusColorState.value),
                    cornerRadius = CornerRadius(indicationCornerRadius.toPx()),
                    size = borderSize,
                    style = Stroke(borderStrokeWidthPx)
                )
            }
        }
    }

    override fun create(interactionSource: InteractionSource): DelegatableNode =
        ToggleableIndicationInstance(interactionSource, theme)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ToggleableFocusIndication) return false

        if (theme != other.theme) return false
        if (indicationCornerRadius != other.indicationCornerRadius) return false

        return true
    }

    override fun hashCode(): Int {
        var result = theme.hashCode()
        result = 31 * result + indicationCornerRadius.hashCode()
        return result
    }
}
