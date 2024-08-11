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

package com.gabrieldrn.carbon.foundation.color

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.gabrieldrn.carbon.Carbon

internal val carbonDefaultTheme = WhiteTheme

/**
 * A [staticCompositionLocalOf] that provides the current Carbon [Theme].
 */
public val LocalCarbonTheme: ProvidableCompositionLocal<Theme> =
    staticCompositionLocalOf { WhiteTheme }

/**
 * A [staticCompositionLocalOf] that provides a Carbon inline [Theme], usually used for components
 * that need to be themed differently from the rest of the app, such as UI Shell components.
 */
public val LocalCarbonInlineTheme: ProvidableCompositionLocal<Theme> =
    staticCompositionLocalOf { Gray100Theme }

/**
 * A [staticCompositionLocalOf] that provides a [Layer] token. Layering tokens are explicit tokens
 * used to manually map the layering model onto components.
 */
public val LocalCarbonLayer: ProvidableCompositionLocal<Layer> =
    staticCompositionLocalOf { Layer.Layer00 }

/**
 * Automatically provides an upper layer to the composition, based on the current layer.
 * @param content Your UI content.
 */
@Composable
public fun CarbonLayer(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        value = LocalCarbonLayer provides Carbon.layer.next(),
        content = content
    )
}

/**
 * Provides a [Layer] to following composition.
 * @param layer The layer to provide.
 * @param content Your UI content.
 */
@Composable
public fun CarbonLayer(
    layer: Layer,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        value = LocalCarbonLayer provides layer,
        content = content
    )
}

/**
 * Applies a background color to the modifier based on the current [Layer].
 */
public fun Modifier.containerBackground(): Modifier = this.composed {
    background(Carbon.theme.containerColor(Carbon.layer))
}
