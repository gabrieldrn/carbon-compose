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

package carbon.compose.dropdown.base

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.InspectableModifier
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
import carbon.compose.Carbon
import carbon.compose.dropdown.base.DropdownInteractiveState.Companion.helperText
import carbon.compose.dropdown.domain.getOptionsPopupHeightRatio
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.motion.Motion
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.Text

private val dropdownTransitionSpecDp = tween<Dp>(
    durationMillis = Motion.Duration.moderate01,
    easing = Motion.Standard.productiveEasing
)

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
    modifier: Modifier = Modifier,
    label: String? = null,
    state: DropdownInteractiveState = DropdownInteractiveState.Enabled,
    dropdownSize: DropdownSize = DropdownSize.Large,
    minVisibleItems: Int,
    fieldContent: @Composable () -> Unit,
    popupContent: @Composable DropdownPopupScope.() -> Unit,
) {
    require(options.isNotEmpty()) {
        "Dropdown must have at least one option."
    }

    require(Carbon.layer < Layer.Layer03) {
        "A dropdown can't be placed on layer 03"
    }

    val interactionSource = remember { MutableInteractionSource() }

    val expandedStates = remember { MutableTransitionState(false) }
    expandedStates.targetState = expanded

    val componentHeight = dropdownSize.height

    val transition = updateTransition(expandedStates, "Dropdown")

    val height by transition.animateDp(
        transitionSpec = { dropdownTransitionSpecDp },
        label = "Popup content height"
    ) {
        if (it) {
            getOptionsPopupHeightRatio(options.size, minVisibleItems)
                .times(componentHeight)
        } else {
            0.dp
        }
    }

    val labelTextColor by colors.labelTextColor(state)
    val helperTextColor by colors.helperTextColor(state)

    Column(modifier = modifier) {
        label.takeIf { !it.isNullOrBlank() }?.let {
            Text(
                text = it,
                style = Carbon.typography.label01,
                color = labelTextColor,
                modifier = Modifier
                    .padding(bottom = SpacingScale.spacing03)
                    .testTag(DropdownTestTags.LABEL_TEXT)
            )
        }

        BoxWithConstraints(
            modifier = Modifier.then(
                InspectableModifier {
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
        ) {
            val elevation by transition.animateDp(
                transitionSpec = { dropdownTransitionSpecDp },
                label = "Popup content shadow"
            ) {
                if (it) 3.dp else 0.dp
            }

            val popupScope = remember(this, dropdownSize, onDismissRequest) {
                object : DropdownPopupScope {
                    override fun Modifier.anchor(): Modifier = this
                        .width(maxWidth)
                        .height(height)
                        // This should be a box shadow (-> 0 2px 6px 0 rgba(0,0,0,.2)). But
                        // compose doesn't provide the same API as CSS for shadows. A 3dp
                        // elevation is the best approximation that could be found for now.
                        .shadow(elevation = elevation)
                        .onEscape(onDismissRequest)
                }
            }

            DropdownField(
                state = state,
                dropdownSize = dropdownSize,
                interactionSource = interactionSource,
                transition = transition,
                colors = colors,
                expandedStates = expandedStates,
                onExpandedChange = onExpandedChange,
                fieldContent = fieldContent,
            )

            if (expandedStates.currentState || expandedStates.targetState) {
                Popup(
                    popupPositionProvider = DropdownMenuPositionProvider,
                    onDismissRequest = onDismissRequest,
                    properties = PopupProperties(focusable = true),
                    content = { popupScope.popupContent() }
                )
            }
        }

        state.helperText?.let {
            Text(
                text = it,
                style = Carbon.typography.helperText01,
                color = helperTextColor,
                modifier = Modifier
                    .padding(top = SpacingScale.spacing02)
                    .testTag(DropdownTestTags.HELPER_TEXT)
            )
        }
    }
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
