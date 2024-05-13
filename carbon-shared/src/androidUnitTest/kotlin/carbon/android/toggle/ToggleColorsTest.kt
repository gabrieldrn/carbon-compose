package carbon.android.toggle

import androidx.compose.ui.graphics.Color
import carbon.android.foundation.color.WhiteTheme
import carbon.android.toggle.domain.ToggleState
import org.junit.Test
import kotlin.test.assertEquals

class ToggleColorsTest {

    @Test
    fun toggleColors_fromTheme_buildsCorrespondingValues() {
        val theme = WhiteTheme
        val toggleColors = ToggleColors.fromTheme(theme)

        val expected = ToggleColors(
            backgroundColor = theme.toggleOff,
            toggledBackgroundColor = theme.supportSuccess,
            disabledBackgroundColor = theme.buttonDisabled,
            readOnlyBackgroundColor = Color.Transparent,
            readOnlyBorderColor = theme.borderSubtle01,
            handleColor = theme.iconOnColor,
            disabledHandleColor = theme.iconOnColorDisabled,
            readOnlyHandleColor = theme.iconPrimary,
            handleCheckmarkColor = theme.supportSuccess,
            disabledHandleCheckmarkColor = theme.buttonDisabled,
            textColor = theme.textPrimary,
            disabledTextColor = theme.textDisabled,
        )

        assertEquals(
            expected = expected,
            actual = toggleColors
        )
    }

    @Test
    fun toggleColors_backgroundColorByState_returnsCorrectColor() {
        val theme = WhiteTheme
        val toggleColors = ToggleColors.fromTheme(theme)

        val testRuns = mapOf(
            ToggleState(
                isEnabled = false,
                isReadOnly = false,
                isToggled = false
            ) to toggleColors.disabledBackgroundColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = false
            ) to toggleColors.backgroundColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = false
            ) to toggleColors.readOnlyBackgroundColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = true
            ) to toggleColors.toggledBackgroundColor,
        )


        testRuns.forEach { (state, expected) ->
            assertEquals(
                expected = expected,
                actual = toggleColors.backgroundColor(state),
                message = "For state $state"
            )
        }
    }

    @Test
    fun toggleColors_borderColorByState_returnsCorrectColor() {
        val theme = WhiteTheme
        val toggleColors = ToggleColors.fromTheme(theme)

        val testRuns = mapOf(
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = false
            ) to toggleColors.readOnlyBorderColor,
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

        testRuns.forEach { (state, expected) ->
            assertEquals(
                expected = expected,
                actual = toggleColors.borderColor(state),
                message = "For state $state"
            )
        }
    }

    @Test
    fun toggleColors_handleColorByState_returnsCorrectColor() {
        val theme = WhiteTheme
        val toggleColors = ToggleColors.fromTheme(theme)

        val testRuns = mapOf(
            ToggleState(
                isEnabled = false,
                isReadOnly = false,
                isToggled = false
            ) to toggleColors.disabledHandleColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = false
            ) to toggleColors.handleColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = false
            ) to toggleColors.readOnlyHandleColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = true
            ) to toggleColors.handleColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = true
            ) to toggleColors.readOnlyHandleColor,
        )

        testRuns.forEach { (state, expected) ->
            assertEquals(
                expected = expected,
                actual = toggleColors.handleColor(state),
                message = "For state $state"
            )
        }
    }

    @Test
    fun toggleColors_handleCheckmarkColorByState_returnsCorrectColor() {
        val theme = WhiteTheme
        val toggleColors = ToggleColors.fromTheme(theme)

        val testRuns = mapOf(
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
            ) to toggleColors.disabledHandleCheckmarkColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = true
            ) to toggleColors.handleCheckmarkColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = true
            ) to Color.Transparent,
        )

        testRuns.forEach { (state, expected) ->
            assertEquals(
                expected = expected,
                actual = toggleColors.handleCheckmarkColor(state),
                message = "For state $state"
            )
        }
    }
}
