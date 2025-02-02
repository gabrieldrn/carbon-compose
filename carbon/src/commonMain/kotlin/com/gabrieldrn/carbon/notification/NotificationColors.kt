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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.color.Theme

@Immutable
internal class NotificationColors private constructor(
    theme: Theme,
    status: NotificationStatus,
    useHighContrast: Boolean,
) {
    val titleColor = if (useHighContrast) {
        theme.textInverse
    } else {
        theme.textPrimary
    }

    val bodyColor = if (useHighContrast) {
        theme.textInverse
    } else {
        theme.textPrimary
    }

    val backgroundColor = if (useHighContrast) {
        theme.backgroundInverse
    } else {
        when (status) {
            NotificationStatus.Informational -> theme.notificationColors.notificationBackgroundInfo
            NotificationStatus.Success -> theme.notificationColors.notificationBackgroundSuccess
            NotificationStatus.Warning -> theme.notificationColors.notificationBackgroundWarning
            NotificationStatus.Error -> theme.notificationColors.notificationBackgroundError
        }
    }

    val borderLeftColor = if (useHighContrast) {
        when (status) {
            NotificationStatus.Informational -> theme.supportInfoInverse
            NotificationStatus.Success -> theme.supportSuccessInverse
            NotificationStatus.Warning -> theme.supportWarningInverse
            NotificationStatus.Error -> theme.supportErrorInverse
        }
    } else {
        when (status) {
            NotificationStatus.Informational -> theme.supportInfo
            NotificationStatus.Success -> theme.supportSuccess
            NotificationStatus.Warning -> theme.supportWarning
            NotificationStatus.Error -> theme.supportError
        }
    }

    val contourColor = if (useHighContrast) {
        Color.Transparent
    } else {
        when (status) {
            NotificationStatus.Informational -> theme.supportInfo
            NotificationStatus.Success -> theme.supportSuccess
            NotificationStatus.Warning -> theme.supportWarning
            NotificationStatus.Error -> theme.supportError
        }
            // Undocumented in Carbon website, but web implementations uses 40% alpha.
            .copy(alpha = .4f)
    }

    val iconColor = if (useHighContrast) {
        when (status) {
            NotificationStatus.Informational -> theme.supportInfoInverse
            NotificationStatus.Success -> theme.supportSuccessInverse
            NotificationStatus.Warning -> theme.supportWarningInverse
            NotificationStatus.Error -> theme.supportErrorInverse
        }
    } else {
        when (status) {
            NotificationStatus.Informational -> theme.supportInfo
            NotificationStatus.Success -> theme.supportSuccess
            NotificationStatus.Warning -> theme.supportWarning
            NotificationStatus.Error -> theme.supportError
        }
    }

    val iconInnerColor = if (status == NotificationStatus.Warning) {
        Color.Black
    } else {
        Color.Transparent
    }

    companion object {

        @Composable
        fun rememberColors(
            status: NotificationStatus,
            useHighContrast: Boolean,
            theme: Theme = Carbon.theme,
        ): NotificationColors = remember(theme, status, useHighContrast) {
            NotificationColors(theme, status, useHighContrast)
        }
    }
}
