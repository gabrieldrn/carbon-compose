package carbon.android.dropdown.multiselect

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
import carbon.android.CarbonDesignSystem
import carbon.android.dropdown.base.DropdownInteractiveState
import carbon.android.dropdown.base.DropdownOption

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
private fun MultiselectDropdownPreview(
    @PreviewParameter(DropdownStateParameterProvider::class) state: DropdownInteractiveState,
) {
    var expanded by remember { mutableStateOf(false) }

    CarbonDesignSystem {
        MultiselectDropdown(
            expanded = expanded,
            placeholder = state::class.java.simpleName,
            options = mapOf(0 to DropdownOption("Option 0")),
            selectedOptions = listOf(0),
            onExpandedChange = { expanded = it },
            onDismissRequest = { expanded = false },
            onOptionClicked = {},
            onClearSelection = {},
            state = state,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
    }
}
