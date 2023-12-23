package carbon.compose.toggle

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import carbon.compose.foundation.color.Theme

internal data class ToggleState(
    val isEnabled: Boolean,
    val isToggled: Boolean,
    val isReadOnly: Boolean,
)

internal data class ToggleDrawValues(
    val handleSize: Float,
    val toggleHeight: Float,
    val toggleWidth: Float,
    val handleCheckmarkOffset: Offset,
    val handleYOffPos: Float,
    val handleXOnPos: Float,
) {
    fun handleXPos(state: ToggleState) =
        if (state.isToggled) handleXOnPos
        else handleYOffPos

    companion object {
        fun buildValues(toggleType: ToggleType, density: Density) = with(density) {
            val handleSize = toggleType.handleSize.toPx()
            val toggleHeight = toggleType.height.toPx()
            val toggleWidth = toggleType.width.toPx()
            val handleYOffPos = (toggleHeight - handleSize) * .5f
            ToggleDrawValues(
                handleSize = handleSize,
                toggleHeight = toggleHeight,
                toggleWidth = toggleWidth,
                handleCheckmarkOffset = Offset(
                    x = (handleSize - toggleCheckmarkIconWidth.toPx()) * .5f,
                    y = (handleSize - toggleCheckmarkIconHeight.toPx()) * .5f,
                ),
                handleYOffPos = handleYOffPos,
                handleXOnPos = toggleWidth - handleSize - handleYOffPos,
            )
        }
    }
}

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
    val disableHandleCheckmarkColor: Color,

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
        !state.isEnabled -> disableHandleCheckmarkColor
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
            disableHandleCheckmarkColor = theme.buttonDisabled,
            textColor = theme.textPrimary,
            disabledTextColor = theme.textDisabled,
        )
    }
}
