package carbon.compose.checkbox

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val checkboxCheckmarkIcon: ImageVector
    get() = ImageVector.Builder(
        name = "CheckboxCheckmarkIcon",
        defaultWidth = checkboxCheckmarkIconWidth,
        defaultHeight = checkboxCheckmarkIconHeight,
        viewportWidth = checkboxCheckmarkIconWidth.value,
        viewportHeight = checkboxCheckmarkIconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(13.3f, 23.3f)
            lineTo(6.6f, 16.7f)
            lineTo(8.7f, 14.6f)
            lineTo(13.3f, 19.2f)
            lineTo(23.2f, 9.3f)
            lineTo(25.3f, 11.4f)
            close()
        }
    }.build()

internal val checkboxCheckmarkIconWidth = 32f.dp
internal val checkboxCheckmarkIconHeight = 32f.dp
