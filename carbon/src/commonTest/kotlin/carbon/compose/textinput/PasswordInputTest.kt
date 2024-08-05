package carbon.compose.textinput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import carbon.compose.CarbonDesignSystem
import carbon.compose.icons.viewIcon
import carbon.compose.icons.viewOffIcon
import carbon.compose.semantics.assertHasImageVector
import carbon.compose.semantics.isReadOnly
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
                        assertIsNotDisplayed()
                    }
                }
            } else {
                onNodeWithTag(TextInputTestTags.PLACEHOLDER, useUnmergedTree = true)
                    .assertIsNotDisplayed()

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
