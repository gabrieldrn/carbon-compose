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
