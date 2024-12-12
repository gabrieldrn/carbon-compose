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

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

/**
 * Enum class representing the size of a [ContentSwitcher].
 *
 * There are three height sizes for the content switcher — small (32dp), medium (40dp), and large
 * (48dp). Choose a size that best fits your layout’s density or the switcher’s prominence.
 *
 * (From [Content switcher documentation](https://carbondesignsystem.com/components/content-switcher/usage/))
 */
public expect enum class ContentSwitcherSize {

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

internal val ContentSwitcherSize.height: Dp
    get() = when (this) {
        ContentSwitcherSize.Small -> SpacingScale.spacing07
        ContentSwitcherSize.Medium -> SpacingScale.spacing08
        else -> SpacingScale.spacing09
    }

internal val ContentSwitcherSize.iconSize: Dp
    get() = if (this == ContentSwitcherSize.Large) {
        20.dp
    } else {
        SpacingScale.spacing05
    }

internal val ContentSwitcherSize.iconPadding: Dp
    get() = when (this) {
        ContentSwitcherSize.Small -> SpacingScale.spacing03
        ContentSwitcherSize.Medium -> SpacingScale.spacing04
        else -> 14.dp
    }
