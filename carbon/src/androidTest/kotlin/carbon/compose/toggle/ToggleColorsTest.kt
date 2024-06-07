package carbon.compose.toggle

import androidx.compose.ui.graphics.Color
import carbon.compose.BaseColorsTest
import carbon.compose.foundation.color.Layer
import carbon.compose.toggle.domain.ToggleState
import org.junit.Test
import kotlin.test.assertEquals

class ToggleColorsTest : BaseColorsTest() {

    @Test
    fun toggleColors_backgroundColorByState_returnsCorrectColor() {
        val expectedColors = mapOf(
            ToggleState(
                isEnabled = false,
                isReadOnly = false,
                isToggled = false
            ) to theme.buttonDisabled,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = false
            ) to theme.toggleOff,
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = false
            ) to Color.Transparent,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = true
            ) to theme.supportSuccess,
        )

        forAllLayersAndStates(expectedColors.keys) { state, _ ->
            assertEquals(
                expected = expectedColors[state],
                actual = ToggleColors.colors().backgroundColor(state).value,
                message = "For state $state"
            )
        }
    }

    @Test
    fun toggleColors_borderColorByState_returnsCorrectColor() {
        val expectedColors = mapOf(
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = false
            ) to mapOf(
                Layer.Layer00 to theme.borderSubtle01,
                Layer.Layer01 to theme.borderSubtle02,
                Layer.Layer02 to theme.borderSubtle03,
                Layer.Layer03 to theme.borderSubtle03
            ),
            ToggleState(
                isEnabled = false,
                isReadOnly = false,
                isToggled = false
            ) to Color.Transparent,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = false
            ) to Color.Transparent,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = true
            ) to Color.Transparent,
        )

        forAllLayersAndStates(expectedColors.keys) { state, layer ->
            val expected = expectedColors[state]
            assertEquals(
                expected = if (expected is Map<*, *>) {
                    expected[layer]
                } else {
                    expected
                },
                actual = ToggleColors.colors().borderColor(state).value,
                message = "For state $state, layer $layer"
            )
        }
    }

    @Test
    fun toggleColors_handleColorByState_returnsCorrectColor() {
        val expectedColors = mapOf(
            ToggleState(
                isEnabled = false,
                isReadOnly = false,
                isToggled = false
            ) to theme.iconOnColorDisabled,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = false
            ) to theme.iconOnColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = false
            ) to theme.iconPrimary,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = true
            ) to theme.iconOnColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = true
            ) to theme.iconPrimary,
        )

        forAllLayersAndStates(expectedColors.keys) { state, _ ->
            assertEquals(
                expected = expectedColors[state],
                actual = ToggleColors.colors().handleColor(state).value,
                message = "For state $state"
            )
        }
    }

    @Test
    fun toggleColors_handleCheckmarkColorByState_returnsCorrectColor() {
        val expectedColors = mapOf(
            ToggleState(
                isEnabled = false,
                isReadOnly = false,
                isToggled = false
            ) to Color.Transparent,
            ToggleState(
                isEnabled = false,
                isReadOnly = true,
                isToggled = false
            ) to Color.Transparent,
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = false
            ) to Color.Transparent,
            ToggleState(
                isEnabled = false,
                isReadOnly = false,
                isToggled = true
            ) to theme.buttonDisabled,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = true
            ) to theme.supportSuccess,
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = true
            ) to Color.Transparent,
        )

        forAllLayersAndStates(expectedColors.keys) { state, _ ->
            assertEquals(
                expected = expectedColors[state],
                actual = ToggleColors.colors().handleCheckmarkColor(state).value,
                message = "For state $state"
            )
        }
    }
}
