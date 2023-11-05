package dev.gabrieldrn.carbon.button

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.referentialEqualityPolicy
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
import dev.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.foundation.color.Theme
import dev.gabrieldrn.carbon.foundation.motion.Motion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

internal class ButtonIndication(
    private val buttonType: CarbonButton
) : Indication {

    private class ButtonIndicationInstance(
        theme: Theme,
        buttonType: CarbonButton
    ) : IndicationInstance {

        private val borderFocusColor = theme.focus
        private val insetFocusColor = if (buttonType == CarbonButton.Ghost) {
            Color.Transparent
        } else {
            theme.focusInset
        }

        private val focusAnimation = Animatable(0f)

        private val borderFocusColorState = derivedStateOf(referentialEqualityPolicy()) {
            // Strangely, when the focus border is multiplied by 0, there is still a border
            // visible. This is why the border and the inset are set to transparent when the focus
            // exits.
            if (focusAnimation.value == 0f) {
                Color.Transparent
            } else {
                borderFocusColor
            }
        }

        private val insetFocusColorState = derivedStateOf(referentialEqualityPolicy()) {
            if (focusAnimation.value == 0f) {
                Color.Transparent
            } else {
                insetFocusColor
            }
        }

        fun animateFocus(scope: CoroutineScope, interaction: FocusInteraction) {
            scope.launch {
                focusAnimation.animateTo(
                    targetValue = if (interaction is FocusInteraction.Focus) 1f else 0f,
                    animationSpec = tween(
                        durationMillis = ButtonTransitionDurationMillis,
                        easing = Motion.Entrance.productiveEasing
                    )
                )
            }
        }

        override fun ContentDrawScope.drawIndication() {
            drawContent()
            clipRect { drawFocus() }
        }

        private fun DrawScope.drawFocus() {
            val borderStrokeWidthPx = (FOCUS_BORDER_WIDTH * focusAnimation.value).dp.toPx()
            val insetStrokeWidthPx = (FOCUS_INSET_WIDTH * focusAnimation.value).dp.toPx()

            val borderHalfStroke = borderStrokeWidthPx / 2
            val insetOffset = borderStrokeWidthPx + borderHalfStroke / 2

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
            interactionSource.interactions
                .filterIsInstance<FocusInteraction>()
                .collect { interaction ->
                    instance.animateFocus(this, interaction)
                }
        }

        return instance
    }
}
