import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.bindToNavigation
import androidx.navigation.compose.rememberNavController
import com.gabrieldrn.carbon.catalog.Catalog
import com.gabrieldrn.carbon.catalog.di.appModule
import kotlinx.browser.document
import kotlinx.browser.window
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class, ExperimentalBrowserHistoryApi::class)
fun main() {
    startKoin {
        modules(appModule())
    }

    val body = document.body ?: return
    ComposeViewport(body) {
        val navController = rememberNavController()
        Catalog(navController = navController)

        LaunchedEffect(Unit) {
            window.bindToNavigation(navController)
        }
    }
}
