package carbon.compose.uishell

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import carbon.compose.CarbonDesignSystem

@Preview
@Composable
private fun UiShellHeaderPreview() {
    CarbonDesignSystem {
        UiShellHeader(
            headerName = "Carbon Design System",
        )
    }
}
