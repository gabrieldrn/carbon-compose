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

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.motion.Motion
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

internal fun <T : Any> getButtonTransitionSpec() = tween<T>(
    durationMillis = Motion.Duration.fast01,
    easing = Motion.Entrance.productiveEasing
)

private fun Modifier.requiredButtonSize(buttonSize: ButtonSize, isIconButton: Boolean) =
    this
        .requiredSize(
            width = if (isIconButton) SpacingScale.spacing09 else Dp.Unspecified,
            height = if (isIconButton) SpacingScale.spacing09 else buttonSize.heightDp()
        )
        .width(IntrinsicSize.Max)

private fun AnimatedContentTransitionScope<ButtonScope>.getContentTransition() =
    if (initialState.isEnabled != targetState.isEnabled) {
        fadeIn(snap(), initialAlpha = 1f) togetherWith
            fadeOut(snap(), targetAlpha = 1f)
    } else {
        fadeIn(getButtonTransitionSpec(), initialAlpha = 1f) togetherWith
            fadeOut(getButtonTransitionSpec(), targetAlpha = 1f)
    }

internal data class ButtonScope(
    val colors: ButtonColors,
    val isEnabled: Boolean,
    val interaction: Interaction?,
)

@Composable
internal fun ButtonRowImpl(
    onClick: () -> Unit,
    buttonType: ButtonType,
    buttonSize: ButtonSize,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    isIconButton: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.(ButtonScope) -> Unit,
) {
    val theme = Carbon.theme
    val colors = ButtonColors.colors(buttonType, isIconButton)
    val containerColor = remember(colors) { Animatable(colors.containerColor) }
    val indication = remember(buttonType) { ButtonFocusIndication(theme, buttonType) }

    // TODO Move container + border drawing to the indication instance. Here, animations are a bit
    //  delayed and doesn't cancel when spammed.
    LaunchedEffect(isEnabled, colors) {
        containerColor.animateTo(
            targetValue = if (isEnabled) colors.containerColor else colors.containerDisabledColor,
            animationSpec = snap()
        )
    }

    LaunchedEffect(interactionSource, isEnabled, containerColor, colors) {
        interactionSource.interactions.collect {
            if (!isEnabled) return@collect
            containerColor.stop()
            containerColor.animateTo(
                targetValue = when (it) {
                    is HoverInteraction.Enter -> colors.containerHoverColor
                    is PressInteraction.Press -> colors.containerActiveColor
                    else -> colors.containerColor
                },
                animationSpec = getButtonTransitionSpec()
            )
        }
    }

    Box(
        modifier = modifier
            .requiredButtonSize(buttonSize, isIconButton)
            .drawBehind {
                drawRect(color = containerColor.value)
            }
            .border(
                width = 1.dp,
                color = if (isEnabled) {
                    colors.containerBorderColor
                } else {
                    colors.containerBorderDisabledColor
                },
            )
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = onClick,
                enabled = isEnabled,
                role = Role.Button
            )
            .then(
                InspectableModifier(
                    debugInspectorInfo {
                        properties["buttonType"] = buttonType.name
                        properties["buttonSize"] = buttonSize::class.simpleName
                        properties["isIconButton"] = isIconButton.toString()
                    }
                )
            ),
    ) {
        val interaction by interactionSource.interactions.collectAsState(null)

        val buttonScope by remember(
            colors,
            isEnabled,
            interaction,
        ) {
            mutableStateOf(
                ButtonScope(
                    colors,
                    isEnabled,
                    interaction,
                )
            )
        }

        AnimatedContent(
            targetState = buttonScope,
            transitionSpec = { getContentTransition() },
            label = "Button icon",
        ) { scope ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(buttonSize.getContainerPaddings(isIconButton))
            ) {
                content(scope)
            }
        }
    }
}

@Composable
internal fun Label(
    label: String,
    scope: ButtonScope,
    modifier: Modifier = Modifier,
) {
    BasicText(
        text = label,
        style = Carbon.typography.bodyCompact01,
        color = {
            when {
                !scope.isEnabled -> scope.colors.labelDisabledColor
                scope.interaction is HoverInteraction.Enter -> scope.colors.labelHoverColor
                scope.interaction is PressInteraction.Press -> scope.colors.labelActiveColor
                else -> scope.colors.labelColor
            }
        },
        maxLines = 1,
        softWrap = false,
        modifier = modifier
    )
}

@Composable
internal fun ButtonIcon(
    painter: Painter,
    scope: ButtonScope,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painter,
        contentDescription = null,
        colorFilter = ColorFilter.tint(
            when {
                !scope.isEnabled -> scope.colors.iconDisabledColor
                scope.interaction is HoverInteraction.Enter -> scope.colors.iconHoverColor
                scope.interaction is PressInteraction.Press -> scope.colors.iconActiveColor
                else -> scope.colors.iconColor
            }
        ),
        modifier = modifier
    )
}
