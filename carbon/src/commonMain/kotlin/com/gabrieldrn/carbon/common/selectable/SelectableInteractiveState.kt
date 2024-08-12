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

package com.gabrieldrn.carbon.common.selectable

/**
 * Represents the possible interactive states of a selectable.
 * Selectable component are for example a checkbox or a radio button.
 */
public sealed class SelectableInteractiveState {

    /**
     * Default state, the component is enabled and can be interacted with.
     */
    public data object Default : SelectableInteractiveState()

    /**
     * Disabled state, the component is disabled and cannot be interacted with.
     */
    public data object Disabled : SelectableInteractiveState()

    /**
     * Read-only state, the component cannot be interacted with but stays focusable.
     */
    public data object ReadOnly : SelectableInteractiveState()

    /**
     * Error state, the component is enabled and be interacted with. An error message is displayed
     * next to the component.
     * @param errorMessage The error message to display.
     */
    public data class Error(val errorMessage: String) : SelectableInteractiveState()

    /**
     * Warning state, the component is enabled and be interacted with. A warning message is
     * displayed next to the component.
     * @param warningMessage The warning message to display.
     */
    public data class Warning(val warningMessage: String) : SelectableInteractiveState()
}
