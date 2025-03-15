/*
 * Copyright 2024 Gabriel Derrien
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

package com.gabrieldrn.carbon.catalog.toggle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.tab.TabItem
import com.gabrieldrn.carbon.toggle.SmallToggle
import com.gabrieldrn.carbon.toggle.Toggle

private val toggleVariants = ToggleVariant.entries.map { TabItem(it.name) }

private enum class ToggleVariant {
    Default, Small
}

@Composable
fun ToggleDemoScreen(modifier: Modifier = Modifier) {

    var isEnabled by rememberSaveable { mutableStateOf(true) }
    var isReadOnly by rememberSaveable { mutableStateOf(false) }
    var isToggled by rememberSaveable { mutableStateOf(false) }

    DemoScreen(
        variants = toggleVariants,
        demoContent = { variant ->
            if (ToggleVariant.valueOf(variant.label) == ToggleVariant.Default) {
                Toggle(
                    label = "Toggle",
                    isToggled = isToggled,
                    isEnabled = isEnabled,
                    isReadOnly = isReadOnly,
                    onToggleChange = { isToggled = it },
                    actionText = "Action text",
                )
            } else {
                SmallToggle(
                    isToggled = isToggled,
                    isEnabled = isEnabled,
                    isReadOnly = isReadOnly,
                    onToggleChange = { isToggled = it },
                    actionText = "Action text",
                )
            }
        },
        demoParametersContent = {
            Toggle(
                label = "Enabled",
                isToggled = isEnabled,
                onToggleChange = { isEnabled = it },
            )

            Toggle(
                label = "Read only",
                isToggled = isReadOnly,
                onToggleChange = { isReadOnly = it },
            )
        },
        modifier = modifier
    )
}
