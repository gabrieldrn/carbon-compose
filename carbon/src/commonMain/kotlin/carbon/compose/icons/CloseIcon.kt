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

private val closeIconWidth = 32f.dp
private val closeIconHeight = 32f.dp

internal val closeIcon: ImageVector
    get() = ImageVector.Builder(
        name = "CloseIcon",
        defaultWidth = closeIconWidth,
        defaultHeight = closeIconHeight,
        viewportWidth = closeIconWidth.value,
        viewportHeight = closeIconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(17.4141f, 16f)
            lineTo(24f, 9.4141f)
            lineTo(22.5859f, 8f)
            lineTo(16f, 14.5859f)
            lineTo(9.4143f, 8f)
            lineTo(8f, 9.4141f)
            lineTo(14.5859f, 16f)
            lineTo(8f, 22.5859f)
            lineTo(9.4143f, 24f)
            lineTo(16f, 17.4141f)
            lineTo(22.5859f, 24f)
            lineTo(24f, 22.5859f)
            close()
        }
    }.build()

@Composable
internal fun CloseIcon(
    modifier: Modifier = Modifier,
    tint: Color = LocalCarbonTheme.current.iconPrimary,
    size: Dp = 16.dp
) {
    Image(
        imageVector = closeIcon,
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint),
        modifier = modifier.requiredSize(size)
    )
}
