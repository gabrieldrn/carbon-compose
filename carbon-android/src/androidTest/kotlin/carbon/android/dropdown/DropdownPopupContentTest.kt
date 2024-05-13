package carbon.android.dropdown

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
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.isNotEnabled
import androidx.compose.ui.test.isNotFocusable
import androidx.compose.ui.test.isNotSelected
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import carbon.android.dropdown.base.DropdownColors
import carbon.android.dropdown.base.DropdownOption
import carbon.android.dropdown.base.DropdownPopupContent
import carbon.android.dropdown.base.DropdownSize
import carbon.android.dropdown.base.DropdownTestTags
import carbon.android.foundation.color.LocalCarbonTheme
import carbon.android.toList
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DropdownPopupContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val options: Map<Int, DropdownOption> = (0..4)
        .associateWith { DropdownOption("Option $it") }
        .toMutableMap()
        .apply {
            set(
                1, DropdownOption(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                        "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
                        "nisi ut aliquip ex ea commodo consequat."
                )
            )
            set(2, DropdownOption("Disabled", enabled = false))
        }

    private var dropdownSize by mutableStateOf(DropdownSize.Large)
    private var isExpanded by mutableStateOf(false)
    private var selectedOptionKey by mutableStateOf<Int?>(null)

    @Before
    fun setup() {
        composeTestRule.setContent {
            Column(Modifier.fillMaxWidth()) {
                DropdownPopupContent(
                    options = options,
                    selectedOption = selectedOptionKey,
                    colors = DropdownColors(LocalCarbonTheme.current),
                    componentHeight = dropdownSize.height,
                    onOptionClicked = { selectedOptionKey = it },
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
                .filter(hasText("Disabled").not())
                .assertAll(isFocusable() and isSelectable() and hasClickAction())

            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .filterToOne(hasText("Disabled"))
                .assert(isNotEnabled() and isNotFocusable())
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
            val selected = 1
            selectedOptionKey = selected
            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .filterToOne(isSelected())
                .assert(hasText(options[selected]!!.value))
        }
    }

    @Test
    fun optionsLayout_optionSelected_validateCheckmarkIsDisplayed() {
        composeTestRule.run {
            selectedOptionKey = 1
            onAllNodesWithTag(DropdownTestTags.MENU_OPTION_CHECKMARK, useUnmergedTree = true)
                .assertCountEquals(1)
                .onFirst()
                .assertIsDisplayed()
        }
    }

    @Test
    fun optionsLayout_optionSelected_validateDividerIsHidden() {
        composeTestRule.run {
            onAllNodesWithTag(DropdownTestTags.MENU_OPTION_DIVIDER, useUnmergedTree = true)
                .assertCountEquals(4)

            selectedOptionKey = 1

            onAllNodesWithTag(DropdownTestTags.MENU_OPTION_DIVIDER, useUnmergedTree = true)
                .assertCountEquals(3)
                .filterToOne(hasText("Option 3"))
                .assertDoesNotExist()
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

    @Test
    fun optionsLayout_onDisabledOptionSelected_validateOptionIsNotSelected() {
        composeTestRule.run {
            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .filterToOne(hasText("Disabled"))
                .performClick()
        }
        assertNull(selectedOptionKey)
    }
}
