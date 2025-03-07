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

package com.gabrieldrn.carbon.catalog.radiobutton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.common.selectable.SelectableInteractiveState
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.radiobutton.RadioButton

private val interactiveStates = listOf(
    SelectableInteractiveState.Default,
    SelectableInteractiveState.Disabled,
    SelectableInteractiveState.ReadOnly,
    SelectableInteractiveState.Error("Error message goes here"),
    SelectableInteractiveState.Warning("Warning message goes here")
)

@Composable
fun RadioButtonDemoScreen(modifier: Modifier = Modifier) {
    DemoScreen(
        demoContent = {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    SpacingScale.spacing05,
                    Alignment.CenterVertically
                )
            ) {
                var checkedButton by rememberSaveable {
                    mutableStateOf(SelectableInteractiveState.Default.toString())
                }

                interactiveStates.forEach { interactiveState ->
                    RadioButton(
                        selected = checkedButton == interactiveState.toString(),
                        label = when (interactiveState) {
                            is SelectableInteractiveState.Default -> "Default"
                            is SelectableInteractiveState.Disabled -> "Disabled"
                            is SelectableInteractiveState.ReadOnly -> "Read-only"
                            is SelectableInteractiveState.Error -> "Error"
                            is SelectableInteractiveState.Warning -> "Warning"
                        },
                        onClick = { checkedButton = interactiveState.toString() },
                        modifier = Modifier.fillMaxWidth(),
                        interactiveState = interactiveState,
                    )
                }
            }
        },
        modifier = modifier,
        displayLayerParameter = false
    )
}
