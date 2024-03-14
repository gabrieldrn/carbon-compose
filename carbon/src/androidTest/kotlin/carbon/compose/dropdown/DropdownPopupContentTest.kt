package carbon.compose.dropdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.isNotSelected
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import carbon.compose.toList
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DropdownPopupContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val options = mapOf(
        0 to "Option 0",
        1 to "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        2 to "Option 2",
        3 to "Option 3",
        4 to "Option 4",
    )

    private var dropdownSize by mutableStateOf(DropdownSize.Large)
    private var isExpanded by mutableStateOf(false)
    private var selectedOptionKey by mutableStateOf<Int?>(null)

    @Before
    fun setup() {
        composeTestRule.setContent {
            Column(Modifier.fillMaxWidth()) {
                DropdownContent(
                    options = options,
                    selectedOption = selectedOptionKey,
                    colors = DropdownColors.colors(),
                    componentHeight = dropdownSize.height,
                    onOptionSelected = { selectedOptionKey = it },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    @After
    fun tearDown() {
        isExpanded = false
        selectedOptionKey = null
    }

    @Test
    fun optionsLayout_validateSize() {
        composeTestRule.run {
            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .toList()
                .forEach {
                    it.assertHeightIsEqualTo(dropdownSize.height)
                }
        }
    }

    @Test
    fun optionsLayout_validateListIsLaidOut() {
        composeTestRule.run {
            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .assertCountEquals(options.size)
        }
    }

    @Test
    fun optionsLayout_validateSemanticsActions() {
        composeTestRule.run {
            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .assertAll(isFocusable() and isSelectable() and hasClickAction())
        }
    }

    @Test
    fun optionsLayout_noSelection_validateNoOptionSelected() {
        composeTestRule.run {
            assertNull(selectedOptionKey)
            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .assertAll(isNotSelected())
        }
    }

    @Test
    fun optionsLayout_optionSelected_validateOptionIsSelected() {
        composeTestRule.run {
            val selected = 2
            selectedOptionKey = selected
            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .filterToOne(isSelected())
                .assert(hasText(options[selected]!!))
        }
    }

    @Test
    fun optionsLayout_optionSelected_validateCheckmarkIsDisplayed() {
        composeTestRule.run {
            selectedOptionKey = 2
            onAllNodesWithTag(DropdownTestTags.MENU_OPTION_CHECKMARK, useUnmergedTree = true)
                .assertCountEquals(1)
                .onFirst()
                .assertIsDisplayed()
        }
    }

    @Test
    fun optionsLayout_onOptionSelected_validateCallbackIsInvoked() {
        composeTestRule.run {
            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .onFirst()
                .performClick()
        }
        assertNotNull(selectedOptionKey)
    }
}
