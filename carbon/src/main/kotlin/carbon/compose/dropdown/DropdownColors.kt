package carbon.compose.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import carbon.compose.foundation.color.LocalCarbonTheme

@Immutable
internal data class DropdownColors(
    val labelTextColor: Color,
    val fieldTextColor: Color,
    val fieldTextPromptColor: Color, //?
    val helperTextColor: Color,
    val fieldBackgroundColor: Color,
    val fieldBorderColor: Color,
    val chevronIconColor: Color,
    val menuOptionTextColor: Color,
    val menuOptionTextSelectedColor: Color,
    val menuOptionBackgroundColor: Color,
    val menuOptionBackgroundSelectedColor: Color,
    val menuOptionBorderColor: Color,
    val checkboxIconBackgroundColor: Color,
    val checkboxIconCheckColor: Color,
    val checkboxIconBorderColor: Color,
    val checkmarkIconColor: Color,
) {
    companion object {
        @Composable
        fun colors() = with(LocalCarbonTheme.current) {
            DropdownColors(
                labelTextColor = textSecondary,
                fieldTextColor = textPrimary,
                fieldTextPromptColor = textHelper,
                helperTextColor = textHelper,
                fieldBackgroundColor = field01,
                fieldBorderColor = borderStrong01,
                chevronIconColor = iconPrimary,
                menuOptionTextColor = textSecondary,
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
