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

package com.gabrieldrn.carbon.dropdown.base

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val chevronDownIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ChevronDownIcon",
        defaultWidth = chevronDownIconWidth,
        defaultHeight = chevronDownIconWidth,
        viewportWidth = chevronDownIconWidth.value,
        viewportHeight = chevronDownIconWidth.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(8f, 11f)
            lineTo(3f, 6f)
            lineTo(3.7f, 5.3f)
            lineTo(8f, 9.6f)
            lineTo(12.3f, 5.3f)
            lineTo(13f, 6f)
            close()
        }
    }.build()

internal val chevronDownIconWidth = 16f.dp
