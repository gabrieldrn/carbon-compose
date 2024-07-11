package carbon.compose.button

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
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
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import carbon.compose.button.ButtonSize.Companion.getContainerPaddings
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.motion.Motion
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography

internal val buttonTransitionSpec: AnimationSpec<Color> = tween(
    durationMillis = Motion.Duration.fast01,
    easing = Motion.Entrance.productiveEasing
)

private fun Modifier.iconButtonModifier() = this.requiredSize(SpacingScale.spacing09)

private fun Modifier.buttonModifier(buttonSize: ButtonSize) =
    this.requiredHeight(buttonSize.height)
        .padding(buttonSize.getContainerPaddings())

internal data class ButtonScope(
    val colors: ButtonColors,
    val buttonType: ButtonType,
    val buttonSize: ButtonSize,
)

@Composable
internal fun ButtonRowImpl(
    onClick: () -> Unit,
    buttonType: ButtonType,
    buttonSize: ButtonSize,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    isIconButton: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.(ButtonScope) -> Unit,
) {
    val colors = ButtonColors.colors(buttonType)
    val containerColor = remember(colors) { Animatable(colors.containerColor) }
    val indication = remember(buttonType) { ButtonFocusIndication(buttonType) }

    LaunchedEffect(isEnabled, colors) {
        containerColor.animateTo(
            targetValue = if (isEnabled) colors.containerColor else colors.containerDisabledColor,
            animationSpec = snap()
        )
    }

    LaunchedEffect(interactionSource, containerColor, colors) {
        interactionSource.interactions.collect {
            if (!isEnabled) return@collect
            containerColor.stop()
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
            .width(IntrinsicSize.Max)
            .drawBehind {
                drawRect(color = containerColor.value)
            }
            .border(
                width = 1.dp,
                color = if (isEnabled) {
                    colors.containerBorderColor
                } else {
                    colors.containerBorderDisabledColor
                },
            )
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = onClick,
                enabled = isEnabled,
                role = Role.Button
            )
            .then(
                InspectableModifier(
                    debugInspectorInfo {
                        properties["buttonType"] = buttonType.name
                        properties["buttonSize"] = buttonSize::class.java.name
                        properties["isIconButton"] = isIconButton.toString()
                    }
                )
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

@Composable
internal fun Label(
    label: String,
    colors: ButtonColors,
    isEnabled: Boolean,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    val theme = LocalCarbonTheme.current

    val animatedLabelTextColor = remember(colors) { Animatable(colors.labelColor) }

    LaunchedEffect(isEnabled, colors) {
        animatedLabelTextColor.animateTo(
            targetValue = if (isEnabled) colors.labelColor else colors.labelDisabledColor,
            animationSpec = snap()
        )
    }

    LaunchedEffect(theme, animatedLabelTextColor, colors) {
        interactionSource
            .interactions
            .collect { interaction ->
                if (!isEnabled) return@collect
                animatedLabelTextColor.stop()
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
        modifier = modifier,
        style = CarbonTypography.bodyCompact01,
        color = { animatedLabelTextColor.value },
        maxLines = 1,
        softWrap = false,
    )
}

@Composable
// FIXME Using Painter + colorFilter: animated color presents a lot of recompositions when animated.
//  Try to optimize.
internal fun ButtonIcon(
    painter: Painter,
    colors: ButtonColors,
    isEnabled: Boolean,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    val theme = LocalCarbonTheme.current

    val animatedIconColor = remember(colors) { Animatable(colors.iconColor) }

    LaunchedEffect(isEnabled, colors) {
        animatedIconColor.animateTo(
            targetValue = if (isEnabled) colors.iconColor else colors.iconDisabledColor,
            animationSpec = snap()
        )
    }

    LaunchedEffect(theme, animatedIconColor, colors) {
        interactionSource
            .interactions
            .collect { interaction ->
                if (!isEnabled) return@collect
                animatedIconColor.stop()
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
