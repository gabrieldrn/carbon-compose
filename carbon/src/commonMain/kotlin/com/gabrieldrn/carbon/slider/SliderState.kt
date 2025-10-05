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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

internal class SliderState(
    value: Float,
    private val valueRange: ClosedFloatingPointRange<Float>,
) {

    private var adjustedValue by mutableFloatStateOf(value)
    private var totalWidth by mutableFloatStateOf(0f)
    private var widthRange = 0f..totalWidth

    /**
     * Distance between the first and last value (end - start).
     */
    private val ClosedFloatingPointRange<Float>.distance: Float get() = endInclusive - start

    val value: Float
        get() = adjustedValue

    val valueAsFraction: Float
        get() = (adjustedValue - valueRange.start) / (valueRange.endInclusive - valueRange.start)

    var onValueChange: ((Float) -> Unit)? = null

    fun update(inputOffset: Offset) {
        adjustedValue = inputOffset.x
            .map(from = widthRange, to = valueRange)
            .coerceIn(valueRange)

        onValueChange?.invoke(adjustedValue)
    }

    fun updateTotalWidth(newWidth: Float) {
        totalWidth = newWidth
        widthRange = 0f..newWidth
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
    // Nice to see you again old friend
    private fun Float.map(
        from: ClosedFloatingPointRange<Float>,
        to: ClosedFloatingPointRange<Float>
    ): Float =
        // ratio = (value - from.start) / (from.end - from.start)
        to.start + (this - from.start) / from.distance * to.distance

    companion object {

        fun Saver(
            valueRange: ClosedFloatingPointRange<Float>
        ) = listSaver(
            save = { listOf(it.adjustedValue) },
            restore = {
                SliderState(
                    value = it[0],
                    valueRange = valueRange
                )
            }
        )
    }
}

@Composable
internal fun rememberSliderState(
    value: Float = 0f,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
) = rememberSaveable(valueRange, saver = SliderState.Saver(valueRange)) {
    SliderState(value, valueRange)
}
