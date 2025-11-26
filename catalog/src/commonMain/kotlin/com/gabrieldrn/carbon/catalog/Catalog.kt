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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gabrieldrn.carbon.api.ExperimentalCarbonApi
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.catalog.BaseDestination.Companion.eq
import com.gabrieldrn.carbon.catalog.common.LocalGridBreakpoint
import com.gabrieldrn.carbon.catalog.common.getGridBreakpoint
import com.gabrieldrn.carbon.catalog.theme.CarbonCatalogTheme
import com.gabrieldrn.carbon.foundation.color.layerBackground
import com.gabrieldrn.carbon.uishell.UiShellHeader
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalCarbonApi::class)
@Composable
fun Catalog(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    layoutType: CatalogLayoutType = CatalogLayoutType.Vertical,
) {
    CarbonCatalogTheme {
        val allDestinations = remember { Destination.entries }

        var currentScreen: BaseDestination by rememberSaveable {
            mutableStateOf(Destination.Home)
        }

        val uriHandler = LocalUriHandler.current
        val navGraph = rememberNavGraph(
            navController = navController,
            onOpenLink = uriHandler::openUri,
        )

        val baseWindowInsets = WindowInsets.statusBars.add(WindowInsets.displayCutout)

        val windowInsets by remember(baseWindowInsets, layoutType) {
            mutableStateOf(
                baseWindowInsets.only(
                    if (layoutType == CatalogLayoutType.Vertical) {
                        WindowInsetsSides.Top
                    } else {
                        WindowInsetsSides.Top + WindowInsetsSides.Horizontal
                    }
                )
            )
        }

        LaunchedEffect(navController, allDestinations) {
            navController.apply {
                addOnDestinationChangedListener { _, destination, _ ->
                    currentScreen = allDestinations.first { it eq destination }
                }
            }
        }

        CompositionLocalProvider(
            LocalCatalogLayoutType provides layoutType,
            LocalCatalogWindowInsets provides windowInsets,
            LocalGridBreakpoint provides getGridBreakpoint()
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .layerBackground()
            ) {
                UiShellHeader(
                    headerName = currentScreen.title,
                    menuIconPainter = if (currentScreen != Destination.Home) {
                        painterResource(Res.drawable.ic_arrow_left)
                    } else {
                        null
                    },
                    menuIconContentDescription = "Navigate up",
                    actions = {
                        if (currentScreen != Destination.Settings) {
                            IconButton(
                                onClick = { navController.navigate(Destination.Settings.route) },
                                iconPainter = painterResource(Res.drawable.ic_settings),
                                buttonType = ButtonType.Ghost,
                            )
                        }
                    },
                    onMenuIconPressed = { navController.navigateUp() },
                    windowInsets = windowInsets
                )

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    NavHost(
                        navController = navController,
                        graph = navGraph,
                    )
                }
            }
        }
    }
}
