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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.base.DropdownOption
import com.gabrieldrn.carbon.dropdown.base.DropdownSize
import com.gabrieldrn.carbon.dropdown.base.DropdownTestTags
import com.gabrieldrn.carbon.toList
import kotlin.test.Test
import kotlin.test.assertEquals

class DropdownTest {

    private var options by mutableStateOf((0..9).associateWith { DropdownOption("Option $it") })
    private val minVisibleItems = 4

    private var label by mutableStateOf<String?>("Dropdown")
    private var state by mutableStateOf<DropdownInteractiveState>(DropdownInteractiveState.Enabled)
    private var dropdownSize by mutableStateOf(DropdownSize.Large)
    private var isExpanded by mutableStateOf(false)
    private var selectedOptionKey by mutableStateOf<Int?>(null)
    private val placeholder = "Dropdown"

    fun ComposeUiTest.setup() {
        setContent {
            Dropdown(
                label = label,
                expanded = isExpanded,
                placeholder = placeholder,
                selectedOption = selectedOptionKey,
                options = options,
                onOptionSelected = { selectedOptionKey = it },
                onExpandedChange = { isExpanded = it },
                onDismissRequest = { isExpanded = false },
                minVisibleItems = minVisibleItems,
                dropdownSize = dropdownSize,
                state = state,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    fun tearDown() {
        label = "Dropdown"
        isExpanded = false
        selectedOptionKey = null
        dropdownSize = DropdownSize.Large
    }

    @Test
    fun dropdown_optionsPopup_validateLayout() = runComposeUiTest {
        setup()

        isExpanded = true

        onNodeWithTag(DropdownTestTags.POPUP_CONTENT)
            .assertIsDisplayed()

        onAllNodesWithTag(DropdownTestTags.MENU_OPTION).run {
            assertCountEquals(minVisibleItems + 1)
            toList().forEach { it.assertIsDisplayed() }
        }

        onNodeWithTag(DropdownTestTags.POPUP_CONTENT)
            .performScrollToNode(hasText("Option 9"))

        onNode(hasTestTag(DropdownTestTags.MENU_OPTION).and(hasText("Option 9")))
            .assertIsDisplayed()

        tearDown()
    }

    @Test
    fun dropdown_optionsPopup_empty_validateLayout() = runComposeUiTest {
        selectedOptionKey = null
        options = mapOf()

        setup()

        isExpanded = true

        onNodeWithTag(DropdownTestTags.POPUP_CONTENT)
            .assertIsNotDisplayed()

        onAllNodesWithTag(DropdownTestTags.MENU_OPTION).assertCountEquals(0)

        tearDown()
    }

    @Test
    fun dropdown_optionsPopup_onOptionClick_validateCallbackIsInvoked() = runComposeUiTest {
        setup()

        isExpanded = true

        onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
            .onFirst()
            .performClick()

        assertEquals(
            expected = 0,
            actual = selectedOptionKey
        )

        tearDown()
    }

    @Test
    fun dropdown_optionsPopup_onOptionClick_validatePlaceholderFollowsState() = runComposeUiTest {
        setup()

        onNodeWithTag(DropdownTestTags.FIELD)
            .assert(hasText(placeholder))
            .performClick()

        onAllNodesWithTag(DropdownTestTags.MENU_OPTION)
            .onFirst()
            .performClick()

        onNodeWithTag(DropdownTestTags.FIELD)
            .assert(hasText("Option 0"))

        tearDown()
    }

    @Test
    fun dropdown_optionsPopup_validatePlaceholderValueOnCompositionComplete() = runComposeUiTest {
        setup()

        selectedOptionKey = 0
        onNodeWithTag(DropdownTestTags.FIELD)
            .assert(hasText("Option 0"))

        tearDown()
    }
}
