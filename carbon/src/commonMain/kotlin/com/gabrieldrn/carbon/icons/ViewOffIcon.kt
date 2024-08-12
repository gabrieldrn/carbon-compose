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

package com.gabrieldrn.carbon.icons

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
import com.gabrieldrn.carbon.foundation.color.LocalCarbonTheme

private val viewOffIconWidth = 32f.dp
private val viewOffIconHeight = 32f.dp

internal val viewOffIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ViewOffIcon",
        defaultWidth = viewOffIconWidth,
        defaultHeight = viewOffIconHeight,
        viewportWidth = viewOffIconWidth.value,
        viewportHeight = viewOffIconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(5.24f, 22.51f)
            lineToRelative(1.43f, -1.42f)
            arcTo(14.06f, 14.06f, 0f, false, true, 3.07f, 16f)
            curveTo(5.1f, 10.93f, 10.7f, 7f, 16f, 7f)
            arcToRelative(12.38f, 12.38f, 0f, false, true, 4f, .72f)
            lineToRelative(1.55f, -1.56f)
            arcTo(14.72f, 14.72f, 0f, false, false, 16f, 5f)
            arcTo(16.69f, 16.69f, 0f, false, false, 1.06f, 15.66f)
            arcToRelative(1f, 1f, 0f, false, false, 0f, 0.68f)
            arcTo(16f, 16f, 0f, false, false, 5.24f, 22.51f)
            close()
        }
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(12f, 15.73f)
            arcToRelative(4f, 4f, 0f, false, true, 3.7f, -3.7f)
            lineToRelative(1.81f, -1.82f)
            arcToRelative(6f, 6f, 0f, false, false, -7.33f, 7.33f)
            close()
        }
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(30.94f,15.66f)
            arcTo(16.4f, 16.4f, 0f, false, false, 25.2f, 8.22f)
            lineTo(30f, 3.41f)
            lineTo(28.59f, 2f)
            lineTo(2f, 28.59f)
            lineTo(3.41f, 30f)
            lineToRelative(5.1f, -5.1f)
            arcTo(15.29f, 15.29f, 0f, false, false, 16f, 27f)
            arcTo(16.69f, 16.69f, 0f, false, false, 30.94f, 16.34f)
            arcTo(1f, 1f, 0f, false, false, 30.94f, 15.66f)
            close()
            moveTo(20f, 16f)
            arcToRelative(4f, 4f, 0f, false, true, -6f, 3.44f)
            lineTo(19.44f, 14f)
            arcTo(4f, 4f, 0f, false, true, 20f, 16f)
            close()
            moveToRelative(-4f, 9f)
            arcToRelative(13.05f, 13.05f, 0f, false, true, -6f, -1.58f)
            lineToRelative(2.54f, -2.54f)
            arcToRelative(6f, 6f, 0f, false, false, 8.35f, -8.35f)
            lineToRelative(2.87f, -2.87f)
            arcTo(14.54f, 14.54f, 0f, false, true, 28.93f, 16f)
            curveTo(26.9f, 21.07f, 21.3f, 25f, 16f, 25f)
            close()
        }
    }.build()

@Composable
internal fun ViewOffIcon(
    modifier: Modifier = Modifier,
    tint: Color = LocalCarbonTheme.current.iconPrimary,
    size: Dp = 16.dp
) {
    Image(
        imageVector = viewOffIcon,
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint),
        modifier = modifier.requiredSize(size)
    )
}
