package carbon.compose.textinput

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
import carbon.compose.semantics.isReadOnly
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.Test

@RunWith(Parameterized::class)
class TextInputTest(
    private val variant: TextInputVariant,
    private val value: String,
    private val placeholderText: String,
    private val helperText: String,
    private val state: TextInputState,
) {

    @Test
    fun textInput_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                when (variant) {
                    TextInputVariant.INPUT -> TextInput(
                        // The label is not parameterized as it's a mandatory element.
                        label = "Label",
                        value = value,
                        onValueChange = {},
                        placeholderText = placeholderText,
                        helperText = helperText,
                        state = state,
                    )
                    TextInputVariant.AREA -> TextArea(
                        label = "Label",
                        value = value,
                        onValueChange = {},
                        placeholderText = placeholderText,
                        helperText = helperText,
                        state = state,
                    )
                }
            }
        }

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

            onNodeWithText(value).assertIsDisplayed()
        }
    }

    @Test
    fun textInput_validateSemantics() = runComposeUiTest {
        setContent {
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

        @Suppress("NestedBlockDepth")
        @JvmStatic
        @Parameterized.Parameters(
            name = "textInputVariant={0}, value={1}, placeholderText={2}, helperText={3}, state={4}"
        )
        fun parameters(): Iterable<Array<Any?>> {
            val parameters = mutableListOf<Array<Any?>>()

            TextInputVariant.entries.forEach { variant ->
                listOf("", loremIpsum).forEach { value ->
                    listOf("", "Placeholder").forEach { placeholderText ->
                        listOf("", "Helper").forEach { helperText ->
                            TextInputState.entries.forEach { state ->
                                parameters.add(
                                    arrayOf(
                                        variant,
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
            }

            return parameters
        }

        enum class TextInputVariant {
            INPUT, AREA
        }
    }
}
