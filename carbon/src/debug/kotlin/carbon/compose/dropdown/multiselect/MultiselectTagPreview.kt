package carbon.compose.dropdown.multiselect

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import carbon.compose.CarbonDesignSystem

@Preview(device = "spec:width=1080px,height=2340px,dpi=640")
@Composable
private fun MultiselectTagPreview() {
    CarbonDesignSystem {
        DropdownMultiselectTag(
            count = 4,
            onCloseTagClick = {}
        )
    }
}
