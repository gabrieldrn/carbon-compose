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

internal val checkboxIndeterminateIcon: ImageVector
    get() = ImageVector.Builder(
        name = "CheckboxIndeterminateIcon",
        defaultWidth = checkboxIndeterminateIconWidth,
        defaultHeight = checkboxIndeterminateIconHeight,
        viewportWidth = checkboxIndeterminateIconWidth.value,
        viewportHeight = checkboxIndeterminateIconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            // <rect x="8" y="13.3" width="16" height="5.3" />
            moveTo(8f, 13.3f)
            lineTo(24f, 13.3f)
            lineTo(24f, 18.6f)
            lineTo(8f, 18.6f)
            close()
        }
    }.build()

internal val checkboxIndeterminateIconWidth = 32f.dp
internal val checkboxIndeterminateIconHeight = 32f.dp
