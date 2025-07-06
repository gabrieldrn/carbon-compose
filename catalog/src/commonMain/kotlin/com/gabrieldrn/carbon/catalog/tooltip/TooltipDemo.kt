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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.button.Button
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.catalog.Res
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.catalog.ic_cognitive
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.toggle.Toggle
import com.gabrieldrn.carbon.tooltip.TooltipAlignment
import com.gabrieldrn.carbon.tooltip.TooltipParameters
import com.gabrieldrn.carbon.tooltip.TooltipPlacement
import org.jetbrains.compose.resources.painterResource

private enum class UITriggerOption { Button, IconButton }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TooltipDemoScreen(modifier: Modifier = Modifier) {

    var uiTrigger by rememberSaveable {
        mutableStateOf(UITriggerOption.Button)
    }
    var tooltipPlacement by rememberSaveable {
        mutableStateOf(TooltipPlacement.Top)
    }
    var tooltipAlignment by rememberSaveable {
        mutableStateOf(TooltipAlignment.Center)
    }
    var singleLine by rememberSaveable {
        mutableStateOf(true)
    }

    val tooltipParameters = remember(singleLine, tooltipPlacement, tooltipAlignment) {
        TooltipParameters(
            text = if (singleLine) {
                "To be, or not to be..."
            } else {
                """
                    To be, or not to be, that is the question
                    Whether 'tis nobler in the mind to suffer
                    The slings and arrows of outrageous fortune,
                    Or to take arms against a sea of troubles
                """.trimIndent()
            },
            singleLine = singleLine,
            placement = tooltipPlacement,
            alignment = tooltipAlignment
        )
    }

    DemoScreen(
        demoContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                when (uiTrigger) {
                    UITriggerOption.Button -> Button(
                        label = "Hover me", // TODO For mobile, use "Long press me" instead.
                        tooltipParameters = tooltipParameters,
                        onClick = {}
                    )
                    UITriggerOption.IconButton -> IconButton(
                        iconPainter = painterResource(Res.drawable.ic_cognitive),
                        tooltipParameters = tooltipParameters,
                        onClick = {},
                        isEnabled = true
                    )
                }
            }
        },
        demoParametersContent = {
            Dropdown(
                placeholder = "Choose option",
                label = "UI trigger",
                options = UITriggerOption.entries.toDropdownOptions(),
                selectedOption = uiTrigger,
                onOptionSelected = { uiTrigger = it }
            )

            Dropdown(
                placeholder = "Choose option",
                label = "Tooltip placement",
                options = TooltipPlacement.entries.toDropdownOptions(),
                selectedOption = tooltipPlacement,
                onOptionSelected = { tooltipPlacement = it }
            )

            Dropdown(
                placeholder = "Choose option",
                label = "Tooltip alignment",
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
