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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.button.Button

private class TooltipPlacementPreviewParameterProvider :
    PreviewParameterProvider<TooltipPlacement> {
    override val values: Sequence<TooltipPlacement>
        get() = TooltipPlacement.entries.asSequence()
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun TooltipPreview(
    @PreviewParameter(TooltipPlacementPreviewParameterProvider::class)
    placement: TooltipPlacement,
) {
    CarbonDesignSystem {
        Box(
            modifier = Modifier.size(400.dp),
            contentAlignment = Alignment.Center
        ) {
            val uiTriggerMutableInteractionSource = remember { MutableInteractionSource() }

            TooltipBox(
                tooltipText = placement.name,
                uiTriggerMutableInteractionSource = uiTriggerMutableInteractionSource,
                state = rememberBasicTooltipState(
                    initialIsVisible = true,
                    isPersistent = true
                ),
                placement = placement
            ) {
                Button(
                    label = "Hover me",
                    onClick = { /* No-op */ },
                    interactionSource = uiTriggerMutableInteractionSource
                )
            }
        }
    }
}
