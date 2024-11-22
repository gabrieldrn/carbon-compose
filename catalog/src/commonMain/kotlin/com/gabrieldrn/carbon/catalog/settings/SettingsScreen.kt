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
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.catalog.di.injectViewModel
import com.gabrieldrn.carbon.catalog.theme.CarbonTheme
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.containerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.radiobutton.RadioButton

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = injectViewModel<SettingsViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues()),
    ) {
        CarbonLayer {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .containerBackground()
                    .padding(SpacingScale.spacing05)
            ) {
                BasicText(
                    text = "Themes",
                    style = Carbon.typography.heading02.copy(
                        color = Carbon.theme.textPrimary
                    ),
                )

                BasicText(
                    text = "Light",
                    style = Carbon.typography.heading01.copy(
                        color = Carbon.theme.textPrimary
                    ),
                    modifier = Modifier.padding(top = SpacingScale.spacing03)
                )

                CarbonTheme.LightTheme.entries.forEach { theme ->
                    RadioButton(
                        selected = theme == uiState.lightTheme,
                        label = theme.displayName,
                        onClick = { viewModel.setLightTheme(theme) },
                        modifier = Modifier.padding(top = SpacingScale.spacing03)
                    )
                }

                BasicText(
                    text = "Dark",
                    style = Carbon.typography.heading01.copy(
                        color = Carbon.theme.textPrimary
                    ),
                    modifier = Modifier.padding(top = SpacingScale.spacing05)
                )

                CarbonTheme.DarkTheme.entries.forEach { theme ->
                    RadioButton(
                        selected = theme == uiState.darkTheme,
                        label = theme.displayName,
                        onClick = { viewModel.setDarkTheme(theme) },
                        modifier = Modifier.padding(top = SpacingScale.spacing03)
                    )
                }
            }
        }
    }
}