package carbon.compose.textinput

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag

@Suppress("NestedBlockDepth")
fun ComposeTestRule.runGlobalTextInputLayoutAssertions(
    label: String,
    helperText: String,
    state: TextInputState,
) = run {
    onNodeWithTag(TextInputTestTags.LABEL, useUnmergedTree = true).run {
        assertIsDisplayed()
        assertTextEquals(label)
    }

    //Field value
    onNodeWithTag(TextInputTestTags.FIELD, useUnmergedTree = true).assertIsDisplayed()

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
