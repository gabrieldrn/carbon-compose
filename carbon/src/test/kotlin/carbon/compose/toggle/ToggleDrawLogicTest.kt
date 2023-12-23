package carbon.compose.toggle

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import carbon.compose.foundation.color.WhiteTheme
import carbon.compose.toggle.domain.ToggleColors
import carbon.compose.toggle.domain.ToggleDrawValues
import carbon.compose.toggle.domain.ToggleState
import org.junit.Test
import kotlin.test.assertEquals

class ToggleDrawLogicTest {

    @Test
    fun toggleDrawValues_buildDefaultToggleValues_computeCorrectValues() {
        val density = Density(density = 1.5f)

        val values = ToggleDrawValues.buildValues(
            toggleType = ToggleType.Default,
            density = density
        )

        assertEquals(
            expected = 36f,
            actual = values.toggleHeight
        )
        assertEquals(
            expected = 72f,
            actual = values.toggleWidth
        )
        assertEquals(
            expected = 27f,
            actual = values.handleSize
        )
        assertEquals(
            expected = 9f,
            actual = values.handleCheckmarkOffset.x
        )
        assertEquals(
            expected = 9.75f,
            actual = values.handleCheckmarkOffset.y
        )
        assertEquals(
            expected = 4.5f,
            actual = values.handleYOffPos
        )
        assertEquals(
            expected = 40.5f,
            actual = values.handleXOnPos
        )
    }

    @Test
    fun toggleDrawValues_buildSmallToggleValues_computeCorrectValues() {
        val density = Density(density = 1.5f)

        val values = ToggleDrawValues.buildValues(
            toggleType = ToggleType.Small,
            density = density
        )

        assertEquals(
            expected = 24f,
            actual = values.toggleHeight
        )
        assertEquals(
            expected = 48f,
            actual = values.toggleWidth
        )
        assertEquals(
            expected = 15f,
            actual = values.handleSize
        )
        assertEquals(
            expected = 3f,
            actual = values.handleCheckmarkOffset.x
        )
        assertEquals(
            expected = 3.75f,
            actual = values.handleCheckmarkOffset.y
        )
        assertEquals(
            expected = 4.5f,
            actual = values.handleYOffPos
        )
        assertEquals(
            expected = 28.5f,
            actual = values.handleXOnPos
        )
    }

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
