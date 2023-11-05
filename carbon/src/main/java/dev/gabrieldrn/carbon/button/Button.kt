package dev.gabrieldrn.carbon.button

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import dev.gabrieldrn.carbon.foundation.motion.Motion
import dev.gabrieldrn.carbon.foundation.spacing.SpacingScale

// From the react-native implementation
internal const val ButtonTransitionDurationMillis = 70
internal val buttonTransitionSpec: AnimationSpec<Color> = tween(
    durationMillis = ButtonTransitionDurationMillis,
    easing = Motion.Entrance.productiveEasing
)

/**
 * TODO Documentation.
 *
 */
// TODO Support system font scale?
@Composable
public fun Button(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconPainter: Painter? = null,
    isEnabled: Boolean = true,
    buttonType: CarbonButton = CarbonButton.Primary,
    buttonSize: ButtonSize = ButtonSize.Medium,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    ButtonRowImpl(
        onClick = onClick,
        buttonType = buttonType,
        buttonSize = buttonSize,
        isEnabled = isEnabled,
        modifier = modifier,
        interactionSource = interactionSource,
    ) { buttonScope ->
        Label(
            isEnabled = isEnabled,
            colors = buttonScope.colors,
            interactionSource = interactionSource,
            label = label,
            modifier = Modifier.weight(1f)
        )
        if (iconPainter != null) {
            ButtonIcon(
                painter = iconPainter,
                colors = buttonScope.colors,
                isEnabled = isEnabled,
                interactionSource = interactionSource,
                modifier = if (buttonSize.isExtraLarge) {
                    Modifier.size(16.dp)
                } else {
                    Modifier
                        .width(16.dp)
                        .fillMaxHeight()
                }
            )
            Spacer(modifier = Modifier.width(SpacingScale.spacing05))
        }
    }
}
