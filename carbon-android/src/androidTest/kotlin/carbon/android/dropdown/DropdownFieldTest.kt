package carbon.android.dropdown

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
import carbon.android.CarbonDesignSystem
import carbon.android.dropdown.base.DropdownField
import carbon.android.dropdown.base.DropdownInteractiveState
import carbon.android.dropdown.base.DropdownPlaceholderText
import carbon.android.dropdown.base.DropdownSize
import carbon.android.dropdown.base.DropdownStateIcon
import carbon.android.dropdown.base.DropdownTestTags
import carbon.android.foundation.color.WhiteTheme
import carbon.android.foundation.spacing.SpacingScale
import carbon.android.semantics.assertIsReadOnly
import org.junit.Before
import org.junit.Rule
import org.junit.Test

open class DropdownFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    protected var state by mutableStateOf<DropdownInteractiveState>(
        DropdownInteractiveState.Enabled
    )
    protected val placeholder = "Dropdown"

    protected val interactiveStates = listOf(
        DropdownInteractiveState.Enabled,
        DropdownInteractiveState.Warning("Warning message goes here"),
        DropdownInteractiveState.Error("Error message goes here"),
        DropdownInteractiveState.Disabled,
        DropdownInteractiveState.ReadOnly
    )

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
            interactiveStates.forEach {
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
            val contentWidths = mutableListOf<Dp>()

            interactiveStates.forEach {
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
                .assertIsReadOnly()
        }
    }
}
