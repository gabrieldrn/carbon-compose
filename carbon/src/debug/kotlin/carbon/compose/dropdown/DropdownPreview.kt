package carbon.compose.dropdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DropdownPreview(
    @PreviewParameter(DropdownSizeParameterProvider::class) dropdownSize: DropdownSize,
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
                dropdownSize = dropdownSize,
                modifier = Modifier
                    .padding(8.dp)
                    .width(200.dp),
            )
        }
    }
}
