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
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

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

/**
 * Returns true if the button size is of [ButtonSize.ExtraLarge] or [ButtonSize.TwiceExtraLarge]
 * size.
 */
internal val ButtonSize.isExtraLarge
    get() =
        this == ButtonSize.ExtraLarge || this == ButtonSize.TwiceExtraLarge

/**
 * Returns the padding values to be applied around a button of this size.
 */
internal fun ButtonSize.getContainerPaddings(isIconButton: Boolean) =
    when {
        isIconButton -> PaddingValues()

        this == ButtonSize.Small -> PaddingValues(
            start = SpacingScale.spacing05,
            top = 7.dp,
            bottom = 7.dp
        )

        this == ButtonSize.Medium -> PaddingValues(
            start = SpacingScale.spacing05,
            top = 11.dp,
            bottom = 11.dp
        )

        this == ButtonSize.LargeProductive || this == ButtonSize.LargeExpressive -> PaddingValues(
            start = SpacingScale.spacing05,
            top = 14.dp,
            bottom = 14.dp
        )

        else -> PaddingValues(
            start = SpacingScale.spacing05,
            top = SpacingScale.spacing05,
            bottom = SpacingScale.spacing05
        )
    }


/**
 * Returns the required height in dp for a button of this size.
 */
internal fun ButtonSize.heightDp() =
    when (this) {
        ButtonSize.Small -> BUTTON_HEIGHT_SMALL_DP
        ButtonSize.Medium -> BUTTON_HEIGHT_MEDIUM_DP
        ButtonSize.LargeProductive -> BUTTON_HEIGHT_LARGE_PRODUCTIVE_DP
        ButtonSize.LargeExpressive -> BUTTON_HEIGHT_LARGE_EXPRESSIVE_DP
        ButtonSize.ExtraLarge -> BUTTON_HEIGHT_EXTRA_LARGE_DP
        else -> BUTTON_HEIGHT_TWICE_EXTRA_LARGE_DP
    }
        .dp
