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
