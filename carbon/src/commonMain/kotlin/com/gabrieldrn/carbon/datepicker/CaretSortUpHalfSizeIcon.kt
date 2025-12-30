/*
 * Copyright 2025 Gabriel Derrien
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

package com.gabrieldrn.carbon.datepicker

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

internal val CaretIncreaseYear: ImageVector
    get() {
        if (_CaretIncreaseYear != null) {
            return _CaretIncreaseYear!!
        }
        _CaretIncreaseYear = ImageVector.Builder(
            name = "CaretSortUp",
            defaultWidth = 32.dp,
            defaultHeight = 8.dp,
            viewportWidth = 32f,
            viewportHeight = 8f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(8f, 8f)
                lineToRelative(8f, -8f)
                lineToRelative(8f, 8f)
                close()
            }
        }.build()

        return _CaretIncreaseYear!!
    }

@Suppress("ObjectPropertyName")
private var _CaretIncreaseYear: ImageVector? = null

@Preview
@Composable
private fun CaretIncreaseYearPreview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = CaretIncreaseYear, contentDescription = null)
    }
}
