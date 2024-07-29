package carbon.compose.radiobutton

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.runComposeUiTest
import carbon.compose.common.BaseSelectableColorsTest
import carbon.compose.common.selectable.SelectableInteractiveState
import kotlin.test.Test
import kotlin.test.assertEquals

class RadioButtonColorsTest : BaseSelectableColorsTest() {

    @Test
    fun radioButtonColors_borderColor_colorsAreCorrect() = runComposeUiTest {
        val expectedColors: Map<SelectableInteractiveState, Any> = mapOf(
            interactiveStates["default"]!! to theme.iconPrimary,
            interactiveStates["warning"]!! to theme.iconPrimary,
            interactiveStates["disabled"]!! to theme.iconDisabled,
            interactiveStates["readOnly"]!! to theme.iconDisabled,
            interactiveStates["error"]!! to theme.supportError,
        )

        forAllLayersAndStates(
            interactiveStates.values,
            listOf(ToggleableState.On, ToggleableState.Off)
        ) { interactiveState, toggleableState, _ ->
            assertEquals(
                expected = expectedColors.getColor(
                    interactiveState = interactiveState,
                    toggleableState = toggleableState
                ),
                actual = RadioButtonColors.colors()
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
    fun checkboxColors_dotColor_colorsAreCorrect() = runComposeUiTest {
        forAllLayersAndStates(
            interactiveStates.values,
            listOf(true, false)
        ) { interactiveState, isSelected, _ ->
            assertEquals(
                expected = when {
                    !isSelected -> Color.Transparent
                    interactiveState == SelectableInteractiveState.Disabled ->
                        theme.iconDisabled
                    else -> theme.iconPrimary
                },
                actual = RadioButtonColors.colors()
                    .dotColor(interactiveState, isSelected)
                    .value,
                message = "Interactive state: $interactiveState, isSelected: $isSelected"
            )
        }
    }
}
