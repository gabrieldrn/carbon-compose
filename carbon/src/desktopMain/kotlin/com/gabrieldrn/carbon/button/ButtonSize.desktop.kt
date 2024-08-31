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

package com.gabrieldrn.carbon.button

/**
 * Enum class representing different sizes for a button.
 * Each size has an associated height in dp.
 *
 * (From [Button documentation](https://carbondesignsystem.com/components/button/usage/))
 */
public actual enum class ButtonSize {

    /**
     * Use when there is not enough vertical space for the default or field-sized button.
     */
    Small,

    /**
     * Use when buttons are paired with input fields.
     */
    Medium,

    /**
     * This is the most common button size.
     */
    LargeProductive,

    /**
     * The larger expressive type size within this button provides balance when used with 16sp body
     * copy. Used by the IBM.com team in website banners.
     */
    LargeExpressive,

    /**
     * Use when buttons bleed to the edge of a larger component, like side panels or modals.
     */
    ExtraLarge,

    /**
     * Use when buttons bleed to the edge of a larger component, like side panels or modals.
     */
    TwiceExtraLarge;
}
