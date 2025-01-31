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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.icons.CheckmarkFilledIcon
import com.gabrieldrn.carbon.icons.ErrorFilledIcon
import com.gabrieldrn.carbon.icons.InformationFilledIcon
import com.gabrieldrn.carbon.icons.WarningFilledIcon

/**
 * # Callout notification
 *
 * Callouts are used to highlight important information contextually within the contents of the
 * page, and cannot be dismissed. Unlike other notification components they are not triggered by the
 * user or system, rather they load with the contents of the page. They do not act as a feedback
 * mechanism, they are persistent, and always present on the screen to provide necessary information
 * to the user.
 *
 * (From [Notification documentation](https://carbondesignsystem.com/components/notification/usage/#callout))
 *
 * @param title The title of the notification.
 * @param body The body of the notification. This can be a simple string or a more complex text
 * with formatting, allowing for links, bold text, etc.
 * @param status The status of the notification, which determines its color and icon used.
 * @param modifier The modifier to apply to the component.
 * @param highContrast Whether to use high contrast colors.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
public fun CalloutNotification(
    title: String,
    body: AnnotatedString,
    status: NotificationStatus,
    modifier: Modifier = Modifier,
    highContrast: Boolean = false
) {
    val colors = NotificationColors.rememberColors(
        status = status,
        useHighContrast = highContrast
    )

    NotificationContainer(
        colors = colors,
        modifier = modifier
    ) {
        Row {
            Icon(
                status = status,
                colors = colors,
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = SpacingScale.spacing05)
            ) {
                BasicText(
                    text = title,
                    style = Carbon.typography.headingCompact01,
                    color = { colors.titleColor }
                )
                BasicText(
                    text = body,
                    style = Carbon.typography.bodyCompact01,
                    color = { colors.bodyColor }
                )
            }
        }
    }
}

private val iconSize = 20.dp

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
            modifier = modifier
        )
        NotificationStatus.Success -> CheckmarkFilledIcon(
            tint = colors.iconColor,
            size = iconSize,
            modifier = modifier
        )
        NotificationStatus.Warning -> WarningFilledIcon(
            tint = colors.iconColor,
            innerTint = colors.iconInnerColor,
            size = iconSize,
            modifier = modifier
        )
        NotificationStatus.Error -> ErrorFilledIcon(
            tint = colors.iconColor,
            size = iconSize,
            modifier = modifier
        )
    }
}

@Composable
private fun NotificationContainer(
    colors: NotificationColors,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
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
            .padding(SpacingScale.spacing05),
        content = content
    )
}
