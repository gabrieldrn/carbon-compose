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

package com.gabrieldrn.carbon.catalog.contentswitcher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.catalog.Res
import com.gabrieldrn.carbon.catalog.ic_add
import com.gabrieldrn.carbon.catalog.ic_subtract
import com.gabrieldrn.carbon.catalog.misc.LayerSelectionDropdown
import com.gabrieldrn.carbon.contentswitcher.ContentSwitcher
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.containerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import org.jetbrains.compose.resources.painterResource

private const val EXTRA_OPTIONS_MIN = 0
private const val EXTRA_OPTIONS_MAX = 2

private const val CONTENT_SWITCHER_OPT_1 = "Option 1"
private const val CONTENT_SWITCHER_OPT_2 = "Long option 2"

@Composable
fun ContentSwitcherDemoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        var layer by rememberSaveable { mutableStateOf(Layer.Layer00) }

        var extraOptions by rememberSaveable { mutableStateOf(1) }

        val options by rememberSaveable(extraOptions) {
            mutableStateOf(
                mutableListOf(CONTENT_SWITCHER_OPT_1, CONTENT_SWITCHER_OPT_2).apply {
                    repeat(extraOptions) { add("Option ${size + 1}") }
                }.toList()
            )
        }

        var selectedOption by remember(extraOptions) { mutableStateOf(CONTENT_SWITCHER_OPT_1) }

        CarbonLayer(layer = layer) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ContentSwitcher(
                    options = options,
                    selectedOption = selectedOption,
                    onOptionSelected = { selectedOption = it }
                )
            }
        }

        CarbonLayer {
            Column(
                modifier = Modifier
                    .padding(SpacingScale.spacing05)
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing04)
            ) {
                BasicText(
                    text = "Configuration",
                    style = Carbon.typography.heading02.copy(color = Carbon.theme.textPrimary)
                )

                BasicText(
                    text = "Extra options",
                    style = Carbon.typography.label01.copy(color = Carbon.theme.textSecondary),
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val lessButtonEnabled by remember(extraOptions) {
                        mutableStateOf(extraOptions > EXTRA_OPTIONS_MIN)
                    }
                    val moreButtonEnabled by remember(extraOptions) {
                        mutableStateOf(extraOptions < EXTRA_OPTIONS_MAX)
                    }

                    IconButton(
                        iconPainter = painterResource(Res.drawable.ic_subtract),
                        onClick = {
                            extraOptions = extraOptions.minus(1).coerceAtLeast(EXTRA_OPTIONS_MIN)
                        },
                        isEnabled = lessButtonEnabled
                    )

                    BasicText(
                        text = extraOptions.toString(),
                        style = Carbon.typography.body01
                            .copy(
                                color = Carbon.theme.textPrimary,
                                textAlign = TextAlign.Center
                            ),
                        modifier = Modifier.width(SpacingScale.spacing09)
                    )

                    IconButton(
                        iconPainter = painterResource(Res.drawable.ic_add),
                        onClick = {
                            extraOptions = extraOptions.plus(1).coerceAtMost(EXTRA_OPTIONS_MAX)
                        },
                        isEnabled = moreButtonEnabled
                    )
                }

                LayerSelectionDropdown(
                    selectedLayer = layer,
                    onLayerSelected = { layer = it },
                    modifier = Modifier.padding(top = SpacingScale.spacing03)
                )
            }
        }
    }
}
