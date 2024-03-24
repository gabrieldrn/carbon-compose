package carbon.compose.dropdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.interaction.FocusIndication
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text

private val checkmarkSize = 16.dp

/**
 * DropdownPopupContent is the composable used to display the popup content of the dropdown. It
 * displays a list of options that can be selected.
 *
 * @param K The type of option keys.
 * @param options The list of options to display. The keys are the values that will be returned when
 * an option is selected, and the values are the strings that will be displayed in the dropdown.
 * @param selectedOption The currently selected option. If null, no option is selected.
 * @param colors The colors to use for the dropdown.
 * @param componentHeight The height of each option in the dropdown.
 * @param onOptionSelected The callback to call when an option is selected.
 * @param modifier The modifier to apply to the composable.
 */
@Composable
internal fun <K : Any> DropdownPopupContent(
    options: Map<K, DropdownOption>,
    selectedOption: K?,
    colors: DropdownColors,
    componentHeight: Dp,
    onOptionSelected: (K) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    val optionEntries = options.entries.toList()
    val selectedOptionIndex = optionEntries.indexOfFirst { it.key == selectedOption }

    // Option to focus on when the composition ends.
    val compositionEndTargetOption = selectedOption ?: options.keys.first()

    LazyColumn(
        state = rememberLazyListState(
            initialFirstVisibleItemIndex = options.keys.indexOf(compositionEndTargetOption)
        ),
        modifier = modifier
            .background(color = colors.menuOptionBackgroundColor)
            .testTag(DropdownTestTags.POPUP_CONTENT)
    ) {
        itemsIndexed(optionEntries) { index, optionEntry ->
            SideEffect {
                if (optionEntry.key == compositionEndTargetOption) {
                    focusRequester.requestFocus()
                }
            }
            DropdownMenuOption(
                option = optionEntry.value,
                isSelected = index == selectedOptionIndex,
                onOptionSelected = { onOptionSelected(optionEntry.key) },
                // Hide divider: first item + when previous item is selected.
                showDivider = index != 0 && index - 1 != selectedOptionIndex,
                colors = colors,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(componentHeight)
                    .then(
                        if (optionEntry.key == compositionEndTargetOption) {
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
private fun DropdownMenuOption(
    option: DropdownOption,
    isSelected: Boolean,
    colors: DropdownColors,
    showDivider: Boolean,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(
        modifier = modifier
            .selectable(
                selected = isSelected,
                interactionSource = interactionSource,
                indication = FocusIndication(),
                enabled = option.enabled,
                onClick = onOptionSelected
            )
            .background(
                color = colors.menuOptionBackgroundSelectedColor(isSelected = isSelected),
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
            Text(
                text = option.value,
                style = CarbonTypography.bodyCompact01,
                color = colors.menuOptionTextColor(
                    isEnabled = option.enabled,
                    isSelected = isSelected
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            if (isSelected) {
                Image(
                    imageVector = dropdownCheckmarkIcon,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colors.checkmarkIconColor),
                    modifier = Modifier
                        .size(checkmarkSize)
                        .testTag(DropdownTestTags.MENU_OPTION_CHECKMARK)
                )
            } else {
                Spacer(modifier = Modifier.size(checkmarkSize))
            }
        }
    }
}

@Composable
private fun DropdownMenuOptionDivider(
    color: Color,
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .background(color = color)
            .height(1.dp)
            .fillMaxWidth()
    )
}
