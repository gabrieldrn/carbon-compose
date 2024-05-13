package carbon.android.toggle

import androidx.compose.ui.graphics.Color
import carbon.android.foundation.color.Theme
import carbon.android.toggle.domain.ToggleState

/**
 * Colors to be used by a [Toggle] based on its state.
 */
internal data class ToggleColors(
    val backgroundColor: Color,
    val toggledBackgroundColor: Color,
    val disabledBackgroundColor: Color,
    val readOnlyBackgroundColor: Color,

    val readOnlyBorderColor: Color,

    val handleColor: Color,
    val disabledHandleColor: Color,
    val readOnlyHandleColor: Color,

    val handleCheckmarkColor: Color,
    val disabledHandleCheckmarkColor: Color,

    val textColor: Color,
    val disabledTextColor: Color,
) {
    fun backgroundColor(state: ToggleState): Color = when {
        !state.isEnabled -> disabledBackgroundColor
        state.isReadOnly -> readOnlyBackgroundColor
        state.isToggled -> toggledBackgroundColor
        else -> backgroundColor
    }

    fun borderColor(state: ToggleState): Color =
        if (state.isReadOnly && state.isEnabled) readOnlyBorderColor
        else Color.Transparent

    fun handleColor(state: ToggleState): Color = when {
        !state.isEnabled -> disabledHandleColor
        state.isReadOnly -> readOnlyHandleColor
        else -> handleColor
    }

    fun handleCheckmarkColor(state: ToggleState): Color = when {
        !state.isToggled || state.isReadOnly -> Color.Transparent
        !state.isEnabled -> disabledHandleCheckmarkColor
        else -> handleCheckmarkColor
    }

    fun textColor(state: ToggleState): Color =
        if (state.isEnabled) textColor
        else disabledTextColor

    companion object {
        fun fromTheme(theme: Theme): ToggleColors = ToggleColors(
            backgroundColor = theme.toggleOff,
            toggledBackgroundColor = theme.supportSuccess,
            disabledBackgroundColor = theme.buttonDisabled,
            readOnlyBackgroundColor = Color.Transparent,
            readOnlyBorderColor = theme.borderSubtle01,
            handleColor = theme.iconOnColor,
            disabledHandleColor = theme.iconOnColorDisabled,
            readOnlyHandleColor = theme.iconPrimary,
            handleCheckmarkColor = theme.supportSuccess,
            disabledHandleCheckmarkColor = theme.buttonDisabled,
            textColor = theme.textPrimary,
            disabledTextColor = theme.textDisabled,
        )
    }
}
