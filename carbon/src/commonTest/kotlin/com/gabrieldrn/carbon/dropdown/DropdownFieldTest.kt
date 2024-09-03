/*
 * Copyright 2024 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gabrieldrn.carbon.dropdown

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.width
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.dropdown.base.DropdownColors
import com.gabrieldrn.carbon.dropdown.base.DropdownField
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.base.DropdownPlaceholderText
import com.gabrieldrn.carbon.dropdown.base.DropdownSize
import com.gabrieldrn.carbon.dropdown.base.DropdownStateIcon
import com.gabrieldrn.carbon.dropdown.base.DropdownTestTags
import com.gabrieldrn.carbon.dropdown.base.dpSize
import com.gabrieldrn.carbon.foundation.color.WhiteTheme
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.semantics.assertIsReadOnly
import kotlin.test.Test

open class DropdownFieldTest {

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

    open fun ComposeUiTest.setup() {
        setContent {
            val expandedStates = remember { MutableTransitionState(false) }
            val transition = updateTransition(expandedStates, "Dropdown")

            CarbonDesignSystem(WhiteTheme) {
                val colors = DropdownColors.colors()
                DropdownField(
                    state = state,
                    dropdownSize = DropdownSize.Large,
                    transition = transition,
                    expandedStates = expandedStates,
                    colors = colors,
                    onExpandedChange = { expandedStates.targetState = it },
                    isInlined = false,
                    fieldContent = {
                        DropdownPlaceholderText(
                            placeholderText = placeholder,
                            state = state,
                            colors = colors
                        )

                        DropdownStateIcon(state = state)
                    }
                )
            }
        }
    }

    open fun onContentValidation(
        testScope: ComposeUiTest,
        state: DropdownInteractiveState
    ): Unit = with(testScope) {
        onNodeWithTag(DropdownTestTags.FIELD)
            .assertIsDisplayed()
            .assertHeightIsEqualTo(DropdownSize.Large.dpSize())

        onNodeWithTag(DropdownTestTags.FIELD_PLACEHOLDER, useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithTag(DropdownTestTags.FIELD_CHEVRON, useUnmergedTree = true)
            .assertIsDisplayed()

        when (state) {
            is DropdownInteractiveState.Enabled,
            is DropdownInteractiveState.ReadOnly -> {
                onNodeWithTag(DropdownTestTags.FIELD_WARNING_ICON, useUnmergedTree = true)
                    .assertDoesNotExist()

                onNodeWithTag(DropdownTestTags.FIELD_ERROR_ICON, useUnmergedTree = true)
                    .assertDoesNotExist()
            }
            is DropdownInteractiveState.Warning ->
                onNodeWithTag(DropdownTestTags.FIELD_WARNING_ICON, useUnmergedTree = true)
                    .assertIsDisplayed()

            is DropdownInteractiveState.Error ->
                onNodeWithTag(DropdownTestTags.FIELD_ERROR_ICON, useUnmergedTree = true)
                    .assertIsDisplayed()

            is DropdownInteractiveState.Disabled ->
                onNodeWithTag(DropdownTestTags.FIELD)
                    .assertHasNoClickAction()
                    .assert(isFocusable().not())
        }
    }

    @Test
    fun dropdownField_validateContent() = runComposeUiTest {
        setup()
        interactiveStates.forEach {
            state = it
            onContentValidation(this, state)
        }
    }

    open fun onLayoutValidationGetFieldContentWidths(
        testScope: ComposeUiTest,
        state: DropdownInteractiveState,
        contentWidths: MutableList<Dp>
    ): Unit = with(testScope) {
        contentWidths.add(
            onNodeWithTag(
                DropdownTestTags.FIELD_PLACEHOLDER,
                useUnmergedTree = true
            ).getUnclippedBoundsInRoot().width
        )

        contentWidths.add(
            onNodeWithTag(
                DropdownTestTags.FIELD_CHEVRON,
                useUnmergedTree = true
            ).getUnclippedBoundsInRoot().width
        )

        when (state) {
            is DropdownInteractiveState.Enabled,
            is DropdownInteractiveState.ReadOnly,
            is DropdownInteractiveState.Disabled ->
                contentWidths.add(SpacingScale.spacing05) // Chevron padding

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
    fun dropdownField_validateLayout() = runComposeUiTest {
        setup()
        val contentWidths = mutableListOf<Dp>()

        interactiveStates.forEach {
            state = it

            onLayoutValidationGetFieldContentWidths(this, state, contentWidths)

            onNodeWithTag(DropdownTestTags.FIELD, useUnmergedTree = true)
                .assertLeftPositionInRootIsEqualTo(SpacingScale.spacing05)
                .assertWidthIsEqualTo(contentWidths.reduce(Dp::plus))

            contentWidths.clear()
        }
    }

    @Test
    fun dropdownField_validateSemantics() = runComposeUiTest {
        setup()
        onNodeWithTag(DropdownTestTags.FIELD)
            .assertHasClickAction()
            .requestFocus()
            .assertIsFocused()
    }

    @Test
    fun dropdownField_disabled_validateSemantics() = runComposeUiTest {
        setup()
        state = DropdownInteractiveState.Disabled
        onNodeWithTag(DropdownTestTags.FIELD)
            .assertHasNoClickAction()
            .assertIsNotEnabled()
            .assert(isFocusable().not())
    }

    @Test
    fun dropdownField_readOnly_validateSemantics() = runComposeUiTest {
        setup()
        state = DropdownInteractiveState.ReadOnly
        onNodeWithTag(DropdownTestTags.FIELD)
            .assertIsReadOnly()
    }
}
