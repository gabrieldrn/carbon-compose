/*
 * Copyright 2024 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

private val viewIconWidth = 32f.dp
private val viewIconHeight = 32f.dp

internal val viewIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ViewIcon",
        defaultWidth = viewIconWidth,
        defaultHeight = viewIconHeight,
        viewportWidth = viewIconWidth.value,
        viewportHeight = viewIconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(30.94f, 15.66f)
            arcTo(16.69f, 16.69f, 0f, false, false, 16f, 5f)
            arcTo(16.69f, 16.69f, 0f, false, false, 1.06f, 15.66f)
            arcToRelative(1f, 1f, 0f, false, false, 0f, 0.68f)
            arcTo(16.69f, 16.69f, 0f, false, false, 16f, 27f)
            arcTo(16.69f, 16.69f, 0f, false, false, 30.94f, 16.34f)
            arcTo(1f, 1f, 0f, false, false, 30.94f, 15.66f)
            close()
            moveTo(16f, 25f)
            curveToRelative(-5.3f, 0f, -10.9f, -3.93f, -12.93f, -9f)
            curveTo(5.1f, 10.93f, 10.7f, 7f, 16f, 7f)
            reflectiveCurveToRelative(10.9f, 3.93f, 12.93f, 9f)
            curveTo(26.9f, 21.07f, 21.3f, 25f, 16f, 25f)
            close()
        }

        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(16f, 10f)
            arcToRelative(6f, 6f, 0f, true, false, 6f, 6f)
            arcTo(6f, 6f, 0f, false, false, 16f, 10f)
            close()
            moveToRelative(0f, 10f)
            arcToRelative(4f, 4f, 0f, true, true, 4f, -4f)
            arcTo(4f, 4f, 0f, false, true, 16f, 20f)
            close()
        }
    }.build()

@Composable
internal fun ViewIcon(
    modifier: Modifier = Modifier,
    tint: Color = LocalCarbonTheme.current.iconPrimary,
    size: Dp = 16.dp
) {
    Image(
        imageVector = viewIcon,
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint),
        modifier = modifier.requiredSize(size)
    )
}
