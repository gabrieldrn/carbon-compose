/*
 * Copyright 2025 Gabriel Derrien
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

package com.gabrieldrn.carbon.tooltip

import androidx.compose.foundation.BasicTooltipBox
import androidx.compose.foundation.BasicTooltipState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.popover.carettip.PopoverCaretTipAlignment
import com.gabrieldrn.carbon.popover.carettip.PopoverCaretTipPlacement
import com.gabrieldrn.carbon.popover.carettip.rememberPopoverCaretTipPositionProvider
import kotlinx.coroutines.launch

private val tooltipSingleLineMaxWidth = 208.dp
private val tooltipMultiLineMaxWidth = 288.dp
private val tooltipSingleLinePaddingValues = PaddingValues(
    horizontal = SpacingScale.spacing05,
    vertical = SpacingScale.spacing01
)
private val tooltipMultiLinePaddingValues = PaddingValues(SpacingScale.spacing05)
private val tooltipMargin = SpacingScale.spacing02

/**
 * # Tooltip (`TooltipBox`)
 *
 * Composable wrapping a UI trigger (e.g. a button) and displaying a tooltip when the user hovers
 * over or focuses on the UI trigger.
 *
 * Tooltips display additional information upon hover or focus that is contextual, helpful, and
 * nonessential while providing the ability to communicate and give clarity to a user.
 *
 * ## Focus interaction
 *
 * As stated by the documentation of Carbon's Tooltip component, tooltips must be triggered by a
 * hover or focus interaction. This composable supports the hover interaction by default, as it's
 * using [BasicTooltipBox] as the underlying implementation but does not support the focus
 * interaction. However, [TooltipBox] supports it when a shared [MutableInteractionSource] is
 * provided to both the UI trigger and itself:
 * ```kotlin
 * val uiTriggerMutableInteractionSource = remember { MutableInteractionSource() }
 *
 * TooltipBox(
 *     tooltipText = "To be, or not to be...",
 *     uiTriggerMutableInteractionSource = uiTriggerMutableInteractionSource,
 * ) {
 *     MyUiTrigger(
 *         text = "Hover me",
 *         modifier = Modifier.clickable(
 *             interactionSource = uiTriggerMutableInteractionSource,
 *             indication = null,
 *             onClick = {}
 *         )
 *     )
 * }
 * ```
 *
 * Writing this for every UI trigger that needs the focus interaction can be cumbersome, but
 * most common UI triggers from Carbon provides alternative APIs to ease its usage.
 * For example, the [com.gabrieldrn.carbon.button.Button] composable has an alt API requiring a
 * `tooltipParameters` parameter:
 * ```kotlin
 * Button(
 *     label = "Hover me",
 *     tooltipParameters = TooltipParameters(
 *         text = "To be, or not to be..."
 *         singleLine = true,
 *     ),
 *     onClick = {}
 * )
 * ```
 *
 * ## Automatic placement
 *
 * Carbon's documentation states that Tooltips have an automatic placement: "tooltips can detect the
 * edges of the browser to be placed in view so the container does not get cutoff". However, this is
 * currently not possible with the foundation elements of Compose as it would require to make
 * multiple layout passes and sacrifice performance. As a result, it is up to consuming projects to
 * decide the placement of tooltips.
 *
 * From [Tooltip documentation](https://carbondesignsystem.com/components/tooltip/usage/)
 *
 * @param tooltipText Text to display in the tooltip.
 * @param modifier Default Modifier to be applied to the whole composable.
 * @param singleLine Either the text in the tooltip should be displayed in a single line or not.
 * Note that the tooltip width is limited to a maximum width depending on this parameter and the
 * text might be truncated if it exceeds the maximum width.
 * @param placement Placement of the tooltip relative to the UI trigger. Defaults to
 * [PopoverCaretTipPlacement.Top].
 * @param alignment Alignment of the tooltip relative to the UI trigger. Defaults to
 * [PopoverCaretTipAlignment.Center].
 * @param uiTriggerMutableInteractionSource A shared [MutableInteractionSource] that will be used
 * to track the focus interactions of the UI trigger.
 * @param content UI trigger content that will be wrapped by the tooltip. This is typically a button
 * or an icon that the user can hover over or long-press to see the tooltip.
 */
@Composable
@ExperimentalFoundationApi
public fun TooltipBox(
    tooltipText: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    placement: PopoverCaretTipPlacement = PopoverCaretTipPlacement.Top,
    alignment: PopoverCaretTipAlignment = PopoverCaretTipAlignment.Center,
    uiTriggerMutableInteractionSource: MutableInteractionSource =
        remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    TooltipBox(
        tooltipText = tooltipText,
        modifier = modifier,
        state = rememberBasicTooltipState(),
        singleLine = singleLine,
        placement = placement,
        alignment = alignment,
        uiTriggerMutableInteractionSource = uiTriggerMutableInteractionSource,
        content = content
    )
}

/**
 * # Tooltip (`TooltipBox`)
 *
 * Convenience composable for [TooltipBox] that takes a [TooltipParameters] object as a parameter.
 *
 * Composable wrapping a UI trigger (e.g. a button) and displaying a tooltip when the user hovers
 * over or focuses on the UI trigger.
 *
 * Tooltips display additional information upon hover or focus that is contextual, helpful, and
 * nonessential while providing the ability to communicate and give clarity to a user.
 *
 * From [Tooltip documentation](https://carbondesignsystem.com/components/tooltip/usage/)
 *
 * @param parameters Object containing the required parameters for the tooltip.
 * @param uiTriggerMutableInteractionSource A shared [MutableInteractionSource] that will be used
 * to track the focus interactions of the UI trigger.
 * @param modifier Default Modifier to be applied to the whole composable.
 * @param content UI trigger content that will be wrapped by the tooltip. This is typically a button
 * or an icon that the user can hover over or long-press to see the tooltip.
 */
@Composable
@ExperimentalFoundationApi
public fun TooltipBox(
    parameters: TooltipParameters,
    uiTriggerMutableInteractionSource: MutableInteractionSource =
        remember { MutableInteractionSource() },
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    TooltipBox(
        tooltipText = parameters.text,
        modifier = modifier,
        singleLine = parameters.singleLine,
        placement = parameters.placement,
        alignment = parameters.alignment,
        uiTriggerMutableInteractionSource = uiTriggerMutableInteractionSource,
        content = content
    )
}

@Composable
@ExperimentalFoundationApi
internal fun TooltipBox(
    tooltipText: String,
    modifier: Modifier = Modifier,
    state: BasicTooltipState = rememberBasicTooltipState(),
    singleLine: Boolean = false,
    placement: PopoverCaretTipPlacement = PopoverCaretTipPlacement.Top,
    alignment: PopoverCaretTipAlignment = PopoverCaretTipAlignment.Center,
    uiTriggerMutableInteractionSource: MutableInteractionSource =
        remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val tooltipContentPaddingValues: PaddingValues = remember(singleLine) {
        if (singleLine) tooltipSingleLinePaddingValues
        else tooltipMultiLinePaddingValues
    }

    val shape: TooltipShape = rememberTooltipShape(
        placement = placement,
        alignment = alignment,
        tooltipContentPaddingValues = tooltipContentPaddingValues,
        isSingleLine = singleLine
    )

    LaunchedEffect(uiTriggerMutableInteractionSource, state) {
        var focusSource: FocusInteraction? = null
        uiTriggerMutableInteractionSource.interactions.collect {
            if (it is FocusInteraction.Focus) {
                focusSource = it
                launch { state.show(MutatePriority.UserInput) }
            } else if (it is FocusInteraction.Unfocus && it.focus == focusSource) {
                focusSource = null
                launch { state.dismiss() }
            }
        }
    }

    BasicTooltipBox(
        state = state,
        positionProvider = rememberPopoverCaretTipPositionProvider(
            caretSize = shape.tipSize,
            alignment = alignment,
            placement = placement,
            contentPaddingValues = tooltipContentPaddingValues
        ),
        modifier = modifier,
        tooltip = {
            BasicText(
                text = tooltipText,
                style = Carbon.typography.body01.copy(color = Carbon.theme.textInverse),
                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(shape.tipSize + tooltipMargin)
                    .background(
                        color = Carbon.theme.backgroundInverse,
                        shape = shape
                    )
                    .widthIn(
                        max =
                            if (singleLine) tooltipSingleLineMaxWidth
                            else tooltipMultiLineMaxWidth
                    )
                    .padding(tooltipContentPaddingValues)
            )
        },
        content = content
    )
}
