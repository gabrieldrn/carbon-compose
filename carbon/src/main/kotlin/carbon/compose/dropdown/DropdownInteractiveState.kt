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
}
