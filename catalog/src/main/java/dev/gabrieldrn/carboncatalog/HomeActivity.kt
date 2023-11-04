package dev.gabrieldrn.carboncatalog

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.gabrieldrn.carbon.CarbonDesignSystem
import dev.gabrieldrn.carbon.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.spacing.SpacingScale
import dev.gabrieldrn.carbon.text.CarbonTypography
import dev.gabrieldrn.carbon.uishell.UiShellHeader

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CarbonDesignSystem {
                Column(
                    modifier = Modifier
                        .background(LocalCarbonTheme.current.background)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UiShellHeader(headerName = "Carbon Design System")
                    BoxWithConstraints(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.FixedSize(maxWidth / 2)
                        ) {
                            items(CarbonComponent.entries) { component ->
                                CarbonComponentGridTile(
                                    component = component,
                                    onClick = {
                                        component.demoActivity?.let {
                                            startActivity(Intent(this@HomeActivity, it))
                                        }
                                    },
                                    modifier = Modifier.aspectRatio(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CarbonComponentGridTile(
        component: CarbonComponent,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Box(
            modifier = modifier
                .clickable(onClick = onClick)
                .background(color = LocalCarbonTheme.current.layer01)
                .border(width = 1.dp, color = LocalCarbonTheme.current.borderTile01)
                .fillMaxSize()
        ) {
            if (component.illustration != null) {
                Image(
                    painter = painterResource(id = component.illustration),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                PlaceholderIllustration()
            }

            BasicText(
                text = component.title,
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
}
