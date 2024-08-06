package carbon.compose.radiobutton

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import carbon.compose.common.selectable.SelectableInteractiveState
import carbon.compose.semantics.assertIsReadOnly
import kotlin.test.Test
import kotlin.test.assertTrue

class RadioButtonTest {

    private var selected by mutableStateOf(false)
    private var interactiveState by mutableStateOf<SelectableInteractiveState>(
        SelectableInteractiveState.Default
    )

    private val interactiveStates = listOf(
        SelectableInteractiveState.Default,
        SelectableInteractiveState.Disabled,
        SelectableInteractiveState.ReadOnly,
        SelectableInteractiveState.Error("Error message"),
        SelectableInteractiveState.Warning("Warning message")
    )

    fun ComposeUiTest.setup() {
        setContent {
            RadioButton(
                selected = selected,
                label = "Radio button",
                onClick = { selected = !selected },
                interactiveState = interactiveState,
                modifier = Modifier.testTag("root")
            )
        }
    }

    private fun ComposeUiTest.assertWarningContentIsDisplayed(displayed: Boolean) =
        onNodeWithTag(RadioButtonTestTags.WARNING_CONTENT, useUnmergedTree = true)
            .run {
                if (displayed) assertIsDisplayed() else assertDoesNotExist()
            }

    private fun ComposeUiTest.assertErrorContentIsDisplayed(displayed: Boolean) =
        onNodeWithTag(RadioButtonTestTags.ERROR_CONTENT, useUnmergedTree = true)
            .run {
                if (displayed) assertIsDisplayed() else assertDoesNotExist()
            }

    @Test
    fun radioButton_validateLayout() = runComposeUiTest {
        setup()
        interactiveStates.forEach { state ->
            interactiveState = state

            onNodeWithTag(RadioButtonTestTags.BUTTON, useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithTag(RadioButtonTestTags.LABEL, useUnmergedTree = true)
                .assertIsDisplayed()

            when (state) {
                is SelectableInteractiveState.Default -> {
                    onNodeWithTag("root").assertHasClickAction()
                    assertErrorContentIsDisplayed(false)
                    assertWarningContentIsDisplayed(false)
                }

                is SelectableInteractiveState.Disabled -> {
                    onNodeWithTag("root").assertIsNotEnabled()
                    assertErrorContentIsDisplayed(false)
                    assertWarningContentIsDisplayed(false)
                }

                is SelectableInteractiveState.ReadOnly -> {
                    onNodeWithTag("root").assertIsReadOnly()
                    assertErrorContentIsDisplayed(false)
                    assertWarningContentIsDisplayed(false)
                }

                is SelectableInteractiveState.Error -> {
                    onNodeWithTag("root").assertHasClickAction()
                    assertWarningContentIsDisplayed(false)
                    assertErrorContentIsDisplayed(true)
                }

                is SelectableInteractiveState.Warning -> {
                    onNodeWithTag("root").assertHasClickAction()
                    assertErrorContentIsDisplayed(false)
                    assertWarningContentIsDisplayed(true)
                }
            }
        }
    }

    @Test
    fun radioButton_onClick_stateGetsUpdated() = runComposeUiTest {
        setup()

        selected = false
        onNodeWithTag("root").performClick()
        assertTrue(selected)
    }
}
