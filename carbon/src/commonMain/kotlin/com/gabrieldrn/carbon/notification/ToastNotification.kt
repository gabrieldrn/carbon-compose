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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.button.Button
import com.gabrieldrn.carbon.button.ButtonSize
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

/**
 * # Toast notification
 *
 * Toast notifications are non-modal, time-based window elements used to display short messages;
 * they usually appear at the top of the screen and disappear after a few seconds.
 *
 * @param title The title of the notification.
 * @param body The body of the notification.
 * @param status The status of the notification, which determines its color and icon used.
 * @param onClose Callback invoked when the close icon is clicked.
 * @param modifier The modifier to apply to the component.
 * @param highContrast Whether to use high contrast colors.
 * @param timestamp An optional timestamp text below the body used showing the time the notification
 * was sent.
 */
@Composable
public fun ToastNotification(
    title: String,
    body: String,
    status: NotificationStatus,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    highContrast: Boolean = false,
    timestamp: String = "",
) {
    NotificationContainer(
        status = status,
        displayCloseButton = true,
        highContrast = highContrast,
        modifier = modifier,
        onClose = onClose,
        isFloating = true
    ) {
        Column(
            modifier = Modifier
                .padding(start = SpacingScale.spacing05)
                .padding(vertical = SpacingScale.spacing05)
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
                text = remember(body) { AnnotatedString(body) },
                style = Carbon.typography.bodyCompact01,
                color = { colors.bodyColor },
                modifier = Modifier.testTag(NotificationTestTags.SUBTITLE)
            )

            if (timestamp.isNotEmpty()) {
                BasicText(
                    text = timestamp,
                    style = Carbon.typography.bodyCompact01,
                    color = { colors.bodyColor },
                    modifier = Modifier
                        .padding(top = SpacingScale.spacing06)
                        .testTag(NotificationTestTags.DETAILS)
                )
            }
        }
    }
}

/**
 * # Toast notification - Actionable
 *
 * Toast notifications are non-modal, time-based window elements used to display short messages;
 * they usually appear at the top of the screen and disappear after a few seconds.
 *
 * @param title The title of the notification.
 * @param body The body of the notification.
 * @param status The status of the notification, which determines its color and icon used.
 * @param onClose Callback invoked when the close icon is clicked.
 * @param modifier The modifier to apply to the component.
 * @param highContrast Whether to use high contrast colors.
 * @param timestamp An optional timestamp text below the body used showing the time the notification
 * was sent.
 */
@Composable
public fun ActionableToastNotification(
    title: String,
    body: String,
    actionLabel: String,
    status: NotificationStatus,
    onAction: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    highContrast: Boolean = false,
) {
    NotificationContainer(
        status = status,
        displayCloseButton = true,
        highContrast = highContrast,
        modifier = modifier,
        onClose = onClose,
        isFloating = true
    ) {
        Column(
            modifier = Modifier
                .padding(start = SpacingScale.spacing05)
                .padding(vertical = SpacingScale.spacing05)
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
                text = remember(body) { AnnotatedString(body) },
                style = Carbon.typography.bodyCompact01,
                color = { colors.bodyColor },
                modifier = Modifier.testTag(NotificationTestTags.SUBTITLE)
            )

            CompositionLocalProvider(
                LocalCarbonTheme provides this@NotificationContainer.actionableToastTheme
            ) {
                Button(
                    label = actionLabel,
                    buttonSize = ButtonSize.Small,
                    buttonType = ButtonType.Tertiary,
                    onClick = onAction,
                    modifier = Modifier
                        .padding(top = SpacingScale.spacing06)
                        .testTag(NotificationTestTags.ACTION_BUTTON)
                )
            }
        }
    }
}
