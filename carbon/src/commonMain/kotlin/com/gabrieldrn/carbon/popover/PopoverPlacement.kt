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

package com.gabrieldrn.carbon.popover

/**
 * The placement of the popover relative to the UI trigger, regardless if the popover has a caret
 * tip or not. However, when using a caret tip, the trigger button and caret tip will be vertically
 * centered with each other.
 *
 * From popover placement
 * [documentation 1](https://carbondesignsystem.com/components/popover/usage/#no-tip),
 * [documentation 2](https://carbondesignsystem.com/components/popover/usage/#caret-tip)
 */
public enum class PopoverPlacement {

    // Auto: Requires a significant amount of work to implement. Impossible to implement with
    // Compose's popup (check Tooltip component KDoc).

    /**
     * Places the popover to the right of the UI trigger.
     */
    Right,

    /**
     * Places the popover to the left of the UI trigger.
     */
    Left,

    /**
     * Places the popover below the UI trigger.
     */
    Bottom,

    /**
     * Places the popover above the UI trigger.
     */
    Top
}
