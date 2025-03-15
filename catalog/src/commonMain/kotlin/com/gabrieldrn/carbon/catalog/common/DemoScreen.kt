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
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.dropdown.base.DropdownOption
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.containerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.notification.CalloutNotification
import com.gabrieldrn.carbon.notification.NotificationStatus
import com.gabrieldrn.carbon.tab.TabItem
import com.gabrieldrn.carbon.tab.TabList

private fun Modifier.contentPadding() = composed {
    if (Carbon.layer == Layer.Layer00) {
        padding(vertical = SpacingScale.spacing05)
    } else {
        padding(SpacingScale.spacing05)
    }
}

@Composable
fun DemoScreen(
    variants: List<TabItem>,
    demoContent: @Composable ColumnScope.(TabItem) -> Unit,
    modifier: Modifier = Modifier,
    defaultVariant: TabItem = variants.first(),
    layers: Map<Layer, DropdownOption> = defaultLayersOptions,
    displayVariantsWIPNotification: Boolean = false,
    displayLayerParameter: Boolean = true,
    demoParametersContent: (@Composable ColumnScope.(TabItem) -> Unit)? = null,
) {
    var layer by rememberSaveable { mutableStateOf(Layer.Layer00) }
    var variant by rememberSaveable { mutableStateOf(defaultVariant) }

    val hasParametersContent = rememberSaveable(demoParametersContent, displayLayerParameter) {
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (!hasParametersContent) {
                        Modifier.weight(1f)
                    } else {
                        Modifier
                    }
                )
        ) {
            TabList(
                tabs = variants,
                selectedTab = variant,
                onTabSelected = { variant = it },
            )

            CarbonLayer(layer = layer) {
                Column(
                    modifier = Modifier
                        .then(
                            if (hasParametersContent) {
                                Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 200.dp)
                            } else {
                                Modifier
                                    .width(IntrinsicSize.Max)
                                    .align(Alignment.CenterHorizontally)
                            }
                        )
                        .containerBackground()
                        .contentPadding(),
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

        if (hasParametersContent) {
            ParametersLayout(
                layers = layers,
                selectedLayer = layer,
                onLayerSelected = { layer = it },
                content = { demoParametersContent?.invoke(this, variant) }
            )
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

    val hasParametersContent = rememberSaveable(demoParametersContent, displayLayerParameter) {
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
                        if (hasParametersContent) {
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
                    .contentPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                content = demoContent
            )
        }

        if (displayVariantsWIPNotification) {
            WIPNotification()
        }

        if (hasParametersContent) {
            ParametersLayout(
                layers = layers,
                selectedLayer = layer,
                onLayerSelected = { layer = it },
                content = { demoParametersContent?.invoke(this) }
            )
        }
    }
}

@Composable
private fun ParametersLayout(
    layers: Map<Layer, DropdownOption>,
    selectedLayer: Layer,
    onLayerSelected: (Layer) -> Unit,
    content: @Composable ColumnScope.() -> Unit?,
) {
    CarbonLayer {
        Column(
            modifier = Modifier
                .containerBackground()
                .padding(SpacingScale.spacing05),
            verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing04)
        ) {
            BasicText(
                text = "Configuration",
                style = Carbon.typography.heading02.copy(color = Carbon.theme.textPrimary)
            )

            content()

            LayerSelectionDropdown(
                layers = layers,
                selectedLayer = selectedLayer,
                onLayerSelected = onLayerSelected,
                modifier = Modifier.padding(top = SpacingScale.spacing03)
            )
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
