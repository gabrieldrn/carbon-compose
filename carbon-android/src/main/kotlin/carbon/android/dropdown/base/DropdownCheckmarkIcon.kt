package carbon.android.dropdown.base

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val dropdownCheckmarkIcon: ImageVector
    get() = ImageVector.Builder(
        name = "DropdownCheckmarkIcon",
        defaultWidth = dropdownCheckmarkIconWidth,
        defaultHeight = dropdownCheckmarkIconHeight,
        viewportWidth = dropdownCheckmarkIconWidth.value,
        viewportHeight = dropdownCheckmarkIconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(13f, 24f)
            lineTo(4f, 15f)
            lineTo(5.414f, 13.586f)
            lineTo(13f, 21.171f)
            lineTo(26.586f, 7.586f)
            lineTo(28f, 9f)
            close()
        }
    }.build()

internal val dropdownCheckmarkIconWidth = 32f.dp
internal val dropdownCheckmarkIconHeight = 32f.dp
