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

internal val warningFilledIcon: ImageVector
    get() = ImageVector.Builder(
        name = "WarningFilledIcon",
        defaultWidth = iconWidth,
        defaultHeight = iconHeight,
        viewportWidth = iconWidth.value,
        viewportHeight = iconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(16f, 2f)
            curveTo(8.3f, 2f, 2f, 8.3f, 2f, 16f)
            reflectiveCurveToRelative(6.3f, 14f, 14f, 14f)
            reflectiveCurveToRelative(14f, -6.3f, 14f, -14f)
            curveTo(30f, 8.3f, 23.7f, 2f, 16f, 2f)
            close()

            moveTo(14.9f, 8f)
            horizontalLineToRelative(2.2f)
            verticalLineToRelative(11f)
            horizontalLineToRelative(-2.2f)
            verticalLineTo(8f)
            close()

            moveTo(16f, 25f)
            curveToRelative(-0.8f, 0f, -1.5f, -0.7f, -1.5f, -1.5f)
            reflectiveCurveTo(15.2f, 22f, 16f, 22f)
            curveToRelative(0.8f, 0f, 1.5f, 0.7f, 1.5f, 1.5f)
            reflectiveCurveTo(16.8f, 25f, 16f, 25f)
            close()
        }
    }.build()

internal val warningFilledInnerIcon: ImageVector
    get() = ImageVector.Builder(
        name = "WarningFilledInnerIcon",
        defaultWidth = iconWidth,
        defaultHeight = iconHeight,
        viewportWidth = iconWidth.value,
        viewportHeight = iconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(17.5f, 23.5f)
            curveToRelative(0f, 0.8f, -0.7f, 1.5f, -1.5f, 1.5f)
            curveToRelative(-0.8f, 0f, -1.5f, -0.7f, -1.5f, -1.5f)
            reflectiveCurveTo(15.2f, 22f, 16f, 22f)
            curveTo(16.8f, 22f, 17.5f, 22.7f, 17.5f, 23.5f)
            close()

            moveTo(17.1f, 8f)
            horizontalLineToRelative(-2.2f)
            verticalLineToRelative(11f)
            horizontalLineToRelative(2.2f)
            verticalLineTo(8f)
            close()
        }
    }.build()

@Composable
internal fun WarningFilledIcon(
    modifier: Modifier = Modifier,
    tint: Color = Carbon.theme.iconPrimary,
    innerTint: Color = Color.Transparent,
    size: Dp = 16.dp
) {
    Box(modifier = modifier.size(size)) {
        Image(
            imageVector = warningFilledIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(tint),
            modifier = Modifier
                .requiredSize(size)
                .align(Alignment.Center)
        )
        Image(
            imageVector = warningFilledInnerIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(innerTint),
            modifier = Modifier
                .requiredSize(size)
                .align(Alignment.Center)
        )
    }
}
