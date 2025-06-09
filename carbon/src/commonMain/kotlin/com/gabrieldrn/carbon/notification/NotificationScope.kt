/*
 * Copyright 2025 Gabriel Derrien
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

package com.gabrieldrn.carbon.notification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.color.Theme

@Stable
internal data class NotificationScope(
    val colors: NotificationColors,
    val actionableInlineTheme: Theme,
    val actionableToastTheme: Theme,
)

@Composable
internal fun rememberNotificationScope(
    status: NotificationStatus,
    useHighContrast: Boolean,
    theme: Theme = Carbon.theme,
): NotificationScope {
    val colors = NotificationColors.rememberColors(
        status = status,
        useHighContrast = useHighContrast,
        theme = theme
    )

    return remember(colors, useHighContrast, theme) {
        NotificationScope(
            colors = colors,
            actionableInlineTheme =
                if (useHighContrast) theme.copy(
                    linkPrimary = theme.linkInverse,
                    linkPrimaryHover = theme.linkInverseHover,
                ) else theme,
            actionableToastTheme =
                if (useHighContrast) with(theme.notificationColors) {
                    val buttonsColors = theme.buttonColors.copy(
                        buttonTertiary = notificationActionTertiaryInverse,
                        buttonTertiaryActive = notificationActionTertiaryInverseActive,
                        buttonTertiaryHover = notificationActionTertiaryInverseHover,
                    )
                    theme.copy(
                        textInverse = notificationActionTertiaryInverseText,
                        buttonColors = buttonsColors
                    )
                } else theme,
        )
    }
}
