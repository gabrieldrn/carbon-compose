package carbon.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import carbon.compose.foundation.color.Gray100Theme
import carbon.compose.foundation.color.LocalCarbonInlineTheme
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.color.Theme
import carbon.compose.foundation.color.WhiteTheme

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
 * @param content Your UI content.
 */
@Composable
public fun CarbonDesignSystem(
    theme: Theme = if (isSystemInDarkTheme()) Gray100Theme else WhiteTheme,
    uiShellInlineTheme: Theme = theme,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalCarbonTheme provides theme,
        LocalCarbonInlineTheme provides uiShellInlineTheme
    ) {
        content()
    }
}
