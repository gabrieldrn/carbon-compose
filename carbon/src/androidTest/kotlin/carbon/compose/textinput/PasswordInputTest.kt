package carbon.compose.textinput

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import carbon.compose.CarbonDesignSystem
import carbon.compose.icons.viewIcon
import carbon.compose.icons.viewOffIcon
import carbon.compose.semantics.assertHasImageVector
import carbon.compose.semantics.isReadOnly
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PasswordInputTest(
    private val value: String,
    private val passwordHidden: Boolean,
    private val placeholderText: String,
    private val helperText: String,
    private val state: TextInputState,
) {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val _viewIcon = viewIcon
    private val _viewOffIcon = viewOffIcon

    @Test
    @Suppress("CognitiveComplexMethod", "NestedBlockDepth")
    fun passwordInput_validateLayout() {
        composeTestRule.setContent {
            CarbonDesignSystem {
                PasswordInput(
                    label = "Label",
                    value = value,
                    passwordHidden = passwordHidden,
                    state = state,
                    placeholderText = placeholderText,
                    helperText = helperText,
                    onValueChange = {},
                    onPasswordHiddenChange = {}
                )
            }
        }

        composeTestRule.run {
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
    fun passwordInput_validateSemantics() {
        composeTestRule.setContent {
            CarbonDesignSystem {
                TextInput(
                    label = "Label", // The label is not parameterized as it's a mandatory element.
                    value = value,
                    onValueChange = {},
                    placeholderText = placeholderText,
                    helperText = helperText,
                    state = state,
                    // Size is not tested as only one size is technically supported
                )
            }
        }

        composeTestRule.run {
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

    companion object {

        const val examplePassword = "S0mePa55word%"
        const val exampleHiddenPassword = "•••••••••••••"

        @Suppress("NestedBlockDepth")
        @JvmStatic
        @Parameterized.Parameters(
            name = "value={0}, passwordHidden={1}, placeholderText={2}, helperText={3}, state={4}"
        )
        fun parameters(): Iterable<Array<Any?>> {
            val parameters = mutableListOf<Array<Any?>>()

            listOf("", examplePassword).forEach { value ->
                listOf(true, false).forEach { passwordHidden ->
                    listOf("", "Placeholder").forEach { placeholderText ->
                        listOf("", "Helper").forEach { helperText ->
                            TextInputState.entries.forEach { state ->
                                parameters.add(
                                    arrayOf(
                                        value,
                                        passwordHidden,
                                        placeholderText,
                                        helperText,
                                        state,
                                    )
                                )
                            }
                        }
                    }
                }
            }

            return parameters
        }
    }
}
