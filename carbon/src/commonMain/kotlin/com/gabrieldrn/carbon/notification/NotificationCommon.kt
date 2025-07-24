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

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.button.ButtonFocusIndication
import com.gabrieldrn.carbon.button.ButtonIcon
import com.gabrieldrn.carbon.button.ButtonLayout
import com.gabrieldrn.carbon.button.ButtonSize
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.icons.CheckmarkFilledIcon
import com.gabrieldrn.carbon.icons.ErrorFilledIcon
import com.gabrieldrn.carbon.icons.InformationFilledIcon
import com.gabrieldrn.carbon.icons.WarningFilledIcon
import com.gabrieldrn.carbon.icons.closeIcon

// Required icon size from Carbon's documentation is 20px, however, when applying paddings of
// 16px around it, this doesn't compute to a min size of 48px (16+16+20=52)
private val iconSize = 18.dp

@Composable
private fun Modifier.notificationContainerModifier(
    colors: NotificationColors,
    isFloating: Boolean
): Modifier {
    val elevationModifier = if (isFloating) Modifier.shadow(elevation = 4.dp) else Modifier

    return this
        .then(elevationModifier)
        .background(colors.backgroundColor)
        .drawWithContent {
            drawContent()
            val contourWidthPx = 1.dp.toPx()
            if (!isFloating) {
                drawRect(
                    brush = SolidColor(colors.contourColor),
                    topLeft = Offset(contourWidthPx / 2, contourWidthPx / 2),
                    size = Size(
                        size.width - contourWidthPx,
                        size.height - contourWidthPx
                    ),
                    style = Stroke(1.dp.toPx())
                )
            }
            drawRect(
                brush = SolidColor(colors.borderLeftColor),
                topLeft = Offset.Zero,
                size = Size(3f.dp.toPx(), size.height)
            )
        }
        .testTag(NotificationTestTags.CONTAINER)
}

@Composable
internal fun NotificationContainer(
    status: NotificationStatus,
    displayCloseButton: Boolean,
    highContrast: Boolean,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    isFloating: Boolean = false, // Not sure if it's the right term...
    content: @Composable NotificationScope.() -> Unit = {},
) {
    val scope = rememberNotificationScope(
        status = status,
        useHighContrast = highContrast
    )

    with(scope) {
        Row(
            modifier = modifier.notificationContainerModifier(
                colors = colors,
                isFloating = isFloating
            )
        ) {
            StatusIcon(
                status = status,
                modifier = Modifier.padding(
                    start = SpacingScale.spacing05,
                    top = SpacingScale.spacing05
                )
            )

            Box(modifier = Modifier.weight(1f)) {
                content(this@with)
            }

            if (displayCloseButton) {
                CloseButton(
                    onClick = onClose,
                    isHighContrast = highContrast,
                    // Rendering issue with contour, avoids displaying the button behind it.
                    modifier = if (!isFloating) {
                        Modifier.padding(top = 1.dp, end = 1.dp)
                    } else {
                        Modifier
                    }
                )
            } else {
                Spacer(modifier = Modifier.width(SpacingScale.spacing05))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun NotificationScope.FlowTextContent(
    title: String,
    body: AnnotatedString,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
            .padding(start = SpacingScale.spacing05)
            .padding(vertical = SpacingScale.spacing05)
    ) {
        if (title.isNotBlank()) {
            BasicText(
                text = title,
                style = Carbon.typography.headingCompact01,
                color = { colors.titleColor },
                modifier = Modifier.testTag(NotificationTestTags.TITLE)
            )
        }
        BasicText(
            text = body,
            style = Carbon.typography.bodyCompact01,
            color = { colors.bodyColor },
            modifier = Modifier.testTag(NotificationTestTags.SUBTITLE)
        )
    }
}

@Composable
private fun CloseButton(
    onClick: () -> Unit,
    isHighContrast: Boolean,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val theme = Carbon.theme

    ButtonLayout(
        onClick = onClick,
        buttonType = ButtonType.Ghost,
        buttonSize = ButtonSize.LargeProductive,
        isEnabled = true,
        isIconButton = true,
        theme = theme,
        interactionSource = interactionSource,
        indication = remember(theme, isHighContrast) {
            ButtonFocusIndication(
                theme = theme,
                buttonType = ButtonType.Ghost,
                useInverseColor = isHighContrast
            )
        },
        content = { buttonScope ->
            ButtonIcon(
                painter = rememberVectorPainter(closeIcon),
                scope = buttonScope,
                contentDescription = "Close notification",
                modifier = Modifier
                    .weight(1f)
                    .padding(SpacingScale.spacing05)
                    .size(SpacingScale.spacing05)
            )
        },
        modifier = modifier.testTag(NotificationTestTags.CLOSE_BUTTON)
    )
}

@Composable
private fun NotificationScope.StatusIcon(
    status: NotificationStatus,
    modifier: Modifier = Modifier
) {
    when (status) {
        NotificationStatus.Informational -> InformationFilledIcon(
            tint = colors.iconColor,
            innerTint = colors.iconInnerColor,
            size = iconSize,
            modifier = modifier.testTag(NotificationTestTags.ICON_INFORMATIONAL)
        )
        NotificationStatus.Success -> CheckmarkFilledIcon(
            tint = colors.iconColor,
            size = iconSize,
            modifier = modifier.testTag(NotificationTestTags.ICON_SUCCESS)
        )
        NotificationStatus.Warning -> WarningFilledIcon(
            tint = colors.iconColor,
            innerTint = colors.iconInnerColor,
            size = iconSize,
            modifier = modifier.testTag(NotificationTestTags.ICON_WARNING)
        )
        NotificationStatus.Error -> ErrorFilledIcon(
            tint = colors.iconColor,
            size = iconSize,
            modifier = modifier.testTag(NotificationTestTags.ICON_ERROR)
        )
    }
}
