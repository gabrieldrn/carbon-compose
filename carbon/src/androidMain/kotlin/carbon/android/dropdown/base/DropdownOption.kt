package carbon.android.dropdown.base

/**
 * Represents an option in a dropdown.
 * @param value The value of the option to be displayed.
 * @param enabled Whether the option is enabled or not.
 */
public data class DropdownOption(
    val value: String,
    val enabled: Boolean = true
)
