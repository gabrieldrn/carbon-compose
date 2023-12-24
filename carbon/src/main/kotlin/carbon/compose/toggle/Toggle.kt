package carbon.compose.toggle

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.interaction.ToggleableFocusIndication
import carbon.compose.foundation.motion.Motion
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text
import carbon.compose.semantics.readOnly
import carbon.compose.toggle.domain.ToggleDrawValues
import carbon.compose.toggle.domain.ToggleState
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
 * # Carbon Toggle
 * A toggle is used to quickly switch between two possible states. They are commonly used for
 * “on/off” switches.
 *
 * ## Content
 * - The toggle itself with a handle that indicates the current state.
 * - The label accompanying the toggle to further clarify the action that the toggle performs.
 * - The action text describing the binary action of toggle so that the action is clear.
 *
 * ## Interactions
 * The component applies a toggleable interaction to the toggle root composable if the
 * [onToggleChange] callback is provided, meaning that the whole component is clickable in order to
 * create a more accessible click target. Otherwise, the toggle won't be interactable.
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
    onToggleChange: ((Boolean) -> Unit)?,
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
        toggleType = ToggleType.Default,
        modifier = modifier,
        label = label,
        actionText = actionText,
        isEnabled = isEnabled,
        isReadOnly = isReadOnly
    )
}

/**
 * # Carbon Toggle
 * A toggle is used to quickly switch between two possible states. They are commonly used for
 * “on/off” switches.
 *
 * ## Content
 * - The toggle itself with a handle that indicates the current state. The small variant have a
 * checkmark icon on the handle when toggled on.
 * - The label accompanying the toggle to further clarify the action that the toggle performs.
 * - The action text describing the binary action of toggle so that the action is clear.
 *
 * ## Interactions
 * The component applies a toggleable interaction to the toggle root composable if the
 * [onToggleChange] callback is provided, meaning that the whole component is clickable in order to
 * create a more accessible click target. Otherwise, the toggle won't be interactable.
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
    onToggleChange: ((Boolean) -> Unit)?,
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
        toggleType = ToggleType.Small,
        modifier = modifier,
        actionText = actionText,
        isEnabled = isEnabled,
        isReadOnly = isReadOnly
    )
}

@Composable
private fun ToggleImpl(
    isToggled: Boolean,
    onToggleChange: ((Boolean) -> Unit)?,
    toggleType: ToggleType,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    label: String = "",
    actionText: String = "",
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
) {
    val theme = LocalCarbonTheme.current
    val density = LocalDensity.current

    val toggleState by remember(isToggled, isEnabled, isReadOnly) {
        mutableStateOf(ToggleState(isToggled, isEnabled, isReadOnly))
    }

    val indication by remember {
        mutableStateOf(ToggleableFocusIndication(toggleType.height))
    }

    val toggleDrawValues by remember(toggleType) {
        mutableStateOf(ToggleDrawValues.buildValues(toggleType, density))
    }

    val toggleColors by remember(theme) {
        mutableStateOf(ToggleColors.fromTheme(theme))
    }

    val toggleModifier = when {
        isReadOnly -> Modifier.readOnly(
            role = Role.Switch,
            state = ToggleableState(isToggled),
            interactionSource = interactionSource,
            mergeDescendants = true
        )
        onToggleChange != null -> Modifier.toggleable(
            value = isToggled,
            onValueChange = { onToggleChange(!isToggled) },
            enabled = isEnabled,
            interactionSource = interactionSource,
            indication = null,
            role = Role.Switch
        )
        else -> Modifier
    }

    Column(
        modifier = Modifier
            .inspectable(
                debugInspectorInfo {
                    properties["toggleType"] = toggleType::class.java.simpleName
                }
            ) { modifier.then(toggleModifier) }
    ) {
        val backgroundColor: Color by animateColorAsState(
            targetValue = toggleColors.backgroundColor(toggleState),
            animationSpec = TOGGLE_COLOR_ANIMATION_SPEC,
            label = "Toggle background color"
        )

        val borderColor: Color by animateColorAsState(
            // TODO Impl contextual border color based on layer
            targetValue = toggleColors.borderColor(toggleState),
            animationSpec = TOGGLE_COLOR_ANIMATION_SPEC,
            label = "Toggle border color"
        )

        val handleColor: Color by animateColorAsState(
            targetValue = toggleColors.handleColor(toggleState),
            animationSpec = TOGGLE_COLOR_ANIMATION_SPEC,
            label = "Handle color"
        )

        val handleCheckmarkColor: Color by animateColorAsState(
            targetValue = toggleColors.handleCheckmarkColor(toggleState),
            animationSpec = TOGGLE_COLOR_ANIMATION_SPEC,
            label = "Handle checkmark color"
        )

        val handleXPos: Float by animateFloatAsState(
            targetValue = toggleDrawValues.handleXPos(toggleState),
            animationSpec = TOGGLE_FLOAT_ANIMATION_SPEC,
            label = "Toggle handle position"
        )

        val textColor by animateColorAsState(
            targetValue = toggleColors.textColor(toggleState),
            animationSpec = TOGGLE_COLOR_ANIMATION_SPEC,
            label = "Toggle text color"
        )

        if (label.isNotEmpty()) {
            Text(
                text = label,
                style = CarbonTypography.label01.copy(color = textColor),
                modifier = Modifier.padding(bottom = SpacingScale.spacing04)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            val handleCheckmarkIcon = rememberVectorPainter(image = toggleCheckmarkIcon)
                .takeIf { toggleType == ToggleType.Small }

            Canvas(
                modifier = Modifier
                    .requiredSize(toggleType.width, toggleType.height)
                    .indication(
                        interactionSource = interactionSource,
                        indication = indication
                    )
            ) {
                drawToggleBackground(
                    backgroundColor = backgroundColor,
                    toggleHeight = toggleDrawValues.toggleHeight,
                    borderColor = borderColor,
                )

                drawToggleHandle(
                    handleXPos = handleXPos,
                    handleYPos = toggleDrawValues.handleYOffPos,
                    handleColor = handleColor,
                    handleSizePx = toggleDrawValues.handleSize,
                    handleCheckmarkOffset = toggleDrawValues.handleCheckmarkOffset,
                    handleCheckmarkIcon = handleCheckmarkIcon,
                    handleCheckmarkColor = handleCheckmarkColor
                )
            }
            if (actionText.isNotEmpty()) {
                Text(
                    text = actionText,
                    style = CarbonTypography.bodyCompact01.copy(color = textColor),
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
                    size = intrinsicSize,
                    colorFilter = ColorFilter.tint(handleCheckmarkColor)
                )
            }
        }
    }
}
