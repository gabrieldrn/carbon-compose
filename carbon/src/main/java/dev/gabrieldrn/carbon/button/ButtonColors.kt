package dev.gabrieldrn.carbon.button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import dev.gabrieldrn.carbon.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.color.Theme

@Immutable
internal data class ButtonColors(
    val containerColor: Color,
    val containerActiveColor: Color,
    val containerHoverColor: Color,
    val containerDisabledColor: Color,
    val labelColor: Color,
    val labelActiveColor: Color,
    val labelHoverColor: Color,
    val labelDisabledColor: Color,
) {
    companion object {
        @Composable
        @Suppress("CyclomaticComplexMethod")
        fun buttonColors(
            buttonType: CarbonButton,
            theme: Theme = LocalCarbonTheme.current
        ): ButtonColors = ButtonColors(
            containerColor = when (buttonType) {
                CarbonButton.Primary -> theme.buttonPrimary
                CarbonButton.Secondary -> theme.buttonSecondary
                CarbonButton.PrimaryDanger -> theme.buttonDangerPrimary
                CarbonButton.Tertiary,
                CarbonButton.TertiaryDanger,
                CarbonButton.Ghost,
                CarbonButton.GhostDanger -> Color.Transparent
            },
            containerActiveColor = when (buttonType) {
                CarbonButton.Primary -> theme.buttonPrimaryActive
                CarbonButton.Secondary -> theme.buttonSecondaryActive
                CarbonButton.Tertiary -> theme.buttonTertiaryActive
                CarbonButton.Ghost -> theme.backgroundActive
                CarbonButton.PrimaryDanger,
                CarbonButton.TertiaryDanger,
                CarbonButton.GhostDanger -> theme.buttonDangerActive
            },
            containerHoverColor = when (buttonType) {
                CarbonButton.Primary -> theme.buttonPrimaryHover
                CarbonButton.Secondary -> theme.buttonSecondaryHover
                CarbonButton.Tertiary -> theme.buttonTertiaryHover
                CarbonButton.Ghost -> theme.backgroundHover
                CarbonButton.PrimaryDanger,
                CarbonButton.TertiaryDanger,
                CarbonButton.GhostDanger -> theme.buttonDangerHover
            },
            containerDisabledColor = when (buttonType) {
                CarbonButton.Primary,
                CarbonButton.Secondary,
                CarbonButton.PrimaryDanger,
                CarbonButton.TertiaryDanger,
                CarbonButton.GhostDanger -> theme.buttonDisabled
                CarbonButton.Tertiary,
                CarbonButton.Ghost -> Color.Transparent
            },
            labelColor = when (buttonType) {
                CarbonButton.Tertiary -> theme.buttonTertiary
                CarbonButton.Ghost -> theme.linkPrimary
                CarbonButton.TertiaryDanger,
                CarbonButton.GhostDanger -> theme.buttonDangerSecondary
                else -> theme.textOnColor
            },
            labelActiveColor = when (buttonType) {
                CarbonButton.Tertiary -> theme.textInverse
                CarbonButton.Ghost -> theme.linkPrimary
                CarbonButton.TertiaryDanger,
                CarbonButton.GhostDanger -> theme.textOnColor
                else -> theme.textOnColor
            },
            labelHoverColor = when (buttonType) {
                CarbonButton.Tertiary,
                CarbonButton.TertiaryDanger,
                CarbonButton.GhostDanger -> theme.textOnColor
                CarbonButton.Ghost -> theme.linkPrimaryHover
                else -> theme.textOnColor
            },
            labelDisabledColor = when (buttonType) {
                CarbonButton.Tertiary,
                CarbonButton.Ghost,
                CarbonButton.TertiaryDanger,
                CarbonButton.GhostDanger -> theme.textDisabled
                else -> theme.textOnColorDisabled
            }
        )
    }
}
