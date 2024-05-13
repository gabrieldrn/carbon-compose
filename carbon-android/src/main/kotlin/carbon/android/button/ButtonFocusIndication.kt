package carbon.android.button

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import carbon.android.foundation.color.LocalCarbonTheme
import carbon.android.foundation.color.Theme
import carbon.android.foundation.interaction.FocusIndication
import carbon.android.foundation.motion.Motion
import kotlinx.coroutines.flow.filterIsInstance

internal class ButtonFocusIndication(
    private val buttonType: CarbonButton
) : FocusIndication() {

    private class ButtonIndicationInstance(
        theme: Theme,
        buttonType: CarbonButton
    ) : DefaultFocusIndicationInstance(theme) {

        override val insetFocusColor = if (buttonType == CarbonButton.Ghost) {
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
