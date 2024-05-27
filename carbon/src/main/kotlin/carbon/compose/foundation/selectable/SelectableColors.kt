package carbon.compose.foundation.selectable

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import carbon.compose.foundation.color.Theme

/**
 * The set of colors used to style a selectable.
 */
@Immutable
@Suppress("LongParameterList")
internal abstract class SelectableColors(val theme: Theme) {

    protected val borderColor = theme.iconPrimary
    protected val borderDisabledColor = theme.iconDisabled
    protected val borderErrorColor = theme.supportError
    val errorMessageTextColor = theme.textError
    val warningMessageTextColor = theme.textPrimary

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

        is SelectableInteractiveState.ReadOnly -> theme.iconDisabled
        is SelectableInteractiveState.Error -> borderErrorColor
    }

    fun labelColor(interactiveState: SelectableInteractiveState): Color =
        if (interactiveState == SelectableInteractiveState.Disabled) {
            theme.textDisabled
        } else {
            theme.textPrimary
        }
}
