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
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.text.buildAnnotatedString
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.forEachParameter
import kotlin.test.Test

class CalloutNotificationTest {

    private var _annotatedStringBody by mutableStateOf(buildAnnotatedString {})
    private var _stringBody by mutableStateOf("")
    private var _status by mutableStateOf(NotificationStatus.Informational)
    private var _title by mutableStateOf("")
    private var _highContrast by mutableStateOf(false)

    private fun ComposeUiTest.commonLayoutValidation(
        title: String,
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
    }

    @Test
    fun calloutNotification_annotatedStringBody_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                CalloutNotification(
                    title = _title,
                    body = _annotatedStringBody,
                    status = _status,
                    highContrast = _highContrast,
                )
            }
        }

        forEachParameter(
            arrayOf(
                buildAnnotatedString {},
                buildAnnotatedString { append("This is a callout notification.") }
            ),
            NotificationStatus.entries.toTypedArray(),
            arrayOf("", "Callout notification"),
            arrayOf(false, true)
        ) { body, status, title, highContrast ->

            _annotatedStringBody = body
            _status = status
            _title = title
            _highContrast = highContrast

            commonLayoutValidation(
                title = title,
                status = status
            )

            onNodeWithTag(NotificationTestTags.SUBTITLE).run {
                if (body.text.isBlank()) {
                    assertExists()
                } else {
                    assertIsDisplayed()
                    assertTextEquals(body.text)
                }
            }
        }
    }

    @Test
    fun calloutNotification_stringBody_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                CalloutNotification(
                    title = _title,
                    body = _stringBody,
                    status = _status,
                    highContrast = _highContrast,
                )
            }
        }

        forEachParameter(
            arrayOf("", "This is a callout notification."),
            NotificationStatus.entries.toTypedArray(),
            arrayOf("", "Callout notification"),
            arrayOf(false, true)
        ) { body, status, title, highContrast ->

            _stringBody = body
            _status = status
            _title = title
            _highContrast = highContrast

            commonLayoutValidation(
                title = title,
                status = status
            )

            onNodeWithTag(NotificationTestTags.SUBTITLE).run {
                if (body.isBlank()) {
                    assertExists()
                } else {
                    assertIsDisplayed()
                    assertTextEquals(body)
                }
            }
        }
    }
}
