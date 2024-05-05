package carbon.compose.checkbox

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.state.ToggleableState
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

class CheckboxTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private var state by mutableStateOf<ToggleableState>(ToggleableState.Off)
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

    private fun nextState() {
        state = ToggleableState.entries.toTypedArray().let { states ->
            states[(states.indexOf(state) + 1) % states.size]
        }
    }

    @Before
    fun setup() {
        composeTestRule.setContent {
            Checkbox(
                state = state,
                label = "Checkbox",
                onClick = ::nextState,
                interactiveState = interactiveState,
                modifier = Modifier.testTag("root")
            )
        }
    }

    private fun assertWarningContentIsDisplayed(displayed: Boolean) = composeTestRule
        .onNodeWithTag(CheckboxTestTags.WARNING_CONTENT, useUnmergedTree = true)
        .run {
            if (displayed) assertIsDisplayed() else assertIsNotDisplayed()
        }

    private fun assertErrorContentIsDisplayed(displayed: Boolean) = composeTestRule
        .onNodeWithTag(CheckboxTestTags.ERROR_CONTENT, useUnmergedTree = true)
        .run {
            if (displayed) assertIsDisplayed() else assertIsNotDisplayed()
        }

    @Test
    fun radioButton_validateLayout() {
        composeTestRule.run {
            interactiveStates.forEach { state ->
                interactiveState = state

                onNodeWithTag(CheckboxTestTags.BUTTON, useUnmergedTree = true)
                    .assertIsDisplayed()
                onNodeWithTag(CheckboxTestTags.LABEL, useUnmergedTree = true)
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
        val oldState = state
        composeTestRule.run {
            onNodeWithTag("root").performClick()
            assert(state != oldState)
        }
    }
}
