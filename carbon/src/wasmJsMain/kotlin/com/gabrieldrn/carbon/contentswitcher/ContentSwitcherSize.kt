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

package com.gabrieldrn.carbon.contentswitcher

/**
 * Enum class representing the size of a [ContentSwitcher].
 *
 * There are three height sizes for the content switcher — small (32px), medium (40px), and large
 * (48px). Choose a size that best fits your layout’s density or the switcher’s prominence.
 *
 * (From [Content switcher documentation](https://carbondesignsystem.com/components/content-switcher/usage/))
 */
public actual enum class ContentSwitcherSize {
    /**
     * 32dp height size for the content switcher.
     */
    Small,

    /**
     * 40dp height size for the content switcher.
     */
    Medium,

    /**
     * 48dp height size for the content switcher.
     */
    Large
}
