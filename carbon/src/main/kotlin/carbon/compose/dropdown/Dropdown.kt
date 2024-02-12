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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
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
import carbon.compose.foundation.input.onEnterKeyEvent
import carbon.compose.foundation.interaction.FocusIndication
import carbon.compose.foundation.motion.Motion
import carbon.compose.foundation.text.CarbonTypography
import carbon.compose.foundation.text.Text

private val dropdownOptionHeight = 40.dp

private val dropdownTransitionSpecFloat = tween<Float>(
    durationMillis = Motion.Duration.moderate01,
    easing = Motion.Standard.productiveEasing
)

private val dropdownTransitionSpecDp = tween<Dp>(
    durationMillis = Motion.Duration.moderate01,
    easing = Motion.Standard.productiveEasing
)

@Composable
public fun <OptionKey : Any> Dropdown(
    expanded: Boolean,
    fieldPlaceholderText: String,
    selectedOption: OptionKey?,
    options: Map<OptionKey, String>,
    onOptionSelected: (OptionKey) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    @IntRange(from = 1) visibleItemsBeforeScroll: Int = 4
) {
    val interactionSource = remember { MutableInteractionSource() }

    val expandedStates = remember { MutableTransitionState(false) }
    expandedStates.targetState = expanded

    val transition = updateTransition(expandedStates, "Dropdown")

    val colors = DropdownColors.colors()

    val chevronRotation by transition.animateFloat(
        transitionSpec = { dropdownTransitionSpecFloat },
        label = "Chevron rotation"
    ) {
        if (it) 180f else 0f
    }

    BoxWithConstraints(
        modifier = modifier
            .height(40.dp)
            .indication(
                interactionSource = interactionSource,
                indication = FocusIndication()
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
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
                            // Avoid expand back if the dropdown was expanded on down.
                            if (!expandStateOnDown) {
                                onExpandedChange(!expandedStates.currentState)
                            }
                        }
                    }
                }
                .onEnterKeyEvent {
                    onExpandedChange(!expandedStates.currentState)
                }
                .semantics {
                    onClick {
                        onExpandedChange(!expandedStates.currentState)
                        true
                    }
                }
        ) {
            Text(
                text = options[selectedOption] ?: fieldPlaceholderText,
                style = CarbonTypography.bodyCompact01,
                color = colors.fieldTextColor,
                modifier = Modifier.weight(1f)
            )
            Image(
                imageVector = chevronDownIcon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(colors.chevronIconColor),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .rotate(chevronRotation)
            )
        }
        Spacer(
            modifier = Modifier
                .background(color = colors.fieldBorderColor)
                .height(1.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )

        // TODO Place popup on top of the field if the menu doesn't have enough space below it.
        if (expandedStates.currentState || expandedStates.targetState) {
            Popup(
                popupPositionProvider = DropdownMenuPositionProvider,
                onDismissRequest = onDismissRequest,
                properties = PopupProperties(focusable = true)
            ) {
                DropdownContent(
                    selectedOption = selectedOption,
                    options = options,
                    visibleItemsBeforeScroll = visibleItemsBeforeScroll,
                    transition = transition,
                    colors = colors,
                    onOptionSelected = { option ->
                        onOptionSelected(option)
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .width(maxWidth)
                        .onEscape(onDismissRequest)
                )
            }
        }
    }
}

@Composable
private fun <OptionKey : Any> DropdownContent(
    options: Map<OptionKey, String>,
    selectedOption: OptionKey?,
    visibleItemsBeforeScroll: Int,
    transition: Transition<Boolean>,
    colors: DropdownColors,
    onOptionSelected: (OptionKey) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentItemFocusRequester = remember { FocusRequester() }
    val maxHeight =
        options.size.coerceAtMost(visibleItemsBeforeScroll.coerceAtLeast(1)) *
            dropdownOptionHeight +
            dropdownOptionHeight * .5f

    val actualSelectedOption = selectedOption ?: options.keys.first()

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

    LazyColumn(
        state = rememberLazyListState(
            initialFirstVisibleItemIndex = options.keys.indexOf(actualSelectedOption)
        ),
        modifier = modifier
            .height(height)
            // This should be a box shadow (-> 0 2px 6px 0 rgba(0,0,0,.2)). But compose
            // doesn't provide the same API as CSS for shadows. A 3dp elevation is the
            // best approximation that could be found for now.
            .shadow(elevation = elevation)
            .background(color = colors.menuOptionBackgroundColor)
    ) {
        itemsIndexed(options.entries.toList()) { index, option ->
            SideEffect {
                if (option.key == actualSelectedOption) {
                    currentItemFocusRequester.requestFocus()
                }
            }
            DropdownMenuOption(
                option = option,
                onOptionSelected = onOptionSelected,
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
private fun <OptionKey : Any> DropdownMenuOption(
    option: Map.Entry<OptionKey, String>,
    colors: DropdownColors,
    showDivider: Boolean,
    onOptionSelected: (OptionKey) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(
        modifier = modifier
            .height(dropdownOptionHeight)
            .clickable(
                interactionSource = interactionSource,
                indication = FocusIndication(),
                onClick = { onOptionSelected(option.key) }
            )
            .padding(horizontal = 16.dp)
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
                text = option.value,
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
