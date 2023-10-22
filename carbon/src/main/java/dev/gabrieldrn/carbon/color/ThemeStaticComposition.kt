package dev.gabrieldrn.carbon.color

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * A [staticCompositionLocalOf] that provides the current Carbon [Theme].
 */
public val LocalCarbonTheme: ProvidableCompositionLocal<Theme> =
    staticCompositionLocalOf { WhiteTheme }
