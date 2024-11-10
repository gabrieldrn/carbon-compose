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

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.BaseColorsTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ButtonColorsTest : BaseColorsTest() {

    @Suppress("CognitiveComplexMethod", "CyclomaticComplexMethod", "LongMethod")
    @Test
    fun buttonColors_static_colorsAreCorrect() = runComposeUiTest {
        forAllLayersAndStates(
            ButtonType.entries,
            listOf(true, false)
        ) { buttonType, isIconButton, _ ->
            val colors = ButtonColors.colors(buttonType = buttonType, isIconButton = isIconButton)

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
                    ButtonType.Ghost -> if (isIconButton) theme.iconPrimary else theme.linkPrimary
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
                    ButtonType.Ghost -> if (isIconButton) theme.iconPrimary else theme.linkPrimary
                    else -> theme.iconOnColor
                },
                actual = colors.iconActiveColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Tertiary -> theme.iconInverse
                    ButtonType.Ghost ->
                        if (isIconButton) theme.iconPrimary else theme.linkPrimaryHover
                    else -> theme.iconOnColor
                },
                actual = colors.iconHoverColor
            )

            assertEquals(
                expected = when (buttonType) {
                    ButtonType.Primary,
                    ButtonType.Secondary,
                    ButtonType.PrimaryDanger -> theme.iconOnColorDisabled
                    else -> theme.iconDisabled
                },
                actual = colors.iconDisabledColor
            )
        }
    }
}
