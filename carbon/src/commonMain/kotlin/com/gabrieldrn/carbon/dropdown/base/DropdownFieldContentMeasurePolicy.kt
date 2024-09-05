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

package com.gabrieldrn.carbon.dropdown.base

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints

/**
 * A custom measure policy for the content of a dropdown field.
 *
 * This measure policy is used to measure and layout the content of a dropdown field. Every content
 * of a Dropdown field is identified with layout ids from [DropdownFieldContentId].
 * The presence of composables identified by layout ids [DropdownFieldContentId.PLACEHOLDER] and
 * [DropdownFieldContentId.CHEVRON] are mandatory.
 * Every content composable should have a unique layout id to avoid conflicts.
 *
 * When inlined, all measurables must be wrapped without exceeding the constraints. When not
 * inlined, the placeholder should take the remaining width also without exceeding the constraints.
 */
internal class DropdownFieldContentMeasurePolicy(
    private val isInlined: Boolean
) : MeasurePolicy {

    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints
    ): MeasureResult {

        require(measurables.map { it.layoutId }.subtract(DropdownFieldContentId.ids).isEmpty()) {
            "Measurables must have unique layout ids."
        }
        require(measurables.count { it.layoutId == DropdownFieldContentId.PLACEHOLDER } == 1) {
            "A DropdownPlaceholderText must be provided to field content."
        }

        val wrappedConstraints = if (constraints.hasBoundedWidth) {
            constraints.copy(minWidth = 0, minHeight = 0)
        } else {
            constraints.copy(minHeight = 0)
        }

        val tag = measurables
            .firstOrNull { it.layoutId == DropdownFieldContentId.MULTISELECT_TAG }
            ?.measure(wrappedConstraints)

        val stateIcon = measurables
            .firstOrNull { it.layoutId == DropdownFieldContentId.STATE_ICON }
            ?.measure(wrappedConstraints)

        val chevron = measurables
            .firstOrNull { it.layoutId == DropdownFieldContentId.CHEVRON }
            ?.measure(wrappedConstraints)

        val (placeholder: Placeable, contentWidth: Int) = measurePlaceholderAndWidth(
            measurablePlaceholder = measurables
                .first { it.layoutId == DropdownFieldContentId.PLACEHOLDER },
            measuredContent = arrayOf(tag, stateIcon, chevron),
            constraints = constraints,
            wrappedConstraints = wrappedConstraints
        )

        return layout(
            width = contentWidth,
            height = wrappedConstraints.maxHeight
        ) {
            var xPos = 0

            listOfNotNull(tag, placeholder, stateIcon, chevron)
                .forEach {
                    it.placeRelative(
                        x = xPos,
                        y = wrappedConstraints.maxHeight / 2 - it.height / 2
                    )
                    xPos += it.width
                }
        }
    }

    private fun measurePlaceholderAndWidth(
        measurablePlaceholder: Measurable,
        measuredContent: Array<Placeable?>,
        constraints: Constraints,
        wrappedConstraints: Constraints
    ): Pair<Placeable, Int> =
        if (constraints.hasBoundedWidth) {
            val widthConsumed = measuredContent
                .filterNotNull()
                .sumOf { it.width }

            val remainingWidth = (wrappedConstraints.maxWidth - widthConsumed).coerceAtLeast(0)

            // See class documentation for more information about the constraints.
            val placeholderConstraints =
                if (isInlined) {
                    wrappedConstraints.copy(maxWidth = remainingWidth)
                } else {
                    wrappedConstraints.copy(
                        minWidth = remainingWidth,
                        maxWidth = remainingWidth,
                    )
                }

            val placeholder = measurablePlaceholder.measure(placeholderConstraints)
            val contentWidth = widthConsumed + placeholder.width

            placeholder to contentWidth
        } else { // Unknown width
            val placeholder = measurablePlaceholder.measure(wrappedConstraints)

            val contentWidth = (measuredContent + placeholder)
                .filterNotNull()
                .sumOf { it.width }
                .coerceIn(wrappedConstraints.minWidth..wrappedConstraints.maxWidth)

            placeholder to contentWidth
        }
}
