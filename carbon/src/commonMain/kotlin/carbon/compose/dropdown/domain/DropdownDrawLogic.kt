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

package carbon.compose.dropdown.domain

import androidx.compose.ui.unit.dp
import carbon.compose.dropdown.base.DropdownInteractiveState
import carbon.compose.foundation.spacing.SpacingScale

/**
 * Computes the height ratio for the dropdown options popup. The output is intended to be multiplied
 * by the height of a single option to get the total height of the popup. As recommended in the
 * Carbon design system guidelines, showing 50% of the last option’s container height indicates
 * that there are more options to see within the popup. In this case, the ratio will have a half
 * value added to it.
 * @param optionsSize The number of options in the dropdown.
 * @param visibleItemsBeforeScroll The number of options that are visible before it is needed to
 * scroll to see the rest of the options.
 * @return The height ratio for the dropdown options popup.
 */
internal fun getOptionsPopupHeightRatio(
    optionsSize: Int,
    visibleItemsBeforeScroll: Int
): Float = (visibleItemsBeforeScroll.coerceAtLeast(1) + .5f)
    .coerceAtMost(optionsSize.toFloat())

/**
 * Returns the start spacing for the chevron icon in the dropdown field based on the current state.
 */
internal fun getChevronStartSpacing(state: DropdownInteractiveState) = if (
    state is DropdownInteractiveState.Warning ||
    state is DropdownInteractiveState.Error
) {
    0.dp
} else {
    SpacingScale.spacing05
}
