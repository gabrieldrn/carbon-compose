package carbon.compose.radiobutton

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun RadioButtonIconPreview() {
    Row {
        RadioButton(checked = false, onClick = {})
        RadioButton(checked = true, onClick = {}, modifier = Modifier.padding(start = 2.dp))
    }
}
