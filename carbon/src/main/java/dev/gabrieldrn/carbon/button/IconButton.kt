package dev.gabrieldrn.carbon.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import dev.gabrieldrn.carbon.foundation.spacing.SpacingScale

/**
 * # Icon button
 * Buttons are used to initialize an action. Button labels express what action will occur when the
 * user interacts with it.
 *
 * Icon buttons allow users to take actions, and make choices, with a single tap. Icon buttons can
 * take the form of a primary, secondary, tertiary, or ghost variant but most commonly will be
 * styled as primary or ghost buttons.
 *
 * (From [Button documentation](https://carbondesignsystem.com/components/button/usage/))
 *
 * @param iconPainter Icon painter to be displayed in the button.
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier The modifier to be applied to the button.
 * @param buttonType A [CarbonButton] that defines the button's type.
 * @param isEnabled Whether the button is enabled or disabled.
 * @param interactionSource The [MutableInteractionSource] that keeps track of the button's state.
 *
 * @see [Button]
 */
@Composable
public fun IconButton(
    iconPainter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonType: CarbonButton = CarbonButton.Primary,
    isEnabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    ButtonRowImpl(
        onClick = onClick,
        buttonType = buttonType,
        buttonSize = ButtonSize.LargeProductive,
        isEnabled = isEnabled,
        modifier = modifier,
        isIconButton = true,
        interactionSource = interactionSource,
    ) { buttonScope ->
        ButtonIcon(
            painter = iconPainter,
            colors = buttonScope.colors,
            isEnabled = isEnabled,
            interactionSource = interactionSource,
            modifier = Modifier
                .padding(start = SpacingScale.spacing05, top = SpacingScale.spacing05)
                .size(SpacingScale.spacing05)
        )
    }
}
