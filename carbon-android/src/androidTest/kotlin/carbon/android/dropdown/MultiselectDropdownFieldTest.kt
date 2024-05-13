package carbon.android.dropdown

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.width
import carbon.android.CarbonDesignSystem
import carbon.android.dropdown.base.DropdownField
import carbon.android.dropdown.base.DropdownInteractiveState
import carbon.android.dropdown.base.DropdownPlaceholderText
import carbon.android.dropdown.base.DropdownSize
import carbon.android.dropdown.base.DropdownStateIcon
import carbon.android.dropdown.base.DropdownTestTags
import carbon.android.dropdown.multiselect.DropdownMultiselectTag
import carbon.android.foundation.color.WhiteTheme
import org.junit.Before
import org.junit.Test

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

    @Test
    fun dropdownField_multiselectTag_validateSemantics() {
        composeTestRule.run {
            interactiveStates.forEach { state ->
                this@MultiselectDropdownFieldTest.state = state

                if (state in arrayOf(
                        DropdownInteractiveState.Disabled, DropdownInteractiveState.ReadOnly
                    )
                ) {
                    onNodeWithTag(DropdownTestTags.FIELD_MULTISELECT_TAG, useUnmergedTree = true)
                        .assertIsNotEnabled()
                } else {
                    onNodeWithTag(DropdownTestTags.FIELD_MULTISELECT_TAG, useUnmergedTree = true)
                        .assertIsEnabled()
                        .assertHasClickAction()
                }
            }
        }
    }
}
