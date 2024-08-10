/*
 * Copyright 2024 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
