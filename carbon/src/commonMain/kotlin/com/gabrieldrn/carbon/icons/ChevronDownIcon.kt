/*
 * Copyright 2024-2025 Gabriel Derrien
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
import com.gabrieldrn.carbon.Carbon

private val chevronDownIconWidth = 32f.dp
private val chevronDownIconHeight = 32f.dp

internal val chevronDownIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ChevronDownIcon",
        defaultWidth = chevronDownIconWidth,
        defaultHeight = chevronDownIconHeight,
        viewportWidth = chevronDownIconWidth.value,
        viewportHeight = chevronDownIconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(16f, 22f)
            lineTo(6f, 12f)
            lineTo(7.4f, 10.6f)
            lineTo(16f, 19.2f)
            lineTo(24.6f, 10.6f)
            lineTo(26f, 12f)
            close()
        }
    }.build()

@Composable
internal fun ChevronDownIcon(
    modifier: Modifier = Modifier,
    tint: Color = Carbon.theme.iconPrimary,
    size: Dp = 16.dp
) {
    Image(
        imageVector = chevronDownIcon,
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint),
        modifier = modifier.requiredSize(size)
    )
}
