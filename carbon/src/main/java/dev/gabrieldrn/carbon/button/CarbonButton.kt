package dev.gabrieldrn.carbon.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.gabrieldrn.carbon.color.LocalCarbonTheme

public enum class CarbonButton {

    Primary,
    Secondary,
    Tertiary,
    Ghost,
    PrimaryDanger,
    TertiaryDanger,
    GhostDanger;

    public val textColor: Color
        @Composable get() = when (this) {
            Primary -> LocalCarbonTheme.current.textOnColor
            Secondary -> LocalCarbonTheme.current.textOnColor
            Tertiary -> LocalCarbonTheme.current.buttonTertiary
            Ghost -> LocalCarbonTheme.current.linkPrimary
            PrimaryDanger -> LocalCarbonTheme.current.textOnColor
            TertiaryDanger -> LocalCarbonTheme.current.buttonDangerSecondary
            GhostDanger -> LocalCarbonTheme.current.buttonDangerSecondary
        }
}
