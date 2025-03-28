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

package com.gabrieldrn.carbon.foundation.interaction

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.foundation.color.Theme
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@Stable
internal abstract class FocusIndicationInstance(
    protected val interactionSource: InteractionSource,
    theme: Theme,
    useInverseColor: Boolean = false
) : Modifier.Node(), DrawModifierNode {

    protected val borderFocusWidth = 2f.dp
    protected val insetFocusWidth = 1f.dp

    protected open val borderFocusColor = if (useInverseColor) theme.focusInverse else theme.focus
    protected open val insetFocusColor = Color.Transparent

    protected val focusAnimation = Animatable(0f)

    abstract val focusAnimationSpec: FiniteAnimationSpec<Float>

    protected val borderFocusColorState = derivedStateOf(referentialEqualityPolicy()) {
        // Strangely, when the focus border is multiplied by 0, there is still a border
        // visible. This is why the border and the inset are set to transparent when the focus
        // exits.
        if (focusAnimation.value == 0f) {
            Color.Transparent
        } else {
            borderFocusColor
        }
    }

    protected val insetFocusColorState = derivedStateOf(referentialEqualityPolicy()) {
        if (focusAnimation.value == 0f) {
            Color.Transparent
        } else {
            insetFocusColor
        }
    }

    private suspend fun animateFocus(interaction: FocusInteraction) {
        focusAnimation.animateTo(
            targetValue = if (interaction is FocusInteraction.Focus) 1f else 0f,
            animationSpec = focusAnimationSpec
        )
    }

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.filterIsInstance<FocusInteraction>()
                .collect { interaction ->
                    animateFocus(interaction)
                }
        }
    }
}
