package carbon.compose.catalog.misc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var expanded by remember { mutableStateOf(false) }
    Dropdown(
        expanded = expanded,
        placeholder = "Layer",
        label = "Layer token",
        options = layers,
        selectedOption = selectedLayer,
        onOptionSelected = onLayerSelected,
        onExpandedChange = { expanded = it },
        onDismissRequest = { expanded = false },
        modifier = modifier
    )
}
