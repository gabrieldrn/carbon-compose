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

package com.gabrieldrn.carbon.textinput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.PARAMTRZD_DEPRECATION_MESSAGE
import com.gabrieldrn.carbon.PARAMTRZD_DEPRECATION_REPLACE
import com.gabrieldrn.carbon.semantics.isReadOnly
import kotlin.test.Test

class TextInputTest {

    private var _variant by mutableStateOf(TextInputVariant.INPUT)
    private var _value by mutableStateOf("")
    private var _placeholderText by mutableStateOf("")
    private var _helperText by mutableStateOf("")
    private var _state by mutableStateOf(TextInputState.Enabled)

    @Test
    fun textInput_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                when (_variant) {
                    TextInputVariant.INPUT -> TextInput(
                        // The label is not parameterized as it's a mandatory element.
                        label = "Label",
                        value = _value,
                        onValueChange = {},
                        placeholderText = _placeholderText,
                        helperText = _helperText,
                        state = _state,
                    )
                    TextInputVariant.AREA -> TextArea(
                        label = "Label",
                        value = _value,
                        onValueChange = {},
                        placeholderText = _placeholderText,
                        helperText = _helperText,
                        state = _state,
                    )
                }
            }
        }

        forEachParameter { variant, value, placeholderText, helperText, state ->

            _variant = variant
            _value = value
            _placeholderText = placeholderText
            _helperText = helperText
            _state = state

            waitForIdle()

            runGlobalTextInputLayoutAssertions(
                label = "Label",
                helperText = helperText,
                state = state
            )

            if (value.isEmpty()) {
                onNodeWithTag(TextInputTestTags.PLACEHOLDER, useUnmergedTree = true).run {
                    if (placeholderText.isNotEmpty()) {
                        assertIsDisplayed()
                        assertTextEquals(placeholderText)
                    } else {
                        assertTextEquals("")
                    }
                }
            } else {
                onNodeWithTag(TextInputTestTags.PLACEHOLDER, useUnmergedTree = true)
                    .assertDoesNotExist()

                onNodeWithText(value).assertIsDisplayed()
            }
        }
    }

    @Test
    fun textInput_validateSemantics() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                TextInput(
                    // The label is not parameterized as it's a mandatory element.
                    label = "Label",
                    value = _value,
                    onValueChange = {},
                    placeholderText = _placeholderText,
                    helperText = _helperText,
                    state = _state,
                    // Size is not tested as only one size is technically supported
                )
            }
        }

        forEachParameter { variant, value, placeholderText, helperText, state ->

            _variant = variant
            _value = value
            _placeholderText = placeholderText
            _helperText = helperText
            _state = state

            // Field state
            onNodeWithTag(TextInputTestTags.FIELD, useUnmergedTree = true).run {
                when (state) {
                    TextInputState.Disabled -> assertIsNotEnabled()
                    TextInputState.ReadOnly -> {
                        assert(isFocusable())
                        assert(isReadOnly())
                    }
                    else -> assertIsEnabled()
                }
                if (helperText.isNotEmpty()) {
                    hasStateDescription(helperText)
                }
            }
        }
    }

    @Suppress("NestedBlockDepth")
    @Deprecated(
        message = PARAMTRZD_DEPRECATION_MESSAGE,
        level = DeprecationLevel.WARNING,
        replaceWith = ReplaceWith(PARAMTRZD_DEPRECATION_REPLACE)
    )
    private fun forEachParameter(
        testBlock: (TextInputVariant, String, String, String, TextInputState) -> Unit
    ) {
        TextInputVariant.entries.forEach { variant ->
            listOf("", loremIpsum).forEach { value ->
                listOf("", "Placeholder").forEach { placeholderText ->
                    listOf("", "Helper").forEach { helperText ->
                        TextInputState.entries.forEach { state ->
                            testBlock(
                                variant,
                                value,
                                placeholderText,
                                helperText,
                                state
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {

        private val loremIpsum =
            """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
                incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud 
                exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute 
                irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla 
                pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia 
                deserunt mollit anim id est laborum
            """.trimIndent().replace("\n", "")

        enum class TextInputVariant {
            INPUT, AREA
        }
    }
}
