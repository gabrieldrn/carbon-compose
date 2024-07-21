package carbon.compose.dropdown.multiselect

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import carbon.compose.CarbonDesignSystem
import carbon.compose.dropdown.base.DropdownInteractiveState

@Preview(device = "spec:width=1080px,height=2340px,dpi=640")
@Composable
private fun MultiselectTagPreview() {
    CarbonDesignSystem {
        DropdownMultiselectTag(
            state = DropdownInteractiveState.Enabled,
            count = 42,
            onCloseTagClick = {}
        )
    }
}

@Preview(device = "spec:width=1080px,height=2340px,dpi=640")
@Composable
private fun MultiselectTagReadOnlyPreview() {
    CarbonDesignSystem {
        DropdownMultiselectTag(
            state = DropdownInteractiveState.ReadOnly,
            count = 42,
            onCloseTagClick = {}
        )
    }
}
