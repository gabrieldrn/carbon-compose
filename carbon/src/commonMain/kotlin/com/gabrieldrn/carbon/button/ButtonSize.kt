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
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.foundation.SMALL_TOUCH_TARGET_SIZE_MESSAGE
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

/**
 * Enum class representing different sizes for a button.
 * Each size has an associated height in dp.
 *
 * (From [Button documentation](https://carbondesignsystem.com/components/button/usage/))
 *
 * @property height The height of the button in dp.
 */
public enum class ButtonSize(internal val height: Dp) {

    /**
     * Use when there is not enough vertical space for the default or field-sized button.
     */
    @Deprecated(
        SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        ReplaceWith("LargeProductive")
    )
    Small(height = 32.dp),

    /**
     * Use when buttons are paired with input fields.
     */
    @Deprecated(
        SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        ReplaceWith("LargeProductive")
    )
    Medium(height = 40.dp),

    /**
     * This is the most common button size.
     */
    LargeProductive(height = 48.dp),

    /**
     * The larger expressive type size within this button provides balance when used with 16sp body
     * copy. Used by the IBM.com team in website banners.
     */
    LargeExpressive(height = 48.dp),

    /**
     * Use when buttons bleed to the edge of a larger component, like side panels or modals.
     */
    ExtraLarge(height = 64.dp),

    /**
     * Use when buttons bleed to the edge of a larger component, like side panels or modals.
     */
    TwiceExtraLarge(height = 80.dp);

    public companion object {
        internal val ButtonSize.isExtraLarge get() = this == ExtraLarge || this == TwiceExtraLarge

        @Suppress("DEPRECATION")
        internal fun ButtonSize.getContainerPaddings(): PaddingValues = when (this) {
            Small -> PaddingValues(
                start = SpacingScale.spacing05,
                top = 7.dp,
                bottom = 7.dp
            )
            Medium -> PaddingValues(
                start = SpacingScale.spacing05,
                top = 11.dp,
                bottom = 11.dp
            )
            LargeProductive,
            LargeExpressive -> PaddingValues(
                start = SpacingScale.spacing05,
                top = 14.dp,
                bottom = 14.dp
            )
            ExtraLarge,
            TwiceExtraLarge -> PaddingValues(
                start = SpacingScale.spacing05,
                top = SpacingScale.spacing05,
                bottom = SpacingScale.spacing05
            )
        }
    }
}
