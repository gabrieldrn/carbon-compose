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

package com.gabrieldrn.carbon.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.icons.CheckmarkFilledIcon
import com.gabrieldrn.carbon.icons.ErrorFilledIcon
import com.gabrieldrn.carbon.icons.InformationFilledIcon
import com.gabrieldrn.carbon.icons.WarningFilledIcon

private val iconSize = 20.dp

@Composable
internal fun NotificationContainer(
    status: NotificationStatus,
    colors: NotificationColors,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(colors.backgroundColor)
            .drawWithContent {
                drawContent()
                val contourWidthPx = 1.dp.toPx()
                drawRect(
                    brush = SolidColor(colors.contourColor),
                    topLeft = Offset(contourWidthPx / 2, contourWidthPx / 2),
                    size = Size(
                        size.width - contourWidthPx,
                        size.height - contourWidthPx
                    ),
                    style = Stroke(1.dp.toPx())
                )
                drawRect(
                    brush = SolidColor(colors.borderLeftColor),
                    topLeft = Offset.Zero,
                    size = Size(3f.dp.toPx(), size.height)
                )
            }
            .padding(SpacingScale.spacing05)
            .testTag(NotificationTestTags.CONTAINER)
    ) {
        Icon(
            status = status,
            colors = colors,
        )

        content()
    }
}

@Composable
private fun Icon(
    status: NotificationStatus,
    colors: NotificationColors,
    modifier: Modifier = Modifier
) {
    when (status) {
        NotificationStatus.Informational -> InformationFilledIcon(
            tint = colors.iconColor,
            innerTint = colors.iconInnerColor,
            size = iconSize,
            modifier = modifier.testTag(NotificationTestTags.ICON_INFORMATIONAL)
        )
        NotificationStatus.Success -> CheckmarkFilledIcon(
            tint = colors.iconColor,
            size = iconSize,
            modifier = modifier.testTag(NotificationTestTags.ICON_SUCCESS)
        )
        NotificationStatus.Warning -> WarningFilledIcon(
            tint = colors.iconColor,
            innerTint = colors.iconInnerColor,
            size = iconSize,
            modifier = modifier.testTag(NotificationTestTags.ICON_WARNING)
        )
        NotificationStatus.Error -> ErrorFilledIcon(
            tint = colors.iconColor,
            size = iconSize,
            modifier = modifier.testTag(NotificationTestTags.ICON_ERROR)
        )
    }
}
