package carbon.compose.dropdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.isFocusable
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

class DropdownPopupContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val options = (0..4).associateWith { "Option $it" }

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
    fun optionsLayout_validateSemanticsActions() {
        composeTestRule.run {
            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .assertAll(isFocusable() and hasClickAction())
        }
    }

    @Test
    fun optionsLayout_onItemClick_validateCallbackIsInvoked() {
        composeTestRule.run {
            onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
                .onFirst()
                .performClick()
        }
        assertNotNull(selectedOptionKey)
    }
}
