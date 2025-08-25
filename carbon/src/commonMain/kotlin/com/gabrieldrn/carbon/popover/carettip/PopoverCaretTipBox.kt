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
 * TODO Doc - move from tooltip to here
 *
 * Implementation has only the caret tip.
 * TODO No tip
 * TODO Tab tip
 */
@ExperimentalFoundationApi
@Composable
public fun PopoverCaretTipBox(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    alignment: PopoverCaretTipAlignment = PopoverCaretTipAlignment.Center,
    placement: PopoverCaretTipPlacement = PopoverCaretTipPlacement.Top,
    popoverMinWith: Dp = Dp.Unspecified,
    popoverMaxWith: Dp = Dp.Unspecified,
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
        popoverMinWidth = popoverMinWith,
        popoverMaxWidth = popoverMaxWith,
        onDismissRequest = onDismissRequest,
        popoverContent = popoverContent,
        content = content
    )
}
