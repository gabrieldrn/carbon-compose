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
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.unit.Dp
import com.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import com.gabrieldrn.carbon.foundation.color.Theme
import kotlinx.coroutines.flow.filterIsInstance

internal class ToggleableFocusIndication(val indicationCorderRadius: Dp) : Indication {

    private inner class ToggleableIndicationInstance(
        theme: Theme
    ) : FocusIndicationInstance(theme) {

        // From React implementation, focus animation is immediate on toggleable components.
        // e.g. https://react.carbondesignsystem.com/?path=/docs/components-toggle--overview
        // This also applies to checkboxes and radio buttons.
        override val focusAnimationSpec = snap<Float>()

        override fun ContentDrawScope.drawIndication() {
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
                    cornerRadius = CornerRadius(indicationCorderRadius.toPx()),
                    size = borderSize,
                    style = Stroke(borderStrokeWidthPx)
                )
            }
        }
    }

    @Composable
    override fun rememberUpdatedInstance(
        interactionSource: InteractionSource
    ): IndicationInstance {
        val theme = LocalCarbonTheme.current

        val instance = remember(theme) {
            ToggleableIndicationInstance(theme = theme)
        }

        LaunchedEffect(interactionSource) {
            interactionSource.interactions
                .filterIsInstance<FocusInteraction>()
                .collect { interaction ->
                    instance.animateFocus(this, interaction)
                }
        }

        return instance
    }
}
