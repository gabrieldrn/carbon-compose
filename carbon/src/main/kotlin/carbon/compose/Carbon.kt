package carbon.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import carbon.compose.foundation.color.Gray100Theme
import carbon.compose.foundation.color.LocalCarbonInlineTheme
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.color.Theme
import carbon.compose.foundation.color.WhiteTheme

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
