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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

/**
 * When using `interactionSource.collectIsPressedAsState()` for mobile targets, the animation seems
 * to be delayed and a simple tap on the button immediately cancels it, resulting in no visual
 * feedback.
 * This issue does not occur on desktop and wasm targets, so this function is directly
 * reusing `interactionSource.collectIsPressedAsState()`. However, for the mobile targets, a custom
 * implementation is provided as a temp workaround, where the state is updated only when the color
 * animation completes.
 */
@Composable
internal expect fun rememberIsButtonPressed(
    interactionSource: InteractionSource,
    containerColorAnimatable: Animatable<*, *>,
    buttonColors: ButtonColors
): State<Boolean>
