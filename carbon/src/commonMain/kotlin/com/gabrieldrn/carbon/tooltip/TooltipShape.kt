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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.popover.PopoverCaretTipAlignment
import com.gabrieldrn.carbon.popover.PopoverPlacement
import com.gabrieldrn.carbon.popover.PopoverShape

internal class TooltipShape(
    placement: PopoverPlacement,
    alignment: PopoverCaretTipAlignment,
    tooltipContentPaddingValues: PaddingValues,
    isSingleLine: Boolean
) : PopoverShape(
    placement = placement,
    alignment = alignment,
    popoverContentPaddingValues = tooltipContentPaddingValues
) {

    override val caretSize: Dp = if (isSingleLine) singleSizeCaretSize else PopoverShape.caretSize

    companion object {
        private val singleSizeCaretSize = 6.dp
    }
}

@Composable
internal fun rememberTooltipShape(
    placement: PopoverPlacement,
    alignment: PopoverCaretTipAlignment,
    tooltipContentPaddingValues: PaddingValues,
    isSingleLine: Boolean
): TooltipShape = remember(
    placement, alignment, tooltipContentPaddingValues, isSingleLine
) {
    TooltipShape(placement, alignment, tooltipContentPaddingValues, isSingleLine)
}
