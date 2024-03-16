package carbon.compose.dropdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import carbon.compose.CarbonDesignSystem

internal class DropdownSizeParameterProvider : PreviewParameterProvider<DropdownSize> {
    override val values: Sequence<DropdownSize>
        get() = DropdownSize.entries.asSequence()
}

@Composable
private fun BaseDropdownPreview(
    dropdownSize: DropdownSize,
    dropdownInteractiveState: DropdownInteractiveState,
) {
    val options: Map<Int, DropdownOption> = (0..9)
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

    var expanded by remember { mutableStateOf(false) }

    CarbonDesignSystem {
        Box(modifier = Modifier.fillMaxSize()) {
            Dropdown(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                onDismissRequest = { expanded = false },
                fieldPlaceholderText = "Dropdown",
                selectedOption = null,
                options = options,
                onOptionSelected = {},
                state = dropdownInteractiveState,
                dropdownSize = dropdownSize,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, group = "Dropdown - active")
@Composable
private fun DropdownPreview(
    @PreviewParameter(DropdownSizeParameterProvider::class) dropdownSize: DropdownSize,
) {
    BaseDropdownPreview(
        dropdownSize = dropdownSize,
        dropdownInteractiveState = DropdownInteractiveState.Enabled
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, group = "Dropdown - warning")
@Composable
private fun DropdownWarningPreview(
    @PreviewParameter(DropdownSizeParameterProvider::class) dropdownSize: DropdownSize,
) {
    BaseDropdownPreview(
        dropdownSize = dropdownSize,
        dropdownInteractiveState = DropdownInteractiveState.Warning("Warning message goes here")
    )
}
