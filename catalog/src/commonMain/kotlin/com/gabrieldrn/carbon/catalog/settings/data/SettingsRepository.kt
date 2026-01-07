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

package com.gabrieldrn.carbon.catalog.settings.data

import com.gabrieldrn.carbon.catalog.theme.CarbonTheme
import com.gabrieldrn.carbon.foundation.misc.Adaptation
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getStringFlow
import com.russhwolf.settings.observable.makeObservable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalSettingsApi::class)
class SettingsRepository {

    private val settings = Settings()
    private val observableSettings = settings.makeObservable()

    var lightTheme
        get() = settings.getString(
            SETTING_KEY_LIGHT_THEME,
            CarbonTheme.LightTheme.W.displayName
        ).let(CarbonTheme.LightTheme::fromDisplayName)
        set(value) {
            observableSettings.putString(SETTING_KEY_LIGHT_THEME, value.displayName)
        }

    var darkTheme
        get() = settings.getString(
            SETTING_KEY_DARK_THEME,
            CarbonTheme.DarkTheme.G100.displayName
        ).let(CarbonTheme.DarkTheme::fromDisplayName)
        set(value) {
            observableSettings.putString(SETTING_KEY_DARK_THEME, value.displayName)
        }

    var adaptation
        get() = settings.getString(
            SETTING_KEY_ADAPTATION,
            Adaptation.None.name
        ).let(Adaptation::valueOf)
        set(value) {
            observableSettings.putString(SETTING_KEY_ADAPTATION, value.name)
        }

    val lightThemeFlow: Flow<CarbonTheme.LightTheme> = observableSettings
        .getStringFlow(
            key = SETTING_KEY_LIGHT_THEME,
            defaultValue = CarbonTheme.LightTheme.W.displayName
        )
        .map(CarbonTheme.LightTheme::fromDisplayName)

    val darkThemeFlow: Flow<CarbonTheme.DarkTheme> = observableSettings
        .getStringFlow(
            key = SETTING_KEY_DARK_THEME,
            defaultValue = CarbonTheme.DarkTheme.G100.displayName
        )
        .map(CarbonTheme.DarkTheme::fromDisplayName)

    val adaptationFlow: Flow<Adaptation> = observableSettings
        .getStringFlow(
            key = SETTING_KEY_ADAPTATION,
            defaultValue = Adaptation.None.name
        )
        .map(Adaptation::valueOf)

    companion object {
        private const val SETTING_KEY_LIGHT_THEME = "lightTheme"
        private const val SETTING_KEY_DARK_THEME = "darkTheme"
        private const val SETTING_KEY_ADAPTATION = "adaptation"
    }
}
