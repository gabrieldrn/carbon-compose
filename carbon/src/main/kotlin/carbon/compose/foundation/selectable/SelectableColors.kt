package carbon.compose.foundation.selectable

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState

/**
 * The set of colors used to style a selectable.
 */
@Immutable
@Suppress("LongParameterList")
internal abstract class SelectableColors(
    val borderColor: Color,
    val borderDisabledColor: Color,
    val borderReadOnlyColor: Color,
    val borderErrorColor: Color,
    val labelColor: Color,
    val labelDisabledColor: Color,
    val errorMessageTextColor: Color,
    val warningMessageTextColor: Color,
) {

    open fun borderColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): Color = when (interactiveState) {
        is SelectableInteractiveState.Default,
        is SelectableInteractiveState.Warning -> if (state == ToggleableState.Off) {
            borderColor
        } else {
            Color.Transparent
        }

        is SelectableInteractiveState.Disabled -> if (state == ToggleableState.Off) {
            borderDisabledColor
        } else {
            Color.Transparent
        }

        is SelectableInteractiveState.ReadOnly -> borderReadOnlyColor
        is SelectableInteractiveState.Error -> borderErrorColor
    }

    fun labelColor(interactiveState: SelectableInteractiveState): Color =
        if (interactiveState == SelectableInteractiveState.Disabled) {
            labelDisabledColor
        } else {
            labelColor
        }
}
