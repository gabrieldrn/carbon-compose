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

package com.gabrieldrn.carbon.checkbox

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val checkboxCheckmarkIcon: ImageVector
    get() = ImageVector.Builder(
        name = "CheckboxCheckmarkIcon",
        defaultWidth = checkboxCheckmarkIconWidth,
        defaultHeight = checkboxCheckmarkIconHeight,
        viewportWidth = checkboxCheckmarkIconWidth.value,
        viewportHeight = checkboxCheckmarkIconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(13.3f, 23.3f)
            lineTo(6.6f, 16.7f)
            lineTo(8.7f, 14.6f)
            lineTo(13.3f, 19.2f)
            lineTo(23.2f, 9.3f)
            lineTo(25.3f, 11.4f)
            close()
        }
    }.build()

internal val checkboxCheckmarkIconWidth = 32f.dp
internal val checkboxCheckmarkIconHeight = 32f.dp
