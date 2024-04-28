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
