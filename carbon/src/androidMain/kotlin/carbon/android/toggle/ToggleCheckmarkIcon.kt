package carbon.android.toggle

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val toggleCheckmarkIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ToggleCheckmarkIcon",
        defaultWidth = toggleCheckmarkIconWidth,
        defaultHeight = toggleCheckmarkIconHeight,
        viewportWidth = toggleCheckmarkIconWidth.value,
        viewportHeight = toggleCheckmarkIconHeight.value
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000))
        ) {
            moveTo(2.2f, 2.7f)
            lineTo(5f, 0f)
            lineTo(6f, 1f)
            lineTo(2.2f, 5f)
            lineTo(0f, 2.7f)
            lineTo(1f, 1.5f)
            close()
        }
    }.build()

internal val toggleCheckmarkIconWidth = 6f.dp
internal val toggleCheckmarkIconHeight = 5f.dp
