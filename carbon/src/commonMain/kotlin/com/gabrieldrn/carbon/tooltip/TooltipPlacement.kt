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
 * The placement of the tooltip relative to the UI trigger.
 *
 * From tooltip placement
 * [documentation](https://carbondesignsystem.com/components/tooltip/usage/#placement).
 */
public enum class TooltipPlacement {

    /**
     * Automatic placement of the tooltip based on available space.
     */
    Auto,

    /**
     * Places the tooltip to the right of the UI trigger.
     */
    Right,

    /**
     * Places the tooltip to the left of the UI trigger.
     */
    Left,

    /**
     * Places the tooltip below the UI trigger.
     */
    Bottom,

    /**
     * Places the tooltip above the UI trigger.
     */
    Top
}
