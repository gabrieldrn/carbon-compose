/*
 * Copyright 2026 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gabrieldrn.carbon.button

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.foundation.motion.Motion
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.loading.inlineloading.InlineLoading
import com.gabrieldrn.carbon.loading.inlineloading.InlineLoadingStatus

private val inlineLoadingButtonTransitionSpec = tween<Float>(
    durationMillis = Motion.Duration.fast01,
    easing = Motion.Entrance.productiveEasing
)

private val inlineLoadingButtonContentTransform: ContentTransform =
    fadeIn(animationSpec = inlineLoadingButtonTransitionSpec, initialAlpha = 0f) togetherWith
        fadeOut(animationSpec = inlineLoadingButtonTransitionSpec, targetAlpha = 0f)

/**
 * # Inline loading button
 *
 * Inline loading should replace the triggering button while a short-running action is in progress
 * or reporting its result.
 *
 * @param label The text displayed by the button while [status] is [InlineLoadingStatus.Inactive].
 * @param onClick Callback invoked when the inactive button is clicked.
 * @param modifier The modifier to apply to the component.
 * @param status The current inline loading status. Any non-inactive status replaces the button.
 * @param inlineLoadingLabel Optional text displayed beside the inline loading indicator.
 * @param inlineLoadingContentDescription Optional accessibility description for the inline loading
 *        indicator when [inlineLoadingLabel] is omitted.
 * @param iconPainter Optional icon displayed by the inactive button.
 * @param isEnabled Whether the inactive button is enabled.
 * @param buttonType A [ButtonType] that defines the button's type.
 * @param buttonSize A [ButtonSize] that defines the button's size.
 * @param interactionSource The [MutableInteractionSource] that keeps track of the button's state.
 */
@Composable
public fun InlineLoadingButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    status: InlineLoadingStatus = InlineLoadingStatus.Inactive,
    inlineLoadingLabel: String? = null,
    inlineLoadingContentDescription: String? = null,
    iconPainter: Painter? = null,
    isEnabled: Boolean = true,
    buttonType: ButtonType = ButtonType.Primary,
    buttonSize: ButtonSize = ButtonSize.LargeProductive,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    var lastInactiveWidthPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    AnimatedContent(
        targetState = status,
        transitionSpec = { inlineLoadingButtonContentTransform },
        contentAlignment = Alignment.CenterStart,
        label = "Inline loading button"
    ) { targetStatus ->
        if (targetStatus == InlineLoadingStatus.Inactive) {
            Button(
                label = label,
                onClick = onClick,
                modifier = modifier
                    .onSizeChanged { size ->
                        if (size.width > 0) {
                            lastInactiveWidthPx = size.width
                        }
                    },
                iconPainter = iconPainter,
                isEnabled = isEnabled,
                buttonType = buttonType,
                buttonSize = buttonSize,
                interactionSource = interactionSource,
            )
        } else {
            Box(
                modifier = modifier
                    .focusable(interactionSource = interactionSource)
                    .defaultMinSize(
                        minWidth = with(density) { lastInactiveWidthPx.toDp() },
                        minHeight = buttonSize.heightDp()
                    )
                    .padding(start = SpacingScale.spacing05),
                contentAlignment = Alignment.CenterStart
            ) {
                InlineLoading(
                    status = targetStatus,
                    label = inlineLoadingLabel,
                    contentDescription = inlineLoadingContentDescription,
                )
            }
        }
    }
}

@Preview
@Composable
private fun InlineLoadingButtonPreview() {
    CarbonDesignSystem {
        InlineLoadingButton(
            label = "Submit",
            inlineLoadingLabel = "Submitting...",
            status = InlineLoadingStatus.Active,
            onClick = {}
        )
    }
}
