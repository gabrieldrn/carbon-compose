package carbon.compose.progressbar

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import carbon.compose.foundation.spacing.SpacingScale

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ProgressBarPreview() {
    ProgressBar(
        value = 0.5f,
        labelText = "Loading",
        helperText = "Please wait",
        modifier = Modifier.padding(SpacingScale.spacing03)
    )
}
