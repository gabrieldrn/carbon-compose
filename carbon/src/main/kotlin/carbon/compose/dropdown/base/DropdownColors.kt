package carbon.compose.dropdown.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import carbon.compose.Carbon
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.Theme

/**
 * The colors used by the dropdown composable based on the current [theme].
 *
 * @param theme The theme to use for the colors.
 * @param layer Current layer where the dropdown is placed on.
 */
@Immutable
internal class DropdownColors(
    val theme: Theme,
    val layer: Layer
) {

    val checkmarkIconColor = theme.iconPrimary
    val fieldBorderErrorColor = theme.supportError
    val menuOptionBackgroundColor = when (layer) {
        Layer.Layer00 -> theme.layer01
        Layer.Layer01 -> theme.layer02
        else -> theme.layer03
    }
    val menuOptionBorderColor = when (layer) {
        Layer.Layer00 -> theme.borderSubtle01
        Layer.Layer01 -> theme.borderSubtle02
        else -> theme.borderSubtle03
    }

    fun chevronIconColor(
        state: DropdownInteractiveState
    ) = with(theme) {
        if (state == DropdownInteractiveState.Disabled) iconDisabled
        else iconPrimary
    }

    fun fieldBackgroundColor(
        state: DropdownInteractiveState
    ) = with(theme) {
        if (state == DropdownInteractiveState.ReadOnly) Color.Transparent
        else when (layer) {
            Layer.Layer00 -> field01
            Layer.Layer01 -> field02
            else -> field03
        }
    }

    fun fieldBorderColor(
        state: DropdownInteractiveState
    ) = with(theme) {
        when (state) {
            is DropdownInteractiveState.Error -> supportError
            is DropdownInteractiveState.Disabled -> Color.Transparent
            is DropdownInteractiveState.ReadOnly -> when (layer) {
                Layer.Layer00 -> borderSubtle01
                Layer.Layer01 -> borderSubtle02
                else -> borderSubtle03
            }
            else -> when (layer) {
                Layer.Layer00 -> borderStrong01
                Layer.Layer01 -> borderStrong02
                else -> borderStrong03
            }
        }
    }

    fun fieldTextColor(
        state: DropdownInteractiveState
    ) = with(theme) {
        if (state == DropdownInteractiveState.Disabled) textDisabled
        else textPrimary
    }

    fun helperTextColor(
        state: DropdownInteractiveState
    ) = with(theme) {
        if (state is DropdownInteractiveState.Error) textError
        else textPrimary
    }

    fun labelTextColor(
        state: DropdownInteractiveState
    ) = with(theme) {
        if (state == DropdownInteractiveState.Disabled) textDisabled
        else textSecondary
    }

    fun menuOptionBackgroundSelectedColor(
        isSelected: Boolean
    ) = with(theme) {
        if (isSelected) when (layer) {
            Layer.Layer00 -> layerSelected01
            Layer.Layer02 -> layerSelected02
            else -> layerSelected03
        }
        else Color.Transparent
    }

    fun menuOptionTextColor(
        isEnabled: Boolean,
        isSelected: Boolean
    ) = with(theme) {
        when {
            !isEnabled -> textDisabled
            isSelected -> textPrimary
            else -> textSecondary
        }
    }

    companion object {

        @Composable
        public fun colors() = DropdownColors(Carbon.theme, Carbon.layer)
    }
}
