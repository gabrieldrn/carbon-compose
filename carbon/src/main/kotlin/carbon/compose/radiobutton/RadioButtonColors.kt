package carbon.compose.radiobutton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import carbon.compose.Carbon
import carbon.compose.foundation.color.Theme
import carbon.compose.foundation.selectable.SelectableColors
import carbon.compose.foundation.selectable.SelectableInteractiveState

/**
 * The set of colors used to style a [RadioButton].
 */
@Immutable
@Suppress("LongParameterList")
internal class RadioButtonColors private constructor(theme: Theme) : SelectableColors(theme) {

    fun borderColor(
        interactiveState: SelectableInteractiveState,
        selected: Boolean
    ): Color = borderColor(
        interactiveState,
        ToggleableState(selected)
    )

    override fun borderColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): Color = when (interactiveState) {
        is SelectableInteractiveState.Default,
        is SelectableInteractiveState.Warning -> borderColor
        is SelectableInteractiveState.Disabled,
        is SelectableInteractiveState.ReadOnly -> borderDisabledColor
        is SelectableInteractiveState.Error -> borderErrorColor
    }

    fun dotColor(
        interactiveState: SelectableInteractiveState,
        selected: Boolean
    ): Color = when {
        !selected -> Color.Transparent
        interactiveState == SelectableInteractiveState.Disabled -> theme.iconDisabled
        else -> theme.iconPrimary
    }

    internal companion object {

        @Composable
        fun colors(): RadioButtonColors = RadioButtonColors(Carbon.theme)
    }
}
