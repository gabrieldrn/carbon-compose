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

package com.gabrieldrn.carbon.catalog.breadcrumb

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.breadcrumb.Breadcrumb
import com.gabrieldrn.carbon.breadcrumb.BreadcrumbSize
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.tab.TabItem
import com.gabrieldrn.carbon.toggle.Toggle

private val breadcrumbVariants = BreadcrumbSize.entries.map { it.name }.map(::TabItem)

private val breadcrumbs = buildList {
    repeat(3) {
        add(
            Breadcrumb(
                label = "Breadcrumb ${it + 1}",
                isEnabled = it != 2
            )
        )
    }
}

@Composable
fun BreadcrumbDemoScreen(modifier: Modifier = Modifier) {
    var trailingSeparator by remember { mutableStateOf(false) }

    DemoScreen(
        variants = breadcrumbVariants,
        demoContent = { variant ->
            Breadcrumb(
                breadcrumbs = breadcrumbs,
                onBreadcrumbClick = {},
                displayTrailingSeparator = trailingSeparator,
                size = BreadcrumbSize.valueOf(variant.label)
            )
        },
        demoParametersContent = {
            Toggle(
                label = "Display trailing separator",
                isToggled = trailingSeparator,
                onToggleChange = { trailingSeparator = it }
            )
        },
        modifier = modifier,
    )
}
