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

package carbon.compose.catalog.misc

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import carbon.compose.dropdown.Dropdown
import carbon.compose.dropdown.base.DropdownOption
import carbon.compose.foundation.color.Layer

private val defaultLayersOptions = Layer.entries.associateWith { DropdownOption(it.toString()) }

@Composable
fun LayerSelectionDropdown(
    selectedLayer: Layer,
    onLayerSelected: (Layer) -> Unit,
    modifier: Modifier = Modifier,
    layers: Map<Layer, DropdownOption> = defaultLayersOptions,
) {
    Dropdown(
        placeholder = "Layer",
        label = "Layer token",
        options = layers,
        selectedOption = selectedLayer,
        onOptionSelected = onLayerSelected,
        modifier = modifier
    )
}
