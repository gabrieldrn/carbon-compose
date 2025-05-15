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
            .filter { it.layoutId is LayoutId.Breadcrumb }
            .sortedBy { (it.layoutId as LayoutId.Breadcrumb).index } // FIXME May not be necessary
            .map { it.measure(childConstraints) }

        val separatorPlaceables = measurables
            .filter { it.layoutId is LayoutId.Separator }
            .sortedBy { (it.layoutId as LayoutId.Separator).index } // FIXME May not be necessary
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
            val width = zippedPlaceables.maxOf { (b, s) -> b.width + (s?.width ?: 0) }
            val height = zippedPlaceables.sumOf { (b, s) -> maxOf(b.height, s?.height ?: 0) }

            layout(width, height) {
                var yPosition = 0

                zippedPlaceables.forEach { (breadcrumb, separator: Placeable?) ->
                    breadcrumb.placeRelative(x = 0, y = yPosition)
                    separator?.placeRelative(x = breadcrumb.width, y = yPosition)

                    yPosition += maxOf(breadcrumb.height, separator?.height ?: 0)
                }
            }
        }
    }

    internal sealed interface LayoutId {
        val index: Int

        data class Breadcrumb(override val index: Int) : LayoutId
        data class Separator(override val index: Int) : LayoutId
    }
}
