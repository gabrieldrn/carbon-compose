package dev.gabrieldrn.carbon.button

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

        private val containerColor = when (buttonType) {
            CarbonButton.Primary -> theme.buttonPrimary
            CarbonButton.Secondary -> theme.buttonSecondary
            CarbonButton.PrimaryDanger -> theme.buttonDangerPrimary
            CarbonButton.TertiaryDanger -> theme.buttonDangerSecondary
            CarbonButton.Tertiary,
            CarbonButton.Ghost,
            CarbonButton.GhostDanger -> Color.Transparent
        }

        private val activeContainerColor = when (buttonType) {
            CarbonButton.Primary -> theme.buttonPrimaryActive
            CarbonButton.Secondary -> theme.buttonSecondaryActive
            CarbonButton.Tertiary -> theme.buttonTertiaryActive
            CarbonButton.Ghost -> theme.backgroundActive
            CarbonButton.PrimaryDanger,
            CarbonButton.TertiaryDanger,
            CarbonButton.GhostDanger -> theme.buttonDangerActive
        }

        private val hoverContainerColor = when (buttonType) {
            CarbonButton.Primary -> theme.buttonPrimaryHover
            CarbonButton.Secondary -> theme.buttonSecondaryHover
            CarbonButton.Tertiary -> theme.buttonTertiaryHover
            CarbonButton.Ghost -> theme.backgroundHover
            CarbonButton.PrimaryDanger,
            CarbonButton.TertiaryDanger,
            CarbonButton.GhostDanger -> theme.buttonDangerHover
        }

        private val borderFocusContainerColor = theme.focus
        private val insetFocusContainerColor =
            if (buttonType == CarbonButton.Ghost) {
                Color.Transparent
            } else {
                theme.focusInset
            }

        private val focusAnimation = Animatable(1f)
        private val animatedContainerColor = androidx.compose.animation.Animatable(containerColor)

        var isPressed by mutableStateOf(false)
        var isHovered by mutableStateOf(false)
        var isFocused by mutableStateOf(false)

        fun animateFocus(scope: CoroutineScope, interaction: Interaction) {
            scope.launch {
                focusAnimation.animateTo(
                    targetValue = if (interaction is FocusInteraction.Focus) 0f else 1f,
                    animationSpec = tween(
                        durationMillis = INDICATION_TRANSITION_DURATION_MILLIS,
                        easing = indicationTransitionEasing
                    )
                )
            }
        }

        fun animateBackground(scope: CoroutineScope, interaction: Interaction) {
            scope.launch {
                animatedContainerColor.animateTo(
                    targetValue = when (interaction) {
                        is HoverInteraction.Enter -> hoverContainerColor
                        is PressInteraction.Press -> activeContainerColor
                        else -> containerColor
                    },
                    animationSpec = tween(
                        durationMillis = INDICATION_TRANSITION_DURATION_MILLIS,
                        easing = indicationTransitionEasing
                    )
                )
            }
        }

        override fun ContentDrawScope.drawIndication() {
            drawRect(color = animatedContainerColor.value)
            clipRect { drawFocus() }
            drawContent()
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
            val insetSize = (insetOffset * 2.60f * focusAnimation.value).let { animOffset ->
                Size(
                    size.width - insetOffset * 2 + animOffset,
                    size.height - insetOffset * 2 + animOffset
                )
            }
            // The inset is drawn first to make it appear below the border while animating
            drawRect(
                brush = SolidColor(insetFocusContainerColor),
                topLeft = topLeftInset,
                size = insetSize,
                style = Stroke(insetStrokeWidthPx)
            )
            drawRect(
                brush = SolidColor(borderFocusContainerColor),
                topLeft = topLeft,
                size = borderSize,
                style = Stroke(borderStrokeWidthPx)
            )
        }

        companion object {
            // From the react-native implementation
            private const val INDICATION_TRANSITION_DURATION_MILLIS = 70
            private const val FOCUS_BORDER_WIDTH = 2f
            private const val FOCUS_INSET_WIDTH = 1f

            // From the react-native implementation
            private val indicationTransitionEasing = CubicBezierEasing(0f, 0f, 0.38f, 0.9f)
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
                when (interaction) {
                    is PressInteraction.Press -> instance.isPressed = true
                    is PressInteraction.Release -> instance.isPressed = false

                    is FocusInteraction.Focus -> instance.isFocused = true
                    is FocusInteraction.Unfocus -> instance.isFocused = false

                    is HoverInteraction.Enter -> instance.isHovered = true
                    is HoverInteraction.Exit -> instance.isHovered = false
                }

                instance.animateBackground(this, interaction)
                instance.animateFocus(this, interaction)
            }
        }

        return instance
    }
}
