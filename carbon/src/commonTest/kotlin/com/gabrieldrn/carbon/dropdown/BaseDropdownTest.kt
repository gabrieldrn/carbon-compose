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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.dropdown.base.BaseDropdown
import com.gabrieldrn.carbon.dropdown.base.DropdownColors
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.base.DropdownOption
import com.gabrieldrn.carbon.dropdown.base.DropdownPlaceholderText
import com.gabrieldrn.carbon.dropdown.base.DropdownSize
import com.gabrieldrn.carbon.dropdown.base.DropdownTestTags
import com.gabrieldrn.carbon.dropdown.base.dpSize
import kotlin.test.Test
import kotlin.test.assertTrue

class BaseDropdownTest {

    private val options = (0..9).associateWith { DropdownOption("Option $it") }
    private val minVisibleItems = 4

    private var label by mutableStateOf<String?>("Dropdown")
    private var state by mutableStateOf<DropdownInteractiveState>(DropdownInteractiveState.Enabled)
    private var dropdownSize by mutableStateOf(DropdownSize.Large)
    private var isExpanded by mutableStateOf(false)
    private var selectedOptionKey by mutableStateOf<Int?>(null)
    private val placeholder = "Dropdown"

    private val popupContentMockTestTag = "PopupContent"

    fun ComposeUiTest.setup() {
        setContent {
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .testTag(popupContentMockTestTag)
                    )
                },
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
    fun baseDropdown_label_validateLayout() = runComposeUiTest {
        setup()

        onNodeWithTag(DropdownTestTags.LABEL_TEXT)
            .assertIsDisplayed()
            .assert(hasText(placeholder))

        label = "   "
        onNodeWithTag(DropdownTestTags.LABEL_TEXT)
            .assertDoesNotExist()

        label = ""
        onNodeWithTag(DropdownTestTags.LABEL_TEXT)
            .assertDoesNotExist()

        label = null
        onNodeWithTag(DropdownTestTags.LABEL_TEXT)
            .assertDoesNotExist()

        tearDown()
    }

    @Test
    fun baseDropdown_withStates_validateLayout() = runComposeUiTest {
        setup()

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
                .assertDoesNotExist()

            when (state) {
                is DropdownInteractiveState.Enabled,
                is DropdownInteractiveState.ReadOnly ->
                    onNodeWithTag(DropdownTestTags.HELPER_TEXT)
                        .assertDoesNotExist()

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

        tearDown()
    }

    @Test
    fun baseDropdown_field_validateSize() = runComposeUiTest {
        setup()

        onNodeWithTag(DropdownTestTags.FIELD)
            .assertHeightIsEqualTo(DropdownSize.Large.dpSize())

        dropdownSize = DropdownSize.Small

        onNodeWithTag(DropdownTestTags.FIELD)
            .assertHeightIsEqualTo(DropdownSize.Small.dpSize())

        dropdownSize = DropdownSize.Medium

        onNodeWithTag(DropdownTestTags.FIELD)
            .assertHeightIsEqualTo(DropdownSize.Medium.dpSize())

        tearDown()
    }

    @Test
    fun baseDropdown_optionsPopup_expandAndCollapse() = runComposeUiTest {
        setup()

        onNodeWithTag(popupContentMockTestTag)
            .assertDoesNotExist()

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
//        onNodeWithTag(DropdownTestTags.FIELD)
//            .performClick()
        isExpanded = false

        onNodeWithTag(popupContentMockTestTag)
            .assertDoesNotExist()

        tearDown()
    }
}
