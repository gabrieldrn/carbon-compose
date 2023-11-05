package dev.gabrieldrn.carbon.button

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import dev.gabrieldrn.carbon.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.spacing.SpacingScale
import dev.gabrieldrn.carbon.text.CarbonTypography

// From the react-native implementation
internal const val ButtonTransitionDurationMillis = 70
internal val buttonTransitionEasing = CubicBezierEasing(0f, 0f, 0.38f, 0.9f)
internal val buttonTransitionSpec: AnimationSpec<Color> = tween(
    durationMillis = ButtonTransitionDurationMillis,
    easing = buttonTransitionEasing
)

/**
 * TODO Documentation.
 *
 */
// TODO Support system font scale?
@Composable
public fun Button(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconPainter: Painter? = null,
    isEnabled: Boolean = true,
    buttonType: CarbonButton = CarbonButton.Primary,
    buttonSize: ButtonSize = ButtonSize.Medium,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    ButtonRowImpl(
        onClick = onClick,
        buttonType = buttonType,
        buttonSize = buttonSize,
        isEnabled = isEnabled,
        modifier = modifier,
        interactionSource = interactionSource,
    ) { buttonScope ->
        Label(
            isEnabled = isEnabled,
            colors = buttonScope.colors,
            interactionSource = interactionSource,
            label = label,
            modifier = Modifier.weight(1f)
        )
        if (iconPainter != null) {
            ButtonIcon(
                painter = iconPainter,
                colors = buttonScope.colors,
                isEnabled = isEnabled,
                interactionSource = interactionSource,
                modifier = if (buttonSize.isExtraLarge) {
                    Modifier.size(16.dp)
                } else {
                    Modifier
                        .width(16.dp)
                        .fillMaxHeight()
                }
            )
            Spacer(modifier = Modifier.width(SpacingScale.spacing05))
        }
    }
}

@Composable
private fun Label(
    label: String,
    colors: ButtonColors,
    isEnabled: Boolean,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    val theme = LocalCarbonTheme.current

    val animatedLabelTextColor = remember { Animatable(colors.labelColor) }

    LaunchedEffect(isEnabled) {
        animatedLabelTextColor.animateTo(
            targetValue = if (isEnabled) colors.labelColor else colors.labelDisabledColor,
            animationSpec = buttonTransitionSpec
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
                    animationSpec = buttonTransitionSpec
                )
            }
    }

    BasicText(
        text = label,
        modifier = modifier.fillMaxHeight(),
        style = CarbonTypography.bodyCompact01,
        color = { animatedLabelTextColor.value }
    )
}

@Composable
internal fun ButtonIcon(
    painter: Painter,
    colors: ButtonColors,
    isEnabled: Boolean,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    val theme = LocalCarbonTheme.current

    val animatedIconColor = remember { Animatable(colors.iconColor) }

    LaunchedEffect(isEnabled) {
        animatedIconColor.animateTo(
            targetValue = if (isEnabled) colors.iconColor else colors.iconDisabledColor,
            animationSpec = buttonTransitionSpec
        )
    }

    LaunchedEffect(theme) {
        interactionSource
            .interactions
            .collect { interaction ->
                if (!isEnabled) return@collect
                animatedIconColor.animateTo(
                    targetValue = when (interaction) {
                        is HoverInteraction.Enter -> colors.iconHoverColor
                        is PressInteraction.Press -> colors.iconActiveColor
                        else -> colors.iconColor
                    },
                    animationSpec = buttonTransitionSpec
                )
            }
    }

    Image(
        painter = painter,
        contentDescription = null,
        colorFilter = ColorFilter.tint(animatedIconColor.value),
        modifier = modifier
    )
}

internal data class ButtonScope(
    val colors: ButtonColors,
    val buttonType: CarbonButton,
    val buttonSize: ButtonSize,
)

private fun Modifier.iconButtonModifier() = size(SpacingScale.spacing09)

private fun Modifier.buttonModifier(buttonSize: ButtonSize) =
    height(buttonSize.height)
    .padding(buttonSize.getContainerPaddings())

@Composable
internal fun ButtonRowImpl(
    onClick: () -> Unit,
    buttonType: CarbonButton,
    buttonSize: ButtonSize,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    isIconButton: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.(ButtonScope) -> Unit = {},
) {
    val colors = ButtonColors.buttonColors(buttonType = buttonType)
    val containerColor = remember { Animatable(colors.containerColor) }

    LaunchedEffect(isEnabled) {
        containerColor.animateTo(
            targetValue = if (isEnabled) colors.containerColor else colors.containerDisabledColor,
            animationSpec = buttonTransitionSpec
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
                animationSpec = buttonTransitionSpec
            )
        }
    }

    Row(
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
            .then(
                if (isIconButton) {
                    Modifier.iconButtonModifier()
                } else {
                    Modifier.buttonModifier(buttonSize)
                }
            ),
    ) {
        content(ButtonScope(colors, buttonType, buttonSize))
    }
}
