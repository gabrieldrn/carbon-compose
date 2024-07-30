package carbon.compose.progressbar

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import carbon.compose.CarbonDesignSystem
import carbon.compose.icons.checkmarkFilledIcon
import carbon.compose.icons.errorFilledIcon
import kotlin.test.Test

class ProgressBarTest {

    @Test
    @Suppress("CognitiveComplexMethod", "CyclomaticComplexMethod")
    fun progressBar_default_validateLayout() = runComposeUiTest {
        forEachParameter { labelText, helperText, inlined, state ->
            setContent {
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
    fun progressBar_indeterminate_validateLayout() = runComposeUiTest {
        forEachParameter { labelText, helperText, inlined, state ->
            setContent {
                CarbonDesignSystem {
                    IndeterminateProgressBar(
                        labelText = labelText,
                        helperText = helperText,
                        inlined = inlined,
                        state = state
                    )
                }
            }

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
        fun forEachParameter(
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
}
