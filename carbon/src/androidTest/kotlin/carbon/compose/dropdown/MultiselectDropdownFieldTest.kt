package carbon.compose.dropdown

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.width
import carbon.compose.CarbonDesignSystem
import carbon.compose.dropdown.base.DropdownField
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.base.DropdownPlaceholderText
import carbon.compose.dropdown.base.DropdownSize
import carbon.compose.dropdown.base.DropdownStateIcon
import carbon.compose.dropdown.base.DropdownTestTags
import carbon.compose.dropdown.multiselect.DropdownMultiselectTag
import carbon.compose.foundation.color.WhiteTheme
import org.junit.Before

class MultiselectDropdownFieldTest : DropdownFieldTest() {

    @Before
    override fun setup() {
        composeTestRule.setContent {
            val expandedStates = remember { MutableTransitionState(false) }
            val transition = updateTransition(expandedStates, "Dropdown")

            CarbonDesignSystem(WhiteTheme) {
                DropdownField(
                    state = state,
                    dropdownSize = DropdownSize.Large,
                    transition = transition,
                    expandedStates = expandedStates,
                    onExpandedChange = { expandedStates.targetState = it },
                    fieldContent = {
                        DropdownMultiselectTag(
                            state = state,
                            count = 1,
                            onCloseTagClick = {}
                        )

                        DropdownPlaceholderText(
                            placeholderText = placeholder,
                            state = state,
                        )

                        DropdownStateIcon(state = state)
                    }
                )
            }
        }
    }

    override fun onContentValidation(
        testRule: ComposeContentTestRule,
        state: DropdownInteractiveState
    ) {
        super.onContentValidation(testRule, state)

        with(testRule) {
            onNodeWithTag(DropdownTestTags.FIELD_MULTISELECT_TAG, useUnmergedTree = true)
                .assertIsDisplayed()
        }
    }

    override fun onLayoutValidationGetFieldContentWidths(
        testRule: ComposeContentTestRule,
        state: DropdownInteractiveState,
        contentWidths: MutableList<Dp>
    ) {
        super.onLayoutValidationGetFieldContentWidths(testRule, state, contentWidths)

        with(testRule) {
            contentWidths.add(
                onNodeWithTag(DropdownTestTags.FIELD_MULTISELECT_TAG, useUnmergedTree = true)
                    .getUnclippedBoundsInRoot()
                    .width
            )
        }
    }
}
