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

package carbon.compose.button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import carbon.compose.Carbon
import carbon.compose.foundation.color.Theme

@Immutable
internal class ButtonColors private constructor(
    private val theme: Theme,
    private val buttonType: ButtonType
) {

    val containerColor: Color = when (buttonType) {
        ButtonType.Primary -> theme.buttonPrimary
        ButtonType.Secondary -> theme.buttonSecondary
        ButtonType.PrimaryDanger -> theme.buttonDangerPrimary
        ButtonType.Tertiary,
        ButtonType.TertiaryDanger,
        ButtonType.Ghost,
        ButtonType.GhostDanger -> Color.Transparent
    }

    val containerBorderColor: Color = when (buttonType) {
        ButtonType.Tertiary -> theme.buttonTertiary
        ButtonType.TertiaryDanger -> theme.buttonDangerSecondary
        else -> Color.Transparent
    }

    val containerBorderDisabledColor: Color = when (buttonType) {
        ButtonType.Tertiary,
        ButtonType.TertiaryDanger -> theme.buttonDisabled
        else -> Color.Transparent
    }

    val containerActiveColor: Color = when (buttonType) {
        ButtonType.Primary -> theme.buttonPrimaryActive
        ButtonType.Secondary -> theme.buttonSecondaryActive
        ButtonType.Tertiary -> theme.buttonTertiaryActive
        ButtonType.Ghost -> theme.backgroundActive
        ButtonType.PrimaryDanger,
        ButtonType.TertiaryDanger,
        ButtonType.GhostDanger -> theme.buttonDangerActive
    }

    val containerHoverColor: Color = when (buttonType) {
        ButtonType.Primary -> theme.buttonPrimaryHover
        ButtonType.Secondary -> theme.buttonSecondaryHover
        ButtonType.Tertiary -> theme.buttonTertiaryHover
        ButtonType.Ghost -> theme.backgroundHover
        ButtonType.PrimaryDanger,
        ButtonType.TertiaryDanger,
        ButtonType.GhostDanger -> theme.buttonDangerHover
    }

    val containerDisabledColor: Color = when (buttonType) {
        ButtonType.Primary,
        ButtonType.Secondary,
        ButtonType.PrimaryDanger,
        ButtonType.TertiaryDanger,
        ButtonType.GhostDanger -> theme.buttonDisabled
        ButtonType.Tertiary,
        ButtonType.Ghost -> Color.Transparent
    }

    val labelColor: Color = when (buttonType) {
        ButtonType.Tertiary -> theme.buttonTertiary
        ButtonType.Ghost -> theme.linkPrimary
        ButtonType.TertiaryDanger,
        ButtonType.GhostDanger -> theme.buttonDangerSecondary
        else -> theme.textOnColor
    }

    val labelActiveColor: Color = when (buttonType) {
        ButtonType.Tertiary -> theme.textInverse
        ButtonType.Ghost -> theme.linkPrimary
        ButtonType.TertiaryDanger,
        ButtonType.GhostDanger -> theme.textOnColor
        else -> theme.textOnColor
    }

    val labelHoverColor: Color = when (buttonType) {
        ButtonType.Tertiary,
        ButtonType.TertiaryDanger,
        ButtonType.GhostDanger -> theme.textOnColor
        ButtonType.Ghost -> theme.linkPrimaryHover
        else -> theme.textOnColor
    }

    val labelDisabledColor: Color = when (buttonType) {
        ButtonType.Tertiary,
        ButtonType.Ghost,
        ButtonType.TertiaryDanger,
        ButtonType.GhostDanger -> theme.textDisabled
        else -> theme.textOnColorDisabled
    }

    val iconColor: Color = when (buttonType) {
        ButtonType.Tertiary -> theme.buttonTertiary
        ButtonType.Ghost -> theme.linkPrimary
        ButtonType.PrimaryDanger -> theme.iconOnColor
        ButtonType.TertiaryDanger,
        ButtonType.GhostDanger -> theme.buttonDangerSecondary
        else -> theme.iconOnColor
    }

    val iconActiveColor: Color = when (buttonType) {
        ButtonType.Tertiary -> theme.iconInverse
        ButtonType.Ghost -> theme.linkPrimary // ø
        else -> theme.iconOnColor
    }

    val iconHoverColor: Color = when (buttonType) {
        ButtonType.Tertiary -> theme.iconInverse
        ButtonType.Ghost -> theme.linkPrimaryHover
        else -> theme.iconOnColor
    }

    val iconDisabledColor: Color = when (buttonType) {
        ButtonType.Primary,
        ButtonType.Secondary,
        ButtonType.PrimaryDanger -> theme.iconOnColorDisabled
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
            buttonType: ButtonType,
        ): ButtonColors = ButtonColors(Carbon.theme, buttonType)
    }
}
