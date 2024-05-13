package carbon.android.dropdown.base

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
