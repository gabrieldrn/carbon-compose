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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.button.Button
import com.gabrieldrn.carbon.button.ButtonFocusIndication
import com.gabrieldrn.carbon.button.ButtonSize
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.icons.CheckmarkFilledIcon
import com.gabrieldrn.carbon.icons.ErrorFilledIcon
import com.gabrieldrn.carbon.icons.InformationFilledIcon
import com.gabrieldrn.carbon.icons.WarningFilledIcon
import com.gabrieldrn.carbon.icons.closeIcon

// Required icon size from Carbon's documentation is 20px, however, when applying paddings of
// 16px around it, this doesn't compute to a min size of 48px (16+16+20=52)
private val iconSize = 18.dp

internal enum class ActionableLayout { None, Toast, Inline }

@Composable
private fun rememberInlineActionButtonTheme(
    theme: Theme,
    highContrast: Boolean
) = remember(theme, highContrast) {
    if (highContrast)
        theme.copy(
            linkPrimary = theme.linkInverse,
            linkPrimaryHover = theme.linkInverseHover,
        )
    else theme
}

@Composable
internal fun NotificationContainer(
    body: AnnotatedString,
    title: String,
    status: NotificationStatus,
    displayCloseButton: Boolean,
    highContrast: Boolean,
    modifier: Modifier = Modifier,
    actionLabel: String = "",
    actionableLayout: ActionableLayout = ActionableLayout.None,
    onAction: () -> Unit = {},
    onClose: () -> Unit = {},
) {
    val colors = NotificationColors.rememberColors(
        status = status,
        useHighContrast = highContrast
    )

    Row(
        modifier = modifier
            .background(colors.backgroundColor)
            .drawWithContent {
                drawContent()
                val contourWidthPx = 1.dp.toPx()
                drawRect(
                    brush = SolidColor(colors.contourColor),
                    topLeft = Offset(contourWidthPx / 2, contourWidthPx / 2),
                    size = Size(
                        size.width - contourWidthPx,
                        size.height - contourWidthPx
                    ),
                    style = Stroke(1.dp.toPx())
                )
                drawRect(
                    brush = SolidColor(colors.borderLeftColor),
                    topLeft = Offset.Zero,
                    size = Size(3f.dp.toPx(), size.height)
                )
            }
            .testTag(NotificationTestTags.CONTAINER)
    ) {
        Icon(
            status = status,
            colors = colors,
            modifier = Modifier.padding(
                start = SpacingScale.spacing05,
                top = SpacingScale.spacing05
            )
        )

        when (actionableLayout) {
            ActionableLayout.None -> TextContent(
                title = title,
                body = body,
                colors = colors,
                modifier = Modifier.weight(1f)
            )

            ActionableLayout.Inline -> Row(modifier = Modifier.weight(1f)) {
                TextContent(
                    title = title,
                    body = body,
                    colors = colors,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = SpacingScale.spacing05)
                )

                val buttonTheme = rememberInlineActionButtonTheme(
                    theme = Carbon.theme,
                    highContrast = highContrast
                )

                CompositionLocalProvider(LocalCarbonTheme provides buttonTheme) {
                    Button(
                        label = actionLabel,
                        buttonSize = ButtonSize.Small,
                        buttonType = ButtonType.Ghost,
                        onClick = onAction,
                        modifier = Modifier
                            .padding(top = SpacingScale.spacing03)
                            .testTag(NotificationTestTags.ACTION_BUTTON)
                    )
                }
            }

            ActionableLayout.Toast -> {
                // TODO
            }
        }

        if (displayCloseButton) {
            CloseButton(
                onClick = onClose,
                isHighContrast = highContrast
            )
        } else {
            Spacer(modifier = Modifier.width(SpacingScale.spacing05))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TextContent(
    title: String,
    body: AnnotatedString,
    colors: NotificationColors,
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
            modifier = Modifier.testTag(NotificationTestTags.BODY)
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
    val indication = remember(theme, isHighContrast) {
        ButtonFocusIndication(
            theme = theme,
            buttonType = ButtonType.Ghost,
            useInverseColor = isHighContrast
        )
    }

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = onClick,
                role = Role.Button
            )
            .padding(SpacingScale.spacing05)
            .testTag(NotificationTestTags.CLOSE_BUTTON)
    ) {
        Image(
            painter = rememberVectorPainter(closeIcon),
            contentDescription = "Close button",
            colorFilter = ColorFilter.tint(
                if (isHighContrast) theme.iconInverse else theme.iconPrimary
            ),
            modifier = Modifier.size(iconSize).align(Alignment.Center),
        )
    }
}

@Composable
private fun Icon(
    status: NotificationStatus,
    colors: NotificationColors,
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
