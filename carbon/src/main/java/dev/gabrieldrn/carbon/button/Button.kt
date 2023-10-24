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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.gabrieldrn.carbon.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.color.Theme
import dev.gabrieldrn.carbon.text.CarbonTypography
import dev.gabrieldrn.carbon.text.Text

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
    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = ButtonIndication(buttonType),
                onClick = onClick,
            )
            .focusable(interactionSource = interactionSource)
            .hoverable(interactionSource = interactionSource)
            .height(buttonSize.height),
    ) {
        Label(
            isEnabled = isEnabled,
            buttonType = buttonType,
            interactionSource = interactionSource,
            label = label,
            buttonSize = buttonSize
        )
    }
}

@Composable
private fun Label(
    label: String,
    buttonType: CarbonButton,
    isEnabled: Boolean,
    buttonSize: ButtonSize,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    // TODO Optimize this to use only the colors that are needed
    val theme = LocalCarbonTheme.current

    val labelTextColor by remember {
        derivedStateOf {
            when {
                !isEnabled -> theme.textOnColorDisabled
                buttonType == CarbonButton.Tertiary -> theme.buttonTertiary
                buttonType == CarbonButton.Ghost -> theme.linkPrimary
                buttonType in arrayOf(CarbonButton.TertiaryDanger, CarbonButton.GhostDanger) ->
                    theme.buttonDangerSecondary

                else -> theme.textOnColor
            }
        }
    }

    val animatedLabelTextColor = remember {
        Animatable(labelTextColor)
    }

    LaunchedEffect(theme) {
        interactionSource
            .interactions
            .collect { interaction ->
                animatedLabelTextColor.animateTo(
                    targetValue = getTargetLabelTextColor(
                        interaction,
                        buttonType,
                        theme,
                        labelTextColor
                    ),
                    animationSpec = tween(
                        durationMillis = buttonTransitionDurationMillis,
                        easing = buttonTransitionEasing
                    ),
                )
            }
    }

    Text(
        text = label,
        color = animatedLabelTextColor.value,
        style = CarbonTypography.bodyCompact01,
        modifier = modifier
            .padding(buttonSize.getLabelPaddings())
            .fillMaxHeight()
    )
}

private fun getTargetLabelTextColor(
    interaction: Interaction,
    buttonType: CarbonButton,
    theme: Theme,
    labelTextColor: Color
): Color {
    return when (interaction) {
        is PressInteraction.Press -> when (buttonType) {
            CarbonButton.Tertiary -> theme.textInverse
            CarbonButton.GhostDanger,
            CarbonButton.TertiaryDanger -> theme.textOnColor

            else -> labelTextColor
        }

        is HoverInteraction.Enter -> when (buttonType) {
            CarbonButton.Tertiary,
            CarbonButton.TertiaryDanger,
            CarbonButton.GhostDanger -> theme.textOnColor

            CarbonButton.Ghost -> theme.linkPrimaryHover

            else -> labelTextColor
        }

        else -> labelTextColor
    }
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
