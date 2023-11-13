package dev.gabrieldrn.carbon.button

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import dev.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.foundation.color.Theme
import dev.gabrieldrn.carbon.foundation.interaction.FocusIndicationInstance
import kotlinx.coroutines.flow.filterIsInstance

internal class ButtonFocusIndication(
    private val buttonType: CarbonButton
) : Indication {

    private class ButtonIndicationInstance(
        theme: Theme,
        buttonType: CarbonButton
    ) : FocusIndicationInstance(theme) {

        override val insetFocusColor = if (buttonType == CarbonButton.Ghost) {
            Color.Transparent
        } else {
            theme.focusInset
        }

        override fun ContentDrawScope.drawIndication() {
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

    @Composable
    override fun rememberUpdatedInstance(
        interactionSource: InteractionSource
    ): IndicationInstance {
        val theme = LocalCarbonTheme.current

        val instance = remember(theme) {
            ButtonIndicationInstance(theme = theme, buttonType = buttonType)
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
