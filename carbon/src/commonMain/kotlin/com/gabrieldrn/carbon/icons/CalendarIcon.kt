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

internal val calendarIcon: ImageVector
    get() {
        if (_Calendar != null) {
            return _Calendar!!
        }
        _Calendar = ImageVector.Builder(
            name = "Calendar",
            defaultWidth = 32.dp,
            defaultHeight = 32.dp,
            viewportWidth = 32f,
            viewportHeight = 32f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(26f, 4f)
                horizontalLineToRelative(-4f)
                verticalLineTo(2f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-8f)
                verticalLineTo(2f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(2f)
                horizontalLineTo(6f)
                curveTo(4.9f, 4f, 4f, 4.9f, 4f, 6f)
                verticalLineToRelative(20f)
                curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
                horizontalLineToRelative(20f)
                curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
                verticalLineTo(6f)
                curveTo(28f, 4.9f, 27.1f, 4f, 26f, 4f)
                close()
                moveTo(26f, 26f)
                horizontalLineTo(6f)
                verticalLineTo(12f)
                horizontalLineToRelative(20f)
                verticalLineTo(26f)
                close()
                moveTo(26f, 10f)
                horizontalLineTo(6f)
                verticalLineTo(6f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(2f)
                verticalLineTo(6f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(2f)
                verticalLineTo(6f)
                horizontalLineToRelative(4f)
                verticalLineTo(10f)
                close()
            }
        }.build()

        return _Calendar!!
    }

@Suppress("ObjectPropertyName")
private var _Calendar: ImageVector? = null

@Composable
internal fun CalendarIcon(
    modifier: Modifier = Modifier,
    tint: Color = Carbon.theme.iconPrimary,
    size: Dp = 16.dp
) {
    Image(
        imageVector = calendarIcon,
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint),
        modifier = modifier
            .requiredSize(size)
            .testTag(calendarIcon.name)
    )
}
