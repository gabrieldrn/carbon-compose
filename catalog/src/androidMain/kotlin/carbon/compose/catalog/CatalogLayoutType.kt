package carbon.compose.catalog

import androidx.compose.runtime.staticCompositionLocalOf

enum class CatalogLayoutType {
    Vertical,
    Horizontal;

    companion object {
        val LocalLayoutType = staticCompositionLocalOf { Vertical }
    }
}
