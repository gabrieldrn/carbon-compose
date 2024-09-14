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
import androidx.compose.runtime.remember
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
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
import com.gabrieldrn.carbon.dropdown.multiselect.DropdownMultiselectTag
import com.gabrieldrn.carbon.foundation.color.WhiteTheme
import kotlin.test.Test

class MultiselectDropdownFieldTest : DropdownFieldTest() {

    override fun ComposeUiTest.setup() {
        setContent {
            val expandedStates = remember { MutableTransitionState(false) }
            val transition = updateTransition(expandedStates, "Dropdown")

            CarbonDesignSystem(WhiteTheme) {
                val colors = DropdownColors.colors()

                DropdownField(
                    state = state,
                    dropdownSize = DropdownSize.Large,
                    expandTransition = transition,
                    expandedStates = expandedStates,
                    colors = colors,
                    onExpandedChange = { expandedStates.targetState = it },
                    isInlined = false,
                    fieldContent = {
                        DropdownMultiselectTag(
                            state = state,
                            count = 1,
                            onCloseTagClick = {}
                        )

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

    override fun onContentValidation(
        testScope: ComposeUiTest,
        state: DropdownInteractiveState
    ) {
        super.onContentValidation(testScope, state)

        testScope.onNodeWithTag(DropdownTestTags.FIELD_MULTISELECT_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    override fun onLayoutValidationGetFieldContentWidths(
        testScope: ComposeUiTest,
        state: DropdownInteractiveState,
        contentWidths: MutableList<Dp>
    ) {
        super.onLayoutValidationGetFieldContentWidths(testScope, state, contentWidths)

        contentWidths.add(
            testScope.onNodeWithTag(DropdownTestTags.FIELD_MULTISELECT_TAG, useUnmergedTree = true)
                .getUnclippedBoundsInRoot()
                .width
        )
    }

    @Test
    fun dropdownField_multiselectTag_validateSemantics() = runComposeUiTest {
        setup()

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
