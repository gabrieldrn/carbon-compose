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

package com.gabrieldrn.carbon.catalog.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.catalog.di.injectViewModel
import com.gabrieldrn.carbon.catalog.theme.CarbonTheme
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.layerBackground
import com.gabrieldrn.carbon.foundation.misc.Adaptation
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.notification.CalloutNotification
import com.gabrieldrn.carbon.notification.NotificationStatus

private const val ADAPTATION_DOC_URL =
    "https://gabrieldrn.github.io/carbon-compose/getting-started/usage/#adaptation"

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = injectViewModel<SettingsViewModel>(),
) {
    val typography = Carbon.typography
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .layerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        CarbonLayer {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .layerBackground()
                    .padding(SpacingScale.spacing05)
            ) {
                BasicText(
                    text = "Themes",
                    style = Carbon.typography.heading02.copy(
                        color = Carbon.theme.textPrimary
                    ),
                )

                // TODO Refactor to use radio tiles when available

                Dropdown(
                    placeholder = "Light theme",
                    label = "Light theme",
                    options = remember {
                        CarbonTheme.LightTheme
                            .entries
                            .map { it.displayName }
                            .toDropdownOptions()
                    },
                    selectedOption = uiState.lightTheme.displayName,
                    onOptionSelected = {
                        viewModel.setLightTheme(CarbonTheme.LightTheme.fromDisplayName(it))
                    },
                    modifier = Modifier.padding(top = SpacingScale.spacing03)
                )

                Dropdown(
                    placeholder = "Dark theme",
                    label = "Dark theme",
                    options = remember {
                        CarbonTheme.DarkTheme
                            .entries
                            .map { it.displayName }
                            .toDropdownOptions()
                    },
                    selectedOption = uiState.darkTheme.displayName,
                    onOptionSelected = {
                        viewModel.setDarkTheme(CarbonTheme.DarkTheme.fromDisplayName(it))
                    },
                    modifier = Modifier.padding(top = SpacingScale.spacing04)
                )

                BasicText(
                    text = "Adaptation",
                    style = Carbon.typography.heading02.copy(
                        color = Carbon.theme.textPrimary
                    ),
                    modifier = Modifier.padding(top = SpacingScale.spacing06)
                )

                Dropdown(
                    placeholder = "Adaptation",
                    label = "Adaptation",
                    options = remember { Adaptation.entries.toDropdownOptions() },
                    selectedOption = uiState.adaptation,
                    onOptionSelected = viewModel::setAdaptation,
                    modifier = Modifier.padding(top = SpacingScale.spacing03)
                )

                //https://gabrieldrn.github.io/carbon-compose/getting-started/usage/#adaptation

                CalloutNotification(
                    title = "Adaptations",
                    body = buildAnnotatedString {
                        val clickableText = "here"
                        val string = "Adaptations are non-official implementation alternatives" +
                            " to help the design system work across different platforms and keep" +
                            " it accessible. Learn more about this $clickableText."

                        append(string)

                        "non-official".let { boldText ->
                            addStyle(
                                style = typography
                                    .bodyCompact01
                                    .copy(fontWeight = FontWeight.Bold)
                                    .toSpanStyle(),
                                start = string.indexOf(boldText),
                                end = string.indexOf(boldText) + boldText.length
                            )
                        }

                        addLink(
                            url = LinkAnnotation.Url(
                                url = ADAPTATION_DOC_URL,
                                styles = TextLinkStyles(
                                    style = SpanStyle(
                                        color = Carbon.theme.linkPrimary,
                                        textDecoration = TextDecoration.Underline
                                    )
                                ),
                            ),
                            start = string.indexOf(clickableText),
                            end = string.indexOf(clickableText) + clickableText.length
                        )
                    },
                    status = NotificationStatus.Informational,
                    modifier = Modifier.padding(top = SpacingScale.spacing03)
                )
            }
        }
    }
}
