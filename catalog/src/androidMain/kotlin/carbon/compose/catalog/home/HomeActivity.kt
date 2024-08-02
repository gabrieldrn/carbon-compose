package carbon.compose.catalog.home

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import carbon.compose.catalog.Res
import carbon.compose.catalog.dropdown.BaseDestination
import carbon.compose.catalog.dropdown.BaseDestination.Companion.eq
import carbon.compose.catalog.dropdown.DropdownNavDestination
import carbon.compose.catalog.ic_arrow_left
import carbon.compose.catalog.theme.CarbonCatalogTheme
import carbon.compose.foundation.color.containerBackground
import carbon.compose.uishell.UiShellHeader
import org.jetbrains.compose.resources.painterResource

@Suppress("UndocumentedPublicClass")
class HomeActivity : AppCompatActivity() {

    private val allDestinations: List<BaseDestination> =
        Destination.entries + DropdownNavDestination.entries

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))

        setContent {
            CarbonCatalogTheme {
                var currentScreen: BaseDestination by remember {
                    mutableStateOf(Destination.Home)
                }

                val navController = rememberNavController().apply {
                    addOnDestinationChangedListener(
                        NavController.OnDestinationChangedListener { _, destination, _ ->
                            currentScreen = allDestinations.first { it eq destination }
                        }
                    )
                }

                val navGraph = rememberNavGraph(
                    navController = navController,
                    onOpenLink = {
                        startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(it))
                        )
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .containerBackground()
                ) {
                    UiShellHeader(
                        headerName = currentScreen.title,
                        menuIconPainter = if (currentScreen != Destination.Home) {
                            painterResource(Res.drawable.ic_arrow_left)
                        } else {
                            null
                        },
                        onMenuIconPressed = { navController.navigateUp() },
                    )

                    NavHost(
                        navController = navController,
                        graph = navGraph,
                    )
                }
            }
        }
    }
}
