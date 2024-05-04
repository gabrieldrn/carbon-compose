package carbon.compose.checkbox

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.interaction.ToggleableFocusIndication
import carbon.compose.foundation.selectable.ErrorContent
import carbon.compose.foundation.selectable.SelectableInteractiveState
import carbon.compose.foundation.selectable.WarningContent
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text
import carbon.compose.semantics.readOnly

private val checkboxBorderWidth = 1.dp
private val checkboxCornerRadius = 2.dp

/**
 * # Carbon Checkbox
 * Checkboxes are used when there are multiple items to select in a list. Users can select zero,
 * one, or any number of items.
 *
 * ## Content
 * - The checkbox itself is a square box with a checkmark or an indeterminate mark.
 * - The label describes the information the user wants to select or unselect.
 * - The error or warning message are displayed below the checkbox and help the user understand
 * about a certain state regarding the checkbox context.
 *
 * (From [Checkbox documentation](https://carbondesignsystem.com/components/checkbox/usage/))
 *
 * @param checked Whether the checkbox is checked.
 * @param label The text to be displayed next to the checkbox.
 * @param onClick Callback invoked when the checkbox is clicked.
 * @param modifier The modifier to be applied to the checkbox.
 * @param interactiveState The [SelectableInteractiveState] of the checkbox.
 * @param interactionSource The [MutableInteractionSource] that keeps track of the checkbox state.
 */
@Composable
public fun Checkbox(
    checked: Boolean,
    label: String,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    interactiveState: SelectableInteractiveState = SelectableInteractiveState.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Checkbox(
        state = remember(checked) { ToggleableState(checked) },
        label = label,
        onClick = onClick,
        modifier = modifier,
        interactiveState = interactiveState,
        interactionSource = interactionSource
    )
}

/**
 * # Carbon Checkbox
 * Checkboxes are used when there are multiple items to select in a list. Users can select zero,
 * one, or any number of items.
 *
 * ## Content
 * - The checkbox itself is a square box with a checkmark or an indeterminate mark.
 * - The label describes the information the user wants to select or unselect.
 * - The error or warning message are displayed below the checkbox and help the user understand
 * about a certain state regarding the checkbox context.
 *
 * ## Interactions
 * The component applies a tri-state toggleable interaction to the checkbox root composable if the
 * [onClick] callback is provided, meaning that the whole component is clickable in order to create
 * a more accessible click target. Otherwise, the checkbox won't be interactable.
 *
 * (From [Checkbox documentation](https://carbondesignsystem.com/components/checkbox/usage/))
 *
 * @param state The [ToggleableState] of the checkbox.
 * @param label The text to be displayed next to the checkbox.
 * @param onClick Callback invoked when the checkbox is clicked.
 * @param modifier The modifier to be applied to the checkbox.
 * @param interactiveState The [SelectableInteractiveState] of the checkbox.
 * @param interactionSource The [MutableInteractionSource] that keeps track of the checkbox state.
 */
@Composable
public fun Checkbox(
    state: ToggleableState,
    label: String,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    interactiveState: SelectableInteractiveState = SelectableInteractiveState.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val colors = CheckboxColors.colors()

    val checkboxModifier = when {
        interactiveState == SelectableInteractiveState.ReadOnly -> Modifier.readOnly(
            role = Role.Checkbox,
            interactionSource = interactionSource,
            state = state,
            mergeDescendants = true
        )
        onClick != null -> Modifier.triStateToggleable(
            state = state,
            interactionSource = interactionSource,
            enabled = interactiveState != SelectableInteractiveState.Disabled,
            onClick = onClick,
            indication = null,
            role = Role.Checkbox
        )
        else -> Modifier
    }

    Column(modifier = modifier.then(checkboxModifier)) {
        Row {
            CheckboxComponent(
                colors = colors,
                interactiveState = interactiveState,
                state = state,
                modifier = Modifier.indication(
                    interactionSource = interactionSource,
                    indication = ToggleableFocusIndication(4.dp)
                )
            )
            Text(
                text = label,
                color = colors.labelColor(interactiveState = interactiveState),
                modifier = Modifier.padding(start = SpacingScale.spacing03),
                style = CarbonTypography.bodyCompact01
            )
        }
        if (interactiveState is SelectableInteractiveState.Error) {
            ErrorContent(
                colors = colors,
                errorMessage = interactiveState.errorMessage,
                modifier = Modifier.padding(top = SpacingScale.spacing03)
            )
        }
        if (interactiveState is SelectableInteractiveState.Warning) {
            WarningContent(
                colors = colors,
                warningMessage = interactiveState.warningMessage,
                modifier = Modifier.padding(top = SpacingScale.spacing03)
            )
        }
    }
}

@Composable
private fun CheckboxComponent(
    colors: CheckboxColors,
    interactiveState: SelectableInteractiveState,
    state: ToggleableState,
    modifier: Modifier = Modifier
) {
    val checkmarkIcon = rememberVectorPainter(image = checkboxCheckmarkIcon)
    val indeterminateIcon = rememberVectorPainter(image = checkboxIndeterminateIcon)
    val icon = when (state) {
        ToggleableState.On -> checkmarkIcon
        ToggleableState.Indeterminate -> indeterminateIcon
        ToggleableState.Off -> null
    }

    Canvas(
        modifier = Modifier
            .padding(2.dp)
            .requiredSize(16.dp)
            .then(modifier)
    ) {
        val borderWidth = checkboxBorderWidth.toPx()

        val borderColor = colors.borderColor(
            interactiveState = interactiveState,
            state = state
        )
        val drawBorder = borderColor != Color.Transparent

        // Background
        // Antialiasing issue: this inset is to reduce the background size when drawing
        // border to avoid antialiasing artifacts outside the border
        inset(if (drawBorder) borderWidth * .5f else 0f) {
            drawRoundRect(
                color = colors.backgroundColor(
                    interactiveState = interactiveState,
                    state = state
                ),
                cornerRadius = CornerRadius(checkboxCornerRadius.toPx()),
            )
        }
        // Border
        if (drawBorder) {
            inset(borderWidth * .5f) {
                drawRoundRect(
                    color = borderColor,
                    cornerRadius = CornerRadius(2f.dp.toPx()),
                    style = Stroke(borderWidth)
                )
            }
        }
        // Checkmark
        icon?.run {
            draw(
                size = intrinsicSize * .5f,
                colorFilter = ColorFilter.tint(
                    colors.checkmarkColor(
                        interactiveState = interactiveState,
                        state = state
                    )
                )
            )
        }
    }
}
