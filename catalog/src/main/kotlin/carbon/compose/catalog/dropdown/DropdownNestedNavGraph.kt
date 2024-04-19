package carbon.compose.catalog.dropdown

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import carbon.compose.catalog.home.Destination
import carbon.compose.catalog.home.Destination.Companion.eq
import carbon.compose.catalog.home.navigationEnterSlideInInverseTransition
import carbon.compose.catalog.home.navigationEnterSlideInTransition
import carbon.compose.catalog.home.navigationExitSlideOutInverseTransition
import carbon.compose.catalog.home.navigationExitSlideOutTransition

fun NavGraphBuilder.dropdownNavigation(
    navController: NavController
) = navigation(
    startDestination = Destination.Dropdown_Home.route,
    route = Destination.Dropdown_SubDestination.route
) {
    composable(
        route = Destination.Dropdown_Home.route,
        enterTransition = {
            if (targetState.destination eq Destination.Dropdown_Home) {
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
        route = Destination.Dropdown_Default.route,
        enterTransition = { navigationEnterSlideInTransition },
        exitTransition = { navigationExitSlideOutTransition },
    ) {
        DefaultDropdownScreen()
    }

    composable(
        route = Destination.Dropdown_MultiSelect.route,
        enterTransition = { navigationEnterSlideInTransition },
        exitTransition = { navigationExitSlideOutTransition },
    ) {
        MultiselectDropdownScreen()
    }
}
