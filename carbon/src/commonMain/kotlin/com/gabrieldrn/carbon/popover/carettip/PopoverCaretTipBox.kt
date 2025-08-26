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

package com.gabrieldrn.carbon.popover.carettip

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.gabrieldrn.carbon.popover.PopoverBoxInternal
import com.gabrieldrn.carbon.popover.popoverContentPaddingValues

/**
 * # Popover - Caret tip
 *
 * Composable wrapping a [content] (generally a ui trigger) to display a Popover with a caret tip
 * when [isVisible] is true.
 *
 * A popover with a caret tip helps to show the relationship between the popover and where it was
 * triggered from. A caret tip is typically used when the trigger button does not have a visually
 * defined down state and for icon buttons.
 *
 * ## ⚠️ Placement limitations
 *
 * Due to limitations from Compose, the Popover may be misplaced or misaligned if the current UI
 * window is not big enough to fit it. This is especially the case for hand-held devices. Please
 * keep this in mind when using this component.
 *
 * From [Popover documentation](https://carbondesignsystem.com/components/popover/usage/#caret-tip)
 *
 * @param isVisible Whether the popover is visible or not.
 * @param modifier The modifier to be applied to the whole component.
 * @param alignment The alignment of the popover relative to the UI trigger.
 * @param placement The placement of the popover relative to the UI trigger.
 * @param popoverMinWidth Minimum width of the displayed popover.
 * @param popoverMaxWidth Maximum width of the displayed popover.
 * @param onDismissRequest Executes when the user clicks outside of the popup.
 * @param popoverContent The content to be displayed inside the popup.
 * @param content The composable that the popover will anchor to.
 */
@ExperimentalFoundationApi
@Composable
public fun PopoverCaretTipBox(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    alignment: PopoverCaretTipAlignment = PopoverCaretTipAlignment.Center,
    placement: PopoverCaretTipPlacement = PopoverCaretTipPlacement.Top,
    popoverMinWidth: Dp = Dp.Unspecified,
    popoverMaxWidth: Dp = Dp.Unspecified,
    onDismissRequest: (() -> Unit)? = null,
    popoverContent: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
) {
    val popoverShape = rememberPopoverCaretTipShape(
        placement = placement,
        alignment = alignment,
        tooltipContentPaddingValues = popoverContentPaddingValues,
    )

    PopoverBoxInternal(
        isVisible = isVisible,
        popoverShape = popoverShape,
        positionProvider = rememberPopoverCaretTipPositionProvider(
            caretSize = popoverShape.tipSize,
            alignment = alignment,
            placement = placement,
            contentPaddingValues = popoverContentPaddingValues
        ),
        modifier = modifier,
        popoverMinWidth = popoverMinWidth,
        popoverMaxWidth = popoverMaxWidth,
        onDismissRequest = onDismissRequest,
        popoverContent = popoverContent,
        content = content
    )
}
