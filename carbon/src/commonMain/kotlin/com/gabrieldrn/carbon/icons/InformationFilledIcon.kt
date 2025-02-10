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

internal val informationFilledIcon: ImageVector
    get() = ImageVector.Builder(
        name = "InformationFilledIcon",
        defaultWidth = iconWidth,
        defaultHeight = iconHeight,
        viewportWidth = iconWidth.value,
        viewportHeight = iconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(16f, 2f)
            arcTo(14f, 14f, 0f, true, false, 30f, 16f)
            arcTo(14f, 14f, 0f, false, false, 16f, 2f)
            close()

            moveToRelative(0f, 6f)
            arcToRelative(1.5f, 1.5f, 0f, true, true, -1.5f, 1.5f)
            arcTo(1.5f, 1.5f, 0f, false, true, 16f, 8f)
            close()

            moveToRelative(4f, 16.125f)
            horizontalLineTo(12f)
            verticalLineToRelative(-2.25f)
            horizontalLineToRelative(2.875f)
            verticalLineToRelative(-5.75f)
            horizontalLineTo(13f)
            verticalLineToRelative(-2.25f)
            horizontalLineToRelative(4.125f)
            verticalLineToRelative(8f)
            horizontalLineTo(20f)
            close()
        }
    }.build()

internal val informationFilledInnerIcon: ImageVector
    get() = ImageVector.Builder(
        name = "InformationFilledInnerIcon",
        defaultWidth = iconWidth,
        defaultHeight = iconHeight,
        viewportWidth = iconWidth.value,
        viewportHeight = iconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(16f, 8f)
            arcToRelative(1.5f, 1.5f, 0f, true, true, -1.5f, 1.5f)
            arcTo(1.5f, 1.5f, 0f, false, true, 16f, 8f)
            close()

            moveToRelative(4f, 13.875f)
            horizontalLineTo(17.125f)
            verticalLineToRelative(-8f)
            horizontalLineTo(13f)
            verticalLineToRelative(2.25f)
            horizontalLineToRelative(1.875f)
            verticalLineToRelative(5.75f)
            horizontalLineTo(12f)
            verticalLineToRelative(2.25f)
            horizontalLineToRelative(8f)
            close()
        }
    }.build()

@Composable
internal fun InformationFilledIcon(
    modifier: Modifier = Modifier,
    tint: Color = Carbon.theme.iconPrimary,
    innerTint: Color = Color.Transparent,
    size: Dp = 16.dp
) {
    Box(modifier = modifier.size(size)) {
        Image(
            imageVector = informationFilledIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(tint),
            modifier = Modifier
                .requiredSize(size)
                .align(Alignment.Center)
        )
        Image(
            imageVector = informationFilledInnerIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(innerTint),
            modifier = Modifier
                .requiredSize(size)
                .align(Alignment.Center)
        )
    }
}
