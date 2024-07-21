package carbon.compose.foundation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import carbon.compose.BaseColorsTest
import carbon.compose.foundation.selectable.SelectableInteractiveState

abstract class BaseSelectableColorsTest : BaseColorsTest() {

    protected val interactiveStates = mapOf(
        "default" to SelectableInteractiveState.Default,
        "disabled" to SelectableInteractiveState.Disabled,
        "readOnly" to SelectableInteractiveState.ReadOnly,
        "error" to SelectableInteractiveState.Error("Error"),
        "warning" to SelectableInteractiveState.Warning("Warning"),
    )

    fun Map<SelectableInteractiveState, Any>.getColor(
        interactiveState: SelectableInteractiveState,
        toggleableState: ToggleableState
    ) = this[interactiveState]!!.let {
        if (it is Map<*, *>) {
            it[toggleableState] as Color
        } else {
            it
        }
    }
}
