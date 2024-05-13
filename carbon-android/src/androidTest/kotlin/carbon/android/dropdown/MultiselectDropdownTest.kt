package carbon.android.dropdown

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import carbon.android.dropdown.base.DropdownInteractiveState
import carbon.android.dropdown.base.DropdownOption
import carbon.android.dropdown.base.DropdownSize
import carbon.android.dropdown.base.DropdownTestTags
import carbon.android.dropdown.multiselect.MultiselectDropdown
import carbon.android.toList
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class MultiselectDropdownTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private var state by mutableStateOf<DropdownInteractiveState>(DropdownInteractiveState.Enabled)
    private val options = (0..9).associateWith { DropdownOption("Option $it") }
    private val selectedOptions = mutableStateListOf<Int>()
    private val minVisibleItems = 4
    private var dropdownSize by mutableStateOf(DropdownSize.Large)
    private var isExpanded by mutableStateOf(false)
    private val placeholder = "Multiselect dropdown"

    @Before
    fun setup() {
        composeTestRule.setContent {
            MultiselectDropdown(
                expanded = isExpanded,
                placeholder = placeholder,
                options = options,
                selectedOptions = selectedOptions,
                onOptionClicked = selectedOptions::add,
                onClearSelection = selectedOptions::clear,
                onExpandedChange = { isExpanded = it },
                onDismissRequest = { isExpanded = false },
                minVisibleItems = minVisibleItems,
                dropdownSize = dropdownSize,
                state = state,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    @After
    fun tearDown() {
        state = DropdownInteractiveState.Enabled
        isExpanded = false
        selectedOptions.clear()
        dropdownSize = DropdownSize.Large
    }

    // Same test as in DropdownTest.kt, could it be merged?
    @Test
    fun multiselectDropdown_optionsPopup_validateLayout() {
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
    fun multiselectDropdown_optionsPopup_onOptionClick_validateCallbackIsInvoked() {
        composeTestRule.run {
            isExpanded = true

            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .onFirst()
                .performClick()

            assertEquals(
                expected = 1,
                actual = selectedOptions.size
            )
        }
    }

    @Test
    fun multiselectDropdown_optionsPopup_onOptionClick_optionsListIsNotReorganized() {
        composeTestRule.run {
            isExpanded = true

            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .get(3)
                .performClick()

            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .get(0)
                .assert(hasText("Option 0")) // Option 0 is still at the top of the list
        }
    }

    @Test
    fun multiselectDropdown_optionsPopup_withSelectedOptions_selectedOptionsAreOnTopOfList() {
        composeTestRule.run {
            selectedOptions.addAll(listOf(3, 5, 9))
            isExpanded = true

            onAllNodesWithTag(DropdownTestTags.MENU_OPTION).run {
                assertCountEquals(minVisibleItems + 1)
                toList().run {
                    get(0).assert(hasText("Option 3"))
                    get(1).assert(hasText("Option 5"))
                    get(2).assert(hasText("Option 9"))
                }
            }
        }
    }

    @Test
    fun multiselectDropdown_withSelectedOptions_tagIsDisplayedWithCountOfSelectedOptions() {
        composeTestRule.run {
            selectedOptions.addAll(listOf(1, 2, 3))

            onNodeWithTag(DropdownTestTags.FIELD_MULTISELECT_TAG)
                .assert(hasText("3"))
        }
    }

    @Test
    fun multiselectDropdown_onClearSelection_validateCallbackIsInvoked() {
        composeTestRule.run {
            selectedOptions.addAll(listOf(0, 1, 2))

            onNodeWithTag(DropdownTestTags.FIELD_MULTISELECT_TAG)
                .performClick()

            assertEquals(
                expected = 0,
                actual = selectedOptions.size
            )
        }
    }
}
