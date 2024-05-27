package carbon.compose.checkbox

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.junit4.createComposeRule
import carbon.compose.CarbonDesignSystem
import carbon.compose.foundation.BaseSelectableColorsTest
import carbon.compose.foundation.color.WhiteTheme
import carbon.compose.foundation.selectable.SelectableInteractiveState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class CheckboxColorsTest : BaseSelectableColorsTest() {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val theme = WhiteTheme
    private var checkboxColors by mutableStateOf<CheckboxColors?>(null)

    @Before
    fun setup() {
        composeTestRule.setContent {
            CarbonDesignSystem(theme = theme) {
                checkboxColors = CheckboxColors.colors()
            }
        }
    }

    @Test
    fun checkboxColors_checkmarkColor_colorsAreCorrect() {
        interactiveStates.values.forEach { interactiveState ->
            ToggleableState.entries.forEach { toggleableState ->
                assertEquals(
                    expected = when {
                        toggleableState == ToggleableState.Off -> Color.Transparent
                        interactiveState == SelectableInteractiveState.ReadOnly -> theme.iconPrimary
                        else -> theme.iconInverse
                    },
                    actual = checkboxColors!!.checkmarkColor(interactiveState, toggleableState),
                    message = "Interactive state: $interactiveState, " +
                        "toggleable state: $toggleableState"
                )
            }
        }
    }

    @Test
    fun checkboxColors_backgroundColor_colorsAreCorrect() {
        val expectedColors: Map<SelectableInteractiveState, Any> = mapOf(
            interactiveStates["default"]!! to mapOf(
                ToggleableState.Off to Color.Transparent,
                ToggleableState.On to theme.iconPrimary,
                ToggleableState.Indeterminate to theme.iconPrimary,
            ),
            interactiveStates["disabled"]!! to mapOf(
                ToggleableState.Off to Color.Transparent,
                ToggleableState.On to theme.iconDisabled,
                ToggleableState.Indeterminate to theme.iconDisabled,
            ),
            interactiveStates["readOnly"]!! to Color.Transparent,
            interactiveStates["error"]!! to mapOf(
                ToggleableState.Off to Color.Transparent,
                ToggleableState.On to theme.iconPrimary,
                ToggleableState.Indeterminate to theme.iconPrimary,
            ),
            interactiveStates["warning"]!! to mapOf(
                ToggleableState.Off to Color.Transparent,
                ToggleableState.On to theme.iconPrimary,
                ToggleableState.Indeterminate to theme.iconPrimary,
            ),
        )

        interactiveStates.values.forEach { interactiveState ->
            ToggleableState.entries.forEach { toggleableState ->
                assertEquals(
                    expected = expectedColors.getColor(
                        interactiveState = interactiveState,
                        toggleableState = toggleableState
                    ),
                    actual = checkboxColors!!.backgroundColor(
                        interactiveState = interactiveState,
                        state = toggleableState
                    ),
                    message = "Interactive state: $interactiveState, " +
                        "toggleable state: $toggleableState"
                )
            }
        }
    }
}
