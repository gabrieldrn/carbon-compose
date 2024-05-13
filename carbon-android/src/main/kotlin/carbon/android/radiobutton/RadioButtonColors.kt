package carbon.android.radiobutton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import carbon.android.foundation.color.LocalCarbonTheme
import carbon.android.foundation.selectable.SelectableColors
import carbon.android.foundation.selectable.SelectableInteractiveState

/**
 * The set of colors used to style a [RadioButton].
 */
@Immutable
@Suppress("LongParameterList")
internal class RadioButtonColors(
    borderColor: Color,
    borderDisabledColor: Color,
    borderReadOnlyColor: Color,
    borderErrorColor: Color,
    labelColor: Color,
    labelDisabledColor: Color,
    errorMessageTextColor: Color,
    warningMessageTextColor: Color,
    val buttonDotColor: Color,
    val buttonDotDisabledColor: Color,
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

    fun borderColor(
        interactiveState: SelectableInteractiveState,
        selected: Boolean
    ): Color = borderColor(
        interactiveState,
        ToggleableState(selected)
    )

    override fun borderColor(
        interactiveState: SelectableInteractiveState,
        state: ToggleableState
    ): Color = when (interactiveState) {
        is SelectableInteractiveState.Default,
        is SelectableInteractiveState.Warning -> borderColor
        is SelectableInteractiveState.Disabled,
        is SelectableInteractiveState.ReadOnly -> borderDisabledColor
        is SelectableInteractiveState.Error -> borderErrorColor
    }

    fun dotColor(
        interactiveState: SelectableInteractiveState,
        selected: Boolean
    ): Color = when {
        !selected -> Color.Transparent
        interactiveState == SelectableInteractiveState.Disabled -> buttonDotDisabledColor
        else -> buttonDotColor
    }

    internal companion object {

        @Composable
        fun colors(): RadioButtonColors = with(LocalCarbonTheme.current) {
            RadioButtonColors(
                borderColor = iconPrimary,
                borderDisabledColor = iconDisabled,
                borderReadOnlyColor = iconDisabled,
                borderErrorColor = supportError,
                labelColor = textPrimary,
                labelDisabledColor = textDisabled,
                errorMessageTextColor = textError,
                warningMessageTextColor = textPrimary,
                buttonDotColor = iconPrimary,
                buttonDotDisabledColor = iconDisabled,
            )
        }
    }
}
