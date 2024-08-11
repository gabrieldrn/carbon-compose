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

package com.gabrieldrn.carbon.toggle

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val toggleCheckmarkIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ToggleCheckmarkIcon",
        defaultWidth = toggleCheckmarkIconWidth,
        defaultHeight = toggleCheckmarkIconHeight,
        viewportWidth = toggleCheckmarkIconWidth.value,
        viewportHeight = toggleCheckmarkIconHeight.value
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000))
        ) {
            moveTo(2.2f, 2.7f)
            lineTo(5f, 0f)
            lineTo(6f, 1f)
            lineTo(2.2f, 5f)
            lineTo(0f, 2.7f)
            lineTo(1f, 1.5f)
            close()
        }
    }.build()

internal val toggleCheckmarkIconWidth = 6f.dp
internal val toggleCheckmarkIconHeight = 5f.dp
