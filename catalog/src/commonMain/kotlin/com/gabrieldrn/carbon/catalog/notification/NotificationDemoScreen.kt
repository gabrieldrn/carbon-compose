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

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.notification.NotificationStatus
import com.gabrieldrn.carbon.tab.TabItem
import com.gabrieldrn.carbon.textinput.TextInput
import com.gabrieldrn.carbon.toggle.Toggle

private enum class NotificationVariant {
    Callout,
    Inline
}

private val variants = NotificationVariant.entries.map { TabItem(it.name) }

@Composable
fun NotificationDemoScreen(
    modifier: Modifier = Modifier
) {
    var notificationStatus by remember { mutableStateOf(NotificationStatus.Success) }
    var highContrast by remember { mutableStateOf(false) }
    var title by remember {
        mutableStateOf("Lorem ipsum")
    }
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

        TextInput(
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
        demoContent = {
            CalloutNotificationDemo(
                title = title,
                body = body,
                notificationStatus = notificationStatus,
                highContrast = highContrast
            )
        },
        demoParametersContent = parametersContent,
        displayVariantsWIPNotification = true
    )
}
