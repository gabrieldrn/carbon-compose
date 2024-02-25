package carbon.compose.dropdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.unit.dp
import carbon.compose.toList
import org.junit.Rule
import org.junit.Test

class DropdownTest {

    // Test:
    // Dropdown is displayed
    // Dropdown is not expanded
    // Dropdown is expanded and options are displayed
    // Chevron icon is displayed
    // Chevron icon is rotated when dropdown is expanded
    // Dropdown options are selectable
    // Placeholder text is displayed
    // Placeholder text changes when an option is selected


    @get:Rule
    val composeTestRule = createComposeRule()

    private val options = (0..9).associateWith { "Option $it" }
    private val minVisibleItems = 4

    @Test
    fun dropdown_field_validateLayout() {
        val isExpanded = mutableStateOf(false)
        composeTestRule.setContent {
            Column(Modifier.fillMaxWidth()) {
                Dropdown(
                    expanded = isExpanded.value,
                    fieldPlaceholderText = "Dropdown",
                    selectedOption = null,
                    options = options,
                    onOptionSelected = {},
                    onExpandedChange = { isExpanded.value = it },
                    onDismissRequest = { isExpanded.value = false },
                    minVisibleItems = minVisibleItems
                )
            }
        }

        composeTestRule.run {
            onNodeWithTag(DropdownTestTags.FIELD)
                .assertIsDisplayed()
                .assertHeightIsEqualTo(40.dp)
                .assert(hasText("Dropdown"))

            onNodeWithTag(DropdownTestTags.POPUP_CONTENT)
                .assertIsNotDisplayed()

            onNodeWithTag(DropdownTestTags.FIELD_CHEVRON, useUnmergedTree = true)
                .assertIsDisplayed()
        }
    }

    @Test
    fun dropdown_optionsPopup_validateLayout() {
        val isExpanded = mutableStateOf(false)
        composeTestRule.setContent {
            Column(Modifier.fillMaxWidth()) {
                Dropdown(
                    expanded = isExpanded.value,
                    fieldPlaceholderText = "Dropdown",
                    selectedOption = null,
                    options = options,
                    onOptionSelected = {},
                    onExpandedChange = { isExpanded.value = it },
                    onDismissRequest = { isExpanded.value = false },
                    minVisibleItems = minVisibleItems
                )
            }
        }

        isExpanded.value = true

        composeTestRule.run {
            onNodeWithTag(DropdownTestTags.POPUP_CONTENT)
                .assertIsDisplayed()

            onAllNodesWithTag(DropdownTestTags.MENU_OPTION).run {
                assertCountEquals(minVisibleItems + 1)
                toList().forEach { it.assertIsDisplayed() }
            }

            onNodeWithTag(DropdownTestTags.POPUP_CONTENT)
                .performScrollToNode(hasText("Option 9"))

            onNode(hasTestTag(DropdownTestTags.MENU_OPTION).and(hasText("Option 9")))
                .assertIsDisplayed()
        }
    }
}
