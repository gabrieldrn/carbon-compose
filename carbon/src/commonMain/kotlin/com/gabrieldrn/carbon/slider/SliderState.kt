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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import com.gabrieldrn.carbon.common.math.map
import kotlin.math.abs

// Use MutatorMutex + offer remember (saveable) function if this state needs to become public
internal class SliderState(
    value: Float,
    @param:FloatRange(from = 0.0) val steps: Float,
    private val valueRange: ClosedFloatingPointRange<Float>,
) {

    private var scaledValue by mutableFloatStateOf(value)
    private var totalWidth by mutableFloatStateOf(0f)
    private var widthRange = 0f..totalWidth
    private val divisions =
        if (steps > 0f) {
            generateSequence(valueRange.start) { previous ->
                if (previous.isInfinite() || previous >= valueRange.endInclusive) {
                    null
                } else {
                    val next = previous + steps
                    if (next > valueRange.endInclusive) valueRange.endInclusive else next
                }
            }
                .toList()
        } else {
            listOf()
        }

    var value: Float
        get() = scaledValue
        set(newValue) {
            if (newValue != scaledValue) {
                scaledValue = newValue.coerceIn(valueRange)
                onValueChange?.invoke(scaledValue)
            }
        }

    val valueAsFraction: Float
        get() = (scaledValue - valueRange.start) / (valueRange.endInclusive - valueRange.start)

    var onValueChange: ((Float) -> Unit)? = null

    private fun Float.getNearestDivision(): Float =
        divisions.minByOrNull { abs(it - this) } ?: this

    fun update(inputOffset: Offset) {
        val newScaledValue = inputOffset.x
            .map(from = widthRange, to = valueRange)
            .getNearestDivision()
            .coerceIn(valueRange)
        scaledValue = newScaledValue
        onValueChange?.invoke(newScaledValue)
    }

    fun updateWidth(newWidth: Float) {
        if (newWidth == totalWidth) return
        totalWidth = newWidth
        widthRange = 0f..newWidth
    }
}
