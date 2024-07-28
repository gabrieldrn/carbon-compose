package carbon.compose.radiobutton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import carbon.compose.Carbon
import carbon.compose.foundation.color.Theme
import carbon.compose.common.selectable.SelectableColors
import carbon.compose.common.selectable.SelectableInteractiveState

/**
 * The set of colors used to style a [RadioButton].
 */
@Immutable
@Suppress("LongParameterList")
internal class RadioButtonColors private constructor(theme: Theme) : SelectableColors(theme) {

    @Composable
    fun borderColor(
        interactiveState: SelectableInteractiveState,
        selected: Boolean
    ): State<Color> =
        borderColor(
            interactiveState,
            ToggleableState(selected)
        )

    @Composable
    override fun borderColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): State<Color> =
        rememberUpdatedState(
            newValue = when (interactiveState) {
                is SelectableInteractiveState.Default,
                is SelectableInteractiveState.Warning -> borderColor
                is SelectableInteractiveState.Disabled,
                is SelectableInteractiveState.ReadOnly -> borderDisabledColor
                is SelectableInteractiveState.Error -> borderErrorColor
            }
        )

    @Composable
    fun dotColor(
        interactiveState: SelectableInteractiveState,
        selected: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = when {
                !selected -> Color.Transparent
                interactiveState == SelectableInteractiveState.Disabled -> theme.iconDisabled
                else -> theme.iconPrimary
            }
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RadioButtonColors) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun hashCode(): Int = super.hashCode()

    internal companion object {

        @Composable
        fun colors(): RadioButtonColors = RadioButtonColors(Carbon.theme)
    }
}
