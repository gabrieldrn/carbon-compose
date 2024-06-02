package carbon.compose.dropdown.base

import androidx.compose.runtime.Immutable

/**
 * Represents an option in a dropdown.
 * @param value The value of the option to be displayed.
 * @param enabled Whether the option is enabled or not.
 */
@Immutable
public data class DropdownOption(
    val value: String,
    val enabled: Boolean = true
)
