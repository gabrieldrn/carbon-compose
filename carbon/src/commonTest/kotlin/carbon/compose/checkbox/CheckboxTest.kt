/*
 * Copyright 2024 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package carbon.compose.checkbox

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.state.ToggleableState
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
import kotlin.test.assertNotEquals

class CheckboxTest {

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

    fun ComposeUiTest.setup() {
        setContent {
            Checkbox(
                state = state,
                label = "Checkbox",
                onClick = ::nextState,
                interactiveState = interactiveState,
                modifier = Modifier.testTag("root")
            )
        }
    }

    private fun ComposeUiTest.assertWarningContentIsDisplayed(displayed: Boolean) =
        onNodeWithTag(CheckboxTestTags.WARNING_CONTENT, useUnmergedTree = true)
            .run {
                if (displayed) assertIsDisplayed() else assertDoesNotExist()
            }

    private fun ComposeUiTest.assertErrorContentIsDisplayed(displayed: Boolean) =
        onNodeWithTag(CheckboxTestTags.ERROR_CONTENT, useUnmergedTree = true)
            .run {
                if (displayed) assertIsDisplayed() else assertDoesNotExist()
            }

    @Test
    fun radioButton_validateLayout() = runComposeUiTest {
        setup()
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

    @Test
    fun radioButton_onClick_stateGetsUpdated() = runComposeUiTest {
        setup()
        val oldState = state
        onNodeWithTag("root").performClick()
        assertNotEquals(oldState, state)
    }
}
