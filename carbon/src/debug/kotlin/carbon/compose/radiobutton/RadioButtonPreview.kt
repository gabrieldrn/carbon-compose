package carbon.compose.radiobutton

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import carbon.compose.CarbonDesignSystem
import carbon.compose.foundation.selectable.SelectableInteractiveState
import carbon.compose.foundation.spacing.SpacingScale

private class InteractiveStatePreviewParameterProvider :
    PreviewParameterProvider<SelectableInteractiveState> {
    override val values: Sequence<SelectableInteractiveState> =
        SelectableInteractiveState.entries.asSequence()
}

private val interactiveLabelStateMap = mapOf(
    SelectableInteractiveState.Default to "Enabled",
    SelectableInteractiveState.Disabled to "Disabled",
    SelectableInteractiveState.ReadOnly to "Read-only",
    SelectableInteractiveState.Error to "Error",
    SelectableInteractiveState.Warning to "Warning",
)

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
    group = "Unselected state",
    device = "spec:width=1080px,height=2340px,dpi=640",
)
@Composable
private fun RadioButtonOffPreview(
    @PreviewParameter(InteractiveStatePreviewParameterProvider::class)
    interactiveState: SelectableInteractiveState
) {
    CarbonDesignSystem {
        RadioButton(
            selected = false,
            interactiveState = interactiveState,
            label = "${interactiveLabelStateMap[interactiveState]} unselected",
            onClick = {},
            modifier = Modifier.padding(SpacingScale.spacing03),
            errorMessage = "Error message goes here",
            warningMessage = "Warning message goes here",
        )
    }
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
    group = "Selected state",
    device = "spec:width=1080px,height=2340px,dpi=640",
)
@Composable
private fun RadioButtonOnPreview(
    @PreviewParameter(InteractiveStatePreviewParameterProvider::class)
    interactiveState: SelectableInteractiveState
) {
    CarbonDesignSystem {
        RadioButton(
            selected = true,
            interactiveState = interactiveState,
            label = "${interactiveLabelStateMap[interactiveState]} selected",
            onClick = {},
            modifier = Modifier.padding(SpacingScale.spacing03),
            errorMessage = "Error message goes here",
            warningMessage = "Warning message goes here",
        )
    }
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
    group = "Focused state",
    device = "spec:width=1080px,height=2340px,dpi=640",
)
@Composable
private fun RadioButtonFocusPreview() {
    CarbonDesignSystem {
        val focusRequester = FocusRequester()

        SideEffect {
            focusRequester.requestFocus()
        }

        RadioButton(
            selected = false,
            label = "Focused",
            onClick = {},
            modifier = Modifier
                .padding(SpacingScale.spacing03)
                .focusRequester(focusRequester),
            errorMessage = "Error message goes here",
            warningMessage = "Warning message goes here"
        )
    }
}
