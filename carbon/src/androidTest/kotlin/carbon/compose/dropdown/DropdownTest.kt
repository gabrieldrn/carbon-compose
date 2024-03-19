package carbon.compose.dropdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.requestFocus
import carbon.compose.toList
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class DropdownTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val options = (0..9).associateWith { DropdownOption("Option $it") }
    private val minVisibleItems = 4

    private var state by mutableStateOf<DropdownInteractiveState>(DropdownInteractiveState.Enabled)
    private var dropdownSize by mutableStateOf(DropdownSize.Large)
    private var isExpanded by mutableStateOf(false)
    private var selectedOptionKey by mutableStateOf<Int?>(null)
    private val placeholder = "Dropdown"

    @Before
    fun setup() {
        composeTestRule.setContent {
            Column(Modifier.fillMaxWidth()) {
                Dropdown(
                    expanded = isExpanded,
                    fieldPlaceholderText = placeholder,
                    selectedOption = selectedOptionKey,
                    options = options,
                    onOptionSelected = { selectedOptionKey = it },
                    onExpandedChange = { isExpanded = it },
                    onDismissRequest = { isExpanded = false },
                    minVisibleItems = minVisibleItems,
                    dropdownSize = dropdownSize,
                    state = state
                )
            }
        }
    }

    @After
    fun tearDown() {
        isExpanded = false
        selectedOptionKey = null
        dropdownSize = DropdownSize.Large
    }

    private fun baseLayoutValidation() {
        composeTestRule.run {
            onNodeWithTag(DropdownTestTags.FIELD)
                .assertIsDisplayed()
                .assertHeightIsEqualTo(DropdownSize.Large.height)

            onNodeWithTag(DropdownTestTags.POPUP_CONTENT)
                .assertIsNotDisplayed()

            onNodeWithTag(DropdownTestTags.FIELD_CHEVRON, useUnmergedTree = true)
                .assertIsDisplayed()
        }
    }

    @Test
    fun dropdown_field_enabled_validateLayout() {
        composeTestRule.run {
            baseLayoutValidation()

            onNodeWithTag(DropdownTestTags.FIELD_DIVIDER, useUnmergedTree = true)
                .assertIsDisplayed()

            onNodeWithTag(DropdownTestTags.FIELD_WARNING_ICON, useUnmergedTree = true)
                .assertIsNotDisplayed()

            onNodeWithTag(DropdownTestTags.FIELD_ERROR_ICON, useUnmergedTree = true)
                .assertIsNotDisplayed()

            onNodeWithTag(DropdownTestTags.FIELD_HELPER_TEXT)
                .assertIsNotDisplayed()
        }
    }

    @Test
    fun dropdown_field_warning_validateLayout() {
        composeTestRule.run {
            val warningMessage = "Warning message goes here"

            state = DropdownInteractiveState.Warning(warningMessage)

            baseLayoutValidation()

            onNodeWithTag(DropdownTestTags.FIELD_WARNING_ICON, useUnmergedTree = true)
                .assertIsDisplayed()

            onNodeWithTag(DropdownTestTags.FIELD_HELPER_TEXT)
                .assertIsDisplayed()
                .assert(hasText(warningMessage))
        }
    }

    @Test
    fun dropdown_field_error_validateLayout() {
        composeTestRule.run {
            val errorMessage = "Error message goes here"

            state = DropdownInteractiveState.Error(errorMessage)

            baseLayoutValidation()

            onNodeWithTag(DropdownTestTags.FIELD_ERROR_ICON, useUnmergedTree = true)
                .assertIsDisplayed()

            onNodeWithTag(DropdownTestTags.FIELD_HELPER_TEXT)
                .assertIsDisplayed()
                .assert(hasText(errorMessage))

            onNodeWithTag(DropdownTestTags.FIELD_DIVIDER, useUnmergedTree = true)
                .assertIsNotDisplayed()
        }
    }

    @Suppress("DEPRECATION")
    @Test
    fun dropdown_field_validateSize() {
        composeTestRule.run {
            onNodeWithTag(DropdownTestTags.FIELD)
                .assertHeightIsEqualTo(DropdownSize.Large.height)

            dropdownSize = DropdownSize.Small

            onNodeWithTag(DropdownTestTags.FIELD)
                .assertHeightIsEqualTo(DropdownSize.Small.height)

            dropdownSize = DropdownSize.Medium

            onNodeWithTag(DropdownTestTags.FIELD)
                .assertHeightIsEqualTo(DropdownSize.Medium.height)
        }
    }

    @Test
    fun dropdown_field_validateFocusAbility() {
        composeTestRule.run {
            onNodeWithTag(DropdownTestTags.FIELD)
                .requestFocus()
                .assertIsFocused()
        }
    }

    @Test
    fun dropdown_optionsPopup_validateLayout() {
        isExpanded = true

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

    @Test
    fun dropdown_optionsPopup_expandAndCollapse() {
        composeTestRule.run {
            onNodeWithTag(DropdownTestTags.POPUP_CONTENT)
                .assertIsNotDisplayed()

            onNodeWithTag(DropdownTestTags.FIELD)
                .performClick()

            onNodeWithTag(DropdownTestTags.POPUP_CONTENT)
                .assertIsDisplayed()

            // TODO Tried to shrink the dropdown by invoking a touch event on the field, but for
            //  some reason it didn't work.
//            onNodeWithTag(DropdownTestTags.FIELD)
//                .performClick()
            isExpanded = false

            onNodeWithTag(DropdownTestTags.POPUP_CONTENT)
                .assertIsNotDisplayed()
        }
    }

    @Test
    fun dropdown_optionsPopup_onOptionClick_validateCallbackIsInvoked() {
        composeTestRule.run {
            isExpanded = true

            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .onFirst()
                .performClick()

            assertEquals(
                expected = 0,
                actual = selectedOptionKey
            )
        }
    }

    @Test
    fun dropdown_optionsPopup_onOptionClick_validatePlaceholderFollowsState() {
        composeTestRule.run {
            onNodeWithTag(DropdownTestTags.FIELD)
                .assert(hasText(placeholder))
                .performClick()

            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .onFirst()
                .performClick()

            onNodeWithTag(DropdownTestTags.FIELD)
                .assert(hasText("Option 0"))
        }
    }

    @Test
    fun dropdown_optionsPopup_validatePlaceholderValueOnCompositionComplete() {
        selectedOptionKey = 0
        composeTestRule.run {
            onNodeWithTag(DropdownTestTags.FIELD)
                .assert(hasText("Option 0"))
        }
    }
}
