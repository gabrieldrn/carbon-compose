package carbon.compose.dropdown

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
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
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.base.DropdownOption
import carbon.compose.dropdown.base.DropdownSize
import carbon.compose.dropdown.base.DropdownTestTags
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

    private var label by mutableStateOf<String?>("Dropdown")
    private var state by mutableStateOf<DropdownInteractiveState>(DropdownInteractiveState.Enabled)
    private var dropdownSize by mutableStateOf(DropdownSize.Large)
    private var isExpanded by mutableStateOf(false)
    private var selectedOptionKey by mutableStateOf<Int?>(null)
    private val placeholder = "Dropdown"

    @Before
    fun setup() {
        composeTestRule.setContent {
            Dropdown(
                label = label,
                expanded = isExpanded,
                fieldPlaceholderText = placeholder,
                selectedOption = selectedOptionKey,
                options = options,
                onOptionSelected = { selectedOptionKey = it },
                onExpandedChange = { isExpanded = it },
                onDismissRequest = { isExpanded = false },
                minVisibleItems = minVisibleItems,
                dropdownSize = dropdownSize,
                state = state,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @After
    fun tearDown() {
        label = "Dropdown"
        isExpanded = false
        selectedOptionKey = null
        dropdownSize = DropdownSize.Large
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
