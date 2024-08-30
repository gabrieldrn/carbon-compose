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

package com.gabrieldrn.carbon.catalog.dropdown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.catalog.misc.LayerSelectionDropdown
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.base.DropdownOption
import com.gabrieldrn.carbon.dropdown.base.DropdownSize
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.containerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

private val dropdownStates = listOf(
    DropdownInteractiveState.Enabled,
    DropdownInteractiveState.Warning("Warning message goes here"),
    DropdownInteractiveState.Error("Error message goes here"),
    DropdownInteractiveState.Disabled,
    DropdownInteractiveState.ReadOnly,
).associateWith {
    DropdownOption(
        when (it) {
            is DropdownInteractiveState.Enabled -> "Enabled"
            is DropdownInteractiveState.Warning -> "Warning"
            is DropdownInteractiveState.Error -> "Error"
            is DropdownInteractiveState.Disabled -> "Disabled"
            is DropdownInteractiveState.ReadOnly -> "Read-only"
        }
    )
}

private val dropdownSizes = DropdownSize.entries.toDropdownOptions()

private val layersOptions =
    Layer.entries.associateWith { DropdownOption(it.toString(), enabled = it != Layer.Layer03) }

private val stateSaver = Saver<DropdownInteractiveState, String>(
    save = { it::class.simpleName },
    restore = {
        when (it) {
            "Enabled" -> DropdownInteractiveState.Enabled
            "Warning" -> DropdownInteractiveState.Warning("Warning message goes here")
            "Error" -> DropdownInteractiveState.Error("Error message goes here")
            "Disabled" -> DropdownInteractiveState.Disabled
            "ReadOnly" -> DropdownInteractiveState.ReadOnly
            else -> throw IllegalArgumentException("Unknown state: $it")
        }
    }
)

@Composable
internal fun DropdownDemoScreen(
    variant: DropdownVariant,
    modifier: Modifier = Modifier
) {
    var layer by rememberSaveable { mutableStateOf(Layer.Layer00) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        var dropdownState by rememberSaveable(Unit, stateSaver = stateSaver) {
            mutableStateOf(DropdownInteractiveState.Enabled)
        }

        var dropdownSize by rememberSaveable {
            mutableStateOf(DropdownSize.Large)
        }

        CarbonLayer(layer = layer) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                contentAlignment = Alignment.Center
            ) {
                when (variant) {
                    DropdownVariant.Default -> DefaultDemoDropdown(
                        state = dropdownState,
                        size = dropdownSize
                    )
                    DropdownVariant.Multiselect -> MultiselectDemoDropdown(
                        state = dropdownState,
                        size = dropdownSize
                    )
                }
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
                BasicText(
                    text = "Configuration",
                    style = Carbon.typography.heading02.copy(color = Carbon.theme.textPrimary)
                )

                Dropdown(
                    placeholder = "Choose option",
                    label = "Dropdown state",
                    options = dropdownStates,
                    selectedOption = dropdownState,
                    onOptionSelected = { dropdownState = it },
                )

                dropdownSizes.takeIf { it.size > 1 }?.let { sizes ->
                    Dropdown(
                        placeholder = "Choose option",
                        label = "Dropdown size",
                        options = sizes,
                        selectedOption = dropdownSize,
                        onOptionSelected = { dropdownSize = it },
                    )
                }

                LayerSelectionDropdown(
                    layers = layersOptions,
                    selectedLayer = layer,
                    onLayerSelected = { layer = it },
                    modifier = Modifier.padding(top = SpacingScale.spacing03)
                )
            }
        }
    }
}
