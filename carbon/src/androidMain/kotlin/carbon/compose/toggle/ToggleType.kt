package carbon.compose.toggle

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
internal sealed class ToggleType(
    val width: Dp,
    val height: Dp,
    val handleSize: Dp,
) {
    data object Default : ToggleType(
        width = 48.dp,
        height = 24.dp,
        handleSize = 18.dp,
    )

    data object Small : ToggleType(
        width = 32.dp,
        height = 16.dp,
        handleSize = 10.dp,
    )
}
