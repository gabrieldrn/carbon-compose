package carbon.compose.foundation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import carbon.compose.foundation.selectable.SelectableColors
import carbon.compose.foundation.selectable.SelectableInteractiveState
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SelectableColorsTest : BaseSelectableColorsTest() {

    @Test
    fun abstractSelectableColors_static_colorsAreCorrect() {
        val colors = object : SelectableColors(theme) {}
        assertNotNull(colors)

        assertEquals(
            expected = theme.textError,
            actual = colors.errorMessageTextColor
        )

        assertEquals(
            expected = theme.textPrimary,
            actual = colors.warningMessageTextColor
        )
    }

    @Test
    fun abstractSelectableColors_borderColor_colorsAreCorrect() {
        val expectedColors: Map<SelectableInteractiveState, Any> = mapOf(
            interactiveStates["default"]!! to mapOf(
                ToggleableState.Off to theme.iconPrimary,
                ToggleableState.On to Color.Transparent,
                ToggleableState.Indeterminate to Color.Transparent,
            ),
            interactiveStates["warning"]!! to mapOf(
                ToggleableState.Off to theme.iconPrimary,
                ToggleableState.On to Color.Transparent,
                ToggleableState.Indeterminate to Color.Transparent,
            ),
            interactiveStates["disabled"]!! to mapOf(
                ToggleableState.Off to theme.iconDisabled,
                ToggleableState.On to Color.Transparent,
                ToggleableState.Indeterminate to Color.Transparent,
            ),
            interactiveStates["readOnly"]!! to theme.iconDisabled,
            interactiveStates["error"]!! to theme.supportError,
        )

        forAllLayersAndStates(
            interactiveStates.values,
            ToggleableState.entries
        ) { interactiveState, toggleableState, _ ->

            assertEquals(
                expected = expectedColors.getColor(
                    interactiveState = interactiveState,
                    toggleableState = toggleableState
                ),
                actual = object : SelectableColors(theme) {}
                    .borderColor(
                        interactiveState = interactiveState,
                        state = toggleableState
                    )
                    .value,
                message = "Interactive state: $interactiveState, " +
                    "toggleable state: $toggleableState"
            )
        }
    }

    @Test
    fun abstractSelectableColors_labelColor_colorsAreCorrect() {
        forAllLayersAndStates(interactiveStates.values) { interactiveState, _ ->
            assertEquals(
                expected = if (interactiveState == SelectableInteractiveState.Disabled) {
                    theme.textDisabled
                } else {
                    theme.textPrimary
                },
                actual = object : SelectableColors(theme) {}
                    .labelColor(interactiveState)
                    .value,
                message = "Interactive state: $interactiveState"
            )
        }
    }
}
