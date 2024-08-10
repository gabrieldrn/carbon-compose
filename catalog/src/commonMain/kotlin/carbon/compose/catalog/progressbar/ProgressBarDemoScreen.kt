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

package carbon.compose.catalog.progressbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import carbon.compose.dropdown.Dropdown
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.base.DropdownOption
import carbon.compose.dropdown.base.toDropdownOptions
import carbon.compose.foundation.color.CarbonLayer
import carbon.compose.foundation.color.containerBackground
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.progressbar.IndeterminateProgressBar
import carbon.compose.progressbar.ProgressBar
import carbon.compose.progressbar.ProgressBarSize
import carbon.compose.progressbar.ProgressBarState
import carbon.compose.toggle.Toggle

private val progressBarStateOptions = ProgressBarState.entries.toDropdownOptions()

private val progressBarVariantOptions = mapOf(
    false to DropdownOption("Determinate"),
    true to DropdownOption("Indeterminate")
)

private val progressBarSizeOptions = ProgressBarSize.entries.toDropdownOptions()

@Composable
fun ProgressBarDemoScreen(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf<ProgressBarState>(ProgressBarState.Active) }
    var isIndeterminate by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf<ProgressBarSize>(ProgressBarSize.Small) }
    var isInlined by remember { mutableStateOf(false) }
    var isIndented by remember { mutableStateOf(false) }
    val isIndentedToggleEnabled by remember { derivedStateOf { !isInlined } }
    val progressBarStateState by remember {
        derivedStateOf {
            if (isIndeterminate) DropdownInteractiveState.Disabled
            else DropdownInteractiveState.Enabled
        }
    }
    val progressBarSizeDropdownState by remember {
        derivedStateOf {
            if (isInlined && state in arrayOf(ProgressBarState.Success, ProgressBarState.Error)) {
                DropdownInteractiveState.Disabled
            } else {
                DropdownInteractiveState.Enabled
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(SpacingScale.spacing05)
            .navigationBarsPadding()
    ) {
        Box(modifier = Modifier.height(100.dp)) {
            if (isIndeterminate) {
                IndeterminateProgressBar(
                    labelText = "Label text",
                    helperText = "Helper text",
                    size = size,
                    indented = isIndented,
                    inlined = isInlined,
                    modifier = Modifier.align(Alignment.Center)
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
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        CarbonLayer {
            Column(
                modifier = Modifier
                    .padding(top = SpacingScale.spacing05)
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing05)
            ) {
                Dropdown(
                    placeholder = "Progress bar variant",
                    label = "Variant",
                    options = progressBarVariantOptions,
                    selectedOption = isIndeterminate,
                    onOptionSelected = { option -> isIndeterminate = option },
                )

                Dropdown(
                    placeholder = "Progress bar state",
                    label = "State",
                    options = progressBarStateOptions,
                    selectedOption = state,
                    state = progressBarStateState,
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
        }
    }
}
