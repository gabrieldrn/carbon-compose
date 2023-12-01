package carbon.compose.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import carbon.compose.foundation.color.LocalCarbonTheme

/**
 * The set of colors used to style a [Checkbox].
 */
@Immutable
@Suppress("LongParameterList")
internal class CheckboxColors(
    val borderColor: Color,
    val borderDisabledColor: Color,
    val borderReadOnlyColor: Color,
    val borderErrorColor: Color,

    val backgroundCheckedColor: Color,
    val backgroundDisabledCheckedColor: Color,

    val checkmarkCheckedColor: Color,
    val checkmarkReadOnlyCheckedColor: Color,

    val labelColor: Color,
    val labelDisabledColor: Color,

    val errorMessageTextColor: Color,
    val errorIconColor: Color,

    val warningMessageTextColor: Color,
    val warningIconColor: Color,
    val warningIconInnerFillColor: Color,
) {

    fun borderColor(
        interactiveState: CheckboxInteractiveState,
        state: ToggleableState
    ): Color = when (interactiveState) {
        CheckboxInteractiveState.Default,
        CheckboxInteractiveState.Warning ->
            if (state == ToggleableState.Off) borderColor else Color.Transparent

        CheckboxInteractiveState.Disabled ->
            if (state == ToggleableState.Off) borderDisabledColor else Color.Transparent

        CheckboxInteractiveState.ReadOnly -> borderReadOnlyColor
        CheckboxInteractiveState.Error -> borderErrorColor
    }

    fun backgroundColor(
        interactiveState: CheckboxInteractiveState,
        state: ToggleableState
    ): Color = when (interactiveState) {
        CheckboxInteractiveState.Default,
        CheckboxInteractiveState.Error,
        CheckboxInteractiveState.Warning ->
            if (state == ToggleableState.Off) Color.Transparent else backgroundCheckedColor

        CheckboxInteractiveState.Disabled ->
            if (state == ToggleableState.Off) Color.Transparent else backgroundDisabledCheckedColor

        CheckboxInteractiveState.ReadOnly -> Color.Transparent
    }

    fun checkmarkColor(
        interactiveState: CheckboxInteractiveState,
        state: ToggleableState
    ): Color = when {
        state == ToggleableState.Off -> Color.Transparent
        interactiveState == CheckboxInteractiveState.ReadOnly -> checkmarkReadOnlyCheckedColor
        else -> checkmarkCheckedColor
    }

    fun labelColor(interactiveState: CheckboxInteractiveState): Color =
        if (interactiveState == CheckboxInteractiveState.Disabled) {
            labelDisabledColor
        } else {
            labelColor
        }

    internal companion object {

        @Composable
        fun colors(): CheckboxColors = with(LocalCarbonTheme.current) {
            CheckboxColors(
                borderColor = iconPrimary,
                borderDisabledColor = iconDisabled,
                borderReadOnlyColor = iconDisabled,
                borderErrorColor = supportError,

                backgroundCheckedColor = iconPrimary,
                backgroundDisabledCheckedColor = iconDisabled,

                checkmarkCheckedColor = iconInverse,
                checkmarkReadOnlyCheckedColor = iconPrimary,

                labelColor = textPrimary,
                labelDisabledColor = textDisabled,

                errorMessageTextColor = textError,
                errorIconColor = textError,

                warningMessageTextColor = textPrimary,
                warningIconColor = supportWarning,
                warningIconInnerFillColor = Color.Black,
            )
        }
    }
}
