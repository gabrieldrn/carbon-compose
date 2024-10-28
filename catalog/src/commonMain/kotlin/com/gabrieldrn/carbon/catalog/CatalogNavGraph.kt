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

package com.gabrieldrn.carbon.catalog

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.gabrieldrn.carbon.catalog.BaseDestination.Companion.eq
import com.gabrieldrn.carbon.catalog.dropdown.dropdownNavigation
import com.gabrieldrn.carbon.catalog.home.HomeScreen

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

val deadEndEnterTransition = navigationEnterSlideInTransition
val deadEndExitTransition = navigationExitSlideOutTransition

fun AnimatedContentTransitionScope<NavBackStackEntry>.getEnterTransition() =
    if (initialState.destination eq Destination.Home) {
        navigationEnterSlideInTransition
    } else {
        navigationEnterSlideInInverseTransition
    }

fun AnimatedContentTransitionScope<NavBackStackEntry>.getExitTransition() =
    if (targetState.destination eq Destination.Home) {
        navigationExitSlideOutTransition
    } else {
        navigationExitSlideOutInverseTransition
    }

@Composable
fun rememberNavGraph(
    navController: NavHostController,
    onOpenLink: (String) -> Unit
) = remember(navController) {
    navController.createGraph(startDestination = Destination.Home.route) {
        Destination
            .entries
            .filterNot { it.route.isEmpty() }
            .forEach { dest ->
                when (dest) {
                    Destination.Home -> composable(
                        route = Destination.Home.route,
                        enterTransition = { navigationEnterScaleInTransition },
                        exitTransition = { navigationExitScaleOutTransition },
                    ) {
                        HomeScreen(
                            onTileClicked = { destination ->
                                destination.route
                                    .takeIf { it.isNotEmpty() }
                                    ?.let(navController::navigate)
                            },
                            onOpenLink = onOpenLink
                        )
                    }

                    Destination.Dropdown -> dropdownNavigation(navController)

                    Destination.Settings -> composable(
                        route = Destination.Settings.route,
                        enterTransition = { deadEndEnterTransition },
                        exitTransition = { deadEndExitTransition },
                        content = { dest.content() }
                    )

                    else -> composable(
                        route = dest.route,
                        enterTransition = { getEnterTransition() },
                        exitTransition = { getExitTransition() },
                        content = { dest.content() }
                    )
                }
            }
    }
}
