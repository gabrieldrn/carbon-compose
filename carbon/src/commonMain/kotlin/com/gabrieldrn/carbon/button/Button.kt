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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.tooltip.TooltipBox
import com.gabrieldrn.carbon.tooltip.TooltipParameters

/**
 * # Button
 * Buttons are used to initialize an action. Button labels express what action will occur when the
 * user interacts with it.
 *
 * ## Overview
 * Buttons are clickable elements that are used to trigger actions. They communicate calls to action
 * to the user and allow users to interact with pages in a variety of ways. Button labels express
 * what action will occur when the user interacts with it.
 *
 * ### When to use
 * Use buttons to communicate actions users can take and to allow users to interact with the page.
 * Each page should have only one primary button, and any remaining calls to action should be
 * represented as lower emphasis buttons.
 *
 * ### When not to use
 * Do not use buttons as navigational elements. Instead, use links when the desired action is to
 * take the user to a new page.
 *
 * (From [Button documentation](https://carbondesignsystem.com/components/button/usage/))
 *
 * @param label The text to be displayed in the button.
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier The modifier to be applied to the button.
 * @param iconPainter Icon painter to be displayed in the button.
 * @param isEnabled Whether the button is enabled or disabled.
 * @param buttonType A [ButtonType] that defines the button's type.
 * @param buttonSize A [ButtonSize] that defines the button's size.
 * @param interactionSource The [MutableInteractionSource] that keeps track of the button's state.
 */
@Composable
public fun Button(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconPainter: Painter? = null,
    isEnabled: Boolean = true,
    buttonType: ButtonType = ButtonType.Primary,
    buttonSize: ButtonSize = ButtonSize.LargeProductive,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    ButtonLayout(
        onClick = onClick,
        buttonType = buttonType,
        buttonSize = buttonSize,
        isEnabled = isEnabled,
        modifier = modifier,
        interactionSource = interactionSource,
    ) { buttonScope ->
        Label(
            label = label,
            scope = buttonScope,
            modifier = Modifier
                .weight(weight = 1f)
                .fillMaxHeight()
                .padding(end = SpacingScale.spacing05)
                .testTag("carbon_button_label")
        )

        if (iconPainter != null) {
            ButtonIcon(
                painter = iconPainter,
                scope = buttonScope,
                modifier = if (buttonSize.isExtraLarge) {
                    Modifier.requiredSize(16.dp)
                } else {
                    Modifier
                        .requiredWidth(16.dp)
                        .fillMaxHeight()
                }.testTag("carbon_button_icon")
            )
            Spacer(modifier = Modifier.width(SpacingScale.spacing05))
        }
    }
}

/**
 * # Button
 * Buttons are used to initialize an action. Button labels express what action will occur when the
 * user interacts with it.
 *
 * This variant of the button uses Carbon's [TooltipBox] to display a tooltip when the user hovers
 * over or focuses the button.
 *
 * ## Overview
 * Buttons are clickable elements that are used to trigger actions. They communicate calls to action
 * to the user and allow users to interact with pages in a variety of ways. Button labels express
 * what action will occur when the user interacts with it.
 *
 * ### When to use
 * Use buttons to communicate actions users can take and to allow users to interact with the page.
 * Each page should have only one primary button, and any remaining calls to action should be
 * represented as lower emphasis buttons.
 *
 * ### When not to use
 * Do not use buttons as navigational elements. Instead, use links when the desired action is to
 * take the user to a new page.
 *
 * (From [Button documentation](https://carbondesignsystem.com/components/button/usage/))
 *
 * @param label The text to be displayed in the button.
 * @param tooltipParameters Parameters for the tooltip applied to the button.
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier The modifier to be applied to the button.
 * @param tooltipModifier The modifier to be applied to the tooltip.
 * @param iconPainter Icon painter to be displayed in the button.
 * @param isEnabled Whether the button is enabled or disabled.
 * @param buttonType A [ButtonType] that defines the button's type.
 * @param buttonSize A [ButtonSize] that defines the button's size.
 * @param interactionSource The [MutableInteractionSource] that keeps track of the button's state.
 */
@ExperimentalFoundationApi
@Composable
public fun Button(
    label: String,
    tooltipParameters: TooltipParameters,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tooltipModifier: Modifier = Modifier,
    iconPainter: Painter? = null,
    isEnabled: Boolean = true,
    buttonType: ButtonType = ButtonType.Primary,
    buttonSize: ButtonSize = ButtonSize.LargeProductive,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    TooltipBox(
        parameters = tooltipParameters,
        uiTriggerMutableInteractionSource = interactionSource,
        modifier = tooltipModifier
    ) {
        Button(
            label = label,
            onClick = onClick,
            iconPainter = iconPainter,
            isEnabled = isEnabled,
            buttonType = buttonType,
            buttonSize = buttonSize,
            interactionSource = interactionSource,
            modifier = modifier
        )
    }
}
