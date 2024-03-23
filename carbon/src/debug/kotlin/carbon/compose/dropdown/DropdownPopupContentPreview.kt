package carbon.compose.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import carbon.compose.CarbonDesignSystem

@Preview
@Composable
private fun DropdownPopupContentPreview(
    @PreviewParameter(DropdownStateParameterProvider::class) dropdownSize: DropdownSize,
) {
    val options: Map<Int, DropdownOption> = (0..4)
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

    CarbonDesignSystem {
        DropdownPopupContent(
            options = options,
            selectedOption = 1,
            colors = DropdownColors.colors(),
            componentHeight = dropdownSize.height,
            onOptionSelected = {},
        )
    }
}
