package carbon.compose.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.color.LocalCarbonTheme

private val errorIconWidth = 32f.dp
private val errorIconHeight = 32f.dp

internal val errorIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ErrorIcon",
        defaultWidth = errorIconWidth,
        defaultHeight = errorIconHeight,
        viewportWidth = errorIconWidth.value,
        viewportHeight = errorIconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(16f, 0f)
            curveTo(7.2f, 0f, 0f, 7.2f, 0f, 16f)
            curveTo(0f, 24.8f, 7.2f, 32f, 16f, 32f)
            curveTo(24.8f, 32f, 32f, 24.8f, 32f, 16f)
            curveTo(32f, 7.2f, 24.8f, 0f, 16f, 0f)
            close()
            moveTo(14.7f, 6.9f)
            horizontalLineTo(17.2f)
            verticalLineTo(19.5f)
            horizontalLineTo(14.7f)
            verticalLineTo(6.9f)
            close()
            moveTo(16f, 26.3f)
            curveTo(15.1f, 26.3f, 14.3f, 25.5f, 14.3f, 24.6f)
            curveTo(14.3f, 23.7f, 15.1f, 22.9f, 16f, 22.9f)
            curveTo(16.9f, 22.9f, 17.7f, 23.7f, 17.7f, 24.6f)
            curveTo(17.7f, 25.5f, 16.9f, 26.3f, 16f, 26.3f)
            close()
        }
    }.build()

@Composable
internal fun ErrorIcon(
    modifier: Modifier = Modifier,
    size: Dp = 16.dp
) {
    Image(
        imageVector = errorIcon,
        contentDescription = null,
        colorFilter = ColorFilter.tint(LocalCarbonTheme.current.supportError),
        modifier = modifier.requiredSize(size)
    )
}
