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

package com.gabrieldrn.carbon.catalog.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.dropdown.base.DropdownOption
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.containerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.notification.CalloutNotification
import com.gabrieldrn.carbon.notification.NotificationStatus
import com.gabrieldrn.carbon.tab.TabItem
import com.gabrieldrn.carbon.tab.TabList

@Composable
fun DemoScreen(
    variants: List<TabItem>,
    defaultVariant: TabItem,
    demoContent: @Composable ColumnScope.(TabItem) -> Unit,
    modifier: Modifier = Modifier,
    layers: Map<Layer, DropdownOption> = defaultLayersOptions,
    displayVariantsWIPNotification: Boolean = false,
    displayLayerParameter: Boolean = true,
    demoParametersContent: (@Composable ColumnScope.(TabItem) -> Unit)? = null,
) {
    var layer by rememberSaveable { mutableStateOf(Layer.Layer00) }
    var variant by rememberSaveable { mutableStateOf(defaultVariant) }

    val hasParameterScreen = rememberSaveable(demoParametersContent, displayLayerParameter) {
        demoParametersContent != null || displayLayerParameter
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(horizontal = SpacingScale.spacing05)
            .padding(top = SpacingScale.spacing03, bottom = SpacingScale.spacing05),
        verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing05)
    ) {

        Column {
            TabList(
                tabs = variants,
                selectedTab = variant,
                onTabSelected = { variant = it },
            )

            CarbonLayer(layer = layer) {
                Column(
                    modifier = Modifier
                        .then(
                            if (hasParameterScreen) {
                                Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 200.dp)
                            } else {
                                Modifier
                                    .width(IntrinsicSize.Max)
                                    .weight(1f)
                                    .align(Alignment.CenterHorizontally)
                            }
                        )
                        .containerBackground()
                        .padding(SpacingScale.spacing05),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    content = {
                        demoContent(variant)
                    }
                )
            }
        }

        if (displayVariantsWIPNotification) {
            WIPNotification()
        }

        if (hasParameterScreen) {
            CarbonLayer {
                Column(
                    modifier = Modifier
                        .containerBackground()
                        .padding(SpacingScale.spacing05),
                    verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing04)
                ) {
                    demoParametersContent?.invoke(this, variant)

                    LayerSelectionDropdown(
                        layers = layers,
                        selectedLayer = layer,
                        onLayerSelected = { layer = it },
                        modifier = Modifier.padding(top = SpacingScale.spacing03)
                    )
                }
            }
        }
    }
}

@Composable
fun DemoScreen(
    demoContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    layers: Map<Layer, DropdownOption> = defaultLayersOptions,
    displayVariantsWIPNotification: Boolean = false,
    displayLayerParameter: Boolean = true,
    demoParametersContent: (@Composable ColumnScope.() -> Unit)? = null
) {
    var layer by rememberSaveable { mutableStateOf(Layer.Layer00) }

    val hasParameterScreen = rememberSaveable(demoParametersContent, displayLayerParameter) {
        demoParametersContent != null || displayLayerParameter
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(SpacingScale.spacing05),
        verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing05)
    ) {
        CarbonLayer(layer = layer) {
            Column(
                modifier = Modifier
                    .then(
                        if (hasParameterScreen) {
                            Modifier
                                .fillMaxWidth()
                                .heightIn(min = 200.dp)
                        } else {
                            Modifier
                                .width(IntrinsicSize.Max)
                                .weight(1f)
                                .align(Alignment.CenterHorizontally)
                        }
                    )
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                content = demoContent
            )
        }

        if (displayVariantsWIPNotification) {
            WIPNotification()
        }

        if (hasParameterScreen) {
            CarbonLayer {
                Column(
                    modifier = Modifier
                        .containerBackground()
                        .padding(SpacingScale.spacing05),
                    verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing04)
                ) {
                    demoParametersContent?.invoke(this)

                    LayerSelectionDropdown(
                        layers = layers,
                        selectedLayer = layer,
                        onLayerSelected = { layer = it },
                        modifier = Modifier.padding(top = SpacingScale.spacing03)
                    )
                }
            }
        }
    }
}

@Composable
private fun WIPNotification(
    modifier: Modifier = Modifier
) {
    CalloutNotification(
        body = "Other variants are a work in progress.",
        status = NotificationStatus.Informational,
        modifier = modifier
            .width(IntrinsicSize.Max)
    )
}
