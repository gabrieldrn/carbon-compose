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

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp

/**
 * Input height for dropdowns.
 */
@Stable
public actual enum class DropdownSize {

    /**
     * Use when space is constricted or when placing a dropdown in a form that is long and complex.
     */
    Small,

    /**
     * This is the default size.
     */
    Medium,

    /**
     * Choose this size when there is a lot of space to work with. This size is typically used in
     * simple forms or when a dropdown is placed by itself on a page, for example as a filter.
     * This should be the default size for dropdowns on portable devices for accessibility reasons.
     */
    Large
}

internal actual fun DropdownSize.dpSize() =
    when (this) {
        DropdownSize.Small -> DROPDOWN_HEIGHT_SMALL_DP
        DropdownSize.Medium -> DROPDOWN_HEIGHT_MEDIUM_DP
        else -> DROPDOWN_HEIGHT_LARGE_DP
    }
        .dp
