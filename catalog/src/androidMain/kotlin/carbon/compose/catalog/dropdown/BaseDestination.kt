package carbon.compose.catalog.dropdown

import androidx.navigation.NavDestination

interface BaseDestination {

    val title: String
    val route: String

    companion object {
        infix fun NavDestination.eq(destination: BaseDestination) = destination.route == route
        infix fun BaseDestination.eq(destination: NavDestination) = route == destination.route
    }
}
