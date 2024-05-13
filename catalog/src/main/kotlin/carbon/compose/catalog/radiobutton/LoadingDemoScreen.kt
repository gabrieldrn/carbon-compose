package carbon.compose.catalog.radiobutton

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import carbon.android.foundation.color.LocalCarbonTheme
import carbon.android.foundation.spacing.SpacingScale
import carbon.android.foundation.text.CarbonTypography
import carbon.android.loading.Loading
import carbon.android.loading.SmallLoading

@Composable
fun LoadingDemoScreen(modifier: Modifier = Modifier) {
    @Composable
    fun LargeLoadingDemo() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BasicText(
                text = "Large",
                style = CarbonTypography.bodyCompact01.copy(
                    color = LocalCarbonTheme.current.textPrimary
                )
            )
            Loading(modifier = Modifier.padding(top = SpacingScale.spacing04))
        }
    }

    @Composable
    fun SmallLoadingDemo() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BasicText(
                text = "Small",
                style = CarbonTypography.bodyCompact01.copy(
                    color = LocalCarbonTheme.current.textPrimary
                )
            )
            SmallLoading(modifier = Modifier.padding(top = SpacingScale.spacing04))
        }
    }

    val isInPortrait = LocalConfiguration.current
        .orientation == Configuration.ORIENTATION_PORTRAIT

    if (isInPortrait) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = SpacingScale.spacing10,
                alignment = Alignment.CenterVertically
            )
        ) {
            LargeLoadingDemo()
            SmallLoadingDemo()
        }
    } else {
        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = SpacingScale.spacing11,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            LargeLoadingDemo()
            SmallLoadingDemo()
        }
    }
}
