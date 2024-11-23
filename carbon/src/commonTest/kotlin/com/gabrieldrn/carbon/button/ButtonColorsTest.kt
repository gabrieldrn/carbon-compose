/*
 * Copyright 2024 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gabrieldrn.carbon.button

import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.BaseColorsTest
import com.gabrieldrn.themesmodel.deserializeColorTokens
import kotlin.test.Test

class ButtonColorsTest : BaseColorsTest() {

    @Suppress("CognitiveComplexMethod", "CyclomaticComplexMethod", "LongMethod")
    @Test
    fun buttonColors_static_colorsAreCorrect() = runComposeUiTest {

        val expectedColors = deserializeColorTokens()[themeName]!!

        forAllLayersAndStates(
            ButtonType.entries,
            listOf(true, false)
        ) { buttonType, isIconButton, _ ->
            val colors = ButtonColors.colors(buttonType = buttonType, isIconButton = isIconButton)

            colors.containerColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Primary -> expectedColors.buttonColors.buttonPrimary
                    ButtonType.Secondary -> expectedColors.buttonColors.buttonSecondary
                    ButtonType.PrimaryDanger -> expectedColors.buttonColors.buttonDangerPrimary
                    ButtonType.Tertiary,
                    ButtonType.TertiaryDanger,
                    ButtonType.Ghost,
                    ButtonType.GhostDanger -> COLOR_TRANSPARENT
                }
            )

            colors.containerBorderColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Tertiary -> expectedColors.buttonColors.buttonTertiary
                    ButtonType.TertiaryDanger -> expectedColors.buttonColors.buttonDangerSecondary
                    else -> COLOR_TRANSPARENT
                }
            )

            colors.containerBorderDisabledColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Tertiary,
                    ButtonType.TertiaryDanger -> expectedColors.buttonColors.buttonDisabled
                    else -> COLOR_TRANSPARENT
                }
            )

            colors.containerActiveColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Primary -> expectedColors.buttonColors.buttonPrimaryActive
                    ButtonType.Secondary -> expectedColors.buttonColors.buttonSecondaryActive
                    ButtonType.Tertiary -> expectedColors.buttonColors.buttonTertiaryActive
                    ButtonType.Ghost -> expectedColors.backgroundActive
                    ButtonType.PrimaryDanger,
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> expectedColors.buttonColors.buttonDangerActive
                }
            )

            colors.containerActiveColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Primary -> expectedColors.buttonColors.buttonPrimaryActive
                    ButtonType.Secondary -> expectedColors.buttonColors.buttonSecondaryActive
                    ButtonType.Tertiary -> expectedColors.buttonColors.buttonTertiaryActive
                    ButtonType.Ghost -> expectedColors.backgroundActive
                    ButtonType.PrimaryDanger,
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> expectedColors.buttonColors.buttonDangerActive
                }
            )

            colors.containerHoverColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Primary -> expectedColors.buttonColors.buttonPrimaryHover
                    ButtonType.Secondary -> expectedColors.buttonColors.buttonSecondaryHover
                    ButtonType.Tertiary -> expectedColors.buttonColors.buttonTertiaryHover
                    ButtonType.Ghost -> expectedColors.backgroundHover
                    ButtonType.PrimaryDanger,
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> expectedColors.buttonColors.buttonDangerHover
                }
            )

            colors.containerDisabledColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Primary,
                    ButtonType.Secondary,
                    ButtonType.PrimaryDanger,
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> expectedColors.buttonColors.buttonDisabled
                    ButtonType.Tertiary,
                    ButtonType.Ghost -> COLOR_TRANSPARENT
                }
            )

            colors.labelColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Tertiary -> expectedColors.buttonColors.buttonTertiary
                    ButtonType.Ghost -> expectedColors.linkPrimary
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> expectedColors.buttonColors.buttonDangerSecondary
                    else -> expectedColors.textOnColor
                }
            )

            colors.labelActiveColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Tertiary -> expectedColors.textInverse
                    ButtonType.Ghost -> expectedColors.linkPrimary
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> expectedColors.textOnColor
                    else -> expectedColors.textOnColor
                }
            )

            colors.labelHoverColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Tertiary -> expectedColors.textInverse
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> expectedColors.textOnColor
                    ButtonType.Ghost -> expectedColors.linkPrimaryHover
                    else -> expectedColors.textOnColor
                }
            )

            colors.labelDisabledColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Tertiary,
                    ButtonType.Ghost,
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> expectedColors.textDisabled
                    else -> expectedColors.textOnColorDisabled
                }
            )

            colors.iconColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Tertiary -> expectedColors.buttonColors.buttonTertiary
                    ButtonType.Ghost -> if (isIconButton) expectedColors.iconPrimary else expectedColors.linkPrimary
                    ButtonType.PrimaryDanger -> expectedColors.iconOnColor
                    ButtonType.TertiaryDanger,
                    ButtonType.GhostDanger -> expectedColors.buttonColors.buttonDangerSecondary
                    else -> expectedColors.iconOnColor
                }
            )

            colors.iconActiveColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Tertiary -> expectedColors.iconInverse
                    ButtonType.Ghost -> if (isIconButton) expectedColors.iconPrimary else expectedColors.linkPrimary
                    else -> expectedColors.iconOnColor
                }
            )

            colors.iconHoverColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Tertiary -> expectedColors.iconInverse
                    ButtonType.Ghost -> if (isIconButton) expectedColors.iconPrimary else expectedColors.linkPrimaryHover
                    else -> expectedColors.iconOnColor
                }
            )

            colors.iconDisabledColor.assertTokenColorValue(
                expectedToken = when (buttonType) {
                    ButtonType.Primary,
                    ButtonType.Secondary,
                    ButtonType.PrimaryDanger -> expectedColors.iconOnColorDisabled
                    else -> expectedColors.iconDisabled
                }
            )
        }
    }
}
