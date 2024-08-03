package carbon.compose.catalog.dropdown

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import carbon.compose.catalog.BaseDestination.Companion.eq
import carbon.compose.catalog.Destination
import carbon.compose.catalog.navigationEnterSlideInInverseTransition
import carbon.compose.catalog.navigationEnterSlideInTransition
import carbon.compose.catalog.navigationExitSlideOutInverseTransition
import carbon.compose.catalog.navigationExitSlideOutTransition

fun NavGraphBuilder.dropdownNavigation(
    navController: NavController
) = navigation(
    startDestination = DropdownNavDestination.Home.route,
    route = Destination.Dropdown.route
) {
    composable(
        route = DropdownNavDestination.Home.route,
        enterTransition = {
            if (targetState.destination eq DropdownNavDestination.Home) {
                if (initialState.destination eq Destination.Home) {
                    navigationEnterSlideInTransition
                } else {
                    navigationEnterSlideInInverseTransition
                }
            } else {
                navigationEnterSlideInInverseTransition
            }
        },
        exitTransition = {
            if (targetState.destination eq Destination.Home) {
                navigationExitSlideOutTransition
            } else {
                navigationExitSlideOutInverseTransition
            }
        },
    ) {
        DropdownDemoMenu(onNavigate = navController::navigate)
    }

    composable(
        route = DropdownNavDestination.Default.route,
        enterTransition = { navigationEnterSlideInTransition },
        exitTransition = { navigationExitSlideOutTransition },
    ) {
        DropdownDemoScreen(DropdownVariant.Default)
    }

    composable(
        route = DropdownNavDestination.MultiSelect.route,
        enterTransition = { navigationEnterSlideInTransition },
        exitTransition = { navigationExitSlideOutTransition },
    ) {
        DropdownDemoScreen(DropdownVariant.Multiselect)
    }
}
