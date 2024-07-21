package carbon.compose.progressbar

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import carbon.compose.CarbonDesignSystem
import carbon.compose.icons.checkmarkFilledIcon
import carbon.compose.icons.errorFilledIcon
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ProgressBarTest(
    private val labelText: String?,
    private val helperText: String?,
    private val inlined: Boolean,
    private val state: ProgressBarState,
) {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    @Suppress("CognitiveComplexMethod", "CyclomaticComplexMethod")
    fun progressBar_default_validateLayout() {
        composeTestRule.setContent {
            CarbonDesignSystem {
                ProgressBar(
                    value = 0.5f,
                    labelText = labelText,
                    helperText = helperText,
                    inlined = inlined,
                    state = state,
                )
            }
        }

        composeTestRule.run {
            onNodeWithTag(ProgressBarTestTags.LABEL_TEXT, useUnmergedTree = true).run {
                if (labelText != null) {
                    assertIsDisplayed()
                    assertTextEquals(labelText)
                } else {
                    assertIsNotDisplayed()
                }
            }

            onNodeWithTag(ProgressBarTestTags.HELPER_TEXT, useUnmergedTree = true).run {
                when {
                    inlined -> assertIsNotDisplayed()
                    helperText != null -> {
                        assertIsDisplayed()
                        assertTextEquals(helperText)
                    }
                    else -> assertIsNotDisplayed()
                }
            }

            onNodeWithTag(ProgressBarTestTags.TRACK, useUnmergedTree = true).run {
                if (inlined && state != ProgressBarState.Active) assertIsNotDisplayed()
                else assertIsDisplayed()
            }

            onNodeWithTag(errorFilledIcon.name, useUnmergedTree = true).run {
                if (state == ProgressBarState.Error) assertIsDisplayed()
                else assertIsNotDisplayed()
            }

            onNodeWithTag(checkmarkFilledIcon.name, useUnmergedTree = true).run {
                if (state == ProgressBarState.Success) assertIsDisplayed()
                else assertIsNotDisplayed()
            }
        }
    }

    @Test
    fun progressBar_indeterminate_validateLayout() {
        composeTestRule.setContent {
            CarbonDesignSystem {
                IndeterminateProgressBar(
                    labelText = labelText,
                    helperText = helperText,
                    inlined = inlined,
                    state = state
                )
            }
        }

        composeTestRule.run {
            onNodeWithTag(ProgressBarTestTags.LABEL_TEXT, useUnmergedTree = true).run {
                if (labelText != null) {
                    assertIsDisplayed()
                    assertTextEquals(labelText)
                } else {
                    assertIsNotDisplayed()
                }
            }

            onNodeWithTag(ProgressBarTestTags.HELPER_TEXT, useUnmergedTree = true).run {
                when {
                    inlined -> assertIsNotDisplayed()
                    helperText != null -> {
                        assertIsDisplayed()
                        assertTextEquals(helperText)
                    }
                    else -> assertIsNotDisplayed()
                }
            }

            onNodeWithTag(ProgressBarTestTags.TRACK, useUnmergedTree = true).run {
                if (inlined && state != ProgressBarState.Active) assertIsNotDisplayed()
                else assertIsDisplayed()
            }
        }
    }

    companion object {

        @Suppress("NestedBlockDepth")
        @JvmStatic
        @Parameterized.Parameters(name = "labelText={0}, helperText={1}, inlined={2}, state={3}")
        fun parameters(): Iterable<Array<Any?>> {
            val parameters = mutableListOf<Array<Any?>>()

            listOf("Label", null).forEach { labelText ->
                listOf("Helper", null).forEach { helperText ->
                    listOf(false, true).forEach { inlined ->
                        ProgressBarState.entries.forEach { state ->
                            parameters.add(arrayOf(labelText, helperText, inlined, state))
                        }
                    }
                }
            }

            return parameters
        }
    }
}
