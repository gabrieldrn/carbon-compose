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
import com.gabrieldrn.carbon.icons.viewIcon
import com.gabrieldrn.carbon.icons.viewOffIcon
import com.gabrieldrn.carbon.semantics.assertHasImageVector
import com.gabrieldrn.carbon.semantics.isReadOnly
import kotlin.test.Test

class PasswordInputTest {

    private val _viewIcon = viewIcon
    private val _viewOffIcon = viewOffIcon

    private var _value by mutableStateOf("")
    private var _passwordHidden by mutableStateOf(false)
    private var _placeholderText by mutableStateOf("")
    private var _helperText by mutableStateOf("")
    private var _state by mutableStateOf(TextInputState.Enabled)

    @Test
    @Suppress("CognitiveComplexMethod", "NestedBlockDepth")
    fun passwordInput_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                PasswordInput(
                    label = "Label",
                    value = _value,
                    passwordHidden = _passwordHidden,
                    state = _state,
                    placeholderText = _placeholderText,
                    helperText = _helperText,
                    onValueChange = {},
                    onPasswordHiddenChange = {}
                )
            }
        }

        forEachParameter { value, passwordHidden, placeholderText, helperText, state ->

            _value = value
            _passwordHidden = passwordHidden
            _placeholderText = placeholderText
            _helperText = helperText
            _state = state

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

                onNodeWithText(
                    if (passwordHidden) exampleHiddenPassword else examplePassword
                ).assertIsDisplayed()
            }

            onNodeWithTag(TextInputTestTags.HIDE_PASSWORD_BUTTON, useUnmergedTree = true)
                .assertIsDisplayed()
                .run {
                    if (passwordHidden) {
                        assertHasImageVector(_viewIcon.name)
                    } else {
                        assertHasImageVector(_viewOffIcon.name)
                    }
                }
        }
    }

    @Test
    fun passwordInput_validateSemantics() = runComposeUiTest {
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

        forEachParameter { value, passwordHidden, placeholderText, helperText, state ->

            _value = value
            _passwordHidden = passwordHidden
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
        testBlock: (String, Boolean, String, String, TextInputState) -> Unit
    ) {
        listOf("", examplePassword).forEach { value ->
            listOf(true, false).forEach { passwordHidden ->
                listOf("", "Placeholder").forEach { placeholderText ->
                    listOf("", "Helper").forEach { helperText ->
                        TextInputState.entries.forEach { state ->
                            testBlock(
                                value,
                                passwordHidden,
                                placeholderText,
                                helperText,
                                state,
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val examplePassword = "S0mePa55word%"
        const val exampleHiddenPassword = "•••••••••••••"
    }
}
