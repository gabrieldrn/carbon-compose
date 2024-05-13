package carbon.android.dropdown

import androidx.annotation.IntRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import carbon.android.dropdown.base.BaseDropdown
import carbon.android.dropdown.base.DropdownColors
import carbon.android.dropdown.base.DropdownInteractiveState
import carbon.android.dropdown.base.DropdownOption
import carbon.android.dropdown.base.DropdownPlaceholderText
import carbon.android.dropdown.base.DropdownPopupContent
import carbon.android.dropdown.base.DropdownSize
import carbon.android.dropdown.base.DropdownStateIcon
import carbon.android.foundation.color.LocalCarbonTheme

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
    @IntRange(from = 1) minVisibleItems: Int = 4,
) {
    val fieldText = remember(selectedOption) {
        options[selectedOption]?.value ?: placeholder
    }

    val colors = DropdownColors(LocalCarbonTheme.current)

    BaseDropdown(
        expanded = expanded,
        options = options,
        onExpandedChange = onExpandedChange,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        label = label,
        state = state,
        dropdownSize = dropdownSize,
        colors = colors,
        minVisibleItems = minVisibleItems,
        fieldContent = {
            DropdownPlaceholderText(
                placeholderText = fieldText,
                colors = colors,
                state = state,
            )

            DropdownStateIcon(state = state)
        },
        popupContent = {
            DropdownPopupContent(
                selectedOption = selectedOption,
                options = options,
                colors = colors,
                componentHeight = dropdownSize.height,
                onOptionClicked = { option ->
                    onOptionSelected(option)
                    onDismissRequest()
                },
                modifier = Modifier.anchor()
            )
        }
    )
}
