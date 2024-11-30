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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.color.Theme

@Immutable
internal class ContentSwitcherColors(
    private val theme: Theme
) {
    @Composable
    fun containerColor(isSelected: Boolean): State<Color> =
        rememberUpdatedState(
            newValue = if (isSelected) theme.layerSelectedInverse else Color.Transparent
        )

    @Composable
    fun labelColor(isSelected: Boolean): State<Color> =
        rememberUpdatedState(
            newValue = if (isSelected) theme.textInverse else theme.textSecondary
        )

    companion object {
        @Composable
        fun colors() = ContentSwitcherColors(theme = Carbon.theme)
    }
}
