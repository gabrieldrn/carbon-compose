package carbon.compose.semantics

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState

/**
 * Configures a component to be stated as "read-only".
 *
 * From [Carbon documentation](https://carbondesignsystem.com/patterns/read-only-states-pattern/),
 * the read-only states "are applied to components when the user is allowed to review but not modify
 * the component. It removes all interactive functions from the component.", meaning that a
 * component in a read-only state is **disabled but stays focusable** and readable by screen
 * reader services.
 *
 * @param role UI element type.
 * @param interactionSource The [MutableInteractionSource] associated with the element.
 * @param state Selection state of the element.
 * @param mergeDescendants Whether to merge descendants into a single tree.
 */
public fun Modifier.readOnly(
    role: Role?,
    interactionSource: MutableInteractionSource,
    state: ToggleableState? = null,
    mergeDescendants: Boolean = false,
): Modifier = this
    .focusable(
        enabled = true,
        interactionSource = interactionSource,
    )
    .semantics(
        mergeDescendants = mergeDescendants
    ) {
        if (role != null) { this.role = role }
        if (state != null) { toggleableState = state }
        readOnly()
        disabled()
    }

/**
 * Configures a component to be stated as "read-only".
 *
 * From [Carbon documentation](https://carbondesignsystem.com/patterns/read-only-states-pattern/),
 * the read-only states "are applied to components when the user is allowed to review but not modify
 * the component. It removes all interactive functions from the component.", meaning that a
 * component in a read-only state is **disabled but stays focusable** and readable by screen
 * reader services.
 *
 * @param role UI element type.
 * @param interactionSource The [MutableInteractionSource] associated with the element.
 * @param selected Selection state of the element.
 * @param mergeDescendants Whether to merge descendants into a single tree.
 */
public fun Modifier.readOnly(
    role: Role?,
    interactionSource: MutableInteractionSource,
    selected: Boolean,
    mergeDescendants: Boolean = false,
): Modifier = readOnly(
    role = role,
    interactionSource = interactionSource,
    state = ToggleableState(selected),
    mergeDescendants = mergeDescendants
)
