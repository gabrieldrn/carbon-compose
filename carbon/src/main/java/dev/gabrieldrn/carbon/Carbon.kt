package dev.gabrieldrn.carbon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import dev.gabrieldrn.carbon.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.color.Theme

@Composable
public fun CarbonDesignSystem(
    theme: Theme = CarbonDesignSystem.theme,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalCarbonTheme provides theme
    ) {
        content()
    }
}

public object CarbonDesignSystem {

    public val theme: Theme
        @Composable
        @ReadOnlyComposable
        get() = LocalCarbonTheme.current

}
