package carbon.compose.catalog.home

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import carbon.compose.catalog.buttons.ButtonDemoScreen
import carbon.compose.catalog.checkbox.CheckboxDemoScreen
import carbon.compose.catalog.dropdown.dropdownNavigation
import carbon.compose.catalog.toggle.ToggleDemoScreen

val navigationEnterScaleInTransition =
    scaleIn(initialScale = 0.9f) + fadeIn()

val navigationExitScaleOutTransition =
    scaleOut(targetScale = 0.9f) + fadeOut()

val navigationEnterSlideInTransition =
    slideInHorizontally(initialOffsetX = { it }) + fadeIn() // right to left

val navigationEnterSlideInInverseTransition =
    slideInHorizontally(initialOffsetX = { -it }) + fadeIn() // left to right

val navigationExitSlideOutTransition =
    slideOutHorizontally(targetOffsetX = { it }) + fadeOut() // left to right

val navigationExitSlideOutInverseTransition =
    slideOutHorizontally(targetOffsetX = { -it }) + fadeOut() // right to left

@Composable
fun rememberNavGraph(navController: NavHostController) =
    remember(navController) {
        navController.createGraph(
            startDestination = Destination.Home.route
        ) {
            composable(
                route = Destination.Home.route,
                enterTransition = { navigationEnterScaleInTransition },
                exitTransition = { navigationExitScaleOutTransition },
            ) {
                HomeScreen(
                    onTileClicked = { destination ->
                        destination.route
                            .takeIf { it.isNotEmpty() }
                            ?.let(navController::navigate)
                    }
                )
            }

            composable(
                route = Destination.Button.route,
                enterTransition = { navigationEnterSlideInTransition },
                exitTransition = { navigationExitSlideOutTransition },
            ) {
                ButtonDemoScreen()
            }

            composable(
                route = Destination.Checkbox.route,
                enterTransition = { navigationEnterSlideInTransition },
                exitTransition = { navigationExitSlideOutTransition },
            ) {
                CheckboxDemoScreen()
            }

            dropdownNavigation(navController)

            composable(
                route = Destination.Toggle.route,
                enterTransition = { navigationEnterSlideInTransition },
                exitTransition = { navigationExitSlideOutTransition },
            ) {
                ToggleDemoScreen()
            }
        }
    }
