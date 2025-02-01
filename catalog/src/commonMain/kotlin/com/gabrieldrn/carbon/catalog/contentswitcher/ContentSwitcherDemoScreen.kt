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
import androidx.compose.foundation.layout.Box
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
import com.gabrieldrn.carbon.catalog.common.LayerSelectionDropdown
import com.gabrieldrn.carbon.catalog.content_switcher_demo_opt1_description
import com.gabrieldrn.carbon.catalog.content_switcher_demo_opt2_description
import com.gabrieldrn.carbon.catalog.content_switcher_demo_opt3_description
import com.gabrieldrn.carbon.catalog.content_switcher_demo_opt4_description
import com.gabrieldrn.carbon.catalog.ic_add
import com.gabrieldrn.carbon.catalog.ic_bicycle
import com.gabrieldrn.carbon.catalog.ic_bus
import com.gabrieldrn.carbon.catalog.ic_sailboat_offshore
import com.gabrieldrn.carbon.catalog.ic_subtract
import com.gabrieldrn.carbon.catalog.ic_train
import com.gabrieldrn.carbon.contentswitcher.ContentSwitcher
import com.gabrieldrn.carbon.contentswitcher.ContentSwitcherSize
import com.gabrieldrn.carbon.contentswitcher.IconContentSwitcher
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.containerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.toggle.Toggle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private const val EXTRA_OPTIONS_MIN = 0
private const val EXTRA_OPTIONS_MAX = 2

private const val DEFAULT_CONTENT_SWITCHER_OPT_1 = "Option 1"
private const val DEFAULT_CONTENT_SWITCHER_OPT_2 = "Long option 2"

private enum class ContentSwitcherVariant { Default, Icon }

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

        var variant by rememberSaveable {
            mutableStateOf(ContentSwitcherVariant.Default)
        }

        var size by rememberSaveable {
            mutableStateOf(ContentSwitcherSize.Large)
        }

        var extraOptions by rememberSaveable { mutableStateOf(1) }

        var isEnabled by remember { mutableStateOf(true) }

        CarbonLayer(layer = layer) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                contentAlignment = Alignment.Center
            ) {
                when (variant) {
                    ContentSwitcherVariant.Default -> DemoDefaultContentSwitcher(
                        extraOptions = extraOptions,
                        isEnabled = isEnabled,
                        size = size
                    )
                    ContentSwitcherVariant.Icon -> DemoIconContentSwitcher(
                        extraOptions = extraOptions,
                        isEnabled = isEnabled,
                        size = size
                    )
                }
            }
        }

        CarbonLayer {
            Column(
                modifier = Modifier
                    .padding(SpacingScale.spacing05)
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing06)
            ) {
                BasicText(
                    text = "Configuration",
                    style = Carbon.typography.heading02.copy(color = Carbon.theme.textPrimary)
                )

                Dropdown(
                    label = "Variant",
                    placeholder = "Select variant",
                    options = ContentSwitcherVariant.entries.toDropdownOptions(),
                    selectedOption = variant,
                    onOptionSelected = { variant = it },
                )

                ExtraOptionsSelector(
                    extraOptions = extraOptions,
                    onExtraOptionsChange = { extraOptions = it }
                )

                Dropdown(
                    label = "Size",
                    placeholder = "Select size",
                    options = ContentSwitcherSize.entries.toDropdownOptions(),
                    selectedOption = size,
                    onOptionSelected = { size = it },
                    state = getSizeSpecification(size)
                        ?.let(DropdownInteractiveState::Warning)
                        ?: DropdownInteractiveState.Enabled
                )

                Toggle(
                    label = "Enable",
                    isToggled = isEnabled,
                    onToggleChange = { isEnabled = it },
                )

                LayerSelectionDropdown(
                    selectedLayer = layer,
                    onLayerSelected = { layer = it },
                )
            }
        }
    }
}

@Composable
private fun DemoDefaultContentSwitcher(
    extraOptions: Int,
    isEnabled: Boolean,
    size: ContentSwitcherSize,
    modifier: Modifier = Modifier
) {
    val options by rememberSaveable(extraOptions) {
        mutableStateOf(
            mutableListOf(DEFAULT_CONTENT_SWITCHER_OPT_1, DEFAULT_CONTENT_SWITCHER_OPT_2).apply {
                repeat(extraOptions) { add("Option ${this.size + 1}") }
            }.toList()
        )
    }

    var selectedOption by remember(extraOptions) { mutableStateOf(DEFAULT_CONTENT_SWITCHER_OPT_1) }

    ContentSwitcher(
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = {
            if (isEnabled) {
                selectedOption = it
            }
        },
        isEnabled = isEnabled,
        size = size,
        modifier = modifier
    )
}

@Composable
private fun DemoIconContentSwitcher(
    extraOptions: Int,
    isEnabled: Boolean,
    size: ContentSwitcherSize,
    modifier: Modifier = Modifier
) {
    val option1 = painterResource(Res.drawable.ic_train)
    val option2 = painterResource(Res.drawable.ic_bus)
    val option3 = painterResource(Res.drawable.ic_bicycle)
    val option4 = painterResource(Res.drawable.ic_sailboat_offshore)

    val contentDescriptions = mapOf(
        option1 to stringResource(Res.string.content_switcher_demo_opt1_description),
        option2 to stringResource(Res.string.content_switcher_demo_opt2_description),
        option3 to stringResource(Res.string.content_switcher_demo_opt3_description),
        option4 to stringResource(Res.string.content_switcher_demo_opt4_description)
    )

    val options by rememberSaveable(extraOptions) {
        mutableStateOf(
            mutableListOf(option1, option2).apply {
                when (extraOptions) {
                    1 -> add(option3)
                    2 -> {
                        add(option3)
                        add(option4)
                    }
                }
            }.toList()
        )
    }

    var selectedOption by remember(extraOptions) { mutableStateOf(option1) }

    IconContentSwitcher(
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = {
            if (isEnabled) {
                selectedOption = it
            }
        },
        optionsContentDescriptions = contentDescriptions,
        isEnabled = isEnabled,
        size = size,
        modifier = modifier
    )
}

@Composable
private fun ExtraOptionsSelector(
    extraOptions: Int,
    onExtraOptionsChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        BasicText(
            text = "Extra options",
            style = Carbon.typography.label01.copy(color = Carbon.theme.textSecondary),
        )

        Row(
            modifier = Modifier.padding(top = SpacingScale.spacing04),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val lessButtonEnabled by remember(extraOptions) {
                mutableStateOf(extraOptions > EXTRA_OPTIONS_MIN)
            }
            val moreButtonEnabled by remember(extraOptions) {
                mutableStateOf(extraOptions < EXTRA_OPTIONS_MAX)
            }

            IconButton(
                iconPainter = painterResource(Res.drawable.ic_subtract),
                onClick = {
                    onExtraOptionsChange(
                        extraOptions.minus(1).coerceAtLeast(EXTRA_OPTIONS_MIN)
                    )
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
                    onExtraOptionsChange(
                        extraOptions.plus(1).coerceAtMost(EXTRA_OPTIONS_MAX)
                    )
                },
                isEnabled = moreButtonEnabled
            )
        }
    }
}
