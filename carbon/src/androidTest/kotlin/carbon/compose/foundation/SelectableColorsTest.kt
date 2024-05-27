package carbon.compose.foundation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.junit4.createComposeRule
import carbon.compose.CarbonDesignSystem
import carbon.compose.foundation.color.WhiteTheme
import carbon.compose.foundation.selectable.SelectableColors
import carbon.compose.foundation.selectable.SelectableInteractiveState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SelectableColorsTest : BaseSelectableColorsTest() {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val theme = WhiteTheme
    private var abstractSelectableColors by mutableStateOf<SelectableColors?>(null)

    @Before
    fun setup() {
        composeTestRule.setContent {
            CarbonDesignSystem(
                theme = theme
            ) {
                abstractSelectableColors = object : SelectableColors(theme) {}
            }
        }
    }

    @Test
    fun abstractSelectableColors_static_colorsAreCorrect() {
        assertNotNull(abstractSelectableColors)

        assertEquals(
            expected = theme.textError,
            actual = abstractSelectableColors!!.errorMessageTextColor
        )

        assertEquals(
            expected = theme.textPrimary,
            actual = abstractSelectableColors!!.warningMessageTextColor
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

        interactiveStates.values.forEach { interactiveState ->
            ToggleableState.entries.forEach { toggleableState ->
                assertEquals(
                    expected = expectedColors.getColor(
                        interactiveState = interactiveState,
                        toggleableState = toggleableState
                    ),
                    actual = abstractSelectableColors!!.borderColor(
                        interactiveState = interactiveState,
                        state = toggleableState
                    ),
                    message = "Interactive state: $interactiveState, " +
                        "toggleable state: $toggleableState"
                )
            }
        }
    }

    @Test
    fun abstractSelectableColors_labelColor_colorsAreCorrect() {
        interactiveStates.values.forEach { interactiveState ->
            assertEquals(
                expected = if (interactiveState == SelectableInteractiveState.Disabled) {
                    theme.textDisabled
                } else {
                    theme.textPrimary
                },
                actual = abstractSelectableColors!!.labelColor(interactiveState),
                message = "Interactive state: $interactiveState"
            )
        }
    }
}
