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

import androidx.compose.foundation.BasicTooltipBox
import androidx.compose.foundation.BasicTooltipState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.layerBackgroundColor
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import kotlinx.coroutines.launch

private val popoverMargin = SpacingScale.spacing02
private val popoverContentPaddingValues: PaddingValues = PaddingValues(SpacingScale.spacing05)

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
    modifier: Modifier = Modifier,
    alignment: PopoverCaretTipAlignment = PopoverCaretTipAlignment.Center,
    placement: PopoverPlacement = PopoverPlacement.Top,
    popoverMinWith: Dp = Dp.Unspecified,
    popoverMaxWith: Dp = Dp.Unspecified,
    uiTriggerMutableInteractionSource: MutableInteractionSource =
        remember { MutableInteractionSource() },
    popoverContent: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
) {
    PopoverBoxInternal(
        modifier = modifier,
        alignment = alignment,
        placement = placement,
        popoverMinWidth = popoverMinWith,
        popoverMaxWidth = popoverMaxWith,
        uiTriggerMutableInteractionSource = uiTriggerMutableInteractionSource,
        popoverContent = popoverContent,
        content = content
    )
}

@ExperimentalFoundationApi
@Composable
internal fun PopoverBoxInternal(
    modifier: Modifier = Modifier,
    state: BasicTooltipState = rememberBasicTooltipState(),
    alignment: PopoverCaretTipAlignment = PopoverCaretTipAlignment.Center,
    placement: PopoverPlacement = PopoverPlacement.Top,
    popoverShape: PopoverWithCaretShape =
        rememberPopupShape(
            placement = placement,
            alignment = alignment,
            tooltipContentPaddingValues = popoverContentPaddingValues,
        ),
    popoverBackgroundColorProvider: @Composable () -> Color =
        { Carbon.theme.layerBackgroundColor(Carbon.layer) },
    popoverMinWidth: Dp = Dp.Unspecified,
    popoverMaxWidth: Dp = Dp.Unspecified,
    uiTriggerMutableInteractionSource: MutableInteractionSource =
        remember { MutableInteractionSource() },
    popoverContent: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current

    val positionProvider = remember(
        placement, alignment, popoverShape.caretSize, density
    ) {
        PopoverPositionProvider(
            placement = placement,
            alignment = alignment,
            caretSize = popoverShape.caretSize,
            tooltipContentPaddingValues = popoverContentPaddingValues,
            density = density,
        )
    }

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
        positionProvider = positionProvider,
        tooltip = {
            CarbonLayer {
                Box(
                    modifier = Modifier
                        .padding(popoverShape.caretSize + popoverMargin)
                        .background(
                            color = popoverBackgroundColorProvider(),
                            shape = popoverShape
                        )
                        .widthIn(min = popoverMinWidth, max = popoverMaxWidth),
                    content = popoverContent
                )
            }
        },
        state = state,
        modifier = modifier,
        focusable = false,
        content = content
    )
}
