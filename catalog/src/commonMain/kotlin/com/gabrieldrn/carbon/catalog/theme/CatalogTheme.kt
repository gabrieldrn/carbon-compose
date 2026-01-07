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

package com.gabrieldrn.carbon.catalog.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.catalog.di.injectViewModel
import com.gabrieldrn.carbon.catalog.settings.SettingsViewModel
import com.gabrieldrn.carbon.foundation.color.Gray100Theme

@Composable
@Suppress("UndocumentedPublicFunction")
fun CarbonCatalogTheme(
    content: @Composable () -> Unit
) {
    val settingsViewModel = injectViewModel<SettingsViewModel>()
    val uiState by settingsViewModel.uiState.collectAsState()

    CarbonDesignSystem(
        theme = if (isSystemInDarkTheme()) {
            uiState.darkTheme.theme
        } else {
            uiState.lightTheme.theme
        },
        adaptation = uiState.adaptation,
        uiShellInlineTheme = Gray100Theme,
        content = content
    )
}
