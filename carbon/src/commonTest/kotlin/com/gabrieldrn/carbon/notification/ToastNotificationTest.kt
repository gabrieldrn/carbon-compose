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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.forEachParameter
import kotlin.test.Test

class ToastNotificationTest {

    private var _title by mutableStateOf("")
    private var _body by mutableStateOf("")
    private var _status by mutableStateOf(NotificationStatus.Informational)
    private var _highContrast by mutableStateOf(false)
    private var _actionLabel by mutableStateOf("")
    private var _timestamp by mutableStateOf("")

    private fun ComposeUiTest.commonLayoutValidation(
        title: String,
        body: String,
        status: NotificationStatus
    ) {
        onNodeWithTag(NotificationTestTags.CONTAINER)
            .assertIsDisplayed()

        onNodeWithTag(
            when (status) {
                NotificationStatus.Informational -> NotificationTestTags.ICON_INFORMATIONAL
                NotificationStatus.Success -> NotificationTestTags.ICON_SUCCESS
                NotificationStatus.Warning -> NotificationTestTags.ICON_WARNING
                NotificationStatus.Error -> NotificationTestTags.ICON_ERROR
            }
        ).assertIsDisplayed()

        onNodeWithTag(NotificationTestTags.TITLE).run {
            if (title.isBlank()) {
                assertDoesNotExist()
            } else {
                assertIsDisplayed()
                assertTextEquals(title)
            }
        }

        onNodeWithTag(NotificationTestTags.SUBTITLE).run {
            if (body.isBlank()) {
                assertExists()
            } else {
                assertIsDisplayed()
                assertTextEquals(body)
            }
        }

        onNodeWithTag(NotificationTestTags.CLOSE_BUTTON)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun toastNotification_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                ToastNotification(
                    title = _title,
                    body = _body,
                    status = _status,
                    onClose = {},
                    highContrast = _highContrast,
                    timestamp = _timestamp
                )
            }
        }

        forEachParameter(
            arrayOf("", "This is a toast notification."),
            NotificationStatus.entries.toTypedArray(),
            arrayOf("", "Toast notification"),
            arrayOf(false, true),
            arrayOf("", "12:00")
        ) { body, status, title, highContrast, timestamp ->

            _body = body
            _status = status
            _title = title
            _highContrast = highContrast
            _timestamp = timestamp

            commonLayoutValidation(
                title = title,
                body = body,
                status = status,
            )

            onNodeWithTag(NotificationTestTags.DETAILS).run {
                if (timestamp.isBlank()) {
                    assertDoesNotExist()
                } else {
                    assertIsDisplayed()
                    assertTextEquals(timestamp)
                }
            }
        }
    }

    @Test
    fun toastNotification_actionable_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                ActionableToastNotification(
                    title = _title,
                    body = _body,
                    actionLabel = _actionLabel,
                    status = _status,
                    onAction = {},
                    onClose = {},
                    highContrast = _highContrast,
                )
            }
        }

        forEachParameter(
            arrayOf("", "This is a toast notification."),
            NotificationStatus.entries.toTypedArray(),
            arrayOf("", "Toast notification"),
            arrayOf("", "Action"),
            arrayOf(false, true)
        ) { body, status, title, actionLabel, highContrast ->

            _body = body
            _status = status
            _title = title
            _actionLabel = actionLabel
            _highContrast = highContrast

            commonLayoutValidation(
                title = title,
                body = body,
                status = status
            )

            onNodeWithTag(NotificationTestTags.ACTION_BUTTON)
                .assertIsDisplayed()
                .assertHasClickAction()
                .assertTextEquals(actionLabel)
        }
    }
}
