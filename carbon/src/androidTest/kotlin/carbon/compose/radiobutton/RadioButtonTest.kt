package carbon.compose.radiobutton

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import carbon.compose.foundation.selectable.SelectableInteractiveState
import carbon.compose.semantics.assertIsReadOnly
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RadioButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private var selected by mutableStateOf(false)
    private var interactiveState by mutableStateOf<SelectableInteractiveState>(
        SelectableInteractiveState.Default
    )
    private var errorMessage by mutableStateOf("")
    private var warningMessage by mutableStateOf("")

    private val interactiveStates = listOf(
        SelectableInteractiveState.Default,
        SelectableInteractiveState.Disabled,
        SelectableInteractiveState.ReadOnly,
        SelectableInteractiveState.Error("Error message"),
        SelectableInteractiveState.Warning("Warning message")
    )

    @Before
    fun setup() {
        composeTestRule.setContent {
            RadioButton(
                selected = selected,
                label = "Radio button",
                onClick = { selected = !selected },
                interactiveState = interactiveState,
                modifier = Modifier.testTag("root")
            )
        }
    }

    private fun assertWarningContentIsDisplayed(displayed: Boolean) = composeTestRule
        .onNodeWithTag(RadioButtonTestTags.WARNING_CONTENT, useUnmergedTree = true)
        .run {
            if (displayed) assertIsDisplayed() else assertIsNotDisplayed()
        }

    private fun assertErrorContentIsDisplayed(displayed: Boolean) = composeTestRule
        .onNodeWithTag(RadioButtonTestTags.ERROR_CONTENT, useUnmergedTree = true)
        .run {
            if (displayed) assertIsDisplayed() else assertIsNotDisplayed()
        }

    @Test
    fun radioButton_validateLayout() {
        composeTestRule.run {
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
    }

    @Test
    fun radioButton_onClick_stateGetsUpdated() {
        selected = false
        composeTestRule.run {
            onNodeWithTag("root").performClick()
            assert(selected)
        }
    }
}
