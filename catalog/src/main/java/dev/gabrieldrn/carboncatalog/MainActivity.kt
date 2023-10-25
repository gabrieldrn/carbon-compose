package dev.gabrieldrn.carboncatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import dev.gabrieldrn.carbon.CarbonDesignSystem
import dev.gabrieldrn.carbon.button.Button
import dev.gabrieldrn.carbon.button.CarbonButton
import dev.gabrieldrn.carbon.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.spacing.SpacingScale
import dev.gabrieldrn.carbon.text.CarbonTypography

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarbonDesignSystem {
                Column(
                    modifier = Modifier
                        .background(LocalCarbonTheme.current.background)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BasicText(
                        text = "Carbon Design System",
                        style = CarbonTypography.heading03.copy(
                            color = LocalCarbonTheme.current.textOnColor
                        ),
                        modifier = Modifier
                            .background(LocalCarbonTheme.current.backgroundInverse)
                            .padding(SpacingScale.spacing05)
                            .fillMaxWidth()
                    )

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
                            .width(IntrinsicSize.Max)
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            label = "Primary button",
                            onClick = {},
                            modifier = Modifier.fillMaxWidth(),
                            isEnabled = isEnabled
                        )
                        Button(
                            label = "Secondary button",
                            onClick = {},
                            buttonType = CarbonButton.Secondary,
                            modifier = Modifier.fillMaxWidth(),
                            isEnabled = isEnabled
                        )
                        Button(
                            label = "Tertiary button",
                            onClick = {},
                            buttonType = CarbonButton.Tertiary,
                            modifier = Modifier.fillMaxWidth(),
                            isEnabled = isEnabled
                        )
                        Button(
                            label = "Ghost button",
                            onClick = {},
                            buttonType = CarbonButton.Ghost,
                            modifier = Modifier.fillMaxWidth(),
                            isEnabled = isEnabled
                        )
                        Button(
                            label = "Primary danger button",
                            onClick = {},
                            buttonType = CarbonButton.PrimaryDanger,
                            modifier = Modifier.fillMaxWidth(),
                            isEnabled = isEnabled
                        )
                        Button(
                            label = "Tertiary danger button",
                            onClick = {},
                            buttonType = CarbonButton.TertiaryDanger,
                            modifier = Modifier.fillMaxWidth(),
                            isEnabled = isEnabled
                        )
                        Button(
                            label = "Ghost danger button",
                            onClick = {},
                            buttonType = CarbonButton.GhostDanger,
                            modifier = Modifier.fillMaxWidth(),
                            isEnabled = isEnabled
                        )
                    }
                }
            }
        }
    }
}
