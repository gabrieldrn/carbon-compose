package carbon.compose.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import carbon.compose.foundation.color.LocalCarbonTheme

@Immutable
internal data class DropdownColors(
    val labelTextColor: Color,
    val fieldTextColor: Color,
    val fieldTextDisabledColor: Color,
    val fieldTextPromptColor: Color, //?
    val helperTextColor: Color,
    val helperTextErrorColor: Color,
    val fieldBackgroundColor: Color,
    val fieldBorderColor: Color,
    val fieldBorderErrorColor: Color,
    val fieldBorderDisabledColor: Color,
    val chevronIconColor: Color,
    val chevronIconDisabledColor: Color,
    val menuOptionTextColor: Color,
    val menuOptionTextDisabledColor: Color,
    val menuOptionTextSelectedColor: Color,
    val menuOptionBackgroundColor: Color,
    val menuOptionBackgroundSelectedColor: Color,
    val menuOptionBorderColor: Color,
    val checkboxIconBackgroundColor: Color,
    val checkboxIconCheckColor: Color,
    val checkboxIconBorderColor: Color,
    val checkmarkIconColor: Color,
) {

    fun fieldTextColor(
        state: DropdownInteractiveState
    ) = if (state == DropdownInteractiveState.Disabled) {
        fieldTextDisabledColor
    } else {
        fieldTextColor
    }

    fun helperTextColor(
        state: DropdownInteractiveState
    ) = if (state is DropdownInteractiveState.Error) {
        helperTextErrorColor
    } else {
        helperTextColor
    }

    fun fieldBorderColor(
        state: DropdownInteractiveState
    ) = when (state) {
        is DropdownInteractiveState.Error -> fieldBorderErrorColor
        is DropdownInteractiveState.Disabled -> fieldBorderDisabledColor
        else -> fieldBorderColor
    }

    fun chevronIconColor(
        state: DropdownInteractiveState
    ) = if (state == DropdownInteractiveState.Disabled) {
        chevronIconDisabledColor
    } else {
        chevronIconColor
    }

    companion object {
        @Composable
        fun colors() = with(LocalCarbonTheme.current) {
            DropdownColors(
                labelTextColor = textSecondary,
                fieldTextColor = textPrimary,
                fieldTextDisabledColor = textDisabled,
                fieldTextPromptColor = textHelper,
                helperTextColor = textPrimary,
                helperTextErrorColor = textError,
                fieldBackgroundColor = field01,
                fieldBorderColor = borderStrong01,
                fieldBorderErrorColor = supportError,
                fieldBorderDisabledColor = Color.Transparent,
                chevronIconColor = iconPrimary,
                chevronIconDisabledColor = iconDisabled,
                menuOptionTextColor = textSecondary,
                menuOptionTextDisabledColor = textDisabled,
                menuOptionTextSelectedColor = textPrimary,
                menuOptionBackgroundColor = layer01,
                menuOptionBackgroundSelectedColor = layerSelected01,
                menuOptionBorderColor = borderSubtle00,
                checkboxIconBackgroundColor = iconPrimary,
                checkboxIconCheckColor = iconPrimary,
                checkboxIconBorderColor = iconPrimary,
                checkmarkIconColor = iconPrimary
            )
        }
    }
}
