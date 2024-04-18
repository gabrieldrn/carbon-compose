package carbon.compose.catalog.dropdown

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import carbon.compose.button.Button
import carbon.compose.catalog.R
import carbon.compose.catalog.theme.CarbonCatalogTheme
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.uishell.UiShellHeader

class DropdownDemoActivity : AppCompatActivity() {

    private val navigationEnterTransition =
        slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn()

    private val navigationExitTransition =
        slideOutHorizontally(targetOffsetX = { it }) + fadeOut()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))

        setContent {
            CarbonCatalogTheme {
                DemoContent()
            }
        }
    }

    @Composable
    private fun DemoContent(modifier: Modifier = Modifier) {
        var currentDestinationLabel by remember {
            mutableStateOf<String>(
                DropdownDemoDestination.labels[DropdownDemoDestination.Menu].orEmpty()
            )
        }

        val navController = rememberNavController().apply {
            addOnDestinationChangedListener(
                NavController.OnDestinationChangedListener { _, dest, _ ->
                    currentDestinationLabel =
                        DropdownDemoDestination.labels[dest.route].orEmpty()
                }
            )
        }

        val navGraph = remember(navController) {
            navController.createGraph(
                startDestination = DropdownDemoDestination.Menu
            ) {
                composable(
                    route = DropdownDemoDestination.Menu,
                    enterTransition = {
                        slideInHorizontally(initialOffsetX = { -it * 2 }) + fadeIn()
                    },
                    exitTransition = {
                        slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
                    },
                ) {
                    DropdownDemoMenu(onNavigate = navController::navigate)
                }

                composable(
                    route = DropdownDemoDestination.DefaultDropdown,
                    enterTransition = { navigationEnterTransition },
                    exitTransition = { navigationExitTransition },
                ) {
                    DefaultDropdownScreen()
                }

                composable(
                    route = DropdownDemoDestination.MultiselectDropdown,
                    enterTransition = { navigationEnterTransition },
                    exitTransition = { navigationExitTransition },
                ) {
                    MultiselectDropdownScreen()
                }
            }
        }

        Column(modifier = modifier.background(LocalCarbonTheme.current.background)) {
            UiShellHeader(
                headerName = currentDestinationLabel,
                menuIconRes = R.drawable.ic_arrow_left,
                onMenuIconPressed = { onBackPressedDispatcher.onBackPressed() },
            )

            NavHost(
                navController = navController,
                graph = navGraph,
            )
        }
    }

    @Composable
    private fun DropdownDemoMenu(
        onNavigate: (String) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier
                .background(LocalCarbonTheme.current.background)
                .padding(SpacingScale.spacing03)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing03)
        ) {
            Button(
                label = "Default Dropdown",
                iconPainter = painterResource(id = R.drawable.ic_arrow_right),
                onClick = { onNavigate(DropdownDemoDestination.DefaultDropdown) },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                label = "Multiselect Dropdown",
                iconPainter = painterResource(id = R.drawable.ic_arrow_right),
                onClick = { onNavigate(DropdownDemoDestination.MultiselectDropdown) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
