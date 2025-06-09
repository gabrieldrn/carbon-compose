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

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.gabrieldrn.carbon.CarbonDesignSystem

@Preview(group = "Low contrast")
@Composable
private fun ToastNotificationPreview(
    @PreviewParameter(NotificationStatusParameterProvider::class) status: NotificationStatus
) {
    CarbonDesignSystem {
        ToastNotification(
            title = "Callout Notification",
            body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            status = status,
            onClose = {}
        )
    }
}

@Preview(group = "Low contrast - Timestamp")
@Composable
private fun ToastNotificationTimestampPreview(
    @PreviewParameter(NotificationStatusParameterProvider::class) status: NotificationStatus
) {
    CarbonDesignSystem {
        ToastNotification(
            title = "Callout Notification",
            body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            status = status,
            onClose = {},
            timestamp = "12:00"
        )
    }
}

@Preview(group = "High contrast")
@Composable
private fun ToastNotificationHighContrastPreview(
    @PreviewParameter(NotificationStatusParameterProvider::class) status: NotificationStatus
) {
    CarbonDesignSystem {
        ToastNotification(
            title = "Callout Notification",
            body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            status = status,
            onClose = {},
            highContrast = true
        )
    }
}


@Preview(group = "High contrast - Timestamp")
@Composable
private fun ToastNotificationTimestampHighContrastPreview(
    @PreviewParameter(NotificationStatusParameterProvider::class) status: NotificationStatus
) {
    CarbonDesignSystem {
        ToastNotification(
            title = "Callout Notification",
            body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            status = status,
            onClose = {},
            highContrast = true,
            timestamp = "12:00"
        )
    }
}
