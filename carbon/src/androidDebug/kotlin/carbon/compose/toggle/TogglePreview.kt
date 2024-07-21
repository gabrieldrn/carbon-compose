package carbon.compose.toggle

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import carbon.compose.CarbonDesignSystem

@Preview(showBackground = true)
@Composable
private fun DefaultTogglePreview() {
    CarbonDesignSystem {
        var isToggled by remember { mutableStateOf(false) }
        Toggle(
            isToggled = isToggled,
            onToggleChange = { isToggled = it },
            label = "Label",
            actionText = if (isToggled) "On" else "Off",
//            isEnabled = false,
//            isReadOnly = true,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SmallTogglePreview() {
    CarbonDesignSystem {
        var isToggled by remember { mutableStateOf(false) }
        SmallToggle(
            isToggled = isToggled,
            onToggleChange = { isToggled = it },
            actionText = if (isToggled) "On" else "Off",
//            isEnabled = false,
//            isReadOnly = true,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}
