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

package com.gabrieldrn.carbon.slider

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.foundation.color.borderSubtleColor
import com.gabrieldrn.carbon.foundation.color.layerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import org.jetbrains.compose.ui.tooling.preview.Preview

private val handleSize = 14.dp
private val handleActiveSize = 20.dp
private val handleActiveScaleRatio = handleActiveSize / handleSize

// TODO onValueChangeFinished
// TODO Focus
// TODO Demo
// TODO KDoc
// TODO GH pages
@Composable
public fun Slider(
    value: Float,
    startLabel: String,
    endLabel: String,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    sliderRange: ClosedFloatingPointRange<Float> = 0f..1f,
    @FloatRange(from = 0.0) steps: Float = 0f,
) {
    Column(modifier = modifier) {
        if (label.isNotEmpty()) {
            val labelColor = Carbon.theme.textSecondary

            BasicText(
                text = label,
                style = Carbon.typography.bodyCompact01,
                color = { labelColor },
                modifier = Modifier.padding(bottom = SpacingScale.spacing03)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {

            val rangeLabelColor = Carbon.theme.textPrimary
            val padding = SpacingScale.spacing04

            val sliderState = rememberSliderState(
                value = value,
                steps = steps,
                valueRange = sliderRange,
            )

            sliderState.onValueChange = onValueChange

            var isPressed by remember { mutableStateOf(false) }

            BasicText(
                text = startLabel,
                style = Carbon.typography.bodyCompact01,
                color = { rangeLabelColor }
            )

            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .height(handleSize)
                    .padding(horizontal = padding)
                    .pointerHoverIcon(icon = PointerIcon.Hand, overrideDescendants = true)
                    .pointerInput(sliderState) {
                        detectTapGestures(
                            onPress = {
                                isPressed = true
                                sliderState.update(it)
                                awaitRelease()
                                isPressed = false
                            }
                        )
                    }
                    .pointerInput(sliderState) {
                        detectDragGestures(
                            onDragStart = { _ -> isPressed = true },
                            onDragEnd = { isPressed = false },
                            onDragCancel = { isPressed = false },
                            onDrag = { change, _ -> sliderState.update(change.position) }
                        )
                    }
            ) {
                val handleInteractionSource = remember { MutableInteractionSource() }

                val trackColor = Carbon.theme.borderSubtleColor(Carbon.layer)
                val filledTrackColor = Carbon.theme.borderInverse

                val isHovered by handleInteractionSource.collectIsHoveredAsState()
                val scaleFactor by animateFloatAsState(
                    if (isHovered || isPressed) handleActiveScaleRatio else 1f
                )

                Canvas(modifier = Modifier.fillMaxSize()) {
                    val halfWidth = size.width * .5f
                    val halfHeight = size.height * .5f

                    drawLine(
                        color = trackColor,
                        start = Offset(x = 0f, y = halfHeight),
                        end = Offset(x = size.width, y = halfHeight),
                        strokeWidth = 2.dp.toPx()
                    )

                    drawLine(
                        color = trackColor,
                        start = Offset(x = halfWidth, y = 0f),
                        end = Offset(x = halfWidth, y = 4.dp.toPx()),
                        strokeWidth = 2.dp.toPx()
                    )

                    drawLine(
                        color = filledTrackColor,
                        start = Offset(x = 0f, y = halfHeight),
                        end = Offset(x = size.width * sliderState.valueAsFraction, y = halfHeight),
                        strokeWidth = 2.dp.toPx()
                    )

                    sliderState.updateWidth(size.width)
                }

                Box(
                    modifier = Modifier
                        .size(handleSize)
                        .offset {
                            IntOffset(
                                x = (maxWidth * sliderState.valueAsFraction - padding * .5f)
                                    .roundToPx(),
                                y = 0
                            )
                        }
                        .hoverable(interactionSource = handleInteractionSource)
                        .graphicsLayer {
                            scaleX = scaleFactor
                            scaleY = scaleFactor
                        }
                        .drawBehind {
                            drawCircle(filledTrackColor)
                        }
                )
            }

            BasicText(
                text = endLabel,
                style = Carbon.typography.bodyCompact01,
                color = { rangeLabelColor }
            )
        }
    }
}

@Preview
@Composable
private fun SliderPreview() {
    CarbonDesignSystem {
        var value by remember {
            mutableStateOf(.25f)
        }

        Slider(
            value = value,
            startLabel = "0",
            endLabel = "1",
            onValueChange = { value = it },
            modifier = Modifier
                .layerBackground()
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun SliderWithStepsPreview() {
    CarbonDesignSystem {
        var value by remember {
            mutableStateOf(0f)
        }

        Slider(
            value = value,
            startLabel = "0",
            endLabel = "1",
            onValueChange = { value = it },
            sliderRange = 0f..2f,
            steps = 1f,
            modifier = Modifier
                .layerBackground()
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun SliderCustomRangePreview() {
    CarbonDesignSystem {
        var value by remember {
            mutableStateOf(120f)
        }

        Slider(
            value = value,
            startLabel = "50",
            endLabel = "200",
            onValueChange = { value = it },
            sliderRange = 50f..200f,
            modifier = Modifier
                .layerBackground()
                .padding(16.dp)
        )
    }
}
