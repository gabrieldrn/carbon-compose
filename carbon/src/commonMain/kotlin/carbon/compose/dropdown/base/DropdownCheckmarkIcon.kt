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

package carbon.compose.dropdown.base

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val dropdownCheckmarkIcon: ImageVector
    get() = ImageVector.Builder(
        name = "DropdownCheckmarkIcon",
        defaultWidth = dropdownCheckmarkIconWidth,
        defaultHeight = dropdownCheckmarkIconHeight,
        viewportWidth = dropdownCheckmarkIconWidth.value,
        viewportHeight = dropdownCheckmarkIconHeight.value
    ).apply {
        path(fill = SolidColor(Color(0xFF000000))) {
            moveTo(13f, 24f)
            lineTo(4f, 15f)
            lineTo(5.414f, 13.586f)
            lineTo(13f, 21.171f)
            lineTo(26.586f, 7.586f)
            lineTo(28f, 9f)
            close()
        }
    }.build()

internal val dropdownCheckmarkIconWidth = 32f.dp
internal val dropdownCheckmarkIconHeight = 32f.dp
