package carbon.compose.toggle

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal sealed class ToggleDimensions(
    val width: Dp,
    val height: Dp,
    val handleSize: Dp,
) {
    data object Default : ToggleDimensions(
        width = 48.dp,
        height = 24.dp,
        handleSize = 18.dp,
    )

    data object Small : ToggleDimensions(
        width = 32.dp,
        height = 16.dp,
        handleSize = 10.dp,
    )
}
