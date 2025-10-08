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

package com.gabrieldrn.carbon.common.math

/**
 * Distance between the first and last value (end - start).
 */
internal val ClosedFloatingPointRange<Float>.distance: Float get() = endInclusive - start

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
internal fun Float.map(
    from: ClosedFloatingPointRange<Float>,
    to: ClosedFloatingPointRange<Float>
): Float =
    // ratio = (value - from.start) / (from.end - from.start)
    to.start + (this - from.start) / from.distance * to.distance
