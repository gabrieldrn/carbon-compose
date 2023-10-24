package dev.gabrieldrn.carboncatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.gabrieldrn.carbon.CarbonDesignSystem
import dev.gabrieldrn.carbon.button.Button
import dev.gabrieldrn.carbon.button.CarbonButton
import dev.gabrieldrn.carbon.color.LocalCarbonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarbonDesignSystem {
                Box(
                    modifier = Modifier
                        .background(LocalCarbonTheme.current.background)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.width(IntrinsicSize.Max)
                    ) {
                        Button(
                            label = "Primary button",
                            onClick = {},
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            label = "Secondary button",
                            onClick = {},
                            buttonType = CarbonButton.Secondary,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
