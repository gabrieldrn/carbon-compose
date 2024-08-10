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

package carbon.compose.textinput

/**
 * Represents the state of a text input.
 */
public enum class TextInputState {

    /**
     * When a text input or text area is live but a user is not directly interacting with it. This
     * is commonly referred to as the default or normal state of the component. An enabled text
     * input field can contain no content, placeholder text, or user generate content.
     */
    Enabled,

    /**
     * When you need to call the user’s attention to an exception condition. The condition might not
     * be an error but can cause problems if not resolved.
     */
    Warning,

    /**
     * When the user input is invalid or a required text input or text area has not been filled in.
     * It can also be triggered due to a system error. This state requires a user response before
     * data can be submitted or saved.
     */
    Error,

    /**
     * When the user cannot interact with a component and all interactive functions have been
     * removed. Unlike read-only states, disabled states are not focusable, are not read by screen
     * readers, and do not need to pass visual contrast, making them inaccessible if they need to be
     * interpreted.
     */
    Disabled,

    /**
     * When the user can review but not modify the component. This state removes all interactive
     * functions like the disabled state but can still be focusable, accessible by screen readers,
     * and passes visual contrast for readability.
     */
    ReadOnly;

    public companion object {

        internal val TextInputState.isFocusable: Boolean
            get() = this != Disabled
    }
}
