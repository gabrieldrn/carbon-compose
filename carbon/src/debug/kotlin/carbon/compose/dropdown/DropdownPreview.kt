package carbon.compose.dropdown

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
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.base.DropdownOption

internal class DropdownStateParameterProvider : PreviewParameterProvider<DropdownInteractiveState> {
    override val values: Sequence<DropdownInteractiveState>
        get() = sequenceOf(
            DropdownInteractiveState.Enabled,
            DropdownInteractiveState.Warning("Warning message"),
            DropdownInteractiveState.Error("Error message"),
            DropdownInteractiveState.Disabled,
        )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DropdownPreview(
    @PreviewParameter(DropdownStateParameterProvider::class) state: DropdownInteractiveState,
) {
    var expanded by remember { mutableStateOf(false) }

    CarbonDesignSystem {
        Dropdown(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            onDismissRequest = { expanded = false },
            fieldPlaceholderText = state::class.java.simpleName,
            selectedOption = null,
            options = mapOf(0 to DropdownOption("Option 0")),
            onOptionSelected = {},
            state = state,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
    }
}
