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

package com.gabrieldrn.carbon.catalog.progressbar

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.progressbar.IndeterminateProgressBar
import com.gabrieldrn.carbon.progressbar.ProgressBar
import com.gabrieldrn.carbon.progressbar.ProgressBarSize
import com.gabrieldrn.carbon.progressbar.ProgressBarState
import com.gabrieldrn.carbon.tab.TabItem
import com.gabrieldrn.carbon.toggle.Toggle

private val progressBarStateOptions = ProgressBarState.entries.toDropdownOptions()
private val progressBarVariants = ProgressBarVariant.entries.map { TabItem(it.name) }
private val progressBarSizeOptions = ProgressBarSize.entries.toDropdownOptions()

private enum class ProgressBarVariant { Indeterminate, Determinate }

@Composable
fun ProgressBarDemoScreen(modifier: Modifier = Modifier) {

    var state by remember { mutableStateOf(ProgressBarState.Active) }
    var size by remember { mutableStateOf(ProgressBarSize.Big) }
    var isInlined by remember { mutableStateOf(false) }
    var isIndented by remember { mutableStateOf(false) }
    val isIndentedToggleEnabled by remember { derivedStateOf { !isInlined } }
    val progressBarSizeDropdownState by remember {
        derivedStateOf {
            if (isInlined && state in arrayOf(ProgressBarState.Success, ProgressBarState.Error)) {
                DropdownInteractiveState.Disabled
            } else {
                DropdownInteractiveState.Enabled
            }
        }
    }

    val parameters: @Composable ColumnScope.(TabItem) -> Unit = { variant ->
        val isIndeterminate = remember(variant) {
            ProgressBarVariant.valueOf(variant.label) == ProgressBarVariant.Indeterminate
        }

        Dropdown(
            placeholder = "Progress bar state",
            label = "State",
            options = progressBarStateOptions,
            selectedOption = state,
            state = if (isIndeterminate) {
                DropdownInteractiveState.Disabled
            } else {
                DropdownInteractiveState.Enabled
            },
            onOptionSelected = { option -> state = option },
        )

        Dropdown(
            placeholder = "Progress bar size",
            label = "Size",
            options = progressBarSizeOptions,
            selectedOption = size,
            state = progressBarSizeDropdownState,
            onOptionSelected = { option -> size = option },
        )

        Toggle(
            actionText = "Inlined",
            isToggled = isInlined,
            onToggleChange = { isInlined = it }
        )

        Toggle(
            actionText = "Indented",
            isToggled = isIndented,
            isEnabled = isIndentedToggleEnabled,
            onToggleChange = { isIndented = it }
        )
    }

    val demoContent: @Composable ColumnScope.(TabItem) -> Unit = { variant ->
        val isIndeterminate = remember(variant) {
            ProgressBarVariant.valueOf(variant.label) == ProgressBarVariant.Indeterminate
        }

        if (isIndeterminate) {
            IndeterminateProgressBar(
                labelText = "Label text",
                helperText = "Helper text",
                size = size,
                indented = isIndented,
                inlined = isInlined,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            ProgressBar(
                value = .75f,
                labelText = "Label text",
                helperText = "Helper text",
                size = size,
                indented = isIndented,
                inlined = isInlined,
                state = state,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

    DemoScreen(
        variants = progressBarVariants,
        demoParametersContent = parameters,
        demoContent = demoContent,
        modifier = modifier
    )
}
