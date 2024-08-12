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

package com.gabrieldrn.carbon.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import com.gabrieldrn.carbon.BaseColorsTest
import com.gabrieldrn.carbon.common.selectable.SelectableInteractiveState

abstract class BaseSelectableColorsTest : BaseColorsTest() {

    protected val interactiveStates = mapOf(
        "default" to SelectableInteractiveState.Default,
        "disabled" to SelectableInteractiveState.Disabled,
        "readOnly" to SelectableInteractiveState.ReadOnly,
        "error" to SelectableInteractiveState.Error("Error"),
        "warning" to SelectableInteractiveState.Warning("Warning"),
    )

    fun Map<SelectableInteractiveState, Any>.getColor(
        interactiveState: SelectableInteractiveState,
        toggleableState: ToggleableState
    ) = this[interactiveState]!!.let {
        if (it is Map<*, *>) {
            it[toggleableState] as Color
        } else {
            it
        }
    }
}
