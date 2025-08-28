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

package com.gabrieldrn.carbon.popover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.layerBackgroundColor
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

internal val popoverContentPaddingValues: PaddingValues = PaddingValues(SpacingScale.spacing05)
internal val popoverDefaultProperties = PopupProperties(focusable = true)
private val popoverDefaultElevation = 4.dp

@Composable
internal fun PopoverBoxInternal(
    popoverShape: PopoverShape,
    isVisible: Boolean,
    positionProvider: PopupPositionProvider,
    modifier: Modifier = Modifier,
    popoverBackgroundColorProvider: @Composable () -> Color =
        { Carbon.theme.layerBackgroundColor(Carbon.layer) },
    popoverMinWidth: Dp = Dp.Unspecified,
    popoverMaxWidth: Dp = Dp.Unspecified,
    popoverElevation: Dp = popoverDefaultElevation,
    popoverMargin: Dp = SpacingScale.spacing02,
    popoverPopupProperties: PopupProperties = popoverDefaultProperties,
    onDismissRequest: (() -> Unit)? = null,
    popoverContent: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        if (isVisible) {
            Popup(
                popupPositionProvider = positionProvider,
                onDismissRequest = onDismissRequest,
                properties = popoverPopupProperties
            ) {
                CarbonLayer {
                    Box(
                        modifier = Modifier
                            .padding(popoverShape.tipSize + popoverMargin)
                            .shadow(elevation = popoverElevation, shape = popoverShape)
                            .background(
                                color = popoverBackgroundColorProvider(),
                                shape = popoverShape
                            )
                            .widthIn(min = popoverMinWidth, max = popoverMaxWidth),
                        content = popoverContent
                    )
                }
            }
        }

        content()
    }
}
