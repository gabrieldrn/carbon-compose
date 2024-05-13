package carbon.android.toggle.domain

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import carbon.android.toggle.Toggle
import carbon.android.toggle.ToggleType
import carbon.android.toggle.toggleCheckmarkIconHeight
import carbon.android.toggle.toggleCheckmarkIconWidth

/**
 * Represents the state of a [Toggle].
 */
internal data class ToggleState(
    val isToggled: Boolean,
    val isEnabled: Boolean,
    val isReadOnly: Boolean,
)

/**
 * Values used to draw a [Toggle].
 */
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
