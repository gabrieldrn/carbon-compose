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

package com.gabrieldrn.carbon.catalog.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.catalog.Res
import com.gabrieldrn.carbon.catalog.ic_carbon
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.containerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.tag.ReadOnlyTag
import com.gabrieldrn.carbon.tag.TagSize
import com.gabrieldrn.carbon.tag.TagType
import com.gabrieldrn.carbon.toggle.Toggle
import org.jetbrains.compose.resources.painterResource

private val tagSizes = TagSize.entries.toDropdownOptions()

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagDemoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var tagSize by rememberSaveable { mutableStateOf(TagSize.Medium) }
        var displayIcon by rememberSaveable { mutableStateOf(false) }

        FlowColumn(
            maxItemsInEachColumn = 3,
            horizontalArrangement = Arrangement.spacedBy(
                SpacingScale.spacing03,
                alignment = Alignment.CenterHorizontally
            ),
            verticalArrangement = Arrangement.spacedBy(
                SpacingScale.spacing03,
                alignment = Alignment.CenterVertically
            ),
            modifier = Modifier
                .height(200.dp)
                .padding(SpacingScale.spacing05)
        ) {
            TagType.entries.forEach { color ->
                ReadOnlyTag(
                    text = "Tag",
                    icon = if (displayIcon) {
                        { painterResource(resource = Res.drawable.ic_carbon) }
                    } else {
                        null
                    },
                    type = color,
                    size = tagSize
                )
            }
        }

        CarbonLayer {
            Column(
                modifier = Modifier
                    .padding(SpacingScale.spacing05)
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing04)
            ) {
                Dropdown(
                    placeholder = "",
                    label = "Tag size",
                    options = tagSizes,
                    selectedOption = tagSize,
                    onOptionSelected = { tagSize = it },
                )

                Toggle(
                    isToggled = displayIcon,
                    onToggleChange = { displayIcon = it },
                    label = "Tag icon",
                    actionText = if (displayIcon) {
                        "Displayed"
                    } else {
                        "Hidden"
                    },
                )
            }
        }
    }
}
