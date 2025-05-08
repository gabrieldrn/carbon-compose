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

package com.gabrieldrn.carbon.catalog.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.notification.ActionableInlineNotification
import com.gabrieldrn.carbon.notification.ActionableToastNotification
import com.gabrieldrn.carbon.notification.InlineNotification
import com.gabrieldrn.carbon.notification.NotificationStatus
import com.gabrieldrn.carbon.notification.ToastNotification
import com.gabrieldrn.carbon.tab.TabItem
import com.gabrieldrn.carbon.textinput.TextArea
import com.gabrieldrn.carbon.textinput.TextInput
import com.gabrieldrn.carbon.toggle.Toggle

private enum class NotificationVariant(val label: String) {
    Callout("Callout"),
    Inline("Inline"),
    Toast("Toast"),
    ActionableInline("Actionable Inline"),
    ActionableToast("Actionable Toast");

    companion object {
        fun fromLabel(label: String) = entries.first { it.label == label }
    }
}

private val variants = NotificationVariant.entries.map { TabItem(it.label) }

@Composable
fun NotificationDemoScreen(
    modifier: Modifier = Modifier
) {
    var notificationStatus by remember { mutableStateOf(NotificationStatus.Success) }
    var highContrast by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("Lorem ipsum") }
    var body by remember {
        mutableStateOf("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
    }

    val parametersContent: @Composable ColumnScope.(TabItem) -> Unit = {
        TextInput(
            label = "Title",
            value = title,
            onValueChange = { title = it },
            placeholderText = "Enter a title"
        )

        TextArea(
            label = "Body",
            value = body,
            onValueChange = { body = it },
            placeholderText = "Enter a body"
        )

        Dropdown(
            placeholder = "Choose option",
            label = "Notification status",
            options = NotificationStatus.entries.toDropdownOptions(),
            selectedOption = notificationStatus,
            onOptionSelected = { notificationStatus = it }
        )

        Toggle(
            label = "High contrast",
            isToggled = highContrast,
            onToggleChange = { highContrast = it }
        )
    }

    DemoScreen(
        variants = variants,
        modifier = modifier,
        demoContent = { variant ->
            when (NotificationVariant.fromLabel(variant.label)) {
                NotificationVariant.Callout -> CalloutNotificationDemo(
                    title = title,
                    body = body,
                    notificationStatus = notificationStatus,
                    highContrast = highContrast
                )

                NotificationVariant.Inline ->
                    // Usage of this key fixes an issue with the focus indication color when
                    // changing the high contrast parameter. Although the focus indication instance
                    // is set to be re-calculated when the high contrast parameter changes, the
                    // rendered color is not updated.
                    key(highContrast) {
                        InlineNotification(
                            title = title,
                            body = body,
                            status = notificationStatus,
                            onClose = {},
                            modifier = Modifier.width(IntrinsicSize.Max),
                            highContrast = highContrast
                        )
                    }

                NotificationVariant.Toast -> Column {
                    ToastNotification(
                        title = title,
                        body = body,
                        status = notificationStatus,
                        onClose = {},
                        modifier = Modifier.width(IntrinsicSize.Max),
                        highContrast = highContrast
                    )

                    ToastNotification(
                        title = title,
                        body = body,
                        status = notificationStatus,
                        onClose = {},
                        modifier = Modifier
                            .width(IntrinsicSize.Max)
                            .padding(top = SpacingScale.spacing05),
                        highContrast = highContrast,
                        timestamp = "Time stamp [12:12:12 AM]"
                    )
                }

                NotificationVariant.ActionableInline ->
                    key(highContrast) {
                        ActionableInlineNotification(
                            title = title,
                            body = body,
                            status = notificationStatus,
                            actionLabel = "Action",
                            onAction = {},
                            onClose = {},
                            modifier = Modifier.width(IntrinsicSize.Max),
                            highContrast = highContrast
                        )
                    }

                NotificationVariant.ActionableToast ->
                    ActionableToastNotification(
                        title = title,
                        body = body,
                        status = notificationStatus,
                        actionLabel = "Action",
                        onAction = {},
                        onClose = {},
                        modifier = Modifier.width(IntrinsicSize.Max),
                        highContrast = highContrast
                    )
            }
        },
        demoParametersContent = parametersContent,
        displayVariantsWIPNotification = true
    )
}
