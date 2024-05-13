package carbon.android.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import carbon.android.foundation.color.LocalCarbonTheme
import carbon.android.foundation.selectable.SelectableColors
import carbon.android.foundation.selectable.SelectableInteractiveState

/**
 * The set of colors used to style a [Checkbox].
 */
@Immutable
@Suppress("LongParameterList")
internal class CheckboxColors(
    borderColor: Color,
    borderDisabledColor: Color,
    borderReadOnlyColor: Color,
    borderErrorColor: Color,
    labelColor: Color,
    labelDisabledColor: Color,
    errorMessageTextColor: Color,
    warningMessageTextColor: Color,
    val backgroundCheckedColor: Color,
    val backgroundDisabledCheckedColor: Color,
    val checkmarkCheckedColor: Color,
    val checkmarkReadOnlyCheckedColor: Color,
) : SelectableColors(
    borderColor = borderColor,
    borderDisabledColor = borderDisabledColor,
    borderReadOnlyColor = borderReadOnlyColor,
    borderErrorColor = borderErrorColor,
    labelColor = labelColor,
    labelDisabledColor = labelDisabledColor,
    errorMessageTextColor = errorMessageTextColor,
    warningMessageTextColor = warningMessageTextColor,
) {

    fun checkmarkColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): Color = when {
        state == ToggleableState.Off -> Color.Transparent
        interactiveState == SelectableInteractiveState.ReadOnly -> checkmarkReadOnlyCheckedColor
        else -> checkmarkCheckedColor
    }

    fun backgroundColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): Color = when (interactiveState) {
        is SelectableInteractiveState.Default,
        is SelectableInteractiveState.Error,
        is SelectableInteractiveState.Warning -> if (state == ToggleableState.Off) {
            Color.Transparent
        } else {
            backgroundCheckedColor
        }

        is SelectableInteractiveState.Disabled -> if (state == ToggleableState.Off) {
            Color.Transparent
        } else {
            backgroundDisabledCheckedColor
        }

        is SelectableInteractiveState.ReadOnly -> Color.Transparent
    }

    internal companion object {

        @Composable
        fun colors(): CheckboxColors = with(LocalCarbonTheme.current) {
            CheckboxColors(
                borderColor = iconPrimary,
                borderDisabledColor = iconDisabled,
                borderReadOnlyColor = iconDisabled,
                borderErrorColor = supportError,
                labelColor = textPrimary,
                labelDisabledColor = textDisabled,
                errorMessageTextColor = textError,
                warningMessageTextColor = textPrimary,
                backgroundCheckedColor = iconPrimary,
                backgroundDisabledCheckedColor = iconDisabled,
                checkmarkCheckedColor = iconInverse,
                checkmarkReadOnlyCheckedColor = iconPrimary
            )
        }
    }
}
