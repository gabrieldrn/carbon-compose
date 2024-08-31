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

package com.gabrieldrn.carbon.dropdown.multiselect

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger
import com.gabrieldrn.carbon.dropdown.base.BaseDropdown
import com.gabrieldrn.carbon.dropdown.base.DropdownColors
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.base.DropdownOption
import com.gabrieldrn.carbon.dropdown.base.DropdownPlaceholderText
import com.gabrieldrn.carbon.dropdown.base.DropdownSize
import com.gabrieldrn.carbon.dropdown.base.DropdownStateIcon
import com.gabrieldrn.carbon.dropdown.base.dpSize
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

/**
 * # Multiselect Dropdown
 *
 * Use when you can select multiple options from a list or to filter information.
 *
 * **Making a selection**
 * - By default, the dropdown displays placeholder text in the field when closed.
 * - Clicking a closed field opens a menu of options. Each option contains a checkbox input to the
 *   left of the option text.
 * - The menu stays open while options are being selected. The menu closes by clicking the field or
 *   outside of the dropdown.
 * - Once options have been selected from the menu, a tag appears to the left of the text in the
 *   field containing the total number of selected options. The placeholder text can change to text
 *   that better reflects what is selected.
 * - Selected options shift to the top of the menu in alphanumeric order.
 * - Unlike dropdown and combo box, the menu does not close once the user makes selections. Because
 *   multiple selections are possible, the user needs to click outside of the dropdown or on the
 *   parent element to close the menu.
 *
 * (From [Dropdown documentation](https://carbondesignsystem.com/components/dropdown/usage#multiselect))
 *
 * @param K Type to identify the options.
 * @param expanded Whether the dropdown is expanded or not.
 * @param placeholder The text to be displayed in the field when no option is selected.
 * @param options The options to be displayed in the dropdown menu. A map signature ensures that the
 * keys are unique and can be used to identify the selected option. The strings associated with each
 * key are the texts to be displayed in the dropdown menu.
 * @param selectedOptions The currently selected options.
 * @param onOptionClicked Callback invoked when an option is clicked. The option key is passed as a
 * parameter, and the callback should be used to update a remembered state with the new value.
 * @param onClearSelection Callback invoked when the clear selection button is clicked.
 * @param onExpandedChange Callback invoked when the expanded state of the dropdown changes. It
 * should be used to update a remembered state with the new value.
 * @param onDismissRequest Callback invoked when the dropdown menu should be dismissed.
 * @param modifier The modifier to be applied to the dropdown.
 * @param label The label to be displayed above the dropdown field (optionnal).
 * @param state The [DropdownInteractiveState] of the dropdown.
 * @param dropdownSize The size of the dropdown, in terms of height. Defaults to
 * [DropdownSize.Large].
 * @param minVisibleItems The minimum number of items to be visible in the dropdown menu before the
 * user needs to scroll. This value is used to calculate the height of the menu. Defaults to 4.
 * @throws IllegalArgumentException If the options map is empty.
 */
@Composable
public fun <K : Any> MultiselectDropdown(
    expanded: Boolean,
    placeholder: String,
    options: Map<K, DropdownOption>,
    selectedOptions: List<K>,
    onOptionClicked: (K) -> Unit,
    onClearSelection: () -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    state: DropdownInteractiveState = DropdownInteractiveState.Enabled,
    dropdownSize: DropdownSize = DropdownSize.Large,
    minVisibleItems: Int = 4,
) {
    val colors = DropdownColors.colors()

    val actualMinVisibleItems = remember(minVisibleItems) {
        if (minVisibleItems < 1) {
            Logger.w("minVisibleItems must be > 0. Using 1 instead.")
        }
        minVisibleItems.coerceAtLeast(1)
    }

    BaseDropdown(
        expanded = expanded,
        options = options,
        onExpandedChange = onExpandedChange,
        onDismissRequest = onDismissRequest,
        colors = colors,
        modifier = modifier,
        label = label,
        state = state,
        dropdownSize = dropdownSize,
        minVisibleItems = actualMinVisibleItems,
        fieldContent = {
            if (selectedOptions.isNotEmpty() && state !is DropdownInteractiveState.Disabled) {
                DropdownMultiselectTag(
                    state = state,
                    count = selectedOptions.size,
                    onCloseTagClick = onClearSelection,
                    modifier = Modifier.padding(end = SpacingScale.spacing03)
                )
            }

            DropdownPlaceholderText(
                placeholderText = placeholder,
                colors = colors,
                state = state,
            )

            DropdownStateIcon(state = state)
        },
        popupContent = {
            MultiselectDropdownPopupContent(
                options = options,
                selectedOptions = selectedOptions,
                colors = colors,
                componentHeight = dropdownSize.dpSize(),
                onOptionClicked = onOptionClicked,
                modifier = Modifier.anchor()
            )
        }
    )
}

/**
 * # Multiselect Dropdown
 *
 * Use when you can select multiple options from a list or to filter information.
 *
 * **Making a selection**
 * - By default, the dropdown displays placeholder text in the field when closed.
 * - Clicking a closed field opens a menu of options. Each option contains a checkbox input to the
 *   left of the option text.
 * - The menu stays open while options are being selected. The menu closes by clicking the field or
 *   outside of the dropdown.
 * - Once options have been selected from the menu, a tag appears to the left of the text in the
 *   field containing the total number of selected options. The placeholder text can change to text
 *   that better reflects what is selected.
 * - Selected options shift to the top of the menu in alphanumeric order.
 * - Unlike dropdown and combo box, the menu does not close once the user makes selections. Because
 *   multiple selections are possible, the user needs to click outside of the dropdown or on the
 *   parent element to close the menu.
 *
 * This overload is a convenience function that uses a mutable state to manage the expanded state.
 *
 * (From [Dropdown documentation](https://carbondesignsystem.com/components/dropdown/usage#multiselect))
 *
 * @param K Type to identify the options.
 * @param placeholder The text to be displayed in the field when no option is selected.
 * @param options The options to be displayed in the dropdown menu. A map signature ensures that the
 * keys are unique and can be used to identify the selected option. The strings associated with each
 * key are the texts to be displayed in the dropdown menu.
 * @param selectedOptions The currently selected options.
 * @param onOptionClicked Callback invoked when an option is clicked. The option key is passed as a
 * parameter, and the callback should be used to update a remembered state with the new value.
 * @param onClearSelection Callback invoked when the clear selection button is clicked.
 * @param modifier The modifier to be applied to the dropdown.
 * @param label The label to be displayed above the dropdown field (optionnal).
 * @param state The [DropdownInteractiveState] of the dropdown.
 * @param dropdownSize The size of the dropdown, in terms of height. Defaults to
 * [DropdownSize.Large].
 * @param minVisibleItems The minimum number of items to be visible in the dropdown menu before the
 * user needs to scroll. This value is used to calculate the height of the menu. Defaults to 4.
 * @throws IllegalArgumentException If the options map is empty.
 */
@Composable
public fun <K : Any> MultiselectDropdown(
    placeholder: String,
    options: Map<K, DropdownOption>,
    selectedOptions: List<K>,
    onOptionClicked: (K) -> Unit,
    onClearSelection: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    state: DropdownInteractiveState = DropdownInteractiveState.Enabled,
    dropdownSize: DropdownSize = DropdownSize.Large,
    minVisibleItems: Int = 4,
) {
    var isExpanded by remember { mutableStateOf(false) }

    val actualMinVisibleItems = remember(minVisibleItems) {
        if (minVisibleItems < 1) {
            Logger.w("minVisibleItems must be > 0. Using 1 instead.")
        }
        minVisibleItems.coerceAtLeast(1)
    }

    MultiselectDropdown(
        expanded = isExpanded,
        placeholder = placeholder,
        options = options,
        selectedOptions = selectedOptions,
        onOptionClicked = onOptionClicked,
        onClearSelection = onClearSelection,
        onExpandedChange = { isExpanded = it },
        onDismissRequest = { isExpanded = false },
        modifier = modifier,
        label = label,
        state = state,
        dropdownSize = dropdownSize,
        minVisibleItems = actualMinVisibleItems,
    )
}
