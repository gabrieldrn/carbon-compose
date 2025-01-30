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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon

private val iconWidth = 32f.dp
private val iconHeight = 32f.dp

internal val warningAltIcon: ImageVector
    get() = ImageVector.Builder(
        name = "WarningAltFilledIcon",
        defaultWidth = iconWidth,
        defaultHeight = iconHeight,
        viewportWidth = iconWidth.value,
        viewportHeight = iconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(16.002f, 6.1714f)
            horizontalLineToRelative(-.004f)
            lineTo(4.6487f, 27.9966f)
            lineTo(4.6506f, 28f)
            horizontalLineTo(27.3494f)
            lineToRelative(.0019f, -.0034f)
            close()

            moveTo(14.875f, 12f)
            horizontalLineToRelative(2.25f)
            verticalLineToRelative(9f)
            horizontalLineToRelative(-2.25f)
            close()

            moveTo(16f, 26f)
            arcToRelative(1.5f, 1.5f, 0f, true, true, 1.5f, -1.5f)
            arcTo(1.5f, 1.5f, 0f, false, true, 16f, 26f)
            close()

            moveTo(29f, 30f)
            horizontalLineTo(3f)
            arcToRelative(1f, 1f, 0f, false, true, -.8872f, -1.4614f)
            lineToRelative(13f, -25f)
            arcToRelative(1f, 1f, 0f, false, true, 1.7744f, 0f)
            lineToRelative(13f, 25f)
            arcTo(1f, 1f, 0f, false, true, 29f, 30f)
            close()

            moveTo(4.6507f, 28f)
            horizontalLineTo(27.3493f)
            lineToRelative(.002f, -.0033f)
            lineTo(16.002f, 6.1714f)
            horizontalLineToRelative(-.004f)
            lineTo(4.6487f, 27.9967f)
            close()
        }
    }.build()

internal val warningAltInnerIcon: ImageVector
    get() = ImageVector.Builder(
        name = "WarningAltFilledInnerIcon",
        defaultWidth = iconWidth,
        defaultHeight = iconHeight,
        viewportWidth = iconWidth.value,
        viewportHeight = iconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(16f, 26f)
            arcToRelative(1.5f, 1.5f, 0f, true, true, 1.5f, -1.5f)
            arcTo(1.5f, 1.5f, 0f, false, true, 16f, 26f)
            close()

            moveToRelative(-1.125f, -5f)
            horizontalLineToRelative(2.25f)
            verticalLineTo(12f)
            horizontalLineToRelative(-2.25f)
            close()
        }
    }.build()

@Composable
internal fun WarningAltFilledIcon(
    modifier: Modifier = Modifier,
    tint: Color = Carbon.theme.iconPrimary,
    innerTint: Color = Color.Transparent,
    size: Dp = 16.dp
) {
    Box(modifier = modifier.size(size)) {
        Image(
            imageVector = warningAltIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(tint),
            modifier = Modifier
                .requiredSize(size)
                .align(Alignment.Center)
        )
        Image(
            imageVector = warningAltInnerIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(innerTint),
            modifier = Modifier
                .requiredSize(size)
                .align(Alignment.Center)
        )
    }
}
