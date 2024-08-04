package carbon.compose.catalog

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.Desktop

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Carbon catalog",
    ) {
        Catalog(
            onOpenLink = {
                Desktop.getDesktop()
                    ?.takeIf { it.isSupported(Desktop.Action.BROWSE) }
                    ?.let { desktop ->
                        desktop.browse(java.net.URI(it))
                    }
            }
        )
    }
}
