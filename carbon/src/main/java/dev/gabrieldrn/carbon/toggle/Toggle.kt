package dev.gabrieldrn.carbon.toggle

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.gabrieldrn.carbon.CarbonDesignSystem
import dev.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.foundation.motion.Motion
import dev.gabrieldrn.carbon.foundation.spacing.SpacingScale
import dev.gabrieldrn.carbon.foundation.text.CarbonTypography
import dev.gabrieldrn.carbon.foundation.text.Text
import kotlin.math.max

private val TOGGLE_COLOR_ANIMATION_SPEC = tween<Color>(
    durationMillis = Motion.Duration.fast01,
    easing = Motion.Entrance.productiveEasing
)
private val TOGGLE_FLOAT_ANIMATION_SPEC = tween<Float>(
    durationMillis = Motion.Duration.fast01,
    easing = Motion.Entrance.productiveEasing
)

/**
 * A toggle is used to quickly switch between two possible states. They are commonly used for
 * “on/off” switches.
 *
 * Use the default toggle when you need to specify a label text in addition to the toggle action
 * text. Default toggles appear in forms or within full pages of information.
 *
 * (From [Toggle documentation](https://carbondesignsystem.com/components/toggle/usage))
 *
 * @param isToggled Whether the toggle is toggled on or off.
 * @param onToggleChange Callback for when the toggle is toggled.
 * @param modifier Modifier to be applied to the toggle.
 * @param labelText Label text to be displayed above the toggle.
 * @param actionText Action text to be displayed next to the toggle.
 * @param isEnabled Whether the toggle is enabled.
 * @param isReadOnly Whether the toggle is read only.
 */
@Composable
public fun Toggle(
    isToggled: Boolean,
    onToggleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String = "",
    actionText: String = "",
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
) {
    ToggleImpl(
        isToggled = isToggled,
        onToggleChange = onToggleChange,
        dimensions = ToggleComponentDimensions.Default,
        modifier = modifier,
        labelText = labelText,
        actionText = actionText,
        isEnabled = isEnabled,
        isReadOnly = isReadOnly,
    )
}

/**
 * A toggle is used to quickly switch between two possible states. They are commonly used for
 * “on/off” switches.
 *
 * Use the small toggle when you do not need to specify label or action text. Small toggles are
 * more compact in size and are used inline with other components.
 *
 * (From [Toggle documentation](https://carbondesignsystem.com/components/toggle/usage))
 *
 * @param isToggled Whether the toggle is toggled on or off.
 * @param onToggleChange Callback for when the toggle is toggled.
 * @param modifier Modifier to be applied to the toggle.
 * @param actionText Action text to be displayed next to the toggle.
 * @param isEnabled Whether the toggle is enabled.
 * @param isReadOnly Whether the toggle is read only.
 */
@Composable
public fun SmallToggle(
    isToggled: Boolean,
    onToggleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    actionText: String = "",
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
) {
    ToggleImpl(
        isToggled = isToggled,
        onToggleChange = onToggleChange,
        dimensions = ToggleComponentDimensions.Small,
        modifier = modifier,
        actionText = actionText,
        isEnabled = isEnabled,
        isReadOnly = isReadOnly,
    )
}

@Composable
@Suppress("CognitiveComplexMethod", "CyclomaticComplexMethod")
private fun ToggleImpl(
    isToggled: Boolean,
    onToggleChange: (Boolean) -> Unit,
    dimensions: ToggleComponentDimensions,
    modifier: Modifier = Modifier,
    labelText: String = "",
    actionText: String = "",
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
) {
    val theme = LocalCarbonTheme.current
    val density = LocalDensity.current

    val handleSizePx = with(density) { dimensions.handleSize.toPx() }
    val toggleHeight = with(density) { dimensions.height.toPx() }
    val toggleWidth = with(density) { dimensions.width.toPx() }
    val handleYOffPosPx = (toggleHeight - handleSizePx) / 2
    val handleXOnPosPx = toggleWidth - handleSizePx - handleYOffPosPx

    val backgroundColor: Color by animateColorAsState(
        targetValue = when {
            !isEnabled -> theme.buttonDisabled
            isReadOnly -> Color.Transparent
            isToggled -> theme.supportSuccess
            else -> theme.toggleOff
        },
        animationSpec = TOGGLE_COLOR_ANIMATION_SPEC,
        label = "Toggle background color"
    )

    val borderColor: Color by animateColorAsState(
        // TODO Impl contextual border color based on layer
        targetValue = if (isReadOnly && isEnabled) theme.borderSubtle01 else Color.Transparent,
        animationSpec = TOGGLE_COLOR_ANIMATION_SPEC,
        label = "Toggle border color"
    )

    val handleColor: Color by animateColorAsState(
        targetValue = when {
            !isEnabled -> theme.iconOnColorDisabled
            isReadOnly -> theme.iconPrimary
            else -> theme.iconOnColor
        },
        animationSpec = TOGGLE_COLOR_ANIMATION_SPEC,
        label = "Handle color"
    )

    val handleXPos: Float by animateFloatAsState(
        targetValue = if (isToggled) handleXOnPosPx else handleYOffPosPx,
        animationSpec = TOGGLE_FLOAT_ANIMATION_SPEC,
        label = "Toggle handle position"
    )

    val labelColor: Color by animateColorAsState(
        targetValue = if (isEnabled) theme.textPrimary else theme.textDisabled,
        animationSpec = TOGGLE_COLOR_ANIMATION_SPEC,
        label = "Label color"
    )

    val actionTextColor: Color by animateColorAsState(
        targetValue = if (isEnabled) theme.textPrimary else theme.textDisabled,
        animationSpec = TOGGLE_COLOR_ANIMATION_SPEC,
        label = "Action text color"
    )

    Column(modifier = modifier) {
        if (labelText.isNotEmpty()) {
            Text(
                text = "Label",
                style = CarbonTypography.label01.copy(color = labelColor),
                modifier = Modifier.padding(bottom = SpacingScale.spacing04)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(
                interactionSource = MutableInteractionSource(),
                enabled = isEnabled && !isReadOnly,
                indication = null,
                onClick = { onToggleChange(!isToggled) },
            )
        ) {
            Canvas(
                modifier = Modifier.size(
                    dimensions.width,
                    dimensions.height
                )
            ) {
                toggleDrawScope(
                    backgroundColor,
                    toggleHeight,
                    borderColor,
                    handleColor,
                    handleSizePx,
                    handleXPos,
                    handleYOffPosPx
                )
            }
            if (actionText.isNotEmpty()) {
                Text(
                    text = actionText,
                    style = CarbonTypography.bodyCompact01.copy(color = actionTextColor),
                    modifier = Modifier.padding(start = SpacingScale.spacing03)
                )
            }
        }
    }
}

private fun DrawScope.toggleDrawScope(
    backgroundColor: Color,
    toggleHeight: Float,
    borderColor: Color,
    handleColor: Color,
    handleSizePx: Float,
    handleXPos: Float,
    handleYOffPosPx: Float
) {
    // Background
    drawRoundRect(
        color = backgroundColor,
        cornerRadius = CornerRadius(toggleHeight),
    )

    //Border
    val strokeWidth = 1f.dp.toPx()
    val halfStroke = strokeWidth / 2
    inset(halfStroke) {
        drawRoundRect(
            color = borderColor,
            cornerRadius = CornerRadius(toggleHeight)
                .let {
                    // Shrink
                    CornerRadius(
                        max(0f, it.x - halfStroke),
                        max(0f, it.y - halfStroke)
                    )
                },
            style = Stroke(strokeWidth)
        )
    }

    // Handle
    drawRoundRect(
        color = handleColor,
        size = Size(handleSizePx, handleSizePx),
        cornerRadius = CornerRadius(toggleHeight),
        topLeft = Offset(handleXPos, handleYOffPosPx),
    )
}

private sealed class ToggleComponentDimensions(
    val width: Dp,
    val height: Dp,
    val handleSize: Dp,
) {
    data object Default : ToggleComponentDimensions(
        width = 48.dp,
        height = 24.dp,
        handleSize = 18.dp,
    )

    data object Small : ToggleComponentDimensions(
        width = 32.dp,
        height = 16.dp,
        handleSize = 10.dp,
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultTogglePreview() {
    CarbonDesignSystem {
        var isToggled by remember { mutableStateOf(false) }
        Toggle(
            isToggled = isToggled,
            onToggleChange = { isToggled = it },
            labelText = "Label",
            actionText = if (isToggled) "On" else "Off",
//            isEnabled = false,
//            isReadOnly = true,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SmallTogglePreview() {
    CarbonDesignSystem {
        var isToggled by remember { mutableStateOf(false) }
        SmallToggle(
            isToggled = isToggled,
            onToggleChange = { isToggled = it },
            actionText = if (isToggled) "On" else "Off",
//            isEnabled = false,
//            isReadOnly = true,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}
