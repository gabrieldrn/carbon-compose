package dev.gabrieldrn.carbon.button

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.Interaction
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
import androidx.compose.ui.unit.dp
import dev.gabrieldrn.carbon.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.color.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class ButtonIndication(
    private val buttonType: CarbonButton
) : Indication {

    private class ButtonIndicationInstance(
        theme: Theme,
        buttonType: CarbonButton
    ) : IndicationInstance {

        private val borderFocusColor = theme.focus
        private val insetFocusColor =
            if (buttonType == CarbonButton.Ghost) {
                Color.Transparent
            } else {
                theme.focusInset
            }

        private val focusAnimation = Animatable(1f)

        fun animateFocus(scope: CoroutineScope, interaction: Interaction) {
            scope.launch {
                focusAnimation.animateTo(
                    targetValue = if (interaction is FocusInteraction.Focus) 0f else 1f,
                    animationSpec = tween(
                        durationMillis = ButtonTransitionDurationMillis,
                        easing = buttonTransitionEasing
                    )
                )
            }
        }

        override fun ContentDrawScope.drawIndication() {
            drawContent()
            clipRect { drawFocus() }
        }

        private fun DrawScope.drawFocus() {
            val borderStrokeWidthPx = with(FOCUS_BORDER_WIDTH.dp) { toPx() }
            val insetStrokeWidthPx = with(FOCUS_INSET_WIDTH.dp) { toPx() }

            val borderHalfStroke = borderStrokeWidthPx / 2
            val insetOffset = borderStrokeWidthPx + (borderHalfStroke / 2)

            val borderStrokeOffset = Offset(borderStrokeWidthPx, borderStrokeWidthPx)
            val insetStrokeOffset = borderStrokeOffset + Offset(insetOffset / 2, insetOffset / 2)

            val topLeft = Offset(borderHalfStroke, borderHalfStroke)
                .minus(borderStrokeOffset * focusAnimation.value)
            val topLeftInset = Offset(insetOffset, insetOffset)
                .minus(insetStrokeOffset * focusAnimation.value)

            val borderSize = (borderStrokeWidthPx * 2 * focusAnimation.value).let { animOffset ->
                Size(
                    size.width - borderStrokeWidthPx + animOffset,
                    size.height - borderStrokeWidthPx + animOffset
                )
            }
            val insetSize = (
                insetOffset *
                    2.60f * // A magic number never heart a button, right?
                    focusAnimation.value
                ).let { animOffset ->
                    Size(
                        size.width - insetOffset * 2 + animOffset,
                        size.height - insetOffset * 2 + animOffset
                    )
                }
            // The inset is drawn first to make it appear below the border while animating
            drawRect(
                brush = SolidColor(insetFocusColor),
                topLeft = topLeftInset,
                size = insetSize,
                style = Stroke(insetStrokeWidthPx)
            )
            drawRect(
                brush = SolidColor(borderFocusColor),
                topLeft = topLeft,
                size = borderSize,
                style = Stroke(borderStrokeWidthPx)
            )
        }

        companion object {
            private const val FOCUS_BORDER_WIDTH = 2f
            private const val FOCUS_INSET_WIDTH = 1f
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
            interactionSource.interactions.collect { interaction ->
                instance.animateFocus(this, interaction)
            }
        }

        return instance
    }
}
