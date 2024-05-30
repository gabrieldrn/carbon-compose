package carbon.compose.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import carbon.compose.Carbon
import carbon.compose.foundation.color.Theme
import carbon.compose.foundation.selectable.SelectableColors
import carbon.compose.foundation.selectable.SelectableInteractiveState

/**
 * The set of colors used to style a [Checkbox].
 */
@Immutable
@Suppress("LongParameterList")
internal class CheckboxColors private constructor(theme: Theme) : SelectableColors(theme) {

    fun checkmarkColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): Color = when {
        state == ToggleableState.Off -> Color.Transparent
        interactiveState == SelectableInteractiveState.ReadOnly -> theme.iconPrimary
        else -> theme.iconInverse
    }

    fun backgroundColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): Color = when (interactiveState) {
        is SelectableInteractiveState.Default,
        is SelectableInteractiveState.Error,
        is SelectableInteractiveState.Warning -> if (state == ToggleableState.Off) {
            Color.Transparent
        } else {
            theme.iconPrimary
        }

        is SelectableInteractiveState.Disabled -> if (state == ToggleableState.Off) {
            Color.Transparent
        } else {
            theme.iconDisabled
        }

        is SelectableInteractiveState.ReadOnly -> Color.Transparent
    }

    internal companion object {

        @Composable
        fun colors(): CheckboxColors = CheckboxColors(Carbon.theme)
    }
}
