package carbon.compose.toggle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import carbon.compose.CarbonDesignSystem
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.WhiteTheme
import carbon.compose.toggle.domain.ToggleState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ToggleColorsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val theme = WhiteTheme
    private var layer by mutableStateOf<Layer>(Layer.Layer00)
    private var toggleColors by mutableStateOf<ToggleColors?>(null)

    @Before
    fun setup() {
        composeTestRule.setContent {
            CarbonDesignSystem(
                theme = theme,
                layer = layer
            ) {
                toggleColors = ToggleColors.colors()
            }
        }
    }

    @Test
    fun toggleColors_static_colorsAreCorrect() {
        assertNotNull(toggleColors)

        assertEquals(
            expected = theme.toggleOff,
            actual = toggleColors!!.backgroundColor
        )

        assertEquals(
            expected = theme.supportSuccess,
            actual = toggleColors!!.toggledBackgroundColor
        )

        assertEquals(
            expected = theme.buttonDisabled,
            actual = toggleColors!!.disabledBackgroundColor
        )

        assertEquals(
            expected = Color.Transparent,
            actual = toggleColors!!.readOnlyBackgroundColor
        )

        assertEquals(
            expected = theme.iconOnColor,
            actual = toggleColors!!.handleColor
        )

        assertEquals(
            expected = theme.iconOnColorDisabled,
            actual = toggleColors!!.disabledHandleColor
        )

        assertEquals(
            expected = theme.iconPrimary,
            actual = toggleColors!!.readOnlyHandleColor
        )

        assertEquals(
            expected = theme.supportSuccess,
            actual = toggleColors!!.handleCheckmarkColor
        )

        assertEquals(
            expected = theme.buttonDisabled,
            actual = toggleColors!!.disabledHandleCheckmarkColor
        )

        assertEquals(
            expected = theme.textPrimary,
            actual = toggleColors!!.textColor
        )

        assertEquals(
            expected = theme.textDisabled,
            actual = toggleColors!!.disabledTextColor
        )
    }

    @Test
    fun toggleColors_backgroundColorByState_returnsCorrectColor() {
        val testRuns = mapOf(
            ToggleState(
                isEnabled = false,
                isReadOnly = false,
                isToggled = false
            ) to toggleColors!!.disabledBackgroundColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = false
            ) to toggleColors!!.backgroundColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = false
            ) to toggleColors!!.readOnlyBackgroundColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = true
            ) to toggleColors!!.toggledBackgroundColor,
        )

        testRuns.forEach { (state, expected) ->
            assertEquals(
                expected = expected,
                actual = toggleColors!!.backgroundColor(state),
                message = "For state $state"
            )
        }
    }

    @Test
    fun toggleColors_borderColorByState_returnsCorrectColor() {
        val testRuns = mapOf(
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

        testRuns.forEach { (state, expected) ->
            Layer.entries.forEach { layer ->
                this.layer = layer
                composeTestRule.waitForIdle()

                assertEquals(
                    expected = if (expected is Map<*, *>) {
                        expected[layer]
                    } else {
                        expected
                    },
                    actual = toggleColors!!.borderColor(state),
                    message = "For state $state, layer $layer"
                )
            }
        }
    }

    @Test
    fun toggleColors_handleColorByState_returnsCorrectColor() {
        val testRuns = mapOf(
            ToggleState(
                isEnabled = false,
                isReadOnly = false,
                isToggled = false
            ) to toggleColors!!.disabledHandleColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = false
            ) to toggleColors!!.handleColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = false
            ) to toggleColors!!.readOnlyHandleColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = true
            ) to toggleColors!!.handleColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = true
            ) to toggleColors!!.readOnlyHandleColor,
        )

        testRuns.forEach { (state, expected) ->
            assertEquals(
                expected = expected,
                actual = toggleColors!!.handleColor(state),
                message = "For state $state"
            )
        }
    }

    @Test
    fun toggleColors_handleCheckmarkColorByState_returnsCorrectColor() {
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
            ) to toggleColors!!.disabledHandleCheckmarkColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = false,
                isToggled = true
            ) to toggleColors!!.handleCheckmarkColor,
            ToggleState(
                isEnabled = true,
                isReadOnly = true,
                isToggled = true
            ) to Color.Transparent,
        )

        testRuns.forEach { (state, expected) ->
            assertEquals(
                expected = expected,
                actual = toggleColors!!.handleCheckmarkColor(state),
                message = "For state $state"
            )
        }
    }
}
