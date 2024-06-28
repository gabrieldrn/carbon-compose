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
import carbon.compose.semantics.isReadOnly
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class TextInputTest(
    private val value: String,
    private val placeholderText: String,
    private val helperText: String,
    private val state: TextInputState,
) {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
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
    }

    @Test
    @Suppress("CognitiveComplexMethod", "CyclomaticComplexMethod", "NestedBlockDepth")
    fun textInput_validateLayout() {
        composeTestRule.run {
            onNodeWithTag(TextInputTestTags.LABEL, useUnmergedTree = true).run {
                assertIsDisplayed()
                assertTextEquals("Label")
            }

            //Field value
            onNodeWithTag(TextInputTestTags.FIELD, useUnmergedTree = true).assertIsDisplayed()

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

                onNodeWithText(value).assertIsDisplayed()
            }

            if (helperText.isNotEmpty()) {
                onNodeWithTag(TextInputTestTags.HELPER_TEXT, useUnmergedTree = true).run {
                    assertIsDisplayed()
                    assertTextEquals(helperText)
                }
            }

            when (state) {
                TextInputState.Enabled -> {}
                TextInputState.Warning ->
                    onNodeWithTag(TextInputTestTags.STATE_ICON_WARNING, useUnmergedTree = true)
                        .assertIsDisplayed()
                TextInputState.Error ->
                    onNodeWithTag(TextInputTestTags.STATE_ICON_ERROR, useUnmergedTree = true)
                        .assertIsDisplayed()
                TextInputState.Disabled -> {}
                TextInputState.ReadOnly -> {}
            }
        }
    }

    @Test
    fun textInput_validateSemantics() {
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

        @Suppress("NestedBlockDepth")
        @JvmStatic
        @Parameterized.Parameters(
            name = "value={0}, placeholderText={1}, helperText={2}, state={3}"
        )
        fun parameters(): Iterable<Array<Any?>> {
            val parameters = mutableListOf<Array<Any?>>()

            listOf("", "Value").forEach { value ->
                listOf("", "Placeholder").forEach { placeholderText ->
                    listOf("", "Helper").forEach { helperText ->
                        TextInputState.entries.forEach { state ->
                            parameters.add(
                                arrayOf(
                                    value,
                                    placeholderText,
                                    helperText,
                                    state,
                                )
                            )
                        }
                    }
                }
            }

            return parameters
        }
    }
}
