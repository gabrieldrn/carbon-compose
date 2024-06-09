package carbon.compose.progressbar

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import carbon.compose.foundation.spacing.SpacingScale

private class ProgressBarStateParameterProvider : PreviewParameterProvider<ProgressBarState> {
    override val values = ProgressBarState.values().asSequence()
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ProgressBarPreview(
    @PreviewParameter(ProgressBarStateParameterProvider::class) state: ProgressBarState
) {
    ProgressBar(
        value = 0.75f,
        labelText = "Loading",
        helperText = when (state) {
            ProgressBarState.Active -> "75%"
            ProgressBarState.Success -> "Done"
            ProgressBarState.Error -> "Error"
        },
        state = state,
        modifier = Modifier.padding(SpacingScale.spacing03)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun InlinedProgressBarPreview(
    @PreviewParameter(ProgressBarStateParameterProvider::class) state: ProgressBarState
) {
    ProgressBar(
        value = 0.75f,
        labelText = "Loading",
        helperText = "",
        inlined = true,
        state = state,
        modifier = Modifier.padding(SpacingScale.spacing03)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun IndeterminateProgressBarPreview() {
    IndeterminateProgressBar(
        labelText = "Loading",
        modifier = Modifier.padding(SpacingScale.spacing03),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun IndeterminateInlinedProgressBarPreview() {
    IndeterminateProgressBar(
        labelText = "Loading",
        inlined = true,
        modifier = Modifier.padding(SpacingScale.spacing03),
    )
}
