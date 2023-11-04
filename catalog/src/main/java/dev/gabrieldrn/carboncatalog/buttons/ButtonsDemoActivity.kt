package dev.gabrieldrn.carboncatalog.buttons

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import dev.gabrieldrn.carbon.CarbonDesignSystem
import dev.gabrieldrn.carbon.button.Button
import dev.gabrieldrn.carbon.button.CarbonButton
import dev.gabrieldrn.carbon.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.spacing.SpacingScale
import dev.gabrieldrn.carbon.text.CarbonTypography
import dev.gabrieldrn.carboncatalog.R

class ButtonsDemoActivity : AppCompatActivity() {

    private val buttons = mapOf(
        "Primary" to CarbonButton.Primary,
        "Secondary" to CarbonButton.Secondary,
        "Tertiary" to CarbonButton.Tertiary,
        "Ghost" to CarbonButton.Ghost,
        "PrimaryDanger" to CarbonButton.PrimaryDanger,
        "TertiaryDanger" to CarbonButton.TertiaryDanger,
        "GhostDanger" to CarbonButton.GhostDanger,
    )

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
                    Row(
                        modifier = Modifier
                            .background(LocalCarbonTheme.current.backgroundInverse)
                            .padding(SpacingScale.spacing05)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow__left),
                            contentDescription = "Back",
                            colorFilter = ColorFilter.tint(LocalCarbonTheme.current.textInverse),
                            modifier = Modifier.clickable(onClick = ::onBackPressed)
                        )
                        Spacer(modifier = Modifier.width(SpacingScale.spacing05))
                        BasicText(
                            text = "Button",
                            style = CarbonTypography.heading03.copy(
                                color = LocalCarbonTheme.current.textInverse
                            )
                        )
                    }

                    var isEnabled by remember {
                        mutableStateOf(true)
                    }

                    Button(
                        label = "Toggle isEnabled",
                        onClick = { isEnabled = !isEnabled },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Column(
                        modifier = Modifier
                            .padding(horizontal = SpacingScale.spacing05)
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(
                            SpacingScale.spacing05,
                            Alignment.CenterVertically
                        ),
                    ) {
                        buttons.forEach { (label, buttonType) ->
                            Button(
                                label = label,
                                onClick = {},
                                buttonType = buttonType,
                                modifier = Modifier.fillMaxWidth(),
                                isEnabled = isEnabled
                            )
                        }
                    }
                }
            }
        }
    }
}
