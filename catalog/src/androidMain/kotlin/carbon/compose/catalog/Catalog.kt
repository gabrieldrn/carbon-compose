package carbon.compose.catalog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import carbon.compose.catalog.dropdown.BaseDestination
import carbon.compose.catalog.dropdown.BaseDestination.Companion.eq
import carbon.compose.catalog.dropdown.DropdownNavDestination
import carbon.compose.catalog.home.Destination
import carbon.compose.catalog.home.rememberNavGraph
import carbon.compose.catalog.theme.CarbonCatalogTheme
import carbon.compose.foundation.color.containerBackground
import carbon.compose.uishell.UiShellHeader
import org.jetbrains.compose.resources.painterResource

@Composable
fun Catalog(
    onOpenLink: (String) -> Unit
) {
    CarbonCatalogTheme {
        val allDestinations = remember {
            Destination.entries + DropdownNavDestination.entries
        }

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
            onOpenLink = onOpenLink,
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
