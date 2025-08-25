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

package com.gabrieldrn.carbon.popover

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * No-tip implementation.
 * TODO Doc
 */
@ExperimentalFoundationApi
@Composable
public fun PopoverBox(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    alignment: PopoverAlignment = PopoverAlignment.Start,
    placement: PopoverPlacement = PopoverPlacement.Top,
    popoverMinWith: Dp = Dp.Unspecified,
    popoverMaxWith: Dp = Dp.Unspecified,
    onDismissRequest: (() -> Unit)? = null,
    popoverContent: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
) {
    val popoverShape = rememberPopoverShape()

    PopoverBoxInternal(
        popoverShape = popoverShape,
        isVisible = isVisible,
        positionProvider = rememberPopoverPositionProvider(
            alignment = alignment,
            placement = placement,
        ),
        modifier = modifier,
        popoverMinWidth = popoverMinWith,
        popoverMaxWidth = popoverMaxWith,
        onDismissRequest = onDismissRequest,
        popoverContent = popoverContent,
        content = content
    )
}
