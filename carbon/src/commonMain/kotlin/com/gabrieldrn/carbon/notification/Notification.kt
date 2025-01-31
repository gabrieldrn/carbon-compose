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

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString

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
 */
@Composable
public fun CalloutNotification(
    title: String,
    body: AnnotatedString,
    status: NotificationStatus,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {

    }
}
