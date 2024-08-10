/*
 * Copyright 2024 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package carbon.compose.dropdown.base

import androidx.compose.runtime.Immutable
import kotlin.enums.EnumEntries

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
 * Converts a list of [Enum] entries to a map of [Enum]s to [DropdownOption]s.
 */
public inline fun <reified E : Enum<E>> EnumEntries<E>.toDropdownOptions(): Map<E, DropdownOption> =
    enumValues<E>().associateWith { DropdownOption(it.name) }

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
