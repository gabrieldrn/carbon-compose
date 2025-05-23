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

package com.gabrieldrn.carbon.breadcrumb

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints

/**
 * A [MeasurePolicy] for arranging breadcrumb items and separators.
 *
 * This policy handles two main scenarios:
 *
 * 1. **Horizontal Layout:** If all breadcrumbs and separators fit within the available
 *    width, they are laid out horizontally. Each breadcrumb is followed by its
 *    corresponding separator. The height of the layout is determined by the tallest
 *    item (breadcrumb or separator) in the row.
 *
 * 2. **Vertical Layout (Overflow):** If the total width of breadcrumbs and separators
 *    exceeds the available width, they are laid out vertically. Each breadcrumb
 *    and its separator (if present) occupy a row. The width of the layout is
 *    determined by the widest breadcrumb-separator pair. The total height is the
 *    sum of the heights of each row.
 *
 * The items are identified with a [LayoutId].
 * If there are more breadcrumbs than separators, the trailing breadcrumbs will be
 * laid out without a following separator.
 */
internal class BreadcrumbMeasurePolicy : MeasurePolicy {

    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints
    ): MeasureResult {
        val childConstraints = Constraints(
            maxWidth = if (constraints.hasBoundedWidth) {
                constraints.maxWidth
            } else {
                Constraints.Infinity
            },
            maxHeight = if (constraints.hasBoundedHeight) {
                constraints.maxHeight
            } else {
                Constraints.Infinity
            }
        )

        val breadcrumbPlaceables = measurables
            .filter { it.layoutId == LayoutId.Breadcrumb }
            .map { it.measure(childConstraints) }

        val separatorPlaceables = measurables
            .filter { it.layoutId == LayoutId.Separator }
            .map { it.measure(childConstraints) }
            .toMutableList<Placeable?>() // <- Add nullability
            // Fill the missing separators with nulls for zipping operation
            .apply {
                repeat(breadcrumbPlaceables.size - this.size) {
                    add(null)
                }
            }

        val zippedPlaceables = breadcrumbPlaceables.zip(separatorPlaceables)

        val totalWidth = (breadcrumbPlaceables + separatorPlaceables)
            .sumOf { it?.width ?: 0 }

        return if (totalWidth <= constraints.maxWidth) {
            val height = zippedPlaceables.maxOf { (b, s) -> maxOf(b.height, s?.height ?: 0) }

            layout(totalWidth, height) {
                var xPosition = 0

                zippedPlaceables.forEach { (breadcrumb, separator: Placeable?) ->
                    breadcrumb.placeRelative(x = xPosition, y = 0)
                    separator?.placeRelative(x = xPosition + breadcrumb.width, y = 0)

                    xPosition += breadcrumb.width + (separator?.width ?: 0)
                }
            }
        } else {
            val height = zippedPlaceables.sumOf { (b, s) -> maxOf(b.height, s?.height ?: 0) }

            layout(childConstraints.maxWidth, height) {
                var yPosition = 0

                zippedPlaceables.forEach { (breadcrumb, separator: Placeable?) ->
                    breadcrumb.placeRelative(x = 0, y = yPosition)
                    separator?.placeRelative(x = breadcrumb.width, y = yPosition)

                    yPosition += maxOf(breadcrumb.height, separator?.height ?: 0)
                }
            }
        }
    }

    internal enum class LayoutId { Breadcrumb, Separator }
}
