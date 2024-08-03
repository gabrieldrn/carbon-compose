package carbon.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import carbon.compose.catalog.Catalog
import carbon.compose.foundation.color.containerBackground
import platform.UIKit.UIViewController

public fun MainViewController(onOpenLink: (String) -> Unit): UIViewController =
    ComposeUIViewController {
        CarbonDesignSystem {
            Box(Modifier.fillMaxSize().containerBackground(), contentAlignment = Alignment.Center) {
                Catalog(onOpenLink = onOpenLink)
            }
        }
    }
