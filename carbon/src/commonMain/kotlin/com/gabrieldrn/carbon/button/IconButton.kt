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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.popover.PopoverAlignment
import com.gabrieldrn.carbon.popover.PopoverBoxInternal
import com.gabrieldrn.carbon.popover.tabtip.rememberPopoverTabTipShape
import com.gabrieldrn.carbon.popover.tabtip.rememberPopoverTapTipPositionProvider
import com.gabrieldrn.carbon.tooltip.TooltipBox
import com.gabrieldrn.carbon.tooltip.TooltipParameters

/**
 * # Icon button
 * Buttons are used to initialize an action. Button labels express what action will occur when the
 * user interacts with it.
 *
 * Icon buttons allow users to take actions, and make choices, with a single tap. Icon buttons can
 * take the form of a primary, secondary, tertiary, or ghost variant but most commonly will be
 * styled as primary or ghost buttons.
 *
 * (From [Button documentation](https://carbondesignsystem.com/components/button/usage/))
 *
 * @param iconPainter Icon painter to be displayed in the button.
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier The modifier to be applied to the button.
 * @param buttonType A [ButtonType] that defines the button's type.
 * @param buttonSize A [ButtonSize] that defines the button's size.
 * @param isEnabled Whether the button is enabled or disabled.
 * @param interactionSource The [MutableInteractionSource] that keeps track of the button's state.
 *
 * @see [Button]
 */
@Composable
public fun IconButton(
    iconPainter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonType: ButtonType = ButtonType.Primary,
    buttonSize: ButtonSize = ButtonSize.LargeProductive,
    isEnabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    ButtonLayout(
        onClick = onClick,
        buttonType = buttonType,
        buttonSize = buttonSize,
        isEnabled = isEnabled,
        modifier = modifier,
        isIconButton = true,
        interactionSource = interactionSource,
    ) { buttonScope ->
        ButtonIcon(
            painter = iconPainter,
            scope = buttonScope,
            modifier = Modifier
                .weight(1f)
                .padding(
                    top = when (buttonSize) {
                        ButtonSize.Small -> SpacingScale.spacing03
                        ButtonSize.Medium -> SpacingScale.spacing04
                        else -> SpacingScale.spacing05
                    }
                )
                .size(SpacingScale.spacing05)
        )
    }
}

/**
 * # Icon button
 * Buttons are used to initialize an action. Button labels express what action will occur when the
 * user interacts with it.
 *
 * Icon buttons allow users to take actions, and make choices, with a single tap. Icon buttons can
 * take the form of a primary, secondary, tertiary, or ghost variant but most commonly will be
 * styled as primary or ghost buttons.
 *
 * This variant of the icon button uses Carbon's [TooltipBox] to display a tooltip when the user
 * hovers over or focuses it.
 *
 * (From [Button documentation](https://carbondesignsystem.com/components/button/usage/))
 *
 * @param iconPainter Icon painter to be displayed in the button.
 * @param tooltipParameters Parameters to configure the tooltip.
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier The modifier to be applied to the button.
 * @param tooltipModifier The modifier to be applied to the tooltip.
 * @param buttonType A [ButtonType] that defines the button's type.
 * @param buttonSize A [ButtonSize] that defines the button's size.
 * @param isEnabled Whether the button is enabled or disabled.
 * @param interactionSource The [MutableInteractionSource] that keeps track of the button's state.
 *
 * @see [Button]
 */
@ExperimentalFoundationApi
@Composable
public fun IconButton(
    iconPainter: Painter,
    tooltipParameters: TooltipParameters,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tooltipModifier: Modifier = Modifier,
    buttonType: ButtonType = ButtonType.Primary,
    buttonSize: ButtonSize = ButtonSize.LargeProductive,
    isEnabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    TooltipBox(
        parameters = tooltipParameters,
        uiTriggerMutableInteractionSource = interactionSource,
        modifier = tooltipModifier
    ) {
        IconButton(
            iconPainter = iconPainter,
            onClick = onClick,
            modifier = modifier,
            buttonType = buttonType,
            buttonSize = buttonSize,
            isEnabled = isEnabled,
            interactionSource = interactionSource
        )
    }
}

@ExperimentalFoundationApi
@Composable
public fun IconButtonWithPopover(
    iconPainter: Painter,
    popoverAlignment: PopoverAlignment,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    popoverMinWidth: Dp = Dp.Unspecified,
    popoverMaxWidth: Dp = Dp.Unspecified,
    buttonType: ButtonType = ButtonType.Primary,
    buttonSize: ButtonSize = ButtonSize.LargeProductive,
    isEnabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    popoverContent: @Composable BoxScope.() -> Unit
) {
    val popoverShape = rememberPopoverTabTipShape(
        alignment = popoverAlignment,
        buttonSize = buttonSize
    )
    val positionProvider = rememberPopoverTapTipPositionProvider(
        buttonSize = buttonSize,
        alignment = popoverAlignment
    )

    var isVisible by remember { mutableStateOf(false) }

    PopoverBoxInternal(
        isVisible = isVisible,
        popoverShape = popoverShape,
        positionProvider = positionProvider,
        modifier = modifier,
        popoverMinWidth = popoverMinWidth,
        popoverMaxWidth = popoverMaxWidth,
        popoverMargin = 0.dp,
        onDismissRequest = { isVisible = false },
        popoverContent = {
            val padding = remember(buttonSize) {
                when (buttonSize) {
                    ButtonSize.Small -> SpacingScale.spacing03
                    ButtonSize.Medium -> SpacingScale.spacing04
                    else -> SpacingScale.spacing05
                }
            }

            Column {
                Image(
                    painter = iconPainter,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Carbon.theme.iconPrimary),
                    modifier = Modifier
                        .padding(padding)
                        .size(SpacingScale.spacing05)
                        .align(
                            if (popoverAlignment == PopoverAlignment.End) {
                                Alignment.End
                            } else {
                                Alignment.Start
                            }
                        )
                )

                Box {
                    popoverContent()
                }
            }
        },
    ) {
        IconButton(
            iconPainter = iconPainter,
            onClick = {
                isVisible = true
                onClick()
            },
            modifier = modifier,
            buttonType = buttonType,
            buttonSize = buttonSize,
            isEnabled = isEnabled,
            interactionSource = interactionSource
        )
    }
}
