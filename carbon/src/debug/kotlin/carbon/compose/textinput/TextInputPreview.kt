package carbon.compose.textinput

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import carbon.compose.CarbonDesignSystem
import carbon.compose.foundation.spacing.SpacingScale

@Preview
@Composable
private fun TextInputPreview() {
    CarbonDesignSystem {
        TextInput(
            label = "Label",
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(SpacingScale.spacing03),
            placeholderText = "Placeholder"
        )
    }
}
