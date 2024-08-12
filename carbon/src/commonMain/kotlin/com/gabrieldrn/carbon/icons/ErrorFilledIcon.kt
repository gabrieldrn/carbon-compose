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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon

private val iconSize = 32f.dp

internal val errorFilledIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ErrorFilledIcon",
        defaultWidth = iconSize,
        defaultHeight = iconSize,
        viewportWidth = iconSize.value,
        viewportHeight = iconSize.value
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(16f, 2f)
            arcTo(14f, 14f, 0f, false, false, 2f, 16f)
            arcTo(14f, 14f, 0f, false, false, 16f, 30f)
            arcTo(14f, 14f, 0f, false, false, 30f, 16f)
            arcTo(14f, 14f, 0f, false, false, 16f, 2f)
            close()
            moveToRelative(5.4449f, 21f)
            lineTo(9f, 10.5557f)
            lineTo(10.5557f, 9f)
            lineTo(23f, 21.4448f)
        }
    }.build()

@Composable
internal fun ErrorFilledIcon(
    modifier: Modifier = Modifier,
    tint: Color = Carbon.theme.iconPrimary,
    size: Dp = 16.dp
) {
    Image(
        imageVector = errorFilledIcon,
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint),
        modifier = modifier
            .requiredSize(size)
            .testTag(errorFilledIcon.name)
    )
}
