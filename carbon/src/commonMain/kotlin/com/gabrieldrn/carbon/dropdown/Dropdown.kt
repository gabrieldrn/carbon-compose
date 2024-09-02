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
import com.gabrieldrn.carbon.dropdown.base.DropdownPopupContent
import com.gabrieldrn.carbon.dropdown.base.DropdownSize
import com.gabrieldrn.carbon.dropdown.base.DropdownStateIcon
import com.gabrieldrn.carbon.dropdown.base.dpSize

/**
 * # Dropdown
 *
 * Dropdowns present a list of options from which a user can select one option.
 * A selected option can represent a value in a form, or can be used as an action to filter or sort
 * existing content.
 *
 * Only one option can be selected at a time.
 * - By default, the dropdown displays placeholder text in the field when closed.
 * - Clicking on a closed field opens a menu of options.
 * - Selecting an option from the menu closes it and the selected option text replaces the
 * placeholder text in the field and also remains as an option in place if the menu is open.
 *
 * (From [Dropdown documentation](https://carbondesignsystem.com/components/dropdown/usage/))
 *
 * @param K Type to identify the options.
 * @param expanded Whether the dropdown is expanded or not.
 * @param placeholder The text to be displayed in the field when no option is selected.
 * @param options The options to be displayed in the dropdown menu. A map signature ensures that the
 * @param selectedOption The currently selected option. When not null, the option associated with
 * this key will be displayed in the field.
 * keys are unique and can be used to identify the selected option. The strings associated with each
 * key are the texts to be displayed in the dropdown menu.
 * @param onOptionSelected Callback invoked when an option is selected. The selected option key is
 * passed as a parameter, and the callback should be used to update a remembered state with the new
 * value.
 * @param onExpandedChange Callback invoked when the expanded state of the dropdown changes. It
 * should be used to update a remembered state with the new value.
 * @param onDismissRequest Callback invoked when the dropdown menu should be dismissed.
 * @param modifier The modifier to be applied to the dropdown.
 * @param label The label to be displayed above the dropdown field (optionnal).
 * @param state The [DropdownInteractiveState] of the dropdown.
 * @param dropdownSize The size of the dropdown, in terms of height. Defaults to
 * [DropdownSize.Large].
 * @param isInlined Whether the dropdown should have the inline modification or not.
 * @param minVisibleItems The minimum number of items to be visible in the dropdown menu before the
 * user needs to scroll. This value is used to calculate the height of the menu. Defaults to 4.
 * @throws IllegalArgumentException If the options map is empty.
 */
@Composable
public fun <K : Any> Dropdown(
    expanded: Boolean,
    placeholder: String,
    options: Map<K, DropdownOption>,
    selectedOption: K?,
    onOptionSelected: (K) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    state: DropdownInteractiveState = DropdownInteractiveState.Enabled,
    dropdownSize: DropdownSize = DropdownSize.Large,
    isInlined: Boolean = false,
    minVisibleItems: Int = 4,
) {
    val fieldText = remember(selectedOption) {
        options[selectedOption]?.value ?: placeholder
    }

    val actualMinVisibleItems = remember(minVisibleItems) {
        if (minVisibleItems < 1) {
            Logger.w("minVisibleItems must be > 0. Using 1 instead.")
        }
        minVisibleItems.coerceAtLeast(1)
    }

    val colors = DropdownColors.colors()

    BaseDropdown(
        expanded = expanded,
        options = options,
        onExpandedChange = onExpandedChange,
        onDismissRequest = onDismissRequest,
        minVisibleItems = actualMinVisibleItems,
        colors = colors,
        modifier = modifier,
        label = label,
        state = state,
        dropdownSize = dropdownSize,
        fieldContent = {
            DropdownPlaceholderText(
                placeholderText = fieldText,
                colors = colors,
                state = state,
                modifier = Modifier.then(if (isInlined) Modifier else Modifier.weight(1f))
            )

            DropdownStateIcon(state = state)
        },
        popupContent = {
            DropdownPopupContent(
                selectedOption = selectedOption,
                options = options,
                colors = colors,
                componentHeight = dropdownSize.dpSize(),
                onOptionClicked = { option ->
                    onOptionSelected(option)
                    onDismissRequest()
                },
                modifier = Modifier.anchor()
            )
        }
    )
}

/**
 * # Dropdown
 *
 * Dropdowns present a list of options from which a user can select one option.
 * A selected option can represent a value in a form, or can be used as an action to filter or sort
 * existing content.
 *
 * Only one option can be selected at a time.
 * - By default, the dropdown displays placeholder text in the field when closed.
 * - Clicking on a closed field opens a menu of options.
 * - Selecting an option from the menu closes it and the selected option text replaces the
 * placeholder text in the field and also remains as an option in place if the menu is open.
 *
 * This overload is a convenience function that uses a mutable state to manage the expanded state.
 *
 * (From [Dropdown documentation](https://carbondesignsystem.com/components/dropdown/usage/))
 *
 * @param K Type to identify the options.
 * @param placeholder The text to be displayed in the field when no option is selected.
 * @param options The options to be displayed in the dropdown menu. A map signature ensures that the
 * @param selectedOption The currently selected option. When not null, the option associated with
 * this key will be displayed in the field.
 * keys are unique and can be used to identify the selected option. The strings associated with each
 * key are the texts to be displayed in the dropdown menu.
 * @param onOptionSelected Callback invoked when an option is selected. The selected option key is
 * passed as a parameter, and the callback should be used to update a remembered state with the new
 * value.
 * @param modifier The modifier to be applied to the dropdown.
 * @param label The label to be displayed above the dropdown field (optionnal).
 * @param state The [DropdownInteractiveState] of the dropdown.
 * @param dropdownSize The size of the dropdown, in terms of height. Defaults to
 * [DropdownSize.Large].
 * @param isInlined Whether the dropdown should have the inline modification or not.
 * @param minVisibleItems The minimum number of items to be visible in the dropdown menu before the
 * user needs to scroll. This value is used to calculate the height of the menu. Defaults to 4.
 * @throws IllegalArgumentException If the options map is empty.
 */
@Composable
public fun <K : Any> Dropdown(
    placeholder: String,
    options: Map<K, DropdownOption>,
    selectedOption: K?,
    onOptionSelected: (K) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    state: DropdownInteractiveState = DropdownInteractiveState.Enabled,
    dropdownSize: DropdownSize = DropdownSize.Large,
    isInlined: Boolean = false,
    minVisibleItems: Int = 4,
) {
    var isExpanded by remember { mutableStateOf(false) }

    val actualMinVisibleItems = remember(minVisibleItems) {
        if (minVisibleItems < 1) {
            Logger.w("minVisibleItems must be > 0. Using 1 instead.")
        }
        minVisibleItems.coerceAtLeast(1)
    }

    Dropdown(
        expanded = isExpanded,
        placeholder = placeholder,
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected,
        onExpandedChange = { isExpanded = it },
        onDismissRequest = { isExpanded = false },
        minVisibleItems = actualMinVisibleItems,
        modifier = modifier,
        label = label,
        state = state,
        dropdownSize = dropdownSize,
        isInlined = isInlined
    )
}
