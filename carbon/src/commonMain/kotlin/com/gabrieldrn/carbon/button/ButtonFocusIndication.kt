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

package com.gabrieldrn.carbon.button

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.DelegatableNode
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.foundation.interaction.FocusIndication
import com.gabrieldrn.carbon.foundation.motion.Motion

internal class ButtonFocusIndication(
    private val theme: Theme,
    private val buttonType: ButtonType
) : FocusIndication(theme) {

    private class ButtonIndicationInstance(
        interactionSource: InteractionSource,
        theme: Theme,
        buttonType: ButtonType
    ) : DefaultFocusIndicationInstance(interactionSource, theme) {

        override val insetFocusColor = if (buttonType == ButtonType.Ghost) {
            Color.Transparent
        } else {
            theme.focusInset
        }

        override val focusAnimationSpec: FiniteAnimationSpec<Float> = tween(
            durationMillis = Motion.Duration.fast01,
            easing = Motion.Entrance.productiveEasing
        )
    }

    override fun create(interactionSource: InteractionSource): DelegatableNode =
        ButtonIndicationInstance(interactionSource, theme, buttonType)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ButtonFocusIndication) return false
        if (!super.equals(other)) return false

        if (theme != other.theme) return false
        if (buttonType != other.buttonType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + theme.hashCode()
        result = 31 * result + buttonType.hashCode()
        return result
    }
}
