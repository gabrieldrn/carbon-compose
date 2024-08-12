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

package com.gabrieldrn.carbon.dropdown.base

import androidx.compose.runtime.Immutable

/**
 * Represents the state of a dropdown.
 */
@Immutable
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
