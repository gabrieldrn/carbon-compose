package dev.gabrieldrn.carbon.foundation.color

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

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
