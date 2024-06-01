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

/**
 * Converts a list of strings to a map of strings to [DropdownOption]s.
 */
public fun Collection<String>.toDropdownOptions(): Map<String, DropdownOption> =
    associateWith { DropdownOption(it) }

/**
 * Returns a map of strings to [DropdownOption]s.
 */
public fun dropdownOptionsOf(vararg values: String): Map<String, DropdownOption> = values
    .associateWith { DropdownOption(it) }