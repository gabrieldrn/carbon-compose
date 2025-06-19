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

package com.gabrieldrn.carbon.catalog.tooltip

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.button.Button
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.toggle.Toggle
import com.gabrieldrn.carbon.tooltip.TooltipAlignment
import com.gabrieldrn.carbon.tooltip.TooltipBox
import com.gabrieldrn.carbon.tooltip.TooltipPlacement

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TooltipDemoScreen(modifier: Modifier = Modifier) {

    var tooltipPlacement by rememberSaveable {
        mutableStateOf(TooltipPlacement.Top)
    }
    var tooltipAlignment by rememberSaveable {
        mutableStateOf(TooltipAlignment.Center)
    }
    var singleLine by rememberSaveable {
        mutableStateOf(true)
    }

    DemoScreen(
        demoContent = {
            TooltipBox(
                tooltipText = if (singleLine) {
                    "To be, or not to be..."
                } else {
                    "To be, or not to be, that is the question\n" +
                        "Whether 'tis nobler in the mind to suffer\n" +
                        "The slings and arrows of outrageous fortune,\n" +
                        "Or to take arms against a sea of troubles"
                },
                singleLine = singleLine,
                placement = tooltipPlacement,
                alignment = tooltipAlignment
            ) {
                Button(
                    label = "Hover me", // TODO For mobile, use "Long press me" instead.
                    onClick = { /* No-op */ },
                )
            }
        },
        demoParametersContent = {
            Dropdown(
                placeholder = "Choose option",
                label = "Placement",
                options = TooltipPlacement.entries.toDropdownOptions(),
                selectedOption = tooltipPlacement,
                onOptionSelected = { tooltipPlacement = it }
            )

            Dropdown(
                placeholder = "Choose option",
                label = "Alignment",
                options = TooltipAlignment.entries.toDropdownOptions(),
                selectedOption = tooltipAlignment,
                onOptionSelected = { tooltipAlignment = it }
            )

            Toggle(
                isToggled = singleLine,
                onToggleChange = { singleLine = it },
                label = "Single line",
            )
        },
        modifier = modifier
    )
}
