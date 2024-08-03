package carbon.compose.catalog.theme

import androidx.compose.runtime.Composable
import carbon.compose.CarbonDesignSystem
import carbon.compose.foundation.color.Gray100Theme

@Composable
@Suppress("UndocumentedPublicFunction")
fun CarbonCatalogTheme(
    content: @Composable () -> Unit
) {
    CarbonDesignSystem(
        uiShellInlineTheme = Gray100Theme,
        content = content
    )
}
