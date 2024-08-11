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

package com.gabrieldrn.carbon

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.gabrieldrn.carbon.common.selectable.SelectableInteractiveState

internal class InteractiveStatePreviewParameterProvider :
    PreviewParameterProvider<SelectableInteractiveState> {
    override val values: Sequence<SelectableInteractiveState> = sequenceOf(
        SelectableInteractiveState.Default,
        SelectableInteractiveState.Disabled,
        SelectableInteractiveState.ReadOnly,
        SelectableInteractiveState.Error("Error message goes here"),
        SelectableInteractiveState.Warning("Warning message goes here")
    )
}

internal fun SelectableInteractiveState.toLabel(): String = when (this) {
    is SelectableInteractiveState.Default -> "Enabled"
    is SelectableInteractiveState.Disabled -> "Disabled"
    is SelectableInteractiveState.ReadOnly -> "Read-only"
    is SelectableInteractiveState.Error -> "Error"
    is SelectableInteractiveState.Warning -> "Warning"
}
