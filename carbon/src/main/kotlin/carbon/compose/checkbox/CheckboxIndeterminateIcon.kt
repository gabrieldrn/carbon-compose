package carbon.compose.checkbox

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val checkboxIndeterminateIcon: ImageVector
    get() = ImageVector.Builder(
        name = "CheckboxIndeterminateIcon",
        defaultWidth = checkboxIndeterminateIconWidth,
        defaultHeight = checkboxIndeterminateIconHeight,
        viewportWidth = checkboxIndeterminateIconWidth.value,
        viewportHeight = checkboxIndeterminateIconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            // <rect x="8" y="13.3" width="16" height="5.3" />
            moveTo(8f, 13.3f)
            lineTo(24f, 13.3f)
            lineTo(24f, 18.6f)
            lineTo(8f, 18.6f)
            close()
        }
    }.build()

internal val checkboxIndeterminateIconWidth = 32f.dp
internal val checkboxIndeterminateIconHeight = 32f.dp
