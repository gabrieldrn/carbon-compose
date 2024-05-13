package carbon.catalog.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import carbon.android.button.Button
import carbon.android.foundation.color.LocalCarbonTheme
import carbon.android.foundation.spacing.SpacingScale
import carbon.catalog.R

@Composable
fun DropdownDemoMenu(
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(LocalCarbonTheme.current.background)
            .padding(SpacingScale.spacing03)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing03)
    ) {
        Button(
            label = "Default Dropdown",
            iconPainter = painterResource(id = R.drawable.ic_arrow_right),
            onClick = { onNavigate(DropdownNavDestination.Default.route) },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            label = "Multiselect Dropdown",
            iconPainter = painterResource(id = R.drawable.ic_arrow_right),
            onClick = { onNavigate(DropdownNavDestination.MultiSelect.route) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
