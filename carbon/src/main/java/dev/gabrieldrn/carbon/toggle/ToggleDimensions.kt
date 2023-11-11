package dev.gabrieldrn.carbon.toggle

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal enum class ToggleDimensions(
    val width: Dp,
    val height: Dp,
    val handleSize: Dp,
) {
    Default(
        width = 48.dp,
        height = 24.dp,
        handleSize = 18.dp,
    ),
    Small(
        width = 32.dp,
        height = 16.dp,
        handleSize = 10.dp,
    )
}
