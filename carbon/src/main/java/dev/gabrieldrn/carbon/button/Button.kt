package dev.gabrieldrn.carbon.button

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.gabrieldrn.carbon.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.color.Theme
import dev.gabrieldrn.carbon.text.CarbonTypography

// From the react-native implementation
internal const val buttonTransitionDurationMillis = 70
internal val buttonTransitionEasing = CubicBezierEasing(0f, 0f, 0.38f, 0.9f)

/**
 * TODO Documentation
 *
 */
// TODO Support system font scale?
@Composable
public fun Button(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonType: CarbonButton = CarbonButton.Primary,
    isEnabled: Boolean = true,
    buttonSize: ButtonSize = ButtonSize.Medium,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val colors = ButtonColors.buttonColors(buttonType = buttonType)

    val containerColor = remember { Animatable(colors.containerColor) }

    LaunchedEffect(isEnabled) {
        containerColor.animateTo(
            targetValue = if (isEnabled) colors.containerColor else colors.containerDisabledColor,
            animationSpec = tween(
                durationMillis = buttonTransitionDurationMillis,
                easing = buttonTransitionEasing
            )
        )
    }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect {
            if (!isEnabled) return@collect
            containerColor.animateTo(
                targetValue = when (it) {
                    is HoverInteraction.Enter -> colors.containerHoverColor
                    is PressInteraction.Press -> colors.containerActiveColor
                    else -> colors.containerColor
                },
                animationSpec = tween(
                    durationMillis = buttonTransitionDurationMillis,
                    easing = buttonTransitionEasing
                )
            )
        }
    }

    Box(
        modifier = modifier
            .drawBehind {
                drawRect(color = containerColor.value)
            }
            .clickable(
                interactionSource = interactionSource,
                indication = ButtonIndication(buttonType),
                onClick = onClick,
                enabled = isEnabled
            )
            .focusable(
                interactionSource = interactionSource,
                enabled = isEnabled
            )
            .hoverable(
                interactionSource = interactionSource,
                enabled = isEnabled
            )
            .height(buttonSize.height),
    ) {
        Label(
            isEnabled = isEnabled,
            colors = colors,
            interactionSource = interactionSource,
            label = label,
            buttonSize = buttonSize
        )
    }
}

@Composable
private fun Label(
    label: String,
    colors: ButtonColors,
    isEnabled: Boolean,
    buttonSize: ButtonSize,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    val theme = LocalCarbonTheme.current

    val animatedLabelTextColor = remember { Animatable(colors.labelColor) }

    LaunchedEffect(isEnabled) {
        animatedLabelTextColor.animateTo(
            targetValue = if (isEnabled) colors.labelColor else colors.labelDisabledColor,
            animationSpec = tween(
                durationMillis = buttonTransitionDurationMillis,
                easing = buttonTransitionEasing
            )
        )
    }

    LaunchedEffect(theme) {
        interactionSource
            .interactions
            .collect { interaction ->
                if (!isEnabled) return@collect
                animatedLabelTextColor.animateTo(
                    targetValue = when (interaction) {
                        is HoverInteraction.Enter -> colors.labelHoverColor
                        is PressInteraction.Press -> colors.labelActiveColor
                        else -> colors.labelColor
                    },
                    animationSpec = tween(
                        durationMillis = buttonTransitionDurationMillis,
                        easing = buttonTransitionEasing
                    ),
                )
            }
    }

    BasicText(
        text = label,
        modifier = modifier
            .padding(buttonSize.getLabelPaddings())
            .fillMaxHeight(),
        style = CarbonTypography.bodyCompact01,
        color = { animatedLabelTextColor.value }
    )
}

private class ButtonPreviewParameterProvider :
    PreviewParameterProvider<Pair<CarbonButton, ButtonSize>> {

    override val values: Sequence<Pair<CarbonButton, ButtonSize>>
        get() = CarbonButton.entries.flatMap { type ->
            ButtonSize.entries.map { size -> type to size }
        }.asSequence()
}

@Preview(group = "All")
@Composable
private fun ButtonPreview(
    @PreviewParameter(ButtonPreviewParameterProvider::class)
    combination: Pair<CarbonButton, ButtonSize>,
) {
    Box(modifier = Modifier.padding(8.dp)) {
        Button(
            label = "${combination.first.name} - ${combination.second.name}",
            onClick = {},
            buttonType = combination.first,
            buttonSize = combination.second,
        )
    }
}
