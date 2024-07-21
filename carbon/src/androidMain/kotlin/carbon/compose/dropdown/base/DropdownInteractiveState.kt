package carbon.compose.dropdown.base

import androidx.compose.runtime.Immutable
import java.io.Serializable

/**
 * Represents the state of a dropdown.
 */
@Immutable
public sealed interface DropdownInteractiveState : Serializable {

    /**
     * Default state of the dropdown.
     */
    public data object Enabled : DropdownInteractiveState

    /**
     * The dropdown presents a warning to the user.
     * @param helperText The warning message to be displayed below the dropdown.
     */
    public data class Warning(val helperText: String) : DropdownInteractiveState

    /**
     * The dropdown presents an error to the user.
     * @param helperText The error message to be displayed below the dropdown.
     */
    public data class Error(val helperText: String) : DropdownInteractiveState

    /**
     * The dropdown is disabled and cannot be interacted with.
     */
    public data object Disabled : DropdownInteractiveState

    /**
     * The dropdown is read-only, meaning the user is not able change the selected option but
     * screen readers can still read the dropdown.
     */
    public data object ReadOnly : DropdownInteractiveState

    public companion object {

        /**
         * Returns the helper text to be displayed below the dropdown, if any.
         */
        internal val DropdownInteractiveState.helperText: String?
            get() = when (this) {
                is Warning -> helperText
                is Error -> helperText
                else -> null
            }

        /**
         * Returns `true` if the dropdown should be allowed to gain focus.
         */
        internal val DropdownInteractiveState.isFocusable: Boolean
            get() = this != Disabled
    }
}
