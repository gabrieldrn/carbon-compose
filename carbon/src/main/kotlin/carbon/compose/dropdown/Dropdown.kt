package carbon.compose.dropdown

import androidx.annotation.IntRange
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import carbon.compose.dropdown.domain.getOptionsPopupHeightRatio
import carbon.compose.foundation.input.onEnterKeyEvent
import carbon.compose.foundation.interaction.FocusIndication
import carbon.compose.foundation.motion.Motion
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text

private val dropdownHeight = 40.dp
private val dropdownOptionHeight = 40.dp

private val dropdownTransitionSpecFloat = tween<Float>(
    durationMillis = Motion.Duration.moderate01,
    easing = Motion.Standard.productiveEasing
)

private val dropdownTransitionSpecDp = tween<Dp>(
    durationMillis = Motion.Duration.moderate01,
    easing = Motion.Standard.productiveEasing
)

private const val CHEVRON_ROTATION_ANGLE = 180f

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
 * @param fieldPlaceholderText The text to be displayed in the field when no option is selected.
 * @param selectedOption The currently selected option. When not null, the option associated with
 * this key will be displayed in the field.
 * @param options The options to be displayed in the dropdown menu. A map signature ensures that the
 * keys are unique and can be used to identify the selected option. The strings associated with each
 * key are the texts to be displayed in the dropdown menu.
 * @param onOptionSelected Callback invoked when an option is selected. The selected option is
 * passed as a parameter, and the callback should be used to update a remembered state with the new
 * value.
 * @param onExpandedChange Callback invoked when the expanded state of the dropdown changes. It
 * should be used to update a remembered state with the new value.
 * @param onDismissRequest Callback invoked when the dropdown menu should be dismissed.
 * @param modifier The modifier to be applied to the dropdown.
 * @param minVisibleItems The minimum number of items to be visible in the dropdown menu before the
 * user needs to scroll. This value is used to calculate the height of the menu. Defaults to 4.
 * @throws IllegalArgumentException If the options map is empty.
 */
@Composable
public fun <K : Any> Dropdown(
    expanded: Boolean,
    fieldPlaceholderText: String,
    selectedOption: K?,
    options: Map<K, String>,
    onOptionSelected: (K) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    @IntRange(from = 1) minVisibleItems: Int = 4
) {
    require(options.isNotEmpty()) {
        "Dropdown must have at least one option."
    }

    val interactionSource = remember { MutableInteractionSource() }

    val expandedStates = remember { MutableTransitionState(false) }
    expandedStates.targetState = expanded

    val colors = DropdownColors.colors()

    val fieldText = remember(selectedOption) { options[selectedOption] ?: fieldPlaceholderText }

    val transition = updateTransition(expandedStates, "Dropdown")
    val maxHeight = getOptionsPopupHeightRatio(options.size, minVisibleItems)
        .times(dropdownOptionHeight)

    val height by transition.animateDp(
        transitionSpec = { dropdownTransitionSpecDp },
        label = "Popup content height"
    ) {
        if (it) maxHeight else 0.dp
    }

    val elevation by transition.animateDp(
        transitionSpec = { dropdownTransitionSpecDp },
        label = "Popup content shadow"
    ) {
        if (it) 3.dp else 0.dp
    }

    BoxWithConstraints(
        modifier = modifier
            .height(dropdownHeight)
            .indication(
                interactionSource = interactionSource,
                indication = FocusIndication()
            )
            .then(
                InspectableModifier {
                    debugInspectorInfo {
                        properties["isExpanded"] = expanded.toString()
                    }
                }
            )
    ) {
        DropdownField(
            interactionSource = interactionSource,
            transition = transition,
            expandedStates = expandedStates,
            fieldPlaceholderText = fieldText,
            colors = colors,
            onExpandedChange = onExpandedChange
        )

        Spacer(
            modifier = Modifier
                .background(color = colors.fieldBorderColor)
                .height(1.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )

        // TODO Place popup on top of the field if the menu doesn't have enough space below it.
        if (expandedStates.currentState || expandedStates.targetState && options.isNotEmpty()) {
            Popup(
                popupPositionProvider = DropdownMenuPositionProvider,
                onDismissRequest = onDismissRequest,
                properties = PopupProperties(focusable = true)
            ) {
                DropdownContent(
                    selectedOption = selectedOption,
                    options = options,
                    colors = colors,
                    onOptionSelected = { option ->
                        onOptionSelected(option)
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .width(maxWidth)
                        .height(height)
                        // This should be a box shadow (-> 0 2px 6px 0 rgba(0,0,0,.2)). But compose
                        // doesn't provide the same API as CSS for shadows. A 3dp elevation is the
                        // best approximation that could be found for now.
                        .shadow(elevation = elevation)
                        .onEscape(onDismissRequest)
                )
            }
        }
    }
}

@Composable
private fun DropdownField(
    transition: Transition<Boolean>,
    expandedStates: MutableTransitionState<Boolean>,
    fieldPlaceholderText: String,
    colors: DropdownColors,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val chevronRotation by transition.animateFloat(
        transitionSpec = { dropdownTransitionSpecFloat },
        label = "Chevron rotation"
    ) {
        if (it) CHEVRON_ROTATION_ANGLE else 0f
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .focusable(interactionSource = interactionSource)
            .fillMaxHeight()
            .background(colors.fieldBackgroundColor)
            .padding(horizontal = 16.dp)
            .pointerInput(Unit) {
                awaitEachGesture {
                    // Custom pointer input to handle input events on the field.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val expandStateOnDown = expandedStates.currentState
                    waitForUpOrCancellation(pass = PointerEventPass.Initial)?.let {
                        // Avoid expanding back if the dropdown was expanded on down.
                        if (!expandStateOnDown) {
                            onExpandedChange(!expandedStates.currentState)
                        }
                    }
                }
            }
            .onEnterKeyEvent {
                onExpandedChange(!expandedStates.currentState)
            }
            .semantics(mergeDescendants = true) {
                onClick {
                    onExpandedChange(!expandedStates.currentState)
                    true
                }
            }
            .testTag(DropdownTestTags.FIELD)
    ) {
        Text(
            text = fieldPlaceholderText,
            style = CarbonTypography.bodyCompact01,
            color = colors.fieldTextColor,
            modifier = Modifier
                .weight(1f)
                .testTag(DropdownTestTags.FIELD_PLACEHOLDER)
        )
        Image(
            imageVector = chevronDownIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(colors.chevronIconColor),
            modifier = Modifier
                .padding(start = 16.dp)
                .graphicsLayer {
                    rotationZ = chevronRotation
                }
                .testTag(DropdownTestTags.FIELD_CHEVRON)
        )
    }
}

@Composable
internal fun <K : Any> DropdownContent(
    options: Map<K, String>,
    selectedOption: K?,
    colors: DropdownColors,
    onOptionSelected: (K) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentItemFocusRequester = remember { FocusRequester() }

    val actualSelectedOption = selectedOption ?: options.keys.first()

    LazyColumn(
        state = rememberLazyListState(
            initialFirstVisibleItemIndex = options.keys.indexOf(actualSelectedOption)
        ),
        modifier = modifier
            .background(color = colors.menuOptionBackgroundColor)
            .testTag(DropdownTestTags.POPUP_CONTENT)
    ) {
        itemsIndexed(options.entries.toList()) { index, option ->
            SideEffect {
                if (option.key == actualSelectedOption) {
                    currentItemFocusRequester.requestFocus()
                }
            }
            DropdownMenuOption(
                optionValue = option.value,
                onOptionSelected = { onOptionSelected(option.key) },
                showDivider = index != 0,
                colors = colors,
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (option.key == actualSelectedOption) {
                            Modifier.focusRequester(currentItemFocusRequester)
                        } else {
                            Modifier
                        }
                    )
            )
        }
    }
}

private fun Modifier.onEscape(block: () -> Unit) = onPreviewKeyEvent {
    if (it.key == Key.Escape) {
        block()
        true
    } else {
        false
    }
}

@Composable
private fun DropdownMenuOption(
    optionValue: String,
    colors: DropdownColors,
    showDivider: Boolean,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(
        modifier = modifier
            .height(dropdownOptionHeight)
            .clickable(
                interactionSource = interactionSource,
                indication = FocusIndication(),
                onClick = onOptionSelected
            )
            .padding(horizontal = 16.dp)
            .testTag(DropdownTestTags.MENU_OPTION)
    ) {
        if (showDivider) {
            DropdownMenuOptionDivider(
                colors.menuOptionBorderColor,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = optionValue,
                style = CarbonTypography.bodyCompact01,
                color = colors.menuOptionTextColor,
            )
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

private object DropdownMenuPositionProvider : PopupPositionProvider {

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset = IntOffset(
        x = anchorBounds.left,
        y = anchorBounds.top + anchorBounds.height
    )
}

internal object DropdownTestTags {
    const val FIELD: String = "carbon_dropdown_field"
    const val FIELD_PLACEHOLDER: String = "carbon_dropdown_field_placeholder"
    const val FIELD_CHEVRON: String = "carbon_dropdown_field_chevron"
    const val POPUP_CONTENT: String = "carbon_dropdown_popup_content"
    const val MENU_OPTION: String = "carbon_dropdown_menu_option"
}
