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

package com.gabrieldrn.carbon.catalog.dropdown

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gabrieldrn.carbon.catalog.BaseDestination.Companion.eq
import com.gabrieldrn.carbon.catalog.Destination
import com.gabrieldrn.carbon.catalog.getEnterTransition
import com.gabrieldrn.carbon.catalog.getExitTransition
import com.gabrieldrn.carbon.catalog.navigationEnterSlideInInverseTransition
import com.gabrieldrn.carbon.catalog.navigationEnterSlideInTransition
import com.gabrieldrn.carbon.catalog.navigationExitSlideOutInverseTransition
import com.gabrieldrn.carbon.catalog.navigationExitSlideOutTransition

fun AnimatedContentTransitionScope<NavBackStackEntry>.getDropdownScreenEnterTransition() =
    if (initialState.destination eq Destination.Settings) {
        navigationEnterSlideInInverseTransition
    } else {
        navigationEnterSlideInTransition
    }

fun AnimatedContentTransitionScope<NavBackStackEntry>.getDropdownScreenExitTransition() =
    if (targetState.destination eq Destination.Settings) {
        navigationExitSlideOutInverseTransition
    } else {
        navigationExitSlideOutTransition
    }

fun NavGraphBuilder.dropdownNavigation(
    navController: NavController
) = navigation(
    startDestination = DropdownNavDestination.Home.route,
    route = Destination.Dropdown.route
) {
    composable(
        route = DropdownNavDestination.Home.route,
        enterTransition = { getEnterTransition() },
        exitTransition = { getExitTransition() },
    ) {
        DropdownDemoMenu(onNavigate = navController::navigate)
    }

    composable(
        route = DropdownNavDestination.Default.route,
        enterTransition = { getDropdownScreenEnterTransition() },
        exitTransition = { getDropdownScreenExitTransition() },
    ) {
        DropdownDemoScreen(DropdownVariant.Default)
    }

    composable(
        route = DropdownNavDestination.MultiSelect.route,
        enterTransition = { getDropdownScreenEnterTransition() },
        exitTransition = { getDropdownScreenExitTransition() },
    ) {
        DropdownDemoScreen(DropdownVariant.Multiselect)
    }
}
