package carbon.compose.dropdown

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.requestFocus
import carbon.compose.CarbonDesignSystem
import carbon.compose.foundation.color.WhiteTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DropdownFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private var state by mutableStateOf<DropdownInteractiveState>(DropdownInteractiveState.Enabled)
    private val placeholder = "Dropdown"

    @Before
    fun setup() {
        composeTestRule.setContent {
            val expandedStates = remember { MutableTransitionState(false) }
            val transition = updateTransition(expandedStates, "Dropdown")

            CarbonDesignSystem(WhiteTheme) {
                DropdownField(
                    state = state,
                    dropdownSize = DropdownSize.Large,
                    transition = transition,
                    expandedStates = expandedStates,
                    placeholderText = placeholder,
                    onExpandedChange = { expandedStates.targetState = it }
                )
            }
        }
    }

    @Test
    fun dropdownField_withStates_validateLayout() {
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

                onNodeWithTag(DropdownTestTags.FIELD)
                    .assertIsDisplayed()
                    .assertHeightIsEqualTo(DropdownSize.Large.height)

                onNodeWithTag(DropdownTestTags.FIELD_CHEVRON, useUnmergedTree = true)
                    .assertIsDisplayed()

                when (state) {
                    is DropdownInteractiveState.Enabled,
                    is DropdownInteractiveState.ReadOnly -> {
                        onNodeWithTag(DropdownTestTags.FIELD_DIVIDER, useUnmergedTree = true)
                            .assertIsDisplayed()

                        onNodeWithTag(DropdownTestTags.FIELD_WARNING_ICON, useUnmergedTree = true)
                            .assertIsNotDisplayed()

                        onNodeWithTag(DropdownTestTags.FIELD_ERROR_ICON, useUnmergedTree = true)
                            .assertIsNotDisplayed()
                    }
                    is DropdownInteractiveState.Warning ->
                        onNodeWithTag(DropdownTestTags.FIELD_WARNING_ICON, useUnmergedTree = true)
                            .assertIsDisplayed()

                    is DropdownInteractiveState.Error -> {
                        onNodeWithTag(DropdownTestTags.FIELD_ERROR_ICON, useUnmergedTree = true)
                            .assertIsDisplayed()

                        onNodeWithTag(DropdownTestTags.FIELD_DIVIDER, useUnmergedTree = true)
                            .assertIsNotDisplayed()
                    }
                    is DropdownInteractiveState.Disabled ->
                        onNodeWithTag(DropdownTestTags.FIELD)
                            .assertHasNoClickAction()
                            .assert(isFocusable().not())
                }
            }
        }
    }

    @Test
    fun dropdownField_validateSemantics() {
        composeTestRule.run {
            onNodeWithTag(DropdownTestTags.FIELD)
                .assertHasClickAction()
                .requestFocus()
                .assertIsFocused()
        }
    }

    @Test
    fun dropdownField_disabled_validateSemantics() {
        composeTestRule.run {
            state = DropdownInteractiveState.Disabled
            onNodeWithTag(DropdownTestTags.FIELD)
                .assertHasNoClickAction()
                .assertIsNotEnabled()
                .assert(isFocusable().not())
        }
    }

    @Test
    fun dropdownField_readOnly_validateSemantics() {
        composeTestRule.run {
            state = DropdownInteractiveState.ReadOnly
            onNodeWithTag(DropdownTestTags.FIELD)
                .assertIsNotEnabled()
                .assert(isFocusable())
        }
    }
}
