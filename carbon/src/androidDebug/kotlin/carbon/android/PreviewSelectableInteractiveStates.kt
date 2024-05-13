package carbon.android

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import carbon.android.foundation.selectable.SelectableInteractiveState

internal class InteractiveStatePreviewParameterProvider :
    PreviewParameterProvider<SelectableInteractiveState> {
    override val values: Sequence<SelectableInteractiveState> = sequenceOf(
        SelectableInteractiveState.Default,
        SelectableInteractiveState.Disabled,
        SelectableInteractiveState.ReadOnly,
        SelectableInteractiveState.Error("Error message goes here"),
        SelectableInteractiveState.Warning("Warning message goes here")
    )
}

internal fun SelectableInteractiveState.toLabel(): String = when (this) {
    is SelectableInteractiveState.Default -> "Enabled"
    is SelectableInteractiveState.Disabled -> "Disabled"
    is SelectableInteractiveState.ReadOnly -> "Read-only"
    is SelectableInteractiveState.Error -> "Error"
    is SelectableInteractiveState.Warning -> "Warning"
}
