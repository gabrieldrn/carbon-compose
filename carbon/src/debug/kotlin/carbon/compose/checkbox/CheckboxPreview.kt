package carbon.compose.checkbox

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import carbon.compose.CarbonDesignSystem
import carbon.compose.foundation.spacing.SpacingScale

private class ButtonPreviewParameterProvider : PreviewParameterProvider<CheckboxInteractiveState> {
    override val values: Sequence<CheckboxInteractiveState> =
        CheckboxInteractiveState.entries.asSequence()
}

private val interactiveLabelStateMap = mapOf(
    CheckboxInteractiveState.Default to "Enabled",
    CheckboxInteractiveState.Disabled to "Disabled",
    CheckboxInteractiveState.ReadOnly to "Read-only",
    CheckboxInteractiveState.Error to "Error",
    CheckboxInteractiveState.Warning to "Warning disabled",
)

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
    group = "Unselected state",
)
@Composable
private fun CheckboxOffPreview(
    @PreviewParameter(ButtonPreviewParameterProvider::class)
    interactiveState: CheckboxInteractiveState
) {
    CarbonDesignSystem {
        Checkbox(
            state = ToggleableState.Off,
            interactiveState = interactiveState,
            label = "${interactiveLabelStateMap[interactiveState]} unselected",
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(SpacingScale.spacing03)
        )
    }
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
    group = "Selected state",
)
@Composable
private fun CheckboxOnPreview(
    @PreviewParameter(ButtonPreviewParameterProvider::class)
    interactiveState: CheckboxInteractiveState
) {
    CarbonDesignSystem {
        Checkbox(
            state = ToggleableState.On,
            interactiveState = interactiveState,
            label = "${interactiveLabelStateMap[interactiveState]} selected",
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(SpacingScale.spacing03)
        )
    }
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
    group = "Indeterminate state",
)
@Composable
private fun CheckboxIndeterminatePreview(
    @PreviewParameter(ButtonPreviewParameterProvider::class)
    interactiveState: CheckboxInteractiveState
) {
    CarbonDesignSystem {
        Checkbox(
            state = ToggleableState.Indeterminate,
            interactiveState = interactiveState,
            label = "${interactiveLabelStateMap[interactiveState]} indeterminate",
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(SpacingScale.spacing03)
        )
    }
}
