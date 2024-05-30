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
        helperText = "50 %",
        modifier = Modifier.padding(SpacingScale.spacing03)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun InlinedProgressBarPreview() {
    InlinedProgressBar(
        value = 0.5f,
        labelText = "Loading",
        modifier = Modifier.padding(SpacingScale.spacing03),
    )
}
