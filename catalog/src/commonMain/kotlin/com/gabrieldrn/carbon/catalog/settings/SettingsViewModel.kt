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

import com.gabrieldrn.carbon.catalog.common.ViewModel
import com.gabrieldrn.carbon.catalog.settings.data.SettingsRepository
import com.gabrieldrn.carbon.catalog.theme.CarbonTheme
import com.gabrieldrn.carbon.foundation.misc.Adaptation
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val uiState: StateFlow<UIState> = with(settingsRepository) {
        combine(lightThemeFlow, darkThemeFlow, adaptationFlow, ::UIState)
            .stateIn(
                scope = this@SettingsViewModel,
                started = SharingStarted.WhileSubscribed(),
                initialValue = UIState(lightTheme, darkTheme, adaptation)
            )
    }

    fun setLightTheme(lightTheme: CarbonTheme.LightTheme) {
        settingsRepository.lightTheme = lightTheme
    }

    fun setDarkTheme(darkTheme: CarbonTheme.DarkTheme) {
        settingsRepository.darkTheme = darkTheme
    }

    fun setAdaptation(adaptation: Adaptation) {
        settingsRepository.adaptation = adaptation
    }

    data class UIState(
        val lightTheme: CarbonTheme.LightTheme,
        val darkTheme: CarbonTheme.DarkTheme,
        val adaptation: Adaptation
    )
}
