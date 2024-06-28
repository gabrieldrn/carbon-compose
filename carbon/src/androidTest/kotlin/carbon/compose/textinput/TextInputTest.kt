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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class TextInputTest(
    private val variant: TextInputVariant,
    private val value: String,
    private val placeholderText: String,
    private val helperText: String,
    private val state: TextInputState,
) {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    @Suppress("All")
    fun textInput_validateLayout() {
        composeTestRule.setContent {
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
                        // Size is not tested as only one size is technically supported
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

        private val loremIpsum =
            """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
                incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud 
                exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute 
                irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla 
                pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia 
                deserunt mollit anim id est laborum
            """.trimIndent().replace("\n","")

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
