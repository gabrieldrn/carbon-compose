package carbon.compose.button

import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import carbon.compose.button.ButtonSize.Companion.getContainerPaddings
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography

private fun Modifier.iconButtonModifier() = requiredSize(SpacingScale.spacing09)

private fun Modifier.buttonModifier(buttonSize: ButtonSize) =
    requiredHeight(buttonSize.height)
        .padding(buttonSize.getContainerPaddings())

internal data class ButtonScope(
    val colors: ButtonColors,
    val buttonType: CarbonButton,
    val buttonSize: ButtonSize,
)

@Composable
internal fun ButtonRowImpl(
    onClick: () -> Unit,
    buttonType: CarbonButton,
    buttonSize: ButtonSize,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    isIconButton: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.(ButtonScope) -> Unit,
) {
    val colors = ButtonColors.buttonColors(buttonType = buttonType)
    val containerColor = remember { Animatable(colors.containerColor) }
    val indiction = remember { ButtonFocusIndication(buttonType) }

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
            .width(IntrinsicSize.Max)
            .drawBehind {
                drawRect(color = containerColor.value)
            }
            .clickable(
                interactionSource = interactionSource,
                indication = indiction,
                onClick = onClick,
                enabled = isEnabled,
                role = Role.Button
            )
            .then(
                InspectableModifier(
                    debugInspectorInfo {
                        properties["buttonType"] = buttonType.name
                        properties["buttonSize"] = buttonSize.name
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
