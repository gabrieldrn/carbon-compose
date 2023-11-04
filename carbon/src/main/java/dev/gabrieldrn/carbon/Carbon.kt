package dev.gabrieldrn.carbon

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import dev.gabrieldrn.carbon.color.Gray100Theme
import dev.gabrieldrn.carbon.color.LocalCarbonInlineTheme
import dev.gabrieldrn.carbon.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.color.Theme
import dev.gabrieldrn.carbon.color.WhiteTheme

@Composable
public fun CarbonDesignSystem(
    theme: Theme = if (isSystemInDarkTheme()) {
        Gray100Theme
    } else {
        WhiteTheme
    },
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

public object CarbonDesignSystem {

    public val theme: Theme
        @Composable
        @ReadOnlyComposable
        get() = LocalCarbonTheme.current

    public val inlineTheme: Theme
        @Composable
        @ReadOnlyComposable
        get() = LocalCarbonInlineTheme.current

}
