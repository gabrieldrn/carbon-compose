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

private val chevronLeftIconWidth = 32f.dp
private val chevronLeftIconHeight = 32f.dp

internal val chevronLeftIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ChevronLeftIcon",
        defaultWidth = chevronLeftIconWidth,
        defaultHeight = chevronLeftIconHeight,
        viewportWidth = chevronLeftIconWidth.value,
        viewportHeight = chevronLeftIconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(10f, 16f)
            lineTo(20f, 6f)
            lineTo(21.4f, 7.4f)
            lineTo(12.8f, 16f)
            lineTo(21.4f, 24.6f)
            lineTo(20f, 26f)
            close()
        }
    }.build()

@Composable
internal fun ChevronLeftIcon(
    modifier: Modifier = Modifier,
    tint: Color = LocalCarbonTheme.current.iconPrimary,
    size: Dp = 16.dp
) {
    Image(
        imageVector = chevronLeftIcon,
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint),
        modifier = modifier.requiredSize(size)
    )
}
