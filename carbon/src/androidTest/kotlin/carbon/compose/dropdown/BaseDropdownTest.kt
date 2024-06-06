package carbon.compose.dropdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import carbon.compose.dropdown.base.BaseDropdown
import carbon.compose.dropdown.base.DropdownColors
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.base.DropdownOption
import carbon.compose.dropdown.base.DropdownPlaceholderText
import carbon.compose.dropdown.base.DropdownSize
import carbon.compose.dropdown.base.DropdownTestTags
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class BaseDropdownTest {

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

    private val popupContentMockTestTag = "PopupContent"

    @Before
    fun setup() {
        composeTestRule.setContent {
            BaseDropdown(
                label = label,
                expanded = isExpanded,
                options = options,
                colors = DropdownColors.colors(),
                onExpandedChange = { isExpanded = it },
                onDismissRequest = { isExpanded = false },
                minVisibleItems = minVisibleItems,
                dropdownSize = dropdownSize,
                state = state,
                fieldContent = {
                    DropdownPlaceholderText(
                        placeholderText = placeholder,
                        colors = DropdownColors.colors(),
                        state = state,
                    )
                },
                popupContent = {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .testTag(popupContentMockTestTag)
                    )
                },
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
    fun baseDropdown_label_validateLayout() {
        composeTestRule.run {
            onNodeWithTag(DropdownTestTags.LABEL_TEXT)
                .assertIsDisplayed()
                .assert(hasText(placeholder))

            label = "   "
            onNodeWithTag(DropdownTestTags.LABEL_TEXT)
                .assertIsNotDisplayed()

            label = ""
            onNodeWithTag(DropdownTestTags.LABEL_TEXT)
                .assertIsNotDisplayed()

            label = null
            onNodeWithTag(DropdownTestTags.LABEL_TEXT)
                .assertIsNotDisplayed()
        }
    }

    @Test
    fun baseDropdown_withStates_validateLayout() {
        composeTestRule.run {
            val warningMessage = "Warning message goes here"
            val errorMessage = "Error message goes here"

            listOf(
                DropdownInteractiveState.Enabled,
                DropdownInteractiveState.Warning(warningMessage),
                DropdownInteractiveState.Error(errorMessage),
                DropdownInteractiveState.Disabled,
                DropdownInteractiveState.ReadOnly
            ).forEach {
                state = it

                onNodeWithTag(DropdownTestTags.POPUP_CONTENT)
                    .assertIsNotDisplayed()

                when (state) {
                    is DropdownInteractiveState.Enabled,
                    is DropdownInteractiveState.ReadOnly ->
                        onNodeWithTag(DropdownTestTags.HELPER_TEXT)
                            .assertIsNotDisplayed()

                    is DropdownInteractiveState.Warning ->
                        onNodeWithTag(DropdownTestTags.HELPER_TEXT)
                            .assertIsDisplayed()
                            .assert(hasText(warningMessage))

                    is DropdownInteractiveState.Error ->
                        onNodeWithTag(DropdownTestTags.HELPER_TEXT)
                            .assertIsDisplayed()
                            .assert(hasText(errorMessage))
                    else -> {}
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    @Test
    fun baseDropdown_field_validateSize() {
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
    fun baseDropdown_optionsPopup_expandAndCollapse() {
        composeTestRule.run {
            onNodeWithTag(popupContentMockTestTag)
                .assertIsNotDisplayed()

            onNodeWithTag(DropdownTestTags.FIELD)
                .performClick()

            assertTrue(
                actual = isExpanded,
                message = "Dropdown should be expanded"
            )

            onNodeWithTag(popupContentMockTestTag)
                .assertIsDisplayed()

            // TODO Tried to shrink the dropdown by invoking a touch event on the field, but for
            //  some reason it didn't work.
//            onNodeWithTag(DropdownTestTags.FIELD)
//                .performClick()
            isExpanded = false

            onNodeWithTag(popupContentMockTestTag)
                .assertIsNotDisplayed()
        }
    }
}
