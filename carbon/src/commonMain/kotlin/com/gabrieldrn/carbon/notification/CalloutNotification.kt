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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

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
 * @param body The body of the notification. This can be a simple string or a more complex text
 * with formatting, allowing for links, bold text, etc.
 * @param status The status of the notification, which determines its color and icon used.
 * @param modifier The modifier to apply to the component.
 * @param title The title of the notification.
 * @param highContrast Whether to use high contrast colors.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
public fun CalloutNotification(
    body: AnnotatedString,
    status: NotificationStatus,
    modifier: Modifier = Modifier,
    title: String = "",
    highContrast: Boolean = false
) {
    val colors = NotificationColors.rememberColors(
        status = status,
        useHighContrast = highContrast
    )

    NotificationContainer(
        status = status,
        colors = colors,
        modifier = modifier
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .weight(1f)
                .padding(start = SpacingScale.spacing05)
        ) {
            if (title.isNotBlank()) {
                BasicText(
                    text = title,
                    style = Carbon.typography.headingCompact01,
                    color = { colors.titleColor },
                    modifier = Modifier.testTag(NotificationTestTags.TITLE)
                )
            }
            BasicText(
                text = body,
                style = Carbon.typography.bodyCompact01,
                color = { colors.bodyColor },
                modifier = Modifier.testTag(NotificationTestTags.BODY)
            )
        }
    }
}

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
 * @param body The body of the notification.
 * @param status The status of the notification, which determines its color and icon used.
 * @param modifier The modifier to apply to the component.
 * @param title The title of the notification.
 * @param highContrast Whether to use high contrast colors.
 */
@Composable
public fun CalloutNotification(
    body: String,
    status: NotificationStatus,
    modifier: Modifier = Modifier,
    title: String = "",
    highContrast: Boolean = false
) {
    CalloutNotification(
        body = AnnotatedString(body),
        status = status,
        modifier = modifier,
        title = title,
        highContrast = highContrast
    )
}
