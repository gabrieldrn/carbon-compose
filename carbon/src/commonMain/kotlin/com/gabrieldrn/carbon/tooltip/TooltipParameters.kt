/*
 * Copyright 2025 Gabriel Derrien
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

package com.gabrieldrn.carbon.tooltip

/**
 * Parameters for the tooltip component.
 *
 * @param text Text to display in the tooltip.
 * @param singleLine Either the text in the tooltip should be displayed in a single line or not.
 * Note that the tooltip width is limited to a maximum width depending on this parameter and the
 * text might be truncated if it exceeds the maximum width.
 * @param placement Placement of the tooltip relative to the UI trigger. Defaults to
 * [TooltipPlacement.Top].
 * @param alignment Alignment of the tooltip relative to the UI trigger. Defaults to
 * [TooltipAlignment.Center].
 */
public data class TooltipParameters(
    val text: String,
    val singleLine: Boolean = false,
    val placement: TooltipPlacement = TooltipPlacement.Top,
    val alignment: TooltipAlignment = TooltipAlignment.Center,
)
