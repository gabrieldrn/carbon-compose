package carbon.compose.button

import androidx.compose.ui.graphics.Color
import carbon.compose.BaseColorsTest
import org.junit.Test
import kotlin.test.assertEquals

class ButtonColorsTest : BaseColorsTest() {

    @Suppress("CognitiveComplexMethod", "CyclomaticComplexMethod", "LongMethod")
    @Test
    fun buttonColors_static_colorsAreCorrect() {
        forAllLayersAndStates(ButtonType.entries) { buttonType, _ ->
            val colors = ButtonColors.colors(buttonType = buttonType)

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Primary -> theme.buttonPrimary
                    ButtonType.Secondary -> theme.buttonSecondary
                    ButtonType.PrimaryDanger -> theme.buttonDangerPrimary
                    ButtonType.Tertiary,
                    ButtonType.TertiaryDanger,
                    ButtonType.Ghost,
                    ButtonType.GhostDanger -> Color.Transparent
                },
                actual = colors.containerColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Tertiary -> theme.buttonTertiary
                    ButtonType.TertiaryDanger -> theme.buttonDangerSecondary
                    else -> Color.Transparent
                },
                actual = colors.containerBorderColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Tertiary,
                    ButtonType.TertiaryDanger -> theme.buttonDisabled
                    else -> Color.Transparent
                },
                actual = colors.containerBorderDisabledColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Primary -> theme.buttonPrimaryActive
                    ButtonType.Secondary -> theme.buttonSecondaryActive
                    ButtonType.Tertiary -> theme.buttonTertiaryActive
                    ButtonType.Ghost -> theme.backgroundActive
                    ButtonType.PrimaryDanger,
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> theme.buttonDangerActive
                },
                actual = colors.containerActiveColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Primary -> theme.buttonPrimaryHover
                    ButtonType.Secondary -> theme.buttonSecondaryHover
                    ButtonType.Tertiary -> theme.buttonTertiaryHover
                    ButtonType.Ghost -> theme.backgroundHover
                    ButtonType.PrimaryDanger,
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> theme.buttonDangerHover
                },
                actual = colors.containerHoverColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Primary,
                    ButtonType.Secondary,
                    ButtonType.PrimaryDanger,
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> theme.buttonDisabled
                    ButtonType.Tertiary,
                    ButtonType.Ghost -> Color.Transparent
                },
                actual = colors.containerDisabledColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Tertiary -> theme.buttonTertiary
                    ButtonType.Ghost -> theme.linkPrimary
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> theme.buttonDangerSecondary
                    else -> theme.textOnColor
                },
                actual = colors.labelColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Tertiary -> theme.textInverse
                    ButtonType.Ghost -> theme.linkPrimary
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> theme.textOnColor
                    else -> theme.textOnColor
                },
                actual = colors.labelActiveColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Tertiary,
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> theme.textOnColor
                    ButtonType.Ghost -> theme.linkPrimaryHover
                    else -> theme.textOnColor
                },
                actual = colors.labelHoverColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Tertiary,
                    ButtonType.Ghost,
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> theme.textDisabled
                    else -> theme.textOnColorDisabled
                },
                actual = colors.labelDisabledColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Tertiary -> theme.buttonTertiary
                    ButtonType.Ghost -> theme.linkPrimary
                    ButtonType.PrimaryDanger -> theme.iconOnColor
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> theme.buttonDangerSecondary
                    else -> theme.iconOnColor
                },
                actual = colors.iconColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Tertiary -> theme.iconInverse
                    ButtonType.Ghost -> theme.linkPrimary
                    else -> theme.iconOnColor
                },
                actual = colors.iconActiveColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Tertiary -> theme.iconInverse
                    ButtonType.Ghost -> theme.linkPrimaryHover
                    else -> theme.iconOnColor
                },
                actual = colors.iconHoverColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Primary,
                    ButtonType.Secondary,
                    ButtonType.PrimaryDanger -> theme.iconOnColorDisabled
                    else -> theme.iconDisabled // Issue in documentation for Ghost
                },
                actual = colors.iconDisabledColor
            )
        }
    }
}
