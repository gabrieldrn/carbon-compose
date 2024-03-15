package carbon.compose.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import carbon.compose.CarbonDesignSystem

@Preview
@Composable
private fun DropdownPopupContentPreview(
    @PreviewParameter(DropdownSizeParameterProvider::class) dropdownSize: DropdownSize,
) {
    val options = mapOf(
        0 to "Option 0",
        1 to "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        2 to "Option 2",
        3 to "Option 3",
        4 to "Option 4",
    )
    var selectedOptionKey by remember {
        mutableStateOf<Int?>(2)
    }
    CarbonDesignSystem {
        DropdownPopupContent(
            options = options,
            selectedOption = selectedOptionKey,
            colors = DropdownColors.colors(),
            componentHeight = dropdownSize.height,
            onOptionSelected = { selectedOptionKey = it },
        )
    }
}
