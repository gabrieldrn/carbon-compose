package carbon.compose.button

import androidx.compose.ui.graphics.Color
import carbon.compose.BaseColorsTest
import org.junit.Test
import kotlin.test.assertEquals

class ButtonColorsTest : BaseColorsTest() {

    @Suppress("CognitiveComplexMethod", "CyclomaticComplexMethod", "LongMethod")
    @Test
    fun buttonColors_static_colorsAreCorrect() {
        forAllLayersAndStates(CarbonButton.entries) { buttonType, _ ->
            val colors = ButtonColors.colors(buttonType = buttonType)

            assertEquals(
                expected = when (buttonType) {
                    CarbonButton.Primary -> theme.buttonPrimary
                    CarbonButton.Secondary -> theme.buttonSecondary
                    CarbonButton.PrimaryDanger -> theme.buttonDangerPrimary
                    CarbonButton.Tertiary,
                    CarbonButton.TertiaryDanger,
                    CarbonButton.Ghost,
                    CarbonButton.GhostDanger -> Color.Transparent
                },
                actual = colors.containerColor
            )

            assertEquals(
                expected = when (buttonType) {
                    CarbonButton.Primary -> theme.buttonPrimaryActive
                    CarbonButton.Secondary -> theme.buttonSecondaryActive
                    CarbonButton.Tertiary -> theme.buttonTertiaryActive
                    CarbonButton.Ghost -> theme.backgroundActive
                    CarbonButton.PrimaryDanger,
                    CarbonButton.TertiaryDanger,
                    CarbonButton.GhostDanger -> theme.buttonDangerActive
                },
                actual = colors.containerActiveColor
            )

            assertEquals(
                expected = when (buttonType) {
                    CarbonButton.Primary -> theme.buttonPrimaryHover
                    CarbonButton.Secondary -> theme.buttonSecondaryHover
                    CarbonButton.Tertiary -> theme.buttonTertiaryHover
                    CarbonButton.Ghost -> theme.backgroundHover
                    CarbonButton.PrimaryDanger,
                    CarbonButton.TertiaryDanger,
                    CarbonButton.GhostDanger -> theme.buttonDangerHover
                },
                actual = colors.containerHoverColor
            )

            assertEquals(
                expected = when (buttonType) {
                    CarbonButton.Primary,
                    CarbonButton.Secondary,
                    CarbonButton.PrimaryDanger,
                    CarbonButton.TertiaryDanger,
                    CarbonButton.GhostDanger -> theme.buttonDisabled
                    CarbonButton.Tertiary,
                    CarbonButton.Ghost -> Color.Transparent
                },
                actual = colors.containerDisabledColor
            )

            assertEquals(
                expected = when (buttonType) {
                    CarbonButton.Tertiary -> theme.buttonTertiary
                    CarbonButton.Ghost -> theme.linkPrimary
                    CarbonButton.TertiaryDanger,
                    CarbonButton.GhostDanger -> theme.buttonDangerSecondary
                    else -> theme.textOnColor
                },
                actual = colors.labelColor
            )

            assertEquals(
                expected = when (buttonType) {
                    CarbonButton.Tertiary -> theme.textInverse
                    CarbonButton.Ghost -> theme.linkPrimary
                    CarbonButton.TertiaryDanger,
                    CarbonButton.GhostDanger -> theme.textOnColor
                    else -> theme.textOnColor
                },
                actual = colors.labelActiveColor
            )

            assertEquals(
                expected = when (buttonType) {
                    CarbonButton.Tertiary,
                    CarbonButton.TertiaryDanger,
                    CarbonButton.GhostDanger -> theme.textOnColor
                    CarbonButton.Ghost -> theme.linkPrimaryHover
                    else -> theme.textOnColor
                },
                actual = colors.labelHoverColor
            )

            assertEquals(
                expected = when (buttonType) {
                    CarbonButton.Tertiary,
                    CarbonButton.Ghost,
                    CarbonButton.TertiaryDanger,
                    CarbonButton.GhostDanger -> theme.textDisabled
                    else -> theme.textOnColorDisabled
                },
                actual = colors.labelDisabledColor
            )

            assertEquals(
                expected = when (buttonType) {
                    CarbonButton.Tertiary -> theme.buttonTertiary
                    CarbonButton.Ghost -> theme.linkPrimary
                    CarbonButton.PrimaryDanger -> theme.iconOnColor
                    CarbonButton.TertiaryDanger,
                    CarbonButton.GhostDanger -> theme.buttonDangerSecondary
                    else -> theme.iconOnColor
                },
                actual = colors.iconColor
            )

            assertEquals(
                expected = when (buttonType) {
                    CarbonButton.Tertiary -> theme.iconInverse
                    CarbonButton.Ghost -> theme.linkPrimary
                    else -> theme.iconOnColor
                },
                actual = colors.iconActiveColor
            )

            assertEquals(
                expected = when (buttonType) {
                    CarbonButton.Tertiary -> theme.iconInverse
                    CarbonButton.Ghost -> theme.linkPrimaryHover
                    else -> theme.iconOnColor
                },
                actual = colors.iconHoverColor
            )

            assertEquals(
                expected = when (buttonType) {
                    CarbonButton.Primary,
                    CarbonButton.Secondary,
                    CarbonButton.PrimaryDanger -> theme.iconOnColorDisabled
                    else -> theme.iconDisabled // Issue in documentation for Ghost
                },
                actual = colors.iconDisabledColor
            )
        }
    }
}
