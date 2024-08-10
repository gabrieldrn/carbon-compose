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

package carbon.compose.dropdown.multiselect

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import carbon.compose.Carbon
import carbon.compose.checkbox.Checkbox
import carbon.compose.dropdown.base.DropdownColors
import carbon.compose.dropdown.base.DropdownMenuOptionDivider
import carbon.compose.dropdown.base.DropdownOption
import carbon.compose.dropdown.base.DropdownTestTags
import carbon.compose.foundation.interaction.FocusIndication
import carbon.compose.common.selectable.SelectableInteractiveState
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.Text

/**
 * Shifts selected options to the top of the list.
 */
private fun <K : Any> List<Map.Entry<K, DropdownOption>>.shiftSelectedOptionsToTop(
    selectedOptions: List<K>
): List<Map.Entry<K, DropdownOption>> {
    if (selectedOptions.isEmpty()) return this
    val selectedOptionsMap = selectedOptions.associateWith { true }
    return partition {
        selectedOptionsMap.containsKey(it.key)
    }.let { (selected, unselected) ->
        selected.sortedBy { it.value.value } + unselected
    }
}

@Composable
internal fun <K : Any> MultiselectDropdownPopupContent(
    options: Map<K, DropdownOption>,
    selectedOptions: List<K>,
    colors: DropdownColors,
    componentHeight: Dp,
    onOptionClicked: (K) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    val optionEntries = remember {
        options.entries
            .toList()
            .shiftSelectedOptionsToTop(selectedOptions)
    }

    val firstSelectedOptionIndex = remember(optionEntries, selectedOptions) {
        optionEntries.indexOfFirst { it.key == selectedOptions.firstOrNull() }
    }

    LazyColumn(
        modifier = modifier
            .background(color = colors.menuOptionBackgroundColor)
            .testTag(DropdownTestTags.POPUP_CONTENT)
    ) {
        itemsIndexed(optionEntries) { index, optionEntry ->
            SideEffect {
                if (index == 0) {
                    focusRequester.requestFocus()
                }
            }
            MultiselectDropdownMenuOption(
                option = optionEntry.value,
                isSelected = optionEntry.key in selectedOptions,
                onOptionClicked = { onOptionClicked(optionEntry.key) },
                // Hide divider: first item + when previous item is selected.
                showDivider = index != 0 && index - 1 != firstSelectedOptionIndex,
                colors = colors,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(componentHeight)
                    .then(
                        if (index == 0) {
                            Modifier.focusRequester(focusRequester)
                        } else {
                            Modifier
                        }
                    )
            )
        }
    }
}

@Composable
private fun MultiselectDropdownMenuOption(
    option: DropdownOption,
    isSelected: Boolean,
    colors: DropdownColors,
    showDivider: Boolean,
    onOptionClicked: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val menuOptionBackgroundSelectedColor by colors.menuOptionBackgroundSelectedColor(
        isSelected = isSelected
    )

    val menuOptionTextColor by colors.menuOptionTextColor(
        isEnabled = option.enabled,
        isSelected = isSelected
    )

    Box(
        modifier = modifier
            .selectable(
                selected = isSelected,
                interactionSource = interactionSource,
                indication = FocusIndication(),
                enabled = option.enabled,
                onClick = onOptionClicked
            )
            .background(
                color = menuOptionBackgroundSelectedColor,
            )
            .padding(horizontal = SpacingScale.spacing05)
            .testTag(DropdownTestTags.MENU_OPTION)
    ) {
        if (showDivider) {
            DropdownMenuOptionDivider(
                colors.menuOptionBorderColor,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .testTag(DropdownTestTags.MENU_OPTION_DIVIDER)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Checkbox(
                checked = isSelected,
                interactiveState = if (option.enabled) {
                    SelectableInteractiveState.Default
                } else {
                    SelectableInteractiveState.Disabled
                },
                label = "",
                onClick = onOptionClicked,
                modifier = Modifier.testTag(DropdownTestTags.MENU_OPTION_CHECKBOX)
            )
            Text(
                text = option.value,
                style = Carbon.typography.bodyCompact01,
                color = menuOptionTextColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
