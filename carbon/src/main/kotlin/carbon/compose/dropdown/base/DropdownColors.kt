package carbon.compose.dropdown.base

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import carbon.compose.foundation.color.Theme

/**
 * The colors used by the dropdown composable based on the current [theme].
 *
 * @param theme The theme to use for the colors.
 */
@Immutable
internal class DropdownColors(val theme: Theme) {

    val checkmarkIconColor = theme.iconPrimary
    val fieldBorderErrorColor = theme.supportError
    val menuOptionBackgroundColor = theme.layer01
    val menuOptionBorderColor = theme.borderSubtle00

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
        else field01
    }

    fun fieldBorderColor(
        state: DropdownInteractiveState
    ) = with(theme) {
        when (state) {
            is DropdownInteractiveState.Error -> supportError
            is DropdownInteractiveState.Disabled -> Color.Transparent
            is DropdownInteractiveState.ReadOnly -> borderSubtle00
            else -> borderStrong01
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
        if (isSelected) layerSelected01
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
}
