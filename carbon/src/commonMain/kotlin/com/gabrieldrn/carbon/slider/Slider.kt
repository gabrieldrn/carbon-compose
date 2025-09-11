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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.foundation.color.borderSubtleColor
import com.gabrieldrn.carbon.foundation.color.layerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
public fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    sliderRange: ClosedFloatingPointRange<Float> = 0f..1f
) {
    val adjustedValue by remember(value, sliderRange) {
        mutableStateOf(value.coerceIn(sliderRange))
    }

    val fraction =
        (adjustedValue - sliderRange.start) / (sliderRange.endInclusive - sliderRange.start)

    val rangeLabelColor = Carbon.theme.textPrimary
    val trackColor = Carbon.theme.borderSubtleColor(Carbon.layer)
    val filledTrackColor = Carbon.theme.borderInverse

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {

        BasicText(
            text = sliderRange.start.toString(),
            style = Carbon.typography.bodyCompact01,
            color = { rangeLabelColor }
        )

        Canvas(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = SpacingScale.spacing04)
                .pointerHoverIcon(icon = PointerIcon.Hand)
                .pointerInput(Unit) {
                    val widthRange = 0f..size.width.toFloat()
                    detectTapGestures(
                        onPress = { offset ->
                            val linearlyMappedPosition =
                                offset.x.map(from = widthRange, to = sliderRange)

                            onValueChange(linearlyMappedPosition)
                        }
                    )
                }
                .pointerInput(Unit) {
                    val widthRange = 0f..size.width.toFloat()
                    detectDragGestures { change, _ ->
                        val linearlyMappedPosition =
                            change.position.x.map(from = widthRange, to = sliderRange)

                        onValueChange(linearlyMappedPosition)
                    }
                }
        ) {
            drawLine(
                color = trackColor,
                start = Offset(x = 0f, y = size.height / 2),
                end = Offset(x = size.width, y = size.height / 2),
                strokeWidth = 2.dp.toPx()
            )

            drawLine(
                color = filledTrackColor,
                start = Offset(x = 0f, y = size.height / 2),
                end = Offset(x = size.width * fraction, y = size.height / 2),
                strokeWidth = 2.dp.toPx()
            )

            drawCircle(
                color = filledTrackColor,
                radius = 7.dp.toPx(),
                center = Offset(x = size.width * fraction, y = size.height / 2)
            )
        }

        BasicText(
            text = sliderRange.endInclusive.toString(),
            style = Carbon.typography.bodyCompact01,
            color = { rangeLabelColor }
        )
    }
}

/**
 * Linearly maps a number that falls inside [from] to [to].
 *
 * Example:
 *
 * ```
 *  0.0    P1 = 250     500.0
 * A|---------x---------|
 * B|---------x---------|
 *  0.0    P2 = 0.5     1.0
 * ```
 *
 * Given a range A [[0.0; 500.0]] and a range B [[0.0;1.0]], if P1 = 250 then P2 = 0.5.
 *
 * @receiver The value to be mapped to the targeted range.
 * @param from Initial range of the receiver.
 * @param to Targeted range.
 * @return The value converted with the targeted range.
 */
private fun Float.map(
    from: ClosedFloatingPointRange<Float>,
    to: ClosedFloatingPointRange<Float>
): Float =
    // ratio = (value - from.start) / (from.end - from.start)
    to.start + (this - from.start) / from.distance * to.distance

/**
 * Distance between the first and last value (end - start).
 */
private val ClosedFloatingPointRange<Float>.distance: Float get() = endInclusive - start

@Preview
@Composable
private fun SliderPreview() {
    CarbonDesignSystem {
        var value by remember {
            mutableStateOf(.5f)
        }

        Slider(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier
                .layerBackground()
                .padding(16.dp)
        )
    }
}
