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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import carbon.compose.Carbon
import carbon.compose.catalog.misc.LayerSelectionDropdown
import carbon.compose.dropdown.Dropdown
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.base.DropdownOption
import carbon.compose.foundation.color.CarbonLayer
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.containerBackground
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography

@Stable
private val dropdownOptions: Map<Int, DropdownOption> = (0..9)
    .associateWith { DropdownOption("Option $it") }
    .toMutableMap()
    .apply {
        set(
            1, DropdownOption(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
                    "nisi ut aliquip ex ea commodo consequat."
            )
        )
        set(2, DropdownOption("Disabled", enabled = false))
    }

private val dropdownStates = listOf(
    DropdownInteractiveState.Enabled,
    DropdownInteractiveState.Warning("Warning message goes here"),
    DropdownInteractiveState.Error("Error message goes here"),
    DropdownInteractiveState.Disabled,
    DropdownInteractiveState.ReadOnly,
).associateWith { DropdownOption(it::class.java.simpleName) }

private val layersOptions =
    Layer.entries.associateWith { DropdownOption(it.toString(), enabled = it != Layer.Layer03) }

@Composable
internal fun DefaultDropdownScreen(modifier: Modifier = Modifier) {
    var layer by rememberSaveable { mutableStateOf(Layer.Layer00) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        var dropdownState by rememberSaveable {
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
                var selectedOption by remember { mutableStateOf<Int?>(null) }

                Dropdown(
                    label = "Dropdown",
                    placeholder = "${dropdownState::class.java.simpleName} dropdown",
                    selectedOption = selectedOption,
                    options = dropdownOptions,
                    onOptionSelected = { selectedOption = it },
                    state = dropdownState,
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
                BasicText(
                    text = "Configuration",
                    style = CarbonTypography.heading02.copy(color = Carbon.theme.textPrimary)
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
