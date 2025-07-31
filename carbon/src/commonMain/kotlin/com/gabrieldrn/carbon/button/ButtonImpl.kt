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
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.foundation.motion.Motion

private val buttonTransitionSpec = tween<Float>(
    durationMillis = Motion.Duration.fast01,
    easing = Motion.Entrance.productiveEasing
)

private val buttonAnimationSpec = tween<Color>(
    durationMillis = Motion.Duration.fast01,
    easing = Motion.Entrance.productiveEasing
)

private val contentTransformEnterTransition: ContentTransform =
    fadeIn(snap(), initialAlpha = 1f) togetherWith fadeOut(snap(), targetAlpha = 1f)

private val contentTransformExitTransition: ContentTransform = buttonTransitionSpec.let { spec ->
    fadeIn(spec, initialAlpha = 1f) togetherWith fadeOut(spec, targetAlpha = 1f)
}

private fun AnimatedContentTransitionScope<ButtonScope>.getContentTransition() =
    if (initialState.isEnabled != targetState.isEnabled) {
        contentTransformEnterTransition
    } else {
        contentTransformExitTransition
    }

private fun Modifier.requiredButtonSize(buttonSize: ButtonSize, isIconButton: Boolean): Modifier =
    this then if (isIconButton) {
        Modifier.size(buttonSize.heightDp())
    } else {
        Modifier
            .requiredSize(
                width = Dp.Unspecified,
                height = buttonSize.heightDp()
            )
            .width(IntrinsicSize.Max)
    }

internal data class ButtonScope(
    val colors: ButtonColors,
    val isEnabled: Boolean,
    val isPressed: Boolean,
    val isHovered: Boolean
)

@Composable
internal fun ButtonLayout(
    onClick: () -> Unit,
    buttonType: ButtonType,
    buttonSize: ButtonSize,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    isIconButton: Boolean = false,
    theme: Theme = Carbon.theme,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication = remember(theme, buttonType) {
        ButtonFocusIndication(theme, buttonType)
    },
    content: @Composable RowScope.(ButtonScope) -> Unit,
) {
    val colors = ButtonColors.colors(buttonType, isIconButton)

    val containerColor = remember(colors) { Animatable(colors.containerColor) }

    val isPressed by rememberIsButtonPressed(
        interactionSource = interactionSource,
        buttonColors = colors,
        containerColorAnimatable = containerColor
    )

    val isHovered by interactionSource.collectIsHoveredAsState()

    LaunchedEffect(isEnabled, isPressed, isHovered, colors) {
        containerColor.animateTo(
            targetValue = when {
                !isEnabled -> colors.containerDisabledColor
                isPressed -> colors.containerActiveColor
                isHovered -> colors.containerHoverColor
                else -> colors.containerColor
            },
            animationSpec = buttonAnimationSpec,
            initialVelocity = containerColor.targetValue
        )
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
        val buttonScope by remember(colors, isEnabled, isPressed, isHovered) {
            mutableStateOf(ButtonScope(colors, isEnabled, isPressed, isHovered))
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
                scope.isHovered -> scope.colors.labelHoverColor
                scope.isPressed -> scope.colors.labelActiveColor
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
    contentDescription: String? = null
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        colorFilter = ColorFilter.tint(
            when {
                !scope.isEnabled -> scope.colors.iconDisabledColor
                scope.isHovered -> scope.colors.iconHoverColor
                scope.isPressed -> scope.colors.iconActiveColor
                else -> scope.colors.iconColor
            }
        ),
        modifier = modifier
    )
}
