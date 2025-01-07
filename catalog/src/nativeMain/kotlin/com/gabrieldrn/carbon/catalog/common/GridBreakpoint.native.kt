/*
 * Copyright 2025 Gabriel Derrien
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.catalog.common.GridBreakpoint.Large
import com.gabrieldrn.carbon.catalog.common.GridBreakpoint.Max
import com.gabrieldrn.carbon.catalog.common.GridBreakpoint.Medium
import com.gabrieldrn.carbon.catalog.common.GridBreakpoint.Small
import com.gabrieldrn.carbon.catalog.common.GridBreakpoint.XLarge

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getGridBreakpoint(): GridBreakpoint {
    val density = LocalDensity.current
    val containerSize = LocalWindowInfo.current.containerSize

    return with(density) {
        when (containerSize.width.toDp()) {
            in 0.dp..<Small.value -> Small
            in Small.value..<Medium.value -> Medium
            in Medium.value..<Large.value -> Large
            in Large.value..<XLarge.value -> XLarge
            else -> Max
        }
    }
}
