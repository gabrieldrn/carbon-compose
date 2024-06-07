package carbon.compose.button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import carbon.compose.Carbon
import carbon.compose.foundation.color.Theme

@Immutable
internal class ButtonColors private constructor(
    private val theme: Theme,
    private val buttonType: CarbonButton
) {

    val containerColor: Color = when (buttonType) {
        CarbonButton.Primary -> theme.buttonPrimary
        CarbonButton.Secondary -> theme.buttonSecondary
        CarbonButton.PrimaryDanger -> theme.buttonDangerPrimary
        CarbonButton.Tertiary,
        CarbonButton.TertiaryDanger,
        CarbonButton.Ghost,
        CarbonButton.GhostDanger -> Color.Transparent
    }

    val containerActiveColor: Color = when (buttonType) {
        CarbonButton.Primary -> theme.buttonPrimaryActive
        CarbonButton.Secondary -> theme.buttonSecondaryActive
        CarbonButton.Tertiary -> theme.buttonTertiaryActive
        CarbonButton.Ghost -> theme.backgroundActive
        CarbonButton.PrimaryDanger,
        CarbonButton.TertiaryDanger,
        CarbonButton.GhostDanger -> theme.buttonDangerActive
    }

    val containerHoverColor: Color = when (buttonType) {
        CarbonButton.Primary -> theme.buttonPrimaryHover
        CarbonButton.Secondary -> theme.buttonSecondaryHover
        CarbonButton.Tertiary -> theme.buttonTertiaryHover
        CarbonButton.Ghost -> theme.backgroundHover
        CarbonButton.PrimaryDanger,
        CarbonButton.TertiaryDanger,
        CarbonButton.GhostDanger -> theme.buttonDangerHover
    }

    val containerDisabledColor: Color = when (buttonType) {
        CarbonButton.Primary,
        CarbonButton.Secondary,
        CarbonButton.PrimaryDanger,
        CarbonButton.TertiaryDanger,
        CarbonButton.GhostDanger -> theme.buttonDisabled
        CarbonButton.Tertiary,
        CarbonButton.Ghost -> Color.Transparent
    }

    val labelColor: Color = when (buttonType) {
        CarbonButton.Tertiary -> theme.buttonTertiary
        CarbonButton.Ghost -> theme.linkPrimary
        CarbonButton.TertiaryDanger,
        CarbonButton.GhostDanger -> theme.buttonDangerSecondary
        else -> theme.textOnColor
    }

    val labelActiveColor: Color = when (buttonType) {
        CarbonButton.Tertiary -> theme.textInverse
        CarbonButton.Ghost -> theme.linkPrimary
        CarbonButton.TertiaryDanger,
        CarbonButton.GhostDanger -> theme.textOnColor
        else -> theme.textOnColor
    }

    val labelHoverColor: Color = when (buttonType) {
        CarbonButton.Tertiary,
        CarbonButton.TertiaryDanger,
        CarbonButton.GhostDanger -> theme.textOnColor
        CarbonButton.Ghost -> theme.linkPrimaryHover
        else -> theme.textOnColor
    }

    val labelDisabledColor: Color = when (buttonType) {
        CarbonButton.Tertiary,
        CarbonButton.Ghost,
        CarbonButton.TertiaryDanger,
        CarbonButton.GhostDanger -> theme.textDisabled
        else -> theme.textOnColorDisabled
    }

    val iconColor: Color = when (buttonType) {
        CarbonButton.Tertiary -> theme.buttonTertiary
        CarbonButton.Ghost -> theme.linkPrimary
        CarbonButton.PrimaryDanger -> theme.iconOnColor
        CarbonButton.TertiaryDanger,
        CarbonButton.GhostDanger -> theme.buttonDangerSecondary
        else -> theme.iconOnColor
    }

    val iconActiveColor: Color = when (buttonType) {
        CarbonButton.Tertiary -> theme.iconInverse
        CarbonButton.Ghost -> theme.linkPrimary // ø
        else -> theme.iconOnColor
    }

    val iconHoverColor: Color = when (buttonType) {
        CarbonButton.Tertiary -> theme.iconInverse
        CarbonButton.Ghost -> theme.linkPrimaryHover
        else -> theme.iconOnColor
    }

    val iconDisabledColor: Color = when (buttonType) {
        CarbonButton.Primary,
        CarbonButton.Secondary,
        CarbonButton.PrimaryDanger -> theme.iconOnColorDisabled
        else -> theme.iconDisabled // Issue in documentation for Ghost
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ButtonColors) return false

        if (theme != other.theme) return false
        if (buttonType != other.buttonType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = theme.hashCode()
        result = 31 * result + buttonType.hashCode()
        return result
    }

    companion object {

        @Composable
        fun colors(
            buttonType: CarbonButton,
        ): ButtonColors = ButtonColors(Carbon.theme, buttonType)
    }
}
