package carbon.compose.catalog.dropdown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import carbon.compose.button.Button
import carbon.compose.catalog.R
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.base.DropdownOption
import carbon.compose.dropdown.multiselect.MultiselectDropdown
import carbon.compose.foundation.color.CarbonLayer
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.containerBackground
import carbon.compose.foundation.spacing.SpacingScale

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

@Composable
internal fun MultiselectDropdownScreen(modifier: Modifier = Modifier) {
    var layer by remember { mutableStateOf(Layer.Layer00) }

    CarbonLayer(layer = layer) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .containerBackground()
                .verticalScroll(state = rememberScrollState())
                .padding(SpacingScale.spacing05)
                .padding(WindowInsets.navigationBars.asPaddingValues()),
            verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing05)
        ) {
            DemoDropdown(title = "Dropdown")

            DemoDropdown(
                title = "Warning dropdown",
                state = DropdownInteractiveState.Warning("Warning message goes here"),
            )

            DemoDropdown(
                title = "Error dropdown",
                state = DropdownInteractiveState.Error("Error message goes here"),
            )

            DemoDropdown(
                title = "Disabled dropdown",
                state = DropdownInteractiveState.Disabled,
            )

            DemoDropdown(
                title = "Read-only dropdown",
                state = DropdownInteractiveState.ReadOnly,
            )

            Button(
                label = "Change layer ($layer)",
                iconPainter = painterResource(id = R.drawable.ic_layers),
                onClick = {
                    if (layer == Layer.Layer02) {
                        layer = Layer.Layer00
                    } else {
                        layer = layer.next()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun DemoDropdown(
    title: String,
    modifier: Modifier = Modifier,
    state: DropdownInteractiveState = DropdownInteractiveState.Enabled,
) {
    var selectedOptions by remember {
        mutableStateOf<List<Int>>(
            if (state in arrayOf(
                    DropdownInteractiveState.Disabled,
                    DropdownInteractiveState.ReadOnly
                )
            ) {
                listOf(0)
            } else {
                listOf()
            }
        )
    }
    var expanded by remember { mutableStateOf(false) }

    MultiselectDropdown(
        label = "Multiselect dropdown",
        expanded = expanded,
        placeholder = title,
        selectedOptions = selectedOptions,
        options = dropdownOptions,
        onOptionClicked = {
            selectedOptions = if (selectedOptions.contains(it)) {
                selectedOptions - it
            } else {
                selectedOptions + it
            }
        },
        onClearSelection = { selectedOptions = listOf() },
        onExpandedChange = { expanded = it },
        onDismissRequest = { expanded = false },
        state = state,
        modifier = modifier,
    )
}
