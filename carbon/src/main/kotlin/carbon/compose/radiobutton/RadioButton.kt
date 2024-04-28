package carbon.compose.radiobutton

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

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
 */
@Composable
public fun RadioButton(
    checked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    radioButtonInteractiveState: RadioButtonInteractiveState = RadioButtonInteractiveState.Default,
    errorMessage: String = "",
    warningMessage: String = "",
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    RadioButtonComponent(
        checked = checked,
        modifier = modifier
    )
}

private val RadioButtonSize = 20.dp
private val RadioButtonStrokeWidth = 1.dp

@Composable
private fun RadioButtonComponent(
    checked: Boolean,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.requiredSize(RadioButtonSize)) {
        drawCircle(
            color = Color.Black,
            radius = (size.width - RadioButtonStrokeWidth.toPx()) * .5f,
            style = Stroke(width = RadioButtonStrokeWidth.toPx())
        )
        if (checked) {
            drawCircle(
                color = Color.Black,
                radius = size.width * .25f,
            )
        }
    }
}
