package carbon.compose.dropdown

/**
 * Represents an option in a dropdown.
 */
public data class DropdownOption(

    /**
     * The value of the option to be displayed.
     */
    val value: String,

    /**
     * Whether the option is enabled or not.
     */
    val enabled: Boolean = true
)
