package carbon.compose.catalog.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import carbon.compose.catalog.R
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.CarbonTypography

@Composable
fun HomeScreen(
    onTileClicked: (Destination) -> Unit,
    modifier: Modifier = Modifier
) {
    val navBarPaddingValues = WindowInsets.navigationBars
        .only(WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal)
        .asPaddingValues()

    val isInPortrait = LocalConfiguration.current
        .orientation == Configuration.ORIENTATION_PORTRAIT

    if (isInPortrait) {
        ComponentsLazyGrid(
            destinations = Destination.homeTilesDestinations,
            navBarPaddingValues = navBarPaddingValues,
            onTileClicked = onTileClicked,
            modifier = modifier
        )
    } else {
        ComponentsLazyRow(
            destinations = Destination.homeTilesDestinations,
            navBarPaddingValues = navBarPaddingValues,
            onTileClicked = onTileClicked,
            modifier = modifier
        )
    }
}

@Composable
private fun ComponentsLazyRow(
    destinations: List<Destination>,
    navBarPaddingValues: PaddingValues,
    onTileClicked: (Destination) -> Unit,
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
        items(destinations) { destination ->
            CarbonComponentGridTile(
                destination = destination,
                onTileClicked = { onTileClicked(destination) },
                modifier = Modifier.aspectRatio(1f)
            )
        }
    }
}

@Composable
private fun ComponentsLazyGrid(
    destinations: List<Destination>,
    navBarPaddingValues: PaddingValues,
    onTileClicked: (Destination) -> Unit,
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
        items(destinations) { destination ->
            CarbonComponentGridTile(
                destination = destination,
                onTileClicked = { onTileClicked(destination) },
                modifier = Modifier.aspectRatio(1f)
            )
        }
    }
}

@Composable
private fun CarbonComponentGridTile(
    destination: Destination,
    onTileClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable(onClick = onTileClicked)
            .background(color = LocalCarbonTheme.current.layer01)
            .fillMaxSize()
    ) {
        if (destination.illustration != null) {
            Image(
                painter = painterResource(id = destination.illustration),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            PlaceholderIllustration()
        }
        BasicText(
            text = destination.title,
            style = CarbonTypography.body01.copy(
                color = LocalCarbonTheme.current.textPrimary
            ),
            modifier = Modifier.padding(SpacingScale.spacing05)
        )
    }
}

@Composable
private fun PlaceholderIllustration(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(SpacingScale.spacing05),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painterResource(id = R.drawable.picto_construction_worker),
            contentDescription = null,
            colorFilter = ColorFilter.tint(LocalCarbonTheme.current.iconPrimary),
            modifier = Modifier.size(48.dp)
        )
    }
}
