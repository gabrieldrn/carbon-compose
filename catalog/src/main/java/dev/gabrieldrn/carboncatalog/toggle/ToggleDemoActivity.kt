package dev.gabrieldrn.carboncatalog.toggle

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.gabrieldrn.carbon.CarbonDesignSystem
import dev.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.foundation.spacing.SpacingScale
import dev.gabrieldrn.carbon.toggle.Toggle
import dev.gabrieldrn.carbon.uishell.UiShellHeader
import dev.gabrieldrn.carboncatalog.R

class ToggleDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CarbonDesignSystem {
                Column(
                    modifier = Modifier
                        .background(LocalCarbonTheme.current.background)
                        .fillMaxSize(),
                ) {
                    UiShellHeader(
                        headerName = "Toggle",
                        menuIconRes = R.drawable.ic_arrow_left,
                        onMenuIconPressed = onBackPressedDispatcher::onBackPressed,
                    )

                    var isToggled by remember { mutableStateOf(false) }

                    Toggle(
                        isToggled = isToggled,
                        onToggleChange = { isToggled = it },
                        labelText = "Toggle",
                        actionText = if (isToggled) "On" else "Off",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(SpacingScale.spacing05)
                    )
                }
            }
        }
    }
}
