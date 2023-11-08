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
 * # Button
 * Buttons are used to initialize an action. Button labels express what action will occur when the
 * user interacts with it.
 *
 * ## Overview
 * Buttons are clickable elements that are used to trigger actions. They communicate calls to action
 * to the user and allow users to interact with pages in a variety of ways. Button labels express
 * what action will occur when the user interacts with it.
 *
 * ### When to use
 * Use buttons to communicate actions users can take and to allow users to interact with the page.
 * Each page should have only one primary button, and any remaining calls to action should be
 * represented as lower emphasis buttons.
 *
 * ### When not to use
 * Do not use buttons as navigational elements. Instead, use links when the desired action is to
 * take the user to a new page.
 *
 * (From [Button documentation](https://carbondesignsystem.com/components/button/usage/))
 *
 * @param label The text to be displayed in the button.
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier The modifier to be applied to the button.
 * @param iconPainter Icon painter to be displayed in the button.
 * @param isEnabled Whether the button is enabled or disabled.
 * @param buttonType A [CarbonButton] that defines the button's type.
 * @param buttonSize A [ButtonSize] that defines the button's size.
 * @param interactionSource The [MutableInteractionSource] that keeps track of the button's state.
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
