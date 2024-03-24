package carbon.compose.semantics

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.onClick
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
 */
public fun Modifier.readOnly(
    role: Role,
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
        readOnly()
        this.role = role
        if (state != null) { toggleableState = state }
        onClick(null, null)
        disabled()
    }
