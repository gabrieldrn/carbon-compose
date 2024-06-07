package carbon.compose.dropdown.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
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
internal class DropdownColors private constructor(
    private val theme: Theme,
    private val layer: Layer
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

    @Composable
    fun chevronIconColor(state: DropdownInteractiveState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == DropdownInteractiveState.Disabled) iconDisabled
                else iconPrimary
            }
        )

    @Composable
    fun fieldBackgroundColor(state: DropdownInteractiveState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == DropdownInteractiveState.ReadOnly) Color.Transparent
                else when (layer) {
                    Layer.Layer00 -> field01
                    Layer.Layer01 -> field02
                    else -> field03
                }
            }
        )

    @Composable
    fun fieldBorderColor(state: DropdownInteractiveState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
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
        )

    @Composable
    fun fieldTextColor(state: DropdownInteractiveState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == DropdownInteractiveState.Disabled) textDisabled
                else textPrimary
            }
        )

    @Composable
    fun helperTextColor(state: DropdownInteractiveState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state is DropdownInteractiveState.Error) textError
                else textPrimary
            }
        )

    @Composable
    fun labelTextColor(state: DropdownInteractiveState): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (state == DropdownInteractiveState.Disabled) textDisabled
                else textSecondary
            }
        )

    @Composable
    fun menuOptionBackgroundSelectedColor(isSelected: Boolean): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                if (isSelected) when (layer) {
                    Layer.Layer00 -> layerSelected01
                    Layer.Layer01 -> layerSelected02
                    else -> layerSelected03
                }
                else Color.Transparent
            }
        )

    @Composable
    fun menuOptionTextColor(isEnabled: Boolean, isSelected: Boolean): State<Color> =
        rememberUpdatedState(
            newValue = with(theme) {
                when {
                    !isEnabled -> textDisabled
                    isSelected -> textPrimary
                    else -> textSecondary
                }
            }
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DropdownColors

        if (theme != other.theme) return false
        if (layer != other.layer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = theme.hashCode()
        result = 31 * result + layer.hashCode()
        return result
    }

    internal companion object {

        @Composable
        fun colors() = DropdownColors(Carbon.theme, Carbon.layer)
    }
}
