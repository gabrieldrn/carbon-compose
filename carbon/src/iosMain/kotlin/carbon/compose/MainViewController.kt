package carbon.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import carbon.compose.foundation.color.containerBackground
import carbon.compose.foundation.text.Text
import org.jetbrains.compose.resources.painterResource
import platform.UIKit.UIViewController

public fun MainViewController(): UIViewController =
    ComposeUIViewController {
        CarbonDesignSystem {
            Box(Modifier.fillMaxSize().containerBackground(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(Res.drawable.ic_carbon),
                        colorFilter = ColorFilter.tint(Carbon.theme.iconPrimary),
                        contentDescription = null
                    )
                    Text(
                        text = "Hello, Carbon!",
                        color = Carbon.theme.textPrimary,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
