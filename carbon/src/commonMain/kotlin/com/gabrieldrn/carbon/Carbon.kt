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

package com.gabrieldrn.carbon

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import com.gabrieldrn.carbon.foundation.color.Gray100Theme
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.LocalCarbonInlineTheme
import com.gabrieldrn.carbon.foundation.color.LocalCarbonLayer
import com.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.foundation.color.WhiteTheme
import com.gabrieldrn.carbon.foundation.misc.Adaptation
import com.gabrieldrn.carbon.foundation.misc.LocalCarbonAdaptation
import com.gabrieldrn.carbon.foundation.text.CarbonTypography
import com.gabrieldrn.carbon.foundation.text.LocalCarbonTypography
import com.gabrieldrn.carbon.foundation.text.getIBMPlexMonoFamily
import com.gabrieldrn.carbon.foundation.text.getIBMPlexSansFamily
import com.gabrieldrn.carbon.foundation.text.getIBMPlexSerifFamily

internal const val LOG_TAG = "CarbonDesignSystem"

/**
 * Entry point for a content using the Carbon Design System.
 *
 * Use this composable at the top of your view hierarchy to provide a Carbon [Theme] to the
 * composition.
 *
 * @param theme The [Theme] to provide to the composition. Defaults to [WhiteTheme] if the system
 * is in light mode, or [Gray100Theme] if not.
 * @param uiShellInlineTheme The [Theme] to provide to the composition for UI Shell components.
 * Defaults to [theme].
 * @param layer The [Layer] token to apply to the composition.
 * @param adaptation The [Adaptation] to apply to components.
 * @param content Your UI content.
 */
@Composable
public fun CarbonDesignSystem(
    theme: Theme = if (isSystemInDarkTheme()) Gray100Theme else WhiteTheme,
    uiShellInlineTheme: Theme = theme,
    layer: Layer = Layer.Layer00,
    adaptation: Adaptation = Adaptation.None,
    content: @Composable () -> Unit,
) {
    val ibmPlexSansFamily = getIBMPlexSansFamily()
    val ibmPlexSerifFamily = getIBMPlexSerifFamily()
    val ibmPlexMonoFamily = getIBMPlexMonoFamily()

    val typography = remember(ibmPlexSansFamily, ibmPlexSerifFamily, ibmPlexMonoFamily) {
        CarbonTypography(
            ibmPlexSansFamily = ibmPlexSansFamily,
            ibmPlexSerifFamily = ibmPlexSerifFamily,
            ibmPlexMonoFamily = ibmPlexMonoFamily
        )
    }

    CompositionLocalProvider(
        LocalCarbonTheme provides theme,
        LocalCarbonInlineTheme provides uiShellInlineTheme,
        LocalCarbonLayer provides layer,
        LocalCarbonTypography provides typography,
        LocalCarbonAdaptation provides adaptation
    ) {
        content()
    }
}

/**
 * Convenience class to provide quick access to Carbon themes, layers, typography, and adaptation
 * mode.
 */
public object Carbon {

    /**
     * Current Carbon theme in current composition.
     */
    public val theme: Theme
        @Composable
        @ReadOnlyComposable
        get() = LocalCarbonTheme.current

    /**
     * Current Carbon inline theme in current composition.
     */
    public val inlineTheme: Theme
        @Composable
        @ReadOnlyComposable
        get() = LocalCarbonInlineTheme.current

    /**
     * Current Carbon layer in current composition.
     */
    public val layer: Layer
        @Composable
        @ReadOnlyComposable
        get() = LocalCarbonLayer.current

    /**
     * Current Carbon typography in current composition.
     */
    public val typography: CarbonTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalCarbonTypography.current

    /**
     * Current UI adaptation mode in current composition.
     */
    public val adaptation: Adaptation
        @Composable
        @ReadOnlyComposable
        get() = LocalCarbonAdaptation.current
}
