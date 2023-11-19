package carbon.compose.toggle

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.motion.Motion
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text
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
 * @param label Label text to be displayed above the toggle.
 * @param actionText Action text to be displayed next to the toggle.
 * @param isEnabled Whether the toggle is enabled.
 * @param isReadOnly Whether the toggle is read only.
 * @param interactionSource The [MutableInteractionSource] to be applied to the toggle.
 */
@Composable
public fun Toggle(
    isToggled: Boolean,
    onToggleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    actionText: String = "",
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    ToggleImpl(
        isToggled = isToggled,
        onToggleChange = onToggleChange,
        interactionSource = interactionSource,
        dimensions = ToggleDimensions.Default,
        modifier = modifier,
        label = label,
        actionText = actionText,
        isEnabled = isEnabled,
        isReadOnly = isReadOnly
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
 * @param interactionSource The [MutableInteractionSource] to be applied to the toggle.
 */
@Composable
public fun SmallToggle(
    isToggled: Boolean,
    onToggleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    actionText: String = "",
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    ToggleImpl(
        isToggled = isToggled,
        onToggleChange = onToggleChange,
        interactionSource = interactionSource,
        dimensions = ToggleDimensions.Small,
        modifier = modifier,
        actionText = actionText,
        isEnabled = isEnabled,
        isReadOnly = isReadOnly
    )
}

@Composable
@Suppress("CognitiveComplexMethod", "CyclomaticComplexMethod")
private fun ToggleImpl(
    isToggled: Boolean,
    onToggleChange: (Boolean) -> Unit,
    dimensions: ToggleDimensions,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    label: String = "",
    actionText: String = "",
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
) {
    Column(modifier = modifier) {
        val theme = LocalCarbonTheme.current
        val density = LocalDensity.current

        val indication by remember { mutableStateOf(ToggleFocusIndication(dimensions)) }

        val handleSizePx = with(density) { dimensions.handleSize.toPx() }
        val toggleHeight = with(density) { dimensions.height.toPx() }
        val toggleWidth = with(density) { dimensions.width.toPx() }
        val handleCheckmarkOffset = with(density) {
            Offset(
                (handleSizePx - toggleCheckmarkIconWidth.toPx()) * .5f,
                (handleSizePx - toggleCheckmarkIconHeight.toPx()) * .5f
            )
        }
        val handleYOffPosPx = (toggleHeight - handleSizePx) * .5f
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

        val handleCheckmarkColor: Color by animateColorAsState(
            targetValue = when {
                !isToggled || isReadOnly -> Color.Transparent
                !isEnabled -> theme.buttonDisabled
                else -> theme.supportSuccess
            },
            animationSpec = TOGGLE_COLOR_ANIMATION_SPEC,
            label = "Handle checkmark color"
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

        if (label.isNotEmpty()) {
            Text(
                text = label,
                style = CarbonTypography.label01.copy(color = labelColor),
                modifier = Modifier.padding(bottom = SpacingScale.spacing04)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .toggleable(
                    value = isToggled,
                    onValueChange = { onToggleChange(!isToggled) },
                    enabled = isEnabled && !isReadOnly,
                    interactionSource = interactionSource,
                    indication = null,
                )
                // This is to restrict focus to the toggle itself. In correlation, the clickable
                // modifier above keep a better accessibility interaction on the whole component.
                .focusProperties { canFocus = false }
        ) {
            val handleCheckmarkIcon = rememberVectorPainter(image = toggleCheckmarkIcon)
                .takeIf { dimensions == ToggleDimensions.Small }

            Canvas(
                modifier = Modifier
                    .size(dimensions.width, dimensions.height)
                    .toggleable(
                        value = isToggled,
                        onValueChange = { onToggleChange(!isToggled) },
                        enabled = isEnabled && !isReadOnly,
                        interactionSource = interactionSource,
                        indication = indication,
                    )
            ) {
                drawToggleBackground(
                    backgroundColor = backgroundColor,
                    toggleHeight = toggleHeight,
                    borderColor = borderColor,
                )

                drawToggleHandle(
                    handleXPos = handleXPos,
                    handleYPos = handleYOffPosPx,
                    handleColor = handleColor,
                    handleSizePx = handleSizePx,
                    handleCheckmarkOffset = handleCheckmarkOffset,
                    handleCheckmarkIcon = handleCheckmarkIcon,
                    handleCheckmarkColor = handleCheckmarkColor
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

private fun DrawScope.drawToggleBackground(
    backgroundColor: Color,
    toggleHeight: Float,
    borderColor: Color,
) {
    // Background
    drawRoundRect(
        color = backgroundColor,
        cornerRadius = CornerRadius(toggleHeight),
    )

    //Border
    val strokeWidth = 1f.dp.toPx()
    val halfStroke = strokeWidth * .5f
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
}

private fun DrawScope.drawToggleHandle(
    handleXPos: Float,
    handleYPos: Float,
    handleColor: Color,
    handleSizePx: Float,
    handleCheckmarkOffset: Offset,
    handleCheckmarkIcon: VectorPainter?,
    handleCheckmarkColor: Color
) {
    translate(
        left = handleXPos,
        top = handleYPos
    ) {
        drawRoundRect(
            color = handleColor,
            size = Size(handleSizePx, handleSizePx),
            cornerRadius = CornerRadius(handleSizePx),
        )
        translate(
            left = handleCheckmarkOffset.x,
            top = handleCheckmarkOffset.y
        ) {
            handleCheckmarkIcon?.run {
                draw(
                    size = handleCheckmarkIcon.intrinsicSize,
                    colorFilter = ColorFilter.tint(handleCheckmarkColor)
                )
            }
        }
    }
}
