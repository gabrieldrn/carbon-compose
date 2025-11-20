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
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.foundation.color.borderSubtleColor
import com.gabrieldrn.carbon.foundation.color.color
import com.gabrieldrn.carbon.foundation.color.layerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.round

private val handleSize = 14.dp
private val handleActiveSize = 20.dp
private val handleActiveScaleRatio = handleActiveSize / handleSize

/**
 * # Slider - Default
 *
 * Sliders provide a visual indication of adjustable content, where the user can increase or
 * decrease the value by moving the handle along a horizontal track.
 *
 * **Disclaimer**: Due to layout complexities - and lack of guidance from Carbon documentation - for
 * narrow widths like mobile targets, text inputs could not be directly integrated into the Slider
 * component. You may wrap the Slider with text input components yourself if this functionality is
 * desired.
 *
 * (From [Slider documentation](https://carbondesignsystem.com/components/slider/usage/))
 *
 * @param value The current value of the slider.
 * @param startLabel The label displayed at the start of the slider.
 * @param endLabel The label displayed at the end of the slider.
 * @param onValueChange Callback invoked when the slider value changes.
 * @param modifier The modifier to be applied to the slider.
 * @param label A label to be displayed above the slider.
 * @param interactionSource The [MutableInteractionSource] representing the stream of
 * [androidx.compose.foundation.interaction.Interaction]s for this slider. You can create and pass
 * in your own `remember`ed instance to observe
 * [androidx.compose.foundation.interaction.Interaction]s and customize the appearance and behavior
 * of this slider in different states.
 * @param onValueChangeFinished Callback invoked when the user has finished interacting with the
 * slider.
 * @param sliderRange The range of values that the slider can take.
 * @param steps The step value of the slider.
 * @param stateDescription A lambda that returns the state description of the slider for
 * accessibility services based on the current value.
 */
@Composable
public fun Slider(
    value: Float,
    startLabel: String,
    endLabel: String,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onValueChangeFinished: () -> Unit = {},
    sliderRange: ClosedFloatingPointRange<Float> = 0f..1f,
    @FloatRange(from = 0.0) steps: Float = 0.1f,
    stateDescription: (Float) -> String = { (round(it * 10) / 10).toString() }
) {
    val sliderState = remember(sliderRange, steps) {
        SliderState(value, steps, sliderRange)
    }

    sliderState.value = value
    sliderState.onValueChange = onValueChange

    Column(
        modifier = modifier
            .sliderSemantics(
                sliderRange = sliderRange,
                steps = steps,
                sliderState = sliderState,
                stateDescription = stateDescription,
                onValueChangeFinished = onValueChangeFinished
            )
    ) {
        if (label.isNotEmpty()) {
            val labelColor = Carbon.theme.textSecondary

            BasicText(
                text = label,
                style = Carbon.typography.bodyCompact01,
                color = { labelColor },
                modifier = Modifier
                    .padding(bottom = SpacingScale.spacing03)
                    .testTag(SliderTestTags.LABEL)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {

            val rangeLabelColor = Carbon.theme.textPrimary
            val padding = SpacingScale.spacing04

            val isFocused by interactionSource.collectIsFocusedAsState()
            var isPressed by remember { mutableStateOf(false) }
            var isDragging by remember { mutableStateOf(false) }

            BasicText(
                text = startLabel,
                style = Carbon.typography.bodyCompact01,
                color = { rangeLabelColor },
                modifier = Modifier
                    .clearAndSetSemantics {
                        testTag = SliderTestTags.START_LABEL
                    }
            )

            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .height(handleSize)
                    .padding(horizontal = padding)
                    .focusable(interactionSource = interactionSource)
                    .onKeyEvent {
                        when (it.key) {
                            Key.DirectionLeft if it.type == KeyEventType.KeyDown -> {
                                sliderState.value -= steps
                                true
                            }
                            Key.DirectionRight if it.type == KeyEventType.KeyDown -> {
                                sliderState.value += steps
                                true
                            }
                            else -> false
                        }
                    }
                    .pointerHoverIcon(icon = PointerIcon.Hand)
                    .pointerInput(sliderState) {
                        detectTapGestures(
                            onPress = {
                                isPressed = true
                                sliderState.update(it)
                                awaitRelease()
                                isPressed = false
                                onValueChangeFinished()
                            }
                        )
                    }
                    .pointerInput(sliderState) {
                        detectDragGestures(
                            onDragStart = { _ -> isDragging = true; isPressed = false },
                            onDragEnd = {
                                isDragging = false
                                onValueChangeFinished()
                            },
                            onDragCancel = {
                                isDragging = false
                                onValueChangeFinished()
                            },
                            onDrag = { change, _ ->
                                sliderState.update(change.position)
                            }
                        )
                    }
                    .testTag(SliderTestTags.TRACK_CONTAINER)
            ) {
                val handleInteractionSource = remember { MutableInteractionSource() }

                val trackColor = Carbon.theme.borderSubtleColor(Carbon.layer)

                val isHovered by handleInteractionSource.collectIsHoveredAsState()

                val filledTrackColor by animateColorAsState(
                    if (isFocused) Carbon.theme.focus
                    else Carbon.theme.borderInverse
                )

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(SliderTestTags.TRACK)
                ) {
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

                Handle(
                    isActive = isPressed || isDragging || isFocused,
                    isHovered = isHovered,
                    color = filledTrackColor,
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                x = (maxWidth * sliderState.valueAsFraction - padding * .5f)
                                    .roundToPx(),
                                y = 0
                            )
                        }
                        .hoverable(interactionSource = handleInteractionSource)
                        .testTag(SliderTestTags.HANDLE)
                )
            }

            BasicText(
                text = endLabel,
                style = Carbon.typography.bodyCompact01,
                color = { rangeLabelColor },
                modifier = Modifier
                    .clearAndSetSemantics {
                        testTag = SliderTestTags.END_LABEL
                    }
            )
        }
    }
}

private fun Modifier.sliderSemantics(
    sliderRange: ClosedFloatingPointRange<Float>,
    steps: Float,
    sliderState: SliderState,
    stateDescription: (Float) -> String,
    onValueChangeFinished: () -> Unit,
): Modifier =
    this.semantics(mergeDescendants = true) {
        this.stateDescription = stateDescription(sliderState.value)
        setProgress(
            action = { newValue ->
                Logger.d("Requested new value: $newValue")
                val adjustedValue =
                    if (steps > 0f) {
                        with(sliderState) {
                            val divIndex = divisions.indexOf(this.value)

                            divisions[
                                when {
                                    newValue > this.value -> divIndex + 1
                                    newValue < this.value -> divIndex - 1
                                    else -> divIndex
                                }
                                    .coerceIn(0..divisions.lastIndex)
                            ]
                        }
                    } else {
                        newValue
                    }

                if (adjustedValue != sliderState.value) {
                    sliderState.value = adjustedValue
                    onValueChangeFinished()
                    true
                } else {
                    false
                }
            }
        )
    }.progressSemantics(
        value = sliderState.value,
        valueRange = sliderRange,
    )

private val innerRingStrokeWidth = 1.5.dp

@Composable
private fun Handle(
    isActive: Boolean,
    isHovered: Boolean,
    color: Color,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val layerColor = Carbon.layer.color

    val scaleFactor by animateFloatAsState(
        if (isActive || isHovered) handleActiveScaleRatio else 1f
    )
    val innerRingWidth by animateFloatAsState(
        with(density) { if (isActive) innerRingStrokeWidth.toPx() else 0.dp.toPx() }
    )

    Box(
        modifier = modifier
            .size(handleSize)
            .drawBehind {
                // Handle
                scale(scaleFactor) {
                    drawCircle(color = color)
                }

                // Inner focus ring
                inset(innerRingStrokeWidth.toPx() * .5f) {
                    drawCircle(
                        color = if (innerRingWidth == 0f) Color.Transparent else layerColor,
                        style = Stroke(innerRingWidth)
                    )
                }
            }
    )
}

@Preview
@Composable
private fun SliderHandlePreview() {
    CarbonDesignSystem {
        Row(
            modifier = Modifier.size(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Handle(
                isActive = true,
                isHovered = true,
                color = Carbon.theme.focus
            )

            Handle(
                isActive = false,
                isHovered = true,
                color = Carbon.theme.focus
            )

            Handle(
                isActive = false,
                isHovered = false,
                color = Carbon.theme.focus
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
