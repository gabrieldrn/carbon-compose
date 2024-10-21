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

package com.gabrieldrn.carbon.catalog.theme

import com.gabrieldrn.carbon.foundation.color.Gray100Theme
import com.gabrieldrn.carbon.foundation.color.Gray10Theme
import com.gabrieldrn.carbon.foundation.color.Gray90Theme
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.foundation.color.WhiteTheme

sealed class CarbonTheme(val displayName: String, val theme: Theme) {
    sealed class LightTheme(displayName: String, theme: Theme) : CarbonTheme(displayName, theme) {
        data object W : LightTheme("White", WhiteTheme)
        data object G10 : LightTheme("Gray 10", Gray10Theme)

        companion object {
            val entries by lazy {
                listOf(W, G10)
            }
        }
    }

    sealed class DarkTheme(displayName: String, theme: Theme) : CarbonTheme(displayName, theme) {
        data object G90 : DarkTheme("Gray 90", Gray90Theme)
        data object G100 : DarkTheme("Gray 100", Gray100Theme)

        companion object {
            val entries by lazy {
                listOf(G90, G100)
            }
        }
    }

    companion object {
        val entries by lazy {
            LightTheme.entries + DarkTheme.entries
        }
    }
}
