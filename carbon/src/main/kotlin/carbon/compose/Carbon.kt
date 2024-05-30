package carbon.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import carbon.compose.foundation.color.Gray100Theme
import carbon.compose.foundation.color.Layer
import carbon.compose.foundation.color.LocalCarbonInlineTheme
import carbon.compose.foundation.color.LocalCarbonLayer
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.color.Theme
import carbon.compose.foundation.color.WhiteTheme

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
 * @param content Your UI content.
 */
@Composable
public fun CarbonDesignSystem(
    theme: Theme = if (isSystemInDarkTheme()) Gray100Theme else WhiteTheme,
    uiShellInlineTheme: Theme = theme,
    layer: Layer = Layer.Layer00,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalCarbonTheme provides theme,
        LocalCarbonInlineTheme provides uiShellInlineTheme,
        LocalCarbonLayer provides layer
    ) {
        content()
    }
}

/**
 * Convenience class to provite quick access to Carbon themes and layer.
 */
public object Carbon {

    /**
     * Current Carbon theme in curent composition.
     */
    public val theme: Theme
        @Composable get() = LocalCarbonTheme.current

    /**
     * Current Carbon inline theme in current composition.
     */
    public val inlineTheme: Theme
        @Composable get() = LocalCarbonInlineTheme.current

    /**
     * Current Carbon layer in current composition.
     */
    public val layer: Layer
        @Composable get() = LocalCarbonLayer.current
}
