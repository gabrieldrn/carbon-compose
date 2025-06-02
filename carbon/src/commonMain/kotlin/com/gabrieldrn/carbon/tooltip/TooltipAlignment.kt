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
 * The alignment of the tooltip relative to the UI trigger.
 *
 * From tooltip alignment
 * [documentation](https://carbondesignsystem.com/components/tooltip/usage/#alignment).
 */
public enum class TooltipAlignment {

    /**
     * Positions the tooltip container the start of the UI trigger.
     */
    Start,

    /**
     * Positions the tooltip container to the center of the UI trigger.
     */
    Center,

    /**
     * Positions the tooltip container to the end of the UI trigger.
     */
    End
}
