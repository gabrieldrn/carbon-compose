package carbon.compose.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.selectable.SelectableInteractiveState

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

    val warningMessageTextColor: Color,
) {

    fun borderColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): Color = when (interactiveState) {
        SelectableInteractiveState.Default,
        SelectableInteractiveState.Warning ->
            if (state == ToggleableState.Off) borderColor else Color.Transparent

        SelectableInteractiveState.Disabled ->
            if (state == ToggleableState.Off) borderDisabledColor else Color.Transparent

        SelectableInteractiveState.ReadOnly -> borderReadOnlyColor
        SelectableInteractiveState.Error -> borderErrorColor
    }

    fun backgroundColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): Color = when (interactiveState) {
        SelectableInteractiveState.Default,
        SelectableInteractiveState.Error,
        SelectableInteractiveState.Warning ->
            if (state == ToggleableState.Off) Color.Transparent else backgroundCheckedColor

        SelectableInteractiveState.Disabled ->
            if (state == ToggleableState.Off) Color.Transparent else backgroundDisabledCheckedColor

        SelectableInteractiveState.ReadOnly -> Color.Transparent
    }

    fun checkmarkColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): Color = when {
        state == ToggleableState.Off -> Color.Transparent
        interactiveState == SelectableInteractiveState.ReadOnly -> checkmarkReadOnlyCheckedColor
        else -> checkmarkCheckedColor
    }

    fun labelColor(interactiveState: SelectableInteractiveState): Color =
        if (interactiveState == SelectableInteractiveState.Disabled) {
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

                warningMessageTextColor = textPrimary,
            )
        }
    }
}
