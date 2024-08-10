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

private val warningIconWidth = 32f.dp
private val warningIconHeight = 32f.dp

internal val warningIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ErrorIcon",
        defaultWidth = warningIconWidth,
        defaultHeight = warningIconHeight,
        viewportWidth = warningIconWidth.value,
        viewportHeight = warningIconHeight.value
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
internal fun WarningIcon(
    modifier: Modifier = Modifier,
    size: Dp = 16.dp
) {
    Image(
        imageVector = warningIcon,
        contentDescription = null,
        colorFilter = ColorFilter.tint(LocalCarbonTheme.current.supportError),
        modifier = modifier.requiredSize(size)
    )
}
