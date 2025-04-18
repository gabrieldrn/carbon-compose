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

package com.gabrieldrn.carbon.dropdown.base

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.common.molecules.AnimatedChevronDownIcon
import com.gabrieldrn.carbon.common.semantics.readOnly
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState.Companion.helperText
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState.Companion.isFocusable
import com.gabrieldrn.carbon.dropdown.domain.getChevronStartSpacing
import com.gabrieldrn.carbon.foundation.input.onEnterKeyEvent
import com.gabrieldrn.carbon.foundation.interaction.FocusIndication
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text
import com.gabrieldrn.carbon.icons.WarningAltFilledIcon
import com.gabrieldrn.carbon.icons.WarningFilledIcon

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
    expandTransition: Transition<Boolean>,
    expandedStates: MutableTransitionState<Boolean>,
    colors: DropdownColors,
    isInlined: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    fieldContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val fieldBackgroundColor by colors.fieldBackgroundColor(state)
    val fieldBorderColor by colors.fieldBorderColor(state)
    val chevronIconColor by colors.chevronIconColor(state)

    Layout(
        content = {
            fieldContent()

            AnimatedChevronDownIcon(
                animationTransition = expandTransition,
                tint = chevronIconColor,
                modifier = Modifier
                    .padding(start = getChevronStartSpacing(state))
                    .layoutId(DropdownFieldContentId.CHEVRON)
                    .testTag(DropdownTestTags.FIELD_CHEVRON)
            )
        },
        modifier = modifier
            .indication(
                interactionSource = interactionSource,
                indication = FocusIndication(Carbon.theme)
            )
            .focusable(
                enabled = state.isFocusable,
                interactionSource = interactionSource
            )
            .height(dropdownSize.dpSize())
            .drawBehind { // Background and bottom border
                if (isInlined) return@drawBehind

                drawRect(fieldBackgroundColor)

                if (state !is DropdownInteractiveState.Error) {
                    // Canvas starts at 0.5px, so offset to draw a crisp line.
                    val y = size.height + 0.5f
                    drawLine(
                        color = fieldBorderColor,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
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
            .padding(horizontal = SpacingScale.spacing05)
            .semantics(mergeDescendants = true) {
                role = Role.DropdownList
                state.helperText?.let { stateDescription = it }
            }
            .testTag(DropdownTestTags.FIELD),
        measurePolicy = DropdownFieldContentMeasurePolicy(isInlined = isInlined)
    )
}

@Composable
internal fun DropdownPlaceholderText(
    placeholderText: String,
    state: DropdownInteractiveState,
    colors: DropdownColors,
    modifier: Modifier = Modifier
) {
    Text(
        text = placeholderText,
        style = Carbon.typography.bodyCompact01,
        color = colors.fieldTextColor(state).value,
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
        is DropdownInteractiveState.Warning -> WarningAltFilledIcon(
            modifier = modifier
                .padding(horizontal = SpacingScale.spacing03)
                .layoutId(DropdownFieldContentId.STATE_ICON)
                .testTag(DropdownTestTags.FIELD_WARNING_ICON),
            tint = Carbon.theme.supportWarning,
            innerTint = Color.Black
        )
        is DropdownInteractiveState.Error -> WarningFilledIcon(
            modifier = modifier
                .padding(horizontal = SpacingScale.spacing03)
                .layoutId(DropdownFieldContentId.STATE_ICON)
                .testTag(DropdownTestTags.FIELD_ERROR_ICON),
            tint = Carbon.theme.supportError
        )
        else -> {}
    }
}

internal object DropdownFieldContentId {
    const val PLACEHOLDER = "placeholder"
    const val STATE_ICON = "stateIcon"
    const val MULTISELECT_TAG = "multiselectTag"
    const val CHEVRON = "chevron"

    val ids = setOf(PLACEHOLDER, STATE_ICON, MULTISELECT_TAG, CHEVRON)
}
