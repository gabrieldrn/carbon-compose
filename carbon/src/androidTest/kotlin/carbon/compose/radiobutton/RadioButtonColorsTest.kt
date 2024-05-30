package carbon.compose.radiobutton

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

class RadioButtonColorsTest : BaseSelectableColorsTest() {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val theme = WhiteTheme
    private var radioButtonColors by mutableStateOf<RadioButtonColors?>(null)

    @Before
    fun setup() {
        composeTestRule.setContent {
            CarbonDesignSystem(theme = theme) {
                radioButtonColors = RadioButtonColors.colors()
            }
        }
    }

    @Test
    fun radioButtonColors_borderColor_colorsAreCorrect() {
        val expectedColors: Map<SelectableInteractiveState, Any> = mapOf(
            interactiveStates["default"]!! to theme.iconPrimary,
            interactiveStates["warning"]!! to theme.iconPrimary,
            interactiveStates["disabled"]!! to theme.iconDisabled,
            interactiveStates["readOnly"]!! to theme.iconDisabled,
            interactiveStates["error"]!! to theme.supportError,
        )

        interactiveStates.values.forEach { interactiveState ->
            listOf(ToggleableState.On, ToggleableState.Off).forEach { toggleableState ->
                assertEquals(
                    expected = expectedColors.getColor(
                        interactiveState = interactiveState,
                        toggleableState = toggleableState
                    ),
                    actual = radioButtonColors!!.borderColor(
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
    fun checkboxColors_dotColor_colorsAreCorrect() {
        interactiveStates.values.forEach { interactiveState ->
            listOf(true, false).forEach { isSelected ->
                assertEquals(
                    expected = when {
                        !isSelected -> Color.Transparent
                        interactiveState == SelectableInteractiveState.Disabled ->
                            theme.iconDisabled
                        else -> theme.iconPrimary
                    },
                    actual = radioButtonColors!!.dotColor(interactiveState, isSelected),
                    message = "Interactive state: $interactiveState, isSelected: $isSelected"
                )
            }
        }
    }
}
