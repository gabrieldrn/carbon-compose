package dev.gabrieldrn.carbon.toggle

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
import dev.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.foundation.color.Theme
import dev.gabrieldrn.carbon.foundation.interaction.FocusIndicationInstance
import kotlinx.coroutines.flow.filterIsInstance

internal class ToggleFocusIndication(
    val toggleDimensions: ToggleDimensions
) : Indication {

    private inner class ToggleIndicationInstance(theme: Theme) : FocusIndicationInstance(theme) {

        // From React implementation, focus animation is immediate:
        // https://react.carbondesignsystem.com/?path=/docs/components-toggle--overview
        override val focusAnimationSpec = snap<Float>()

        override fun ContentDrawScope.drawIndication() {
            val borderStrokeWidthPx = borderFocusWidth.toPx()
            val insetWidthPx = insetFocusWidth.toPx()

            val borderSize = Size(
                size.width + borderStrokeWidthPx + insetWidthPx * 2,
                size.height + borderStrokeWidthPx + insetWidthPx * 2
            )

            drawContent()

            inset(borderStrokeWidthPx * -0.5f - insetWidthPx) {
                drawRoundRect(
                    brush = SolidColor(borderFocusColorState.value),
                    cornerRadius = CornerRadius(toggleDimensions.height.toPx()),
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
            ToggleIndicationInstance(theme = theme)
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
