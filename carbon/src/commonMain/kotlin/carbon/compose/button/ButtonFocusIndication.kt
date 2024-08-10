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

package carbon.compose.button

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.color.Theme
import carbon.compose.foundation.interaction.FocusIndication
import carbon.compose.foundation.motion.Motion
import kotlinx.coroutines.flow.filterIsInstance

internal class ButtonFocusIndication(
    private val buttonType: ButtonType
) : FocusIndication() {

    private class ButtonIndicationInstance(
        theme: Theme,
        buttonType: ButtonType
    ) : DefaultFocusIndicationInstance(theme) {

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

    @Composable
    override fun rememberUpdatedInstance(
        interactionSource: InteractionSource
    ): IndicationInstance {
        val theme = LocalCarbonTheme.current

        val instance = remember(theme) {
            ButtonIndicationInstance(theme = theme, buttonType = buttonType)
        }

        LaunchedEffect(interactionSource) {
            interactionSource.interactions
                .filterIsInstance<FocusInteraction>()
                .collect { interaction ->
                    instance.animateFocus(this, interaction)
                }
        }

        return instance
    }
}
