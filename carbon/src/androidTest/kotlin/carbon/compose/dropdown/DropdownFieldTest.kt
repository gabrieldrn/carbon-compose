package carbon.compose.dropdown

import androidx.annotation.CallSuper
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
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.width
import carbon.compose.CarbonDesignSystem
import carbon.compose.dropdown.base.DropdownField
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.dropdown.base.DropdownPlaceholderText
import carbon.compose.dropdown.base.DropdownSize
import carbon.compose.dropdown.base.DropdownStateIcon
import carbon.compose.dropdown.base.DropdownTestTags
import carbon.compose.foundation.color.WhiteTheme
import carbon.compose.foundation.spacing.SpacingScale
import org.junit.Before
import org.junit.Rule
import org.junit.Test

open class DropdownFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    val sideComposeTestRule = createComposeRule()

    protected var state by mutableStateOf<DropdownInteractiveState>(
        DropdownInteractiveState.Enabled
    )
    protected val placeholder = "Dropdown"

    @Before
    open fun setup() {
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

    @CallSuper
    open fun onContentValidation(
        testRule: ComposeContentTestRule,
        state: DropdownInteractiveState
    ): Unit = with(testRule) {
        onNodeWithTag(DropdownTestTags.FIELD)
            .assertIsDisplayed()
            .assertHeightIsEqualTo(DropdownSize.Large.height)

        onNodeWithTag(DropdownTestTags.FIELD_PLACEHOLDER, useUnmergedTree = true)
            .assertIsDisplayed()

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

    @Test
    fun dropdownField_validateContent() {
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
                onContentValidation(this, state)
            }
        }
    }

    @CallSuper
    open fun onLayoutValidationGetFieldContentWidths(
        testRule: ComposeContentTestRule,
        state: DropdownInteractiveState,
        contentWidths: MutableList<Dp>
    ): Unit = with(testRule) {
        contentWidths += onNodeWithTag(
            DropdownTestTags.FIELD_PLACEHOLDER,
            useUnmergedTree = true
        ).getUnclippedBoundsInRoot().width

        when (state) {
            is DropdownInteractiveState.Enabled,
            is DropdownInteractiveState.ReadOnly,
            is DropdownInteractiveState.Disabled -> {}
            is DropdownInteractiveState.Warning ->
                onNodeWithTag(DropdownTestTags.FIELD_WARNING_ICON, useUnmergedTree = true)
                    .assertIsDisplayed()
                    .also {
                        contentWidths.add(it.getUnclippedBoundsInRoot().width)
                        contentWidths.add(SpacingScale.spacing03 * 2) // Horizontal padding
                    }

            is DropdownInteractiveState.Error ->
                onNodeWithTag(DropdownTestTags.FIELD_ERROR_ICON, useUnmergedTree = true)
                    .assertIsDisplayed()
                    .also {
                        contentWidths.add(it.getUnclippedBoundsInRoot().width)
                        contentWidths.add(SpacingScale.spacing03 * 2) // Horizontal padding
                    }
        }
    }

    @Test
    fun dropdownField_validateLayout() {
        composeTestRule.run {
            val warningMessage = "Warning message goes here"
            val errorMessage = "Error message goes here"

            val contentWidths = mutableListOf<Dp>()

            listOf(
                DropdownInteractiveState.Enabled,
                DropdownInteractiveState.Warning(warningMessage),
                DropdownInteractiveState.Error(errorMessage),
                DropdownInteractiveState.Disabled,
                DropdownInteractiveState.ReadOnly
            ).forEach {
                state = it

                onLayoutValidationGetFieldContentWidths(this, state, contentWidths)

                onNodeWithTag(DropdownTestTags.FIELD_LAYOUT, useUnmergedTree = true)
                    .assertLeftPositionInRootIsEqualTo(SpacingScale.spacing05)
                    .assertWidthIsEqualTo(contentWidths.reduce(Dp::plus))

                contentWidths.clear()
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
