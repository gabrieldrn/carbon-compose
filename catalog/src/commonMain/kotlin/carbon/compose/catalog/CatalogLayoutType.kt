package carbon.compose.catalog

import androidx.compose.runtime.staticCompositionLocalOf
import carbon.compose.catalog.CatalogLayoutType.Vertical

val LocalLayoutType = staticCompositionLocalOf { Vertical }

enum class CatalogLayoutType {
    Vertical,
    Horizontal
}
