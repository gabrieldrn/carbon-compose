package carbon.compose.catalog.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import carbon.compose.dropdown.Dropdown
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.base.DropdownOption
import carbon.compose.dropdown.multiselect.MultiselectDropdown

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
fun DefaultDemoDropdown(
    state: DropdownInteractiveState,
    modifier: Modifier = Modifier,
) {
    var selectedOption by remember { mutableStateOf<Int?>(null) }

    Dropdown(
        label = "Dropdown",
        placeholder = "Choose option",
        selectedOption = selectedOption,
        options = dropdownOptions,
        onOptionSelected = { selectedOption = it },
        state = state,
        modifier = modifier,
    )
}

@Composable
fun MultiselectDemoDropdown(
    state: DropdownInteractiveState,
    modifier: Modifier = Modifier,
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
    val placeholder by remember(selectedOptions) {
        mutableStateOf(
            when (selectedOptions.size) {
                0 -> "Choose options"
                1 -> "Option selected"
                else -> "Options selected"
            }
        )
    }

    MultiselectDropdown(
        label = "Multiselect dropdown",
        placeholder = placeholder,
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
        state = state,
        modifier = modifier,
    )
}
