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
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

internal fun PaddingValues.getTooltipContentPaddingByPosition(
    placement: TooltipPlacement,
    alignment: TooltipAlignment,
    layoutDirection: LayoutDirection,
): Dp = when (placement) {
    TooltipPlacement.Top,
    TooltipPlacement.Bottom -> calculateStartPadding(layoutDirection)
    else -> when (alignment) {
        TooltipAlignment.Start -> calculateTopPadding()
        TooltipAlignment.Center -> 0.dp
        TooltipAlignment.End -> calculateBottomPadding()
    }
}
