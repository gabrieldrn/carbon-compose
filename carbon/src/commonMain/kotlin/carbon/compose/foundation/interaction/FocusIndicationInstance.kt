package carbon.compose.foundation.interaction

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.color.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
internal abstract class FocusIndicationInstance(theme: Theme) : IndicationInstance {

    protected val borderFocusWidth = 2f.dp
    protected val insetFocusWidth = 1f.dp

    protected open val borderFocusColor = theme.focus
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

    fun animateFocus(scope: CoroutineScope, interaction: FocusInteraction) {
        scope.launch {
            focusAnimation.animateTo(
                targetValue = if (interaction is FocusInteraction.Focus) 1f else 0f,
                animationSpec = focusAnimationSpec
            )
        }
    }
}
