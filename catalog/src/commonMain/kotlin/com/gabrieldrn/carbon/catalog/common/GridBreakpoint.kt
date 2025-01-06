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

package com.gabrieldrn.carbon.catalog.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Implementation draft for the 2x Grid breakpoints. That could be refined to be introduced in the
// library.

val LocalGridBreakpoint = staticCompositionLocalOf<GridBreakpoint> {
    error("No GridBreakpoint provided")
}

@Suppress("MagicNumber")
enum class GridBreakpoint(
    val value: Dp,
    val columns: Int,
    val size: Dp,
    val padding: Dp,
    val margin: Dp
) {
    Small(320.dp, 4, 80.dp, 16.dp, 0.dp),
    Medium(672.dp, 8, 80.dp, 16.dp, 16.dp),
    Large(1056.dp, 16, 64.dp, 16.dp, 16.dp),
    XLarge(1312.dp, 16, 80.dp, 16.dp, 16.dp),
    Max(1584.dp, 16, 96.dp, 16.dp, 24.dp);
}

@Composable
expect fun getGridBreakpoint(): GridBreakpoint
