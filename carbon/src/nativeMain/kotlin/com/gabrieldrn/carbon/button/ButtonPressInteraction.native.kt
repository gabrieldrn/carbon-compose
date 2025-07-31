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

package com.gabrieldrn.carbon.button

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.filterIsInstance

@Composable
internal actual fun rememberIsButtonPressed(
    interactionSource: InteractionSource,
    containerColorAnimatable: Animatable<*, *>,
    buttonColors: ButtonColors
): State<Boolean> {
    val containerColorState by containerColorAnimatable.asState()
    val isPressed = remember { mutableStateOf(false) }
    var isReleasedOrCancelled by remember { mutableStateOf(false) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions
            .filterIsInstance<PressInteraction>()
            .collect {
                if (it is PressInteraction.Press) {
                    isReleasedOrCancelled = false
                    isPressed.value = true
                } else {
                    isReleasedOrCancelled = true
                }
            }
    }

    LaunchedEffect(containerColorState, isReleasedOrCancelled) {
        if (containerColorState == buttonColors.containerActiveColor && isReleasedOrCancelled) {
            isPressed.value = false
        }
    }

    return isPressed
}
