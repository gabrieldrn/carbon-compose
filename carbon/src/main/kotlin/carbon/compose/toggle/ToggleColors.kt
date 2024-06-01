package carbon.compose.toggle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import carbon.compose.Carbon
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.Theme

/**
 * Colors to be used by a [Toggle] based on its state.
 */
@Immutable
internal data class ToggleColors private constructor(
    val theme: Theme,
    val layer: Layer
) {

    @Composable
    fun backgroundColor(
        isEnabled: Boolean,
        isReadOnly: Boolean,
        isToggled: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = when {
                !isEnabled -> theme.buttonDisabled
                isReadOnly -> Color.Transparent
                isToggled -> theme.supportSuccess
                else -> theme.toggleOff
            }
        )

    @Composable
    fun borderColor(
        isEnabled: Boolean,
        isReadOnly: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = if (isReadOnly && isEnabled) {
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
    fun handleColor(
        isEnabled: Boolean,
        isReadOnly: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = when {
                !isEnabled -> theme.iconOnColorDisabled
                isReadOnly -> theme.iconPrimary
                else -> theme.iconOnColor
            }
        )

    @Composable
    fun handleCheckmarkColor(
        isEnabled: Boolean,
        isReadOnly: Boolean,
        isToggled: Boolean
    ): State<Color> =
        rememberUpdatedState(
            newValue = when {
                !isToggled || isReadOnly -> Color.Transparent
                !isEnabled -> theme.buttonDisabled
                else -> theme.supportSuccess
            }
        )

    @Composable
    fun textColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(
            newValue = if (isEnabled) theme.textPrimary else theme.textDisabled
        )

    companion object {

        @Composable
        fun colors() = ToggleColors(Carbon.theme, Carbon.layer)
    }
}