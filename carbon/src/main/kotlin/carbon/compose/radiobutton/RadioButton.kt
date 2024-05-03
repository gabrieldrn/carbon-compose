package carbon.compose.radiobutton

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.interaction.ToggleableFocusIndication
import carbon.compose.foundation.selectable.ErrorContent
import carbon.compose.foundation.selectable.SelectableInteractiveState
import carbon.compose.foundation.selectable.WarningContent
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text
import carbon.compose.semantics.readOnly

private val RadioButtonSize = 20.dp
private val RadioButtonStrokeWidth = 1.dp

/**
 * # Carbon Radio button
 * Radio buttons are used for mutually exclusive choices, not for multiple choices. Only one radio
 * button can be selected at a time. When a user chooses a new item, the previous choice is
 * automatically deselected.
 *
 * ## Anatomy
 * The radio button component is comprised of a set of clickable circles (the inputs) with text
 * labels positioned to the right. If there is a group of radio buttons, a group label can be added.
 *
 * (From [Radio button documentation](https://carbondesignsystem.com/components/radio-button/usage))
 *
 * @param selected Whether the radio button is selected.
 * @param label The text to be displayed next to the radio button.
 * @param onClick Callback invoked when the radio button is clicked.
 * @param modifier The modifier to be applied to the radio button.
 * @param interactiveState The [SelectableInteractiveState] of the radio button.
 * @param errorMessage The error message to be displayed below the radio button, it will be
 * displayed only if the [interactiveState] is [SelectableInteractiveState.Error].
 * @param warningMessage The warning message to be displayed below the radio button, it will be
 * displayed only if the [interactiveState] is [SelectableInteractiveState.Warning].
 * @param interactionSource The [MutableInteractionSource] that keeps track of the radio button
 * state.
 */
@Composable
public fun RadioButton(
    selected: Boolean,
    label: String,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    interactiveState: SelectableInteractiveState = SelectableInteractiveState.Default,
    errorMessage: String = "",
    warningMessage: String = "",
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val colors = RadioButtonColors.colors()

    val radioButtonModifier = when {
        interactiveState == SelectableInteractiveState.ReadOnly -> Modifier.readOnly(
            role = Role.RadioButton,
            interactionSource = interactionSource,
            selected = selected,
            mergeDescendants = true
        )
        onClick != null -> Modifier.selectable(
            selected = selected,
            interactionSource = interactionSource,
            enabled = interactiveState != SelectableInteractiveState.Disabled,
            onClick = onClick,
            indication = null,
            role = Role.RadioButton
        )
        else -> Modifier
    }

    Column(modifier = modifier.then(radioButtonModifier)) {
        Row {
            RadioButtonComponent(
                colors = colors,
                interactiveState = interactiveState,
                selected = selected,
                modifier = Modifier.indication(
                    interactionSource = interactionSource,
                    indication = ToggleableFocusIndication(RadioButtonSize)
                )
            )
            Text(
                text = label,
                color = colors.labelColor(interactiveState = interactiveState),
                modifier = Modifier.padding(start = SpacingScale.spacing03),
                style = CarbonTypography.bodyCompact01
            )
        }
        if (interactiveState == SelectableInteractiveState.Error) {
            ErrorContent(
                colors = colors,
                errorMessage = errorMessage,
                modifier = Modifier.padding(top = SpacingScale.spacing03)
            )
        }
        if (interactiveState == SelectableInteractiveState.Warning) {
            WarningContent(
                colors = colors,
                warningMessage = warningMessage,
                modifier = Modifier.padding(top = SpacingScale.spacing03)
            )
        }
    }
}

@Composable
private fun RadioButtonComponent(
    colors: RadioButtonColors,
    interactiveState: SelectableInteractiveState,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.requiredSize(RadioButtonSize)) {
        drawCircle(
            color = colors.borderColor(interactiveState, selected),
            radius = (size.width - RadioButtonStrokeWidth.toPx()) * .5f,
            style = Stroke(width = RadioButtonStrokeWidth.toPx())
        )
        drawCircle(
            color = colors.dotColor(interactiveState, selected),
            radius = size.width * .25f,
        )
    }
}
