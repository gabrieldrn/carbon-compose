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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.button.Button
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

/**
 * # Popover - No tip
 *
 * Composable wrapping a [content] (generally a ui trigger) to display a Popover without a tip when
 * [isVisible] is true.
 *
 * Popovers without a tip can be used for a wide variety of different use cases. No tip popovers are
 * typically used when the trigger button has a visually defined down state.
 *
 * ## ⚠️ Placement limitations
 *
 * Due to limitations from Compose, the Popover may be misplaced or misaligned if the current UI
 * window is not big enough to fit it. This is especially the case for hand-held devices. Please
 * keep this in mind when using this component.
 *
 * From [Popover documentation](https://carbondesignsystem.com/components/popover/usage/#no-tip)
 *
 * @param isVisible Whether the popover is visible or not.
 * @param modifier The modifier to be applied to the whole component.
 * @param alignment The alignment of the popover relative to the UI trigger.
 * @param placement The placement of the popover relative to the UI trigger.
 * @param popoverMinWidth Minimum width of the displayed popover.
 * @param popoverMaxWidth Maximum width of the displayed popover.
 * @param popoverPopupProperties [PopupProperties] for further customization of the underlying
 * popup's behavior.
 * @param onDismissRequest Executes when the user clicks outside of the popup.
 * @param popoverContent The content to be displayed inside the popup.
 * @param content The composable that the popover will anchor to.
 */
@Composable
public fun PopoverBox(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    alignment: PopoverAlignment = PopoverAlignment.Start,
    placement: PopoverPlacement = PopoverPlacement.Top,
    popoverMinWidth: Dp = Dp.Unspecified,
    popoverMaxWidth: Dp = Dp.Unspecified,
    popoverPopupProperties: PopupProperties = popoverDefaultProperties,
    onDismissRequest: (() -> Unit)? = null,
    popoverContent: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
) {
    val popoverShape = rememberPopoverShape()

    PopoverBoxInternal(
        popoverShape = popoverShape,
        isVisible = isVisible,
        positionProvider = rememberPopoverPositionProvider(
            alignment = alignment,
            placement = placement,
        ),
        modifier = modifier,
        popoverMinWidth = popoverMinWidth,
        popoverMaxWidth = popoverMaxWidth,
        popoverPopupProperties = popoverPopupProperties,
        onDismissRequest = onDismissRequest,
        popoverContent = popoverContent,
        content = content
    )
}

// region Previews

private class NoTipPopoverPreviewParameterProvider :
    PreviewParameterProvider<Pair<PopoverAlignment, PopoverPlacement>> {
    override val values = sequence {
        PopoverAlignment.entries.forEach { alignment ->
            PopoverPlacement.entries.forEach { placement ->
                yield(alignment to placement)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun NoTipPopoverPreview(
    @PreviewParameter(NoTipPopoverPreviewParameterProvider::class)
    params: Pair<PopoverAlignment, PopoverPlacement>
) {
    CarbonDesignSystem {
        Box(
            modifier = Modifier.size(400.dp),
            contentAlignment = Alignment.Center
        ) {
            val (alignment, placement) = params

            PopoverBox(
                isVisible = true,
                alignment = alignment,
                placement = placement,
                popoverContent = {
                    BasicText(
                        text = "Hello",
                        style = Carbon.typography.body01,
                        modifier = Modifier.padding(SpacingScale.spacing04)
                    )
                }
            ) {
                Button(
                    label = "Click me",
                    onClick = { /* No-op */ },
                )
            }
        }
    }
}

// endregion
