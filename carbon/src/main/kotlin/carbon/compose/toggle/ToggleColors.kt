package carbon.compose.toggle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import carbon.compose.Carbon
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.Theme
import carbon.compose.toggle.domain.ToggleState

/**
 * Colors to be used by a [Toggle] based on its state.
 */
@Immutable
internal data class ToggleColors private constructor(
    val theme: Theme,
    val layer: Layer
) {

    @Composable
    fun backgroundColor(state: ToggleState): State<Color> =
        rememberUpdatedState(
            newValue = when {
                !state.isEnabled -> theme.buttonDisabled
                state.isReadOnly -> Color.Transparent
                state.isToggled -> theme.supportSuccess
                else -> theme.toggleOff
            }
        )

    @Composable
    fun borderColor(state: ToggleState): State<Color> =
        rememberUpdatedState(
            newValue = if (state.isReadOnly && state.isEnabled) {
                when (layer) {
                    Layer.Layer00 -> theme.borderSubtle01
                    Layer.Layer01 -> theme.borderSubtle02
                    else -> theme.borderSubtle03
                }
            } else {
                Color.Transparent
            }
        )

    @Composable
    fun handleColor(state: ToggleState): State<Color> =
        rememberUpdatedState(
            newValue = when {
                !state.isEnabled -> theme.iconOnColorDisabled
                state.isReadOnly -> theme.iconPrimary
                else -> theme.iconOnColor
            }
        )

    @Composable
    fun handleCheckmarkColor(state: ToggleState): State<Color> =
        rememberUpdatedState(
            newValue = when {
                !state.isToggled || state.isReadOnly -> Color.Transparent
                !state.isEnabled -> theme.buttonDisabled
                else -> theme.supportSuccess
            }
        )

    @Composable
    fun textColor(state: ToggleState): State<Color> =
        rememberUpdatedState(
            newValue = if (state.isEnabled) theme.textPrimary else theme.textDisabled
        )

    companion object {

        @Composable
        fun colors() = ToggleColors(Carbon.theme, Carbon.layer)
    }
}
