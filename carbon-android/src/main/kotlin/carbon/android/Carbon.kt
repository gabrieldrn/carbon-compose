package carbon.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import carbon.android.foundation.color.Gray100Theme
import carbon.android.foundation.color.LocalCarbonInlineTheme
import carbon.android.foundation.color.LocalCarbonTheme
import carbon.android.foundation.color.Theme
import carbon.android.foundation.color.WhiteTheme

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
