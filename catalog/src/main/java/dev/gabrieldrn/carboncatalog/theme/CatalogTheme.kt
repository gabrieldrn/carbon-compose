package dev.gabrieldrn.carboncatalog.theme

import androidx.compose.runtime.Composable
import dev.gabrieldrn.carbon.CarbonDesignSystem
import dev.gabrieldrn.carbon.foundation.color.Gray100Theme

@Composable
fun CarbonCatalogTheme(
    content: @Composable () -> Unit
) {
    CarbonDesignSystem(
        uiShellInlineTheme = Gray100Theme,
        content = content
    )
}
