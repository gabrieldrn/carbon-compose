package carbon.android.dropdown.base

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import carbon.android.dropdown.base.DropdownInteractiveState.Companion.helperText
import carbon.android.dropdown.base.DropdownInteractiveState.Companion.isFocusable
import carbon.android.dropdown.domain.getChevronStartSpacing
import carbon.android.foundation.color.LocalCarbonTheme
import carbon.android.foundation.input.onEnterKeyEvent
import carbon.android.foundation.interaction.FocusIndication
import carbon.android.foundation.motion.Motion
import carbon.android.foundation.spacing.SpacingScale
import carbon.android.foundation.text.CarbonTypography
import carbon.android.foundation.text.Text
import carbon.android.icons.ErrorIcon
import carbon.android.icons.WarningIcon
import carbon.android.semantics.readOnly

private val dropdownTransitionSpecFloat = tween<Float>(
    durationMillis = Motion.Duration.moderate01,
    easing = Motion.Standard.productiveEasing
)

private const val CHEVRON_ROTATION_ANGLE = 180f

/**
 * Sets up a custom clickability for the dropdown field.
 *
 * `pointerInput` handles pointer events.
 * `onEnterKeyEvent` handles physical enter key events (keyboard).
 * `semantics` handles accessibility events.
 */
private fun Modifier.dropdownClickable(
    expandedStates: MutableTransitionState<Boolean>,
    onClick: () -> Unit
): Modifier = this
    .pointerInput(onClick) {
        awaitEachGesture {
            // Custom pointer input to handle input events on the field.
            val downEvent = awaitFirstDown(pass = PointerEventPass.Initial)
            val expandStateOnDown = expandedStates.currentState
            waitForUpOrCancellation(pass = PointerEventPass.Initial)?.let {
                // Avoid expanding back if the dropdown was expanded on down.
                if (!downEvent.isConsumed && !expandStateOnDown) {
                    onClick()
                }
            }
        }
    }
    .onEnterKeyEvent {
        onClick()
    }
    .semantics(mergeDescendants = true) {
        onClick {
            onClick()
            true
        }
    }

/**
 * Composable representing the field of a dropdown. Its content is driven by [fieldContent] as it
 * only provides the field structure and the chevron icon (common to all kinds of dropdowns).
 *
 * [DropdownPlaceholderText] and [DropdownStateIcon] are two available components intended to be
 * used as content for the field.
 */
@Composable
internal fun DropdownField(
    state: DropdownInteractiveState,
    dropdownSize: DropdownSize,
    transition: Transition<Boolean>,
    expandedStates: MutableTransitionState<Boolean>,
    onExpandedChange: (Boolean) -> Unit,
    fieldContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    colors: DropdownColors = DropdownColors(LocalCarbonTheme.current),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val chevronRotation by transition.animateFloat(
        transitionSpec = { dropdownTransitionSpecFloat },
        label = "Chevron rotation"
    ) {
        if (it) CHEVRON_ROTATION_ANGLE else 0f
    }

    Box(
        modifier = modifier
            .indication(
                interactionSource = interactionSource,
                indication = FocusIndication()
            )
            .focusable(
                enabled = state.isFocusable,
                interactionSource = interactionSource
            )
            .height(dropdownSize.height)
            .background(colors.fieldBackgroundColor(state))
            .then(
                if (state is DropdownInteractiveState.Error) {
                    Modifier.border(
                        width = SpacingScale.spacing01,
                        color = colors.fieldBorderErrorColor
                    )
                } else {
                    Modifier
                }
            )
            .then(
                when (state) {
                    is DropdownInteractiveState.Disabled -> Modifier.semantics { disabled() }
                    is DropdownInteractiveState.ReadOnly -> Modifier.readOnly(
                        role = Role.DropdownList,
                        interactionSource = interactionSource,
                        mergeDescendants = true
                    )
                    else -> Modifier.dropdownClickable(
                        expandedStates = expandedStates,
                        onClick = { onExpandedChange(!expandedStates.currentState) }
                    )
                }
            )
            .semantics(mergeDescendants = true) {
                role = Role.DropdownList
                state.helperText?.let { stateDescription = it }
            }
            .testTag(DropdownTestTags.FIELD)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = SpacingScale.spacing05)
        ) {
            Layout(
                content = fieldContent,
                measurePolicy = DropdownFieldContentMeasurePolicy(),
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .testTag(DropdownTestTags.FIELD_LAYOUT),
            )

            Image(
                imageVector = chevronDownIcon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(colors.chevronIconColor(state)),
                modifier = Modifier
                    .padding(start = getChevronStartSpacing(state))
                    .graphicsLayer {
                        rotationZ = chevronRotation
                    }
                    .testTag(DropdownTestTags.FIELD_CHEVRON)
            )
        }

        if (state !is DropdownInteractiveState.Error) {
            Spacer(
                modifier = Modifier
                    .background(color = colors.fieldBorderColor(state))
                    .height(1.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .testTag(DropdownTestTags.FIELD_DIVIDER)
            )
        }
    }
}

@Composable
internal fun DropdownPlaceholderText(
    placeholderText: String,
    state: DropdownInteractiveState,
    modifier: Modifier = Modifier,
    colors: DropdownColors = DropdownColors(LocalCarbonTheme.current),
) {
    Text(
        text = placeholderText,
        style = CarbonTypography.bodyCompact01,
        color = colors.fieldTextColor(state),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .layoutId(DropdownFieldContentId.PLACEHOLDER)
            .testTag(DropdownTestTags.FIELD_PLACEHOLDER)
    )
}

@Composable
internal fun DropdownStateIcon(
    state: DropdownInteractiveState,
    modifier: Modifier = Modifier
) {
    when (state) {
        is DropdownInteractiveState.Warning -> WarningIcon(
            modifier = modifier
                .padding(horizontal = SpacingScale.spacing03)
                .layoutId(DropdownFieldContentId.STATE_ICON)
                .testTag(DropdownTestTags.FIELD_WARNING_ICON)
        )
        is DropdownInteractiveState.Error -> ErrorIcon(
            modifier = modifier
                .padding(horizontal = SpacingScale.spacing03)
                .layoutId(DropdownFieldContentId.STATE_ICON)
                .testTag(DropdownTestTags.FIELD_ERROR_ICON)
        )
        else -> {}
    }
}

internal object DropdownFieldContentId {
    const val PLACEHOLDER = "placeholder"
    const val STATE_ICON = "stateIcon"
    const val MULTISELECT_TAG = "multiselectTag"

    val ids = listOf(PLACEHOLDER, STATE_ICON, MULTISELECT_TAG)
}
