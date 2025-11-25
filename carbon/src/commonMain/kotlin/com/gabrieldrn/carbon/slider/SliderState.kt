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

import androidx.annotation.IntRange
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import com.gabrieldrn.carbon.common.math.map
import kotlin.math.abs

// Use MutatorMutex + offer remember (saveable) function if this class needs to become public
/**
 * State of the [Slider].
 *
 * @param value Initial value of the [Slider].
 * @param steps Intermediate steps in the range, must be a positive value.
 * @param valueRange Range of values that the slider can take.
 */
internal class SliderState(
    value: Float,
    @param:IntRange(from = 0) val steps: Int,
    val valueRange: ClosedFloatingPointRange<Float>,
) {
    private var scaledValue by mutableFloatStateOf(value.coerceIn(valueRange))
    private var totalWidth by mutableFloatStateOf(0f)
    private var widthRange = 0f..totalWidth
    private val divisions =
        if (steps > 0) {
            val range = valueRange.endInclusive - valueRange.start
            val div = if (range > 0) range / (steps + 1) else 0f
            generateSequence(valueRange.start) { previous ->
                if (previous.isInfinite() || previous >= valueRange.endInclusive) {
                    null
                } else {
                    val next = previous + div
                    if (next > valueRange.endInclusive) valueRange.endInclusive else next
                }
            }
                .toList()
        } else {
            listOf()
        }

    /**
     * Current scaled value of the [Slider].
     */
    var value: Float
        get() = scaledValue
        set(newValue) {
            if (newValue != scaledValue) {
                scaledValue = newValue.coerceIn(valueRange)
                onValueChange?.invoke(scaledValue)
            }
        }

    /**
     * The scaled value of the [Slider] expressed as a fraction of the total range.
     */
    val valueAsFraction: Float
        get() = (scaledValue - valueRange.start) / (valueRange.endInclusive - valueRange.start)

    /**
     * Callback triggered when the scaled value changes.
     */
    var onValueChange: ((Float) -> Unit)? = null

    internal fun getNearestDivision(value: Float): Float =
        value
            .coerceIn(valueRange)
            .let { coerced -> divisions.minByOrNull { abs(it - coerced) } ?: coerced }

    /**
     * Update the state based on the given input offset.
     */
    fun update(inputOffset: Offset) {
        val newScaledValue = getNearestDivision(
            inputOffset.x
                .coerceIn(widthRange)
                .map(from = widthRange, to = valueRange)
        )
        scaledValue = newScaledValue
        onValueChange?.invoke(newScaledValue)
    }

    /**
     * Updates the state with the total width of the slider, adjusting the internal range
     * accordingly.
     */
    fun updateWidth(newWidth: Float) {
        if (newWidth == totalWidth) return
        totalWidth = newWidth
        widthRange = 0f..newWidth
    }
}
