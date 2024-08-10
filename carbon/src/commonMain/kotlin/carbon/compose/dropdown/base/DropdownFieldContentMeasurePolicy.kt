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

package carbon.compose.dropdown.base

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints

/**
 * A custom measure policy for the content of a dropdown field.
 *
 * This measure policy is used to measure and layout the content of a dropdown field. Every content
 * of a Dropdown field is identified with layout ids from [DropdownFieldContentId].
 * The presence of a DropdownPlaceholderText composable is mandatory.
 * Every content composable should have a unique layout id to avoid conflicts.
 */
internal class DropdownFieldContentMeasurePolicy : MeasurePolicy {
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

        val childConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val tag = measurables
            .firstOrNull { it.layoutId == DropdownFieldContentId.MULTISELECT_TAG }
            ?.measure(childConstraints)

        val stateIcon = measurables
            .firstOrNull { it.layoutId == DropdownFieldContentId.STATE_ICON }
            ?.measure(childConstraints)

        val widthConsumed = arrayOf(tag, stateIcon).filterNotNull().sumOf { it.width }

        // The placeholder will take up the remaining space.
        val placeholder = measurables
            .first { it.layoutId == DropdownFieldContentId.PLACEHOLDER }
            .measure(
                constraints.copy(
                    minWidth = constraints.minWidth - widthConsumed,
                    maxWidth = constraints.maxWidth - widthConsumed,
                    minHeight = 0
                )
            )

        return layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            var xPos = 0

            listOf(tag, placeholder, stateIcon)
                .filterNotNull()
                .forEach {
                    it.placeRelative(
                        x = xPos,
                        y = constraints.maxHeight / 2 - it.height / 2
                    )
                    xPos += it.width
                }
        }
    }
}
