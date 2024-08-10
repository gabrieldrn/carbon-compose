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

package carbon.compose.progressbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import carbon.compose.CarbonDesignSystem
import carbon.compose.icons.checkmarkFilledIcon
import carbon.compose.icons.errorFilledIcon
import kotlin.test.Test

class ProgressBarTest {

    private var _labelText by mutableStateOf<String?>(null)
    private var _helperText by mutableStateOf<String?>(null)
    private var _inlined by mutableStateOf(false)
    private var _state by mutableStateOf(ProgressBarState.Active)

    @Test
    @Suppress("CognitiveComplexMethod", "CyclomaticComplexMethod")
    fun progressBar_default_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                ProgressBar(
                    value = 0.5f,
                    labelText = _labelText,
                    helperText = _helperText,
                    inlined = _inlined,
                    state = _state,
                )
            }
        }

        forEachParameter { labelText, helperText, inlined, state ->

            _labelText = labelText
            _helperText = helperText
            _inlined = inlined
            _state = state

            onNodeWithTag(ProgressBarTestTags.LABEL_TEXT, useUnmergedTree = true).run {
                if (labelText != null) {
                    assertIsDisplayed()
                    assertTextEquals(labelText)
                } else {
                    assertDoesNotExist()
                }
            }

            onNodeWithTag(ProgressBarTestTags.HELPER_TEXT, useUnmergedTree = true).run {
                when {
                    inlined -> assertDoesNotExist()
                    helperText != null -> {
                        assertIsDisplayed()
                        assertTextEquals(helperText)
                    }
                    else -> assertDoesNotExist()
                }
            }

            onNodeWithTag(ProgressBarTestTags.TRACK, useUnmergedTree = true).run {
                if (inlined && state != ProgressBarState.Active) assertDoesNotExist()
                else assertIsDisplayed()
            }

            onNodeWithTag(errorFilledIcon.name, useUnmergedTree = true).run {
                if (state == ProgressBarState.Error) assertIsDisplayed()
                else assertDoesNotExist()
            }

            onNodeWithTag(checkmarkFilledIcon.name, useUnmergedTree = true).run {
                if (state == ProgressBarState.Success) assertIsDisplayed()
                else assertDoesNotExist()
            }
        }
    }

    @Test
    fun progressBar_indeterminate_validateLayout() = runComposeUiTest {

        setContent {
            CarbonDesignSystem {
                IndeterminateProgressBar(
                    labelText = _labelText,
                    helperText = _helperText,
                    inlined = _inlined,
                    state = _state
                )
            }
        }

        forEachParameter { labelText, helperText, inlined, state ->

            _labelText = labelText
            _helperText = helperText
            _inlined = inlined
            _state = state

            onNodeWithTag(ProgressBarTestTags.LABEL_TEXT, useUnmergedTree = true).run {
                if (labelText != null) {
                    assertIsDisplayed()
                    assertTextEquals(labelText)
                } else {
                    assertDoesNotExist()
                }
            }

            onNodeWithTag(ProgressBarTestTags.HELPER_TEXT, useUnmergedTree = true).run {
                when {
                    inlined -> assertDoesNotExist()
                    helperText != null -> {
                        assertIsDisplayed()
                        assertTextEquals(helperText)
                    }
                    else -> assertDoesNotExist()
                }
            }

            onNodeWithTag(ProgressBarTestTags.TRACK, useUnmergedTree = true).run {
                if (inlined && state != ProgressBarState.Active) assertDoesNotExist()
                else assertIsDisplayed()
            }
        }
    }

    @Suppress("NestedBlockDepth")
    private fun forEachParameter(
        testBlock: (String?, String?, Boolean, ProgressBarState) -> Unit
    ) {
        listOf("Label", null).forEach { labelText ->
            listOf("Helper", null).forEach { helperText ->
                listOf(false, true).forEach { inlined ->
                    ProgressBarState.entries.forEach { state ->
                        testBlock(labelText, helperText, inlined, state)
                    }
                }
            }
        }
    }
}
