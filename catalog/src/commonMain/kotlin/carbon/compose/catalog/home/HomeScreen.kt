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

package carbon.compose.catalog.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import carbon.compose.catalog.CatalogLayoutType
import carbon.compose.catalog.LocalLayoutType
import carbon.compose.catalog.Destination
import carbon.compose.foundation.color.CarbonLayer
import carbon.compose.foundation.spacing.SpacingScale

@Composable
fun HomeScreen(
    onTileClicked: (Destination) -> Unit,
    onOpenLink: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val navBarPaddingValues = WindowInsets.navigationBars
        .only(WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal)
        .asPaddingValues()

    val isVertical = LocalLayoutType.current == CatalogLayoutType.Vertical

    val destinations = remember { Destination.homeTilesDestinations }

    val destinationsWithDemo by remember(destinations) {
        mutableStateOf(destinations.filter { it.route.isNotEmpty() })
    }
    val wipDestinations by remember(destinations) {
        mutableStateOf(destinations.filter { it.route.isEmpty() })
    }

    CarbonLayer {
        if (isVertical) {
            ComponentsLazyGrid(
                destinationsWithDemo = destinationsWithDemo,
                wipDestinations = wipDestinations,
                navBarPaddingValues = navBarPaddingValues,
                onTileClicked = onTileClicked,
                onOpenLink = onOpenLink,
                modifier = modifier
            )
        } else {
            ComponentsLazyRow(
                destinationsWithDemo = destinationsWithDemo,
                wipDestinations = wipDestinations,
                navBarPaddingValues = navBarPaddingValues,
                onTileClicked = onTileClicked,
                onOpenLink = onOpenLink,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun ComponentsLazyRow(
    destinationsWithDemo: List<Destination>,
    wipDestinations: List<Destination>,
    navBarPaddingValues: PaddingValues,
    onTileClicked: (Destination) -> Unit,
    onOpenLink: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(
            start = SpacingScale.spacing05 + navBarPaddingValues
                .calculateLeftPadding(LocalLayoutDirection.current),
            top = SpacingScale.spacing05,
            end = SpacingScale.spacing05 + navBarPaddingValues
                .calculateRightPadding(LocalLayoutDirection.current),
            bottom = SpacingScale.spacing05 +
                navBarPaddingValues.calculateBottomPadding(),
        ),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        modifier = modifier
    ) {
        destinationItems(destinationsWithDemo, onTileClicked)
        if (wipDestinations.isNotEmpty()) {
            item { WIPIndicatorItem(isInVerticalLayout = false) }
            destinationItems(wipDestinations, onTileClicked)
        }
        item { HomeLinksItem(onOpenLink = onOpenLink, isVertical = false) }
    }
}

@Composable
private fun ComponentsLazyGrid(
    destinationsWithDemo: List<Destination>,
    wipDestinations: List<Destination>,
    navBarPaddingValues: PaddingValues,
    onTileClicked: (Destination) -> Unit,
    onOpenLink: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = SpacingScale.spacing05,
            top = SpacingScale.spacing05,
            end = SpacingScale.spacing05,
            bottom = SpacingScale.spacing05 +
                navBarPaddingValues.calculateBottomPadding(),
        ),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        modifier = modifier
    ) {
        destinationItems(destinationsWithDemo, onTileClicked)
        if (wipDestinations.isNotEmpty()) {
            item(span = { GridItemSpan(2) }) {
                WIPIndicatorItem(isInVerticalLayout = true)
            }
            destinationItems(wipDestinations, onTileClicked)
        }
        item(span = { GridItemSpan(2) }) {
            HomeLinksItem(onOpenLink = onOpenLink, isVertical = true)
        }
    }
}

private fun LazyListScope.destinationItems(
    destinationsWithDemo: List<Destination>,
    onTileClicked: (Destination) -> Unit
) {
    items(destinationsWithDemo) { destination ->
        CarbonComponentGridTile(
            destination = destination,
            onTileClicked = { onTileClicked(destination) },
            modifier = Modifier.aspectRatio(1f)
        )
    }
}

private fun LazyGridScope.destinationItems(
    destinationsWithDemo: List<Destination>,
    onTileClicked: (Destination) -> Unit
) {
    items(destinationsWithDemo) { destination ->
        CarbonComponentGridTile(
            destination = destination,
            onTileClicked = { onTileClicked(destination) },
            modifier = Modifier.aspectRatio(1f)
        )
    }
}
