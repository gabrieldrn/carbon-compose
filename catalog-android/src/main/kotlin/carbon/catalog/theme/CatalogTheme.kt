package carbon.catalog.theme

import androidx.compose.runtime.Composable
import carbon.android.CarbonDesignSystem
import carbon.android.foundation.color.Gray100Theme

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
