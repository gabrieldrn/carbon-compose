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

package com.gabrieldrn.carbon.catalog.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.dropdown.base.DropdownOption
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.tab.TabItem
import com.gabrieldrn.carbon.tab.TabList
import com.gabrieldrn.carbon.tab.TabVariant

private val demoTabs = listOf(
    TabItem(label = "Dashboard"),
    TabItem(label = "Monitoring"),
    TabItem(label = "Activity"),
    TabItem(label = "Disabled", enabled = false)
)

private val layersOptions =
    Layer.entries.associateWith { DropdownOption(it.toString(), enabled = it != Layer.Layer03) }

private val tabVariants = TabVariant.entries.map { TabItem(it.name) }

@Composable
internal fun TabListDemoScreen(modifier: Modifier = Modifier) {
    DemoScreen(
        variants = tabVariants,
        defaultVariant = tabVariants.first(),
        demoContent = { variant ->
            var selectedTab by remember { mutableStateOf(demoTabs[0]) }

            TabList(
                tabs = demoTabs,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                variant = TabVariant.valueOf(variant.label)
            )
        },
        layers = layersOptions,
        modifier = modifier
    )
}
