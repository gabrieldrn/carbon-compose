package carbon.compose.dropdown

/**
 * Represents the state of a dropdown.
 */
public sealed interface DropdownInteractiveState {

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

    public companion object {

        internal val DropdownInteractiveState.helperText: String?
            get() = when (this) {
                is Warning -> helperText
                is Error -> helperText
                else -> null
            }
    }
}
