package carbon.compose.dropdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import carbon.compose.CarbonDesignSystem

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DropdownPreview() {
    val options = (0..5).associateWith { "Option $it" }
    CarbonDesignSystem {
        Box(modifier = Modifier.fillMaxSize()) {
            Dropdown(
                fieldPlaceholderText = "Dropdown",
                optionSelected = null,
                options = options,
                onOptionSelected = {},
                modifier = Modifier.padding(8.dp).width(200.dp),
            )
        }
    }
}
