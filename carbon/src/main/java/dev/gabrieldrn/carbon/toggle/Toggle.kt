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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gabrieldrn.carbon.CarbonDesignSystem
import dev.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.foundation.motion.Motion
import dev.gabrieldrn.carbon.foundation.spacing.SpacingScale
import dev.gabrieldrn.carbon.foundation.text.CarbonTypography
import dev.gabrieldrn.carbon.foundation.text.Text
import kotlin.math.max

private val TOGGLE_BACKGROUND_DEFAULT_WIDTH = 48.dp
private val TOGGLE_BACKGROUND_DEFAULT_HEIGHT = 24.dp
private val TOGGLE_HANDLE_DEFAULT_SIZE = 18.dp
private val TOGGLE_COLOR_ANIMATION_SPEC = tween<Color>(
    durationMillis = Motion.Duration.fast01,
    easing = Motion.Entrance.productiveEasing
)
private val TOGGLE_FLOAT_ANIMATION_SPEC = tween<Float>(
    durationMillis = Motion.Duration.fast01,
    easing = Motion.Entrance.productiveEasing
)

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
    val theme = LocalCarbonTheme.current
    val density = LocalDensity.current

    // Define the sizes with LocalDensity as they are needed outside of Canvas
    val handleSizePx = with(density) {
        TOGGLE_HANDLE_DEFAULT_SIZE.toPx()
    }
    val handleYOffPosPx = with(density) {
        (TOGGLE_BACKGROUND_DEFAULT_HEIGHT.toPx() - handleSizePx) / 2
    }
    val handleXOnPosPx = with(density) {
        TOGGLE_BACKGROUND_DEFAULT_WIDTH.toPx() - handleSizePx - handleYOffPosPx
    }

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
            modifier = Modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { onToggleChange(!isToggled) },
                )
        ) {
            Canvas(
                modifier = Modifier.size(
                    TOGGLE_BACKGROUND_DEFAULT_WIDTH,
                    TOGGLE_BACKGROUND_DEFAULT_HEIGHT
                )
            ) {
                // Background
                drawRoundRect(
                    color = backgroundColor,
                    cornerRadius = CornerRadius(TOGGLE_BACKGROUND_DEFAULT_HEIGHT.toPx()),
                )

                //Border
                val strokeWidth = 1f.dp.toPx()
                val halfStroke = strokeWidth / 2
                inset(halfStroke) {
                    drawRoundRect(
                        color = borderColor,
                        cornerRadius = CornerRadius(TOGGLE_BACKGROUND_DEFAULT_HEIGHT.toPx())
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
                    cornerRadius = CornerRadius(TOGGLE_BACKGROUND_DEFAULT_HEIGHT.toPx() / 2),
                    topLeft = Offset(handleXPos, handleYOffPosPx),
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

@Preview(showBackground = true)
@Composable
private fun TogglePreview() {
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
