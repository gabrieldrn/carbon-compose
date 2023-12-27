package carbon.compose.dropdown

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val chevronDownIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ChevronDownIcon",
        defaultWidth = chevronDownIconWidth,
        defaultHeight = chevronDownIconWidth,
        viewportWidth = chevronDownIconWidth.value,
        viewportHeight = chevronDownIconWidth.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(8f, 11f)
            lineTo(3f, 6f)
            lineTo(3.7f, 5.3f)
            lineTo(8f, 9.6f)
            lineTo(12.3f, 5.3f)
            lineTo(13f, 6f)
            close()
        }
    }.build()

internal val chevronDownIconWidth = 16f.dp
