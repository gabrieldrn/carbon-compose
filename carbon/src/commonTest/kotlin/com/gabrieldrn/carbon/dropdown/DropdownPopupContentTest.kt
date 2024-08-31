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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ComposeUiTest
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
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.dropdown.base.*
import com.gabrieldrn.carbon.dropdown.base.DropdownColors
import com.gabrieldrn.carbon.dropdown.base.DropdownPopupContent
import com.gabrieldrn.carbon.dropdown.base.DropdownTestTags
import com.gabrieldrn.carbon.toList
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DropdownPopupContentTest {

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

    fun ComposeUiTest.setup() {
        setContent {
            Column(Modifier.fillMaxWidth()) {
                DropdownPopupContent(
                    options = options,
                    selectedOption = selectedOptionKey,
                    colors = DropdownColors.colors(),
                    componentHeight = dropdownSize.dpSize(),
                    onOptionClicked = { selectedOptionKey = it },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    fun tearDown() {
        isExpanded = false
        selectedOptionKey = null
    }

    @Test
    fun optionsLayout_validateSize() = runComposeUiTest {
        setup()

        onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
            .toList()
            .forEach {
                it.assertHeightIsEqualTo(dropdownSize.dpSize())
            }

        tearDown()
    }

    @Test
    fun optionsLayout_validateListIsLaidOut() = runComposeUiTest {
        setup()

        onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
            .assertCountEquals(options.size)

        tearDown()
    }

    @Test
    fun optionsLayout_validateSemanticsActions() = runComposeUiTest {
        setup()

        onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
            .filter(hasText("Disabled").not())
            .assertAll(isFocusable() and isSelectable() and hasClickAction())

        onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
            .filterToOne(hasText("Disabled"))
            .assert(isNotEnabled() and isNotFocusable())

        tearDown()
    }

    @Test
    fun optionsLayout_noSelection_validateNoOptionSelected() = runComposeUiTest {
        setup()

        assertNull(selectedOptionKey)
        onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
            .assertAll(isNotSelected())

        tearDown()
    }

    @Test
    fun optionsLayout_optionSelected_validateOptionIsSelected() = runComposeUiTest {
        setup()

        val selected = 1
        selectedOptionKey = selected
        onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
            .filterToOne(isSelected())
            .assert(hasText(options[selected]!!.value))

        tearDown()
    }

    @Test
    fun optionsLayout_optionSelected_validateCheckmarkIsDisplayed() = runComposeUiTest {
        setup()

        selectedOptionKey = 1
        onAllNodesWithTag(DropdownTestTags.MENU_OPTION_CHECKMARK, useUnmergedTree = true)
            .assertCountEquals(1)
            .onFirst()
            .assertIsDisplayed()

        tearDown()
    }

    @Test
    fun optionsLayout_optionSelected_validateDividerIsHidden() = runComposeUiTest {
        setup()

        onAllNodesWithTag(DropdownTestTags.MENU_OPTION_DIVIDER, useUnmergedTree = true)
            .assertCountEquals(4)

        selectedOptionKey = 1

        onAllNodesWithTag(DropdownTestTags.MENU_OPTION_DIVIDER, useUnmergedTree = true)
            .assertCountEquals(3)
            .filterToOne(hasText("Option 3"))
            .assertDoesNotExist()

        tearDown()
    }

    @Test
    fun optionsLayout_onOptionSelected_validateCallbackIsInvoked() = runComposeUiTest {
        setup()

        onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
            .onFirst()
            .performClick()
        assertNotNull(selectedOptionKey)

        tearDown()
    }

    @Test
    fun optionsLayout_onDisabledOptionSelected_validateOptionIsNotSelected() = runComposeUiTest {
        setup()

        onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
            .filterToOne(hasText("Disabled"))
            .performClick()
        assertNull(selectedOptionKey)

        tearDown()
    }
}
