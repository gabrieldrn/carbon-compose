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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.button.Button
import com.gabrieldrn.carbon.foundation.color.containerBackground

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun TooltipPreview() {
    CarbonDesignSystem {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .containerBackground(),
            contentAlignment = Alignment.Center
        ) {
            TooltipBox(
                tooltipText = "This is a tooltip",
                tooltipState = rememberBasicTooltipState(
                    initialIsVisible = true,
                    isPersistent = true
                )
            ) {
                Button(
                    label = "Hover me",
                    onClick = { /* No-op */ },
                )
            }
        }
    }
}
