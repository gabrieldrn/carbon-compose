/*
 * Copyright 2026 Gabriel Derrien
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

package com.gabrieldrn.carbon.catalog.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.button.Button
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.button.InlineLoadingButton
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.loading.inlineloading.InlineLoading
import com.gabrieldrn.carbon.loading.inlineloading.InlineLoadingStatus
import com.gabrieldrn.carbon.tab.TabItem
import com.gabrieldrn.carbon.toggle.Toggle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val LOADING_SIMULATION_DELAY_MS = 1_500L
private const val STATUS_RESET_DELAY_MS = 1_200L

private enum class InlineLoadingVariant { Standalone, Button }

private val inlineLoadingVariants = InlineLoadingVariant.entries.map { TabItem(it.name) }
private val inlineLoadingStatuses = InlineLoadingStatus.entries.toDropdownOptions()

@Composable
fun InlineLoadingDemoScreen(modifier: Modifier = Modifier) {
    var standaloneStatus by rememberSaveable { mutableStateOf(InlineLoadingStatus.Active) }
    var showStandaloneLabel by rememberSaveable { mutableStateOf(true) }
    var simulateError by rememberSaveable { mutableStateOf(false) }
    var showButtonLoadingLabel by rememberSaveable { mutableStateOf(true) }
    var buttonStatus by rememberSaveable { mutableStateOf(InlineLoadingStatus.Inactive) }

    val coroutineScope = rememberCoroutineScope()

    DemoScreen(
        modifier = modifier,
        variants = inlineLoadingVariants,
        displayLayerParameter = false,
        demoParametersContent = { variant ->
            when (InlineLoadingVariant.valueOf(variant.label)) {
                InlineLoadingVariant.Standalone -> {
                    Dropdown(
                        label = "Status",
                        placeholder = "Choose option",
                        options = inlineLoadingStatuses,
                        selectedOption = standaloneStatus,
                        onOptionSelected = { standaloneStatus = it }
                    )

                    Toggle(
                        label = "Show visible label",
                        isToggled = showStandaloneLabel,
                        onToggleChange = { showStandaloneLabel = it },
                    )
                }

                InlineLoadingVariant.Button -> {
                    Toggle(
                        label = "Finish with error",
                        isToggled = simulateError,
                        onToggleChange = { simulateError = it },
                    )

                    Toggle(
                        label = "Show loading label",
                        isToggled = showButtonLoadingLabel,
                        onToggleChange = { showButtonLoadingLabel = it },
                    )
                }
            }
        },
        demoContent = { variant ->
            when (InlineLoadingVariant.valueOf(variant.label)) {
                InlineLoadingVariant.Standalone -> {
                    val statusLabel = standaloneStatus.catalogLabel()

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing06)
                    ) {
                        InlineLoading(
                            status = standaloneStatus,
                            label = statusLabel.takeIf { showStandaloneLabel },
                            contentDescription = statusLabel.takeUnless { showStandaloneLabel }
                        )
                    }
                }

                InlineLoadingVariant.Button ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            label = "Cancel",
                            onClick = {},
                            isEnabled = buttonStatus == InlineLoadingStatus.Inactive,
                            buttonType = ButtonType.Secondary,
                            modifier = Modifier.width(160.dp)
                        )

                        val buttonLoadingLabel = buttonStatus.catalogButtonLabel()

                        InlineLoadingButton(
                            label = "Submit",
                            onClick = {
                                coroutineScope.launch {
                                    buttonStatus = InlineLoadingStatus.Active
                                    delay(LOADING_SIMULATION_DELAY_MS)
                                    buttonStatus = if (simulateError) {
                                        InlineLoadingStatus.Error
                                    } else {
                                        InlineLoadingStatus.Finished
                                    }
                                    delay(STATUS_RESET_DELAY_MS)
                                    buttonStatus = InlineLoadingStatus.Inactive
                                }
                            },
                            status = buttonStatus,
                            inlineLoadingLabel = buttonLoadingLabel
                                .takeIf { showButtonLoadingLabel },
                            inlineLoadingContentDescription = buttonLoadingLabel,
                            modifier = Modifier.width(220.dp),
                        )
                    }
            }
        }
    )
}

private fun InlineLoadingStatus.catalogLabel(): String? = when (this) {
    InlineLoadingStatus.Inactive -> "Idle"
    InlineLoadingStatus.Active -> "Submitting..."
    InlineLoadingStatus.Finished -> "Submitted"
    InlineLoadingStatus.Error -> "Submission failed"
}

private fun InlineLoadingStatus.catalogButtonLabel(): String? = when (this) {
    InlineLoadingStatus.Inactive -> null
    InlineLoadingStatus.Active -> "Submitting..."
    InlineLoadingStatus.Finished -> "Submitted"
    InlineLoadingStatus.Error -> "Submission failed"
}
