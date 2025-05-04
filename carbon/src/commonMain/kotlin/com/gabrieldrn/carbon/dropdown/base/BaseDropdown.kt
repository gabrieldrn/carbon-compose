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
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.testTag
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
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState.Companion.helperText
import com.gabrieldrn.carbon.dropdown.domain.getOptionsPopupHeightRatio
import com.gabrieldrn.carbon.foundation.motion.Motion
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text

private val dropdownTransitionSpecDp = tween<Dp>(
    durationMillis = Motion.Duration.moderate01,
    easing = Motion.Standard.productiveEasing
)

private val inlinedPopupWidth = 288.dp

/**
 * Adds a callback to be invoked when the escape key (hardware) is pressed.
 */
private fun Modifier.onEscape(block: () -> Unit) = onPreviewKeyEvent {
    if (it.key == Key.Escape) {
        block()
        true
    } else {
        false
    }
}

/**
 * Common composable to all kinds of dropdowns.
 * A dropdown consists of a field and a popup. Field content is driven by [fieldContent] and popup
 * content by [popupContent]. Contents of the field should be marked with layout ids from
 * [DropdownFieldContentId].
 * This composable should not be used directly but rather used by the dropdown variants.
 */
@Composable
internal fun <K : Any> BaseDropdown(
    expanded: Boolean,
    options: Map<K, DropdownOption>,
    colors: DropdownColors,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    minVisibleItems: Int,
    modifier: Modifier = Modifier,
    label: String? = null,
    state: DropdownInteractiveState = DropdownInteractiveState.Enabled,
    dropdownSize: DropdownSize = DropdownSize.Large,
    isInlined: Boolean = false,
    fieldContent: @Composable () -> Unit,
    popupContent: @Composable DropdownPopupScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val expandedStates = remember { MutableTransitionState(expanded) }
    val expandTransition = rememberTransition(expandedStates, "Dropdown")

    LaunchedEffect(expanded) {
        expandedStates.targetState = expanded
    }

    val labelTextColor by colors.labelTextColor(state)
    val helperTextColor by colors.helperTextColor(state)

    val fieldAndPopup = @Composable {
        FieldAndPopupLayout(
            options = options,
            dropdownSize = dropdownSize,
            expandTransition = expandTransition,
            minVisibleItems = minVisibleItems,
            isInlined = isInlined,
            onDismissRequest = onDismissRequest,
            field = {
                DropdownField(
                    state = state,
                    dropdownSize = dropdownSize,
                    interactionSource = interactionSource,
                    expandTransition = expandTransition,
                    colors = colors,
                    isInlined = isInlined,
                    expandedStates = expandedStates,
                    onExpandedChange = onExpandedChange,
                    fieldContent = fieldContent,
                )
            },
            popup = {
                if (expandedStates.currentState || expandedStates.targetState) {
                    Popup(
                        popupPositionProvider = DropdownMenuPositionProvider,
                        onDismissRequest = onDismissRequest,
                        properties = PopupProperties(focusable = true),
                        content = { popupContent() }
                    )
                }
            },
            modifier = InspectableModifier {
                debugInspectorInfo {
                    properties["isExpanded"] = expanded.toString()
                    properties["interactiveState"] = when (state) {
                        is DropdownInteractiveState.Disabled -> "Disabled"
                        is DropdownInteractiveState.Enabled -> "Enabled"
                        is DropdownInteractiveState.Error -> "Error"
                        is DropdownInteractiveState.Warning -> "Warning"
                        is DropdownInteractiveState.ReadOnly -> "Read-only"
                    }
                }
            }
        )
    }

    if (isInlined) {
        val isWarningOrError = state is DropdownInteractiveState.Warning
            || state is DropdownInteractiveState.Error
        if (isWarningOrError) {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SpacingScale.spacing06)
            ) {
                label.takeIf { !it.isNullOrBlank() }?.let {
                    DropdownLabel(
                        text = it,
                        color = labelTextColor
                    )
                }

                state.helperText?.let {
                    DropdownHelperText(
                        text = it,
                        color = helperTextColor,
                        modifier = Modifier
                            .padding(start = SpacingScale.spacing06)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        } else {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                label.takeIf { !it.isNullOrBlank() }?.let {
                    DropdownLabel(
                        text = it,
                        color = labelTextColor
                    )
                }

                Column(modifier = Modifier.padding(start = SpacingScale.spacing06)) {
                    fieldAndPopup()

                    state.helperText?.let {
                        DropdownHelperText(
                            text = it,
                            color = helperTextColor
                        )
                    }
                }
            }
        }

    } else {
        Column(modifier = modifier) {
            label.takeIf { !it.isNullOrBlank() }?.let {
                DropdownLabel(
                    text = it,
                    color = labelTextColor,
                    modifier = Modifier.padding(bottom = SpacingScale.spacing03)
                )
            }

            fieldAndPopup()

            state.helperText?.let {
                DropdownHelperText(
                    text = it,
                    color = helperTextColor
                )
            }
        }
    }
}

@Composable
private fun <K : Any> FieldAndPopupLayout(
    options: Map<K, DropdownOption>,
    dropdownSize: DropdownSize,
    expandTransition: Transition<Boolean>,
    minVisibleItems: Int,
    isInlined: Boolean,
    onDismissRequest: () -> Unit,
    popup: @Composable DropdownPopupScope.() -> Unit,
    field: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    val height by expandTransition.animateDp(
        transitionSpec = { dropdownTransitionSpecDp },
        label = "Popup content height"
    ) {
        if (it) {
            getOptionsPopupHeightRatio(options.size, minVisibleItems)
                .times(dropdownSize.dpSize())
        } else {
            0.dp
        }
    }

    var size by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = modifier.onSizeChanged { size = it }
    ) {
        val elevation by expandTransition.animateDp(
            transitionSpec = { dropdownTransitionSpecDp },
            label = "Popup content shadow"
        ) {
            if (it) 3.dp else 0.dp
        }

        val popupScope = remember(this, isInlined, size, dropdownSize, onDismissRequest) {
            object : DropdownPopupScope {
                override fun Modifier.anchor(): Modifier = this
                    .then(
                        if (isInlined) {
                            Modifier.width(inlinedPopupWidth)
                        } else {
                            Modifier.width(with(density) { size.width.toDp() })
                        }
                    )
                    .height(height)
                    // This should be a box shadow (-> 0 2px 6px 0 rgba(0,0,0,.2)). But
                    // compose doesn't provide the same API as CSS for shadows. A 3dp
                    // elevation is the best approximation that could be found for now.
                    .shadow(elevation = elevation)
                    .onEscape(onDismissRequest)
            }
        }

        field()
        popup(popupScope)
    }
}

@Composable
private fun DropdownLabel(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = Carbon.typography.label01,
        color = color,
        modifier = modifier
            .testTag(DropdownTestTags.LABEL_TEXT)
    )
}

@Composable
private fun DropdownHelperText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = Carbon.typography.helperText01,
        color = color,
        modifier = modifier
            .padding(top = SpacingScale.spacing02)
            .testTag(DropdownTestTags.HELPER_TEXT)
    )
}

@Stable
internal interface DropdownPopupScope {
    fun Modifier.anchor(): Modifier
}

@Stable
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
