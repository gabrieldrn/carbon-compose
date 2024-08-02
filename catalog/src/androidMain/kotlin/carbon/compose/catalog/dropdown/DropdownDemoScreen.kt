package carbon.compose.catalog.dropdown

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
import carbon.compose.Carbon
import carbon.compose.catalog.misc.LayerSelectionDropdown
import carbon.compose.dropdown.Dropdown
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.base.DropdownInteractiveState.Disabled
import carbon.compose.dropdown.base.DropdownInteractiveState.Enabled
import carbon.compose.dropdown.base.DropdownInteractiveState.Error
import carbon.compose.dropdown.base.DropdownInteractiveState.ReadOnly
import carbon.compose.dropdown.base.DropdownInteractiveState.Warning
import carbon.compose.dropdown.base.DropdownOption
import carbon.compose.foundation.color.CarbonLayer
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.containerBackground
import carbon.compose.foundation.spacing.SpacingScale

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

private val layersOptions =
    Layer.entries.associateWith { DropdownOption(it.toString(), enabled = it != Layer.Layer03) }

private val stateSaver = Saver<DropdownInteractiveState, String>(
    save = { it::class.simpleName },
    restore = {
        when (it) {
            "Enabled" -> Enabled
            "Warning" -> Warning("Warning message goes here")
            "Error" -> Error("Error message goes here")
            "Disabled" -> Disabled
            "ReadOnly" -> ReadOnly
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
            mutableStateOf<DropdownInteractiveState>(DropdownInteractiveState.Enabled)
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
                    DropdownVariant.Default -> DefaultDemoDropdown(state = dropdownState)
                    DropdownVariant.Multiselect -> MultiselectDemoDropdown(state = dropdownState)
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
