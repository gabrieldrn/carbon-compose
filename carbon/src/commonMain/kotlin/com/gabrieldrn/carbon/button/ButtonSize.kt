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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp

internal const val BUTTON_HEIGHT_SMALL_DP = 32
internal const val BUTTON_HEIGHT_MEDIUM_DP = 40
internal const val BUTTON_HEIGHT_LARGE_PRODUCTIVE_DP = 48
internal const val BUTTON_HEIGHT_LARGE_EXPRESSIVE_DP = 48
internal const val BUTTON_HEIGHT_EXTRA_LARGE_DP = 64
internal const val BUTTON_HEIGHT_TWICE_EXTRA_LARGE_DP = 80

/**
 * Enum class representing different sizes for a button.
 * Each size has an associated height in dp.
 *
 * (From [Button documentation](https://carbondesignsystem.com/components/button/usage/))
 */
public expect enum class ButtonSize {

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

/**
 * Returns true if the button size is of [ExtraLarge] or [TwiceExtraLarge] size.
 */
internal val ButtonSize.isExtraLarge
    get() =
        this == ButtonSize.ExtraLarge || this == ButtonSize.TwiceExtraLarge

/**
 * Returns the padding values to be applied around a button of this size.
 */
internal expect fun ButtonSize.getContainerPaddings(): PaddingValues

/**
 * Returns the required height in dp for a button of this size.
 */
internal expect fun ButtonSize.heightDp(): Dp
