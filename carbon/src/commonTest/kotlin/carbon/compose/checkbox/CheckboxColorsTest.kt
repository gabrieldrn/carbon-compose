package carbon.compose.checkbox

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.runComposeUiTest
import carbon.compose.common.BaseSelectableColorsTest
import carbon.compose.common.selectable.SelectableInteractiveState
import kotlin.test.Test
import kotlin.test.assertEquals

class CheckboxColorsTest : BaseSelectableColorsTest() {

    @Test
    fun checkboxColors_checkmarkColor_colorsAreCorrect() = runComposeUiTest {
        forAllLayersAndStates(
            interactiveStates.values,
            ToggleableState.entries
        ) { interactiveState, toggleableState, _ ->
            assertEquals(
                expected = when {
                    toggleableState == ToggleableState.Off -> Color.Transparent
                    interactiveState == SelectableInteractiveState.ReadOnly -> theme.iconPrimary
                    else -> theme.iconInverse
                },
                actual = CheckboxColors.colors()
                    .checkmarkColor(interactiveState, toggleableState)
                    .value,
                message = "Interactive state: $interactiveState, " +
                    "toggleable state: $toggleableState"
            )
        }
    }

    @Test
    fun checkboxColors_backgroundColor_colorsAreCorrect() = runComposeUiTest {
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

        forAllLayersAndStates(
            interactiveStates.values,
            ToggleableState.entries
        ) { interactiveState, toggleableState, _ ->
            assertEquals(
                expected = expectedColors.getColor(
                    interactiveState = interactiveState,
                    toggleableState = toggleableState
                ),
                actual = CheckboxColors.colors()
                    .backgroundColor(
                        interactiveState = interactiveState,
                        state = toggleableState
                    )
                    .value,
                message = "Interactive state: $interactiveState, " +
                    "toggleable state: $toggleableState"
            )
        }
    }
}
