package dev.gabrieldrn.carbon.button

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.gabrieldrn.carbon.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.color.Theme
import dev.gabrieldrn.carbon.text.CarbonTypography
import dev.gabrieldrn.carbon.text.Text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * TODO Documentation
 * TODO Support system font scale?
 */
@Composable
public fun PrimaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    buttonSize: ButtonSize = ButtonSize.Medium,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    // TODO Optimize this to use only the colors that are needed
    val theme = LocalCarbonTheme.current

    val labelTextColor by remember {
        derivedStateOf {
            when {
                !isEnabled -> theme.textOnColorDisabled
                else -> theme.textOnColor
            }
        }
    }

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = PrimaryButtonIndication,
                onClick = onClick,
            )
            .focusable(interactionSource = interactionSource)
            .hoverable(interactionSource = interactionSource)
            .height(buttonSize.height),
    ) {
        Text(
            text = label,
            color = labelTextColor,
            style = CarbonTypography.bodyCompact01,
            modifier = Modifier
                .padding(buttonSize.getLabelPaddings())
                .fillMaxHeight()
        )
    }
}

private object PrimaryButtonIndication : Indication {

    private class PrimaryButtonIndicationInstance(
        private val theme: Theme,
    ) : IndicationInstance {

        var isPressed by mutableStateOf(false)
        var isHovered by mutableStateOf(false)
        var isFocused by mutableStateOf(false)

        private val focusAnimation = Animatable(1f)

        private val animatedBackground = androidx.compose.animation.Animatable(theme.buttonPrimary)

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
                animatedBackground.animateTo(
                    targetValue = when (interaction) {
                        is HoverInteraction.Enter -> theme.buttonPrimaryHover
                        is PressInteraction.Press -> theme.buttonPrimaryActive
                        else -> theme.buttonPrimary
                    },
                    animationSpec = tween(
                        durationMillis = INDICATION_TRANSITION_DURATION_MILLIS,
                        easing = indicationTransitionEasing
                    )
                )
            }
        }

        override fun ContentDrawScope.drawIndication() {
            drawRect(color = animatedBackground.value)
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
                brush = SolidColor(theme.focusInset),
                topLeft = topLeftInset,
                size = insetSize,
                style = Stroke(insetStrokeWidthPx)
            )
            drawRect(
                brush = SolidColor(theme.focus),
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
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val theme = LocalCarbonTheme.current

        val instance = remember(theme) {
            PrimaryButtonIndicationInstance(theme)
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

public enum class CarbonButton {

    Primary,
    Secondary,
    Tertiary,
    Ghost,
    PrimaryDanger,
    TertiaryDanger,
    GhostDanger;

//    public val backgroundColor: Color
//        @Composable get() = when (this) {
//            Primary -> LocalCarbonTheme.current.buttonPrimary
//            Secondary -> LocalCarbonTheme.current.buttonSecondary
//            Tertiary -> Color.Transparent
//            Ghost -> Color.Transparent
//            PrimaryDanger -> LocalCarbonTheme.current.buttonDangerPrimary
//            TertiaryDanger -> LocalCarbonTheme.current.buttonDangerSecondary
//            GhostDanger -> Color.Transparent
//        }

    public val textColor: Color
        @Composable get() = when (this) {
            Primary -> LocalCarbonTheme.current.textOnColor
            Secondary -> LocalCarbonTheme.current.textOnColor
            Tertiary -> LocalCarbonTheme.current.buttonTertiary
            Ghost -> LocalCarbonTheme.current.linkPrimary
            PrimaryDanger -> LocalCarbonTheme.current.textOnColor
            TertiaryDanger -> LocalCarbonTheme.current.buttonDangerSecondary
            GhostDanger -> LocalCarbonTheme.current.buttonDangerSecondary
        }
}

public enum class ButtonSize(internal val height: Dp) {

    Small(height = 32.dp),
    Medium(height = 40.dp),
    LargeProductive(height = 48.dp),
    LargeExpressive(height = 48.dp),
    ExtraLarge(height = 64.dp),
    TwiceExtraLarge(height = 80.dp);

    internal fun getLabelPaddings() = when (this) {
        Small -> PaddingValues(start = 16.dp, top = 7.dp, end = 64.dp, bottom = 7.dp)
        Medium -> PaddingValues(start = 16.dp, top = 11.dp, end = 64.dp, bottom = 11.dp)
        LargeProductive,
        LargeExpressive,
        ExtraLarge,
        TwiceExtraLarge -> PaddingValues(start = 16.dp, top = 14.dp, end = 64.dp, bottom = 14.dp)
    }
}

private class ButtonSizePreviewParameterProvider : PreviewParameterProvider<ButtonSize> {

    override val values: Sequence<ButtonSize>
        get() = ButtonSize.entries.asSequence()
}

@Preview(group = "Primary")
@Composable
private fun PrimaryButtonPreview(
    @PreviewParameter(ButtonSizePreviewParameterProvider::class) size: ButtonSize,
) {
    Box(modifier = Modifier.padding(8.dp)) {
        PrimaryButton(
            label = "Primary Button",
            onClick = {},
            buttonSize = size,
        )
    }
}
