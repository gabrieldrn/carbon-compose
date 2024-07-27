package carbon.compose.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import carbon.compose.button.ButtonSize.Companion.isExtraLarge
import carbon.compose.foundation.spacing.SpacingScale

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
 * @param buttonType A [ButtonType] that defines the button's type.
 * @param buttonSize A [ButtonSize] that defines the button's size.
 * @param interactionSource The [MutableInteractionSource] that keeps track of the button's state.
 */
// TODO Support system font scale?
// FIXME This is currently compiled as restartable but not skippable because of the use of Painter.
//  Check recomposition count with current implementation and replace Painter usage if necessary.
@Composable
public fun Button(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconPainter: Painter? = null,
    isEnabled: Boolean = true,
    buttonType: ButtonType = ButtonType.Primary,
    buttonSize: ButtonSize = ButtonSize.LargeProductive,
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
            modifier = Modifier
                .weight(weight = 1f)
                .fillMaxHeight()
                .padding(end = SpacingScale.spacing05)
                .testTag("carbon_button_label")
        )

        if (iconPainter != null) {
            ButtonIcon(
                painter = iconPainter,
                colors = buttonScope.colors,
                isEnabled = isEnabled,
                interactionSource = interactionSource,
                modifier = if (buttonSize.isExtraLarge) {
                    Modifier.requiredSize(16.dp)
                } else {
                    Modifier
                        .requiredWidth(16.dp)
                        .fillMaxHeight()
                }.testTag("carbon_button_icon")
            )
            Spacer(modifier = Modifier.width(SpacingScale.spacing05))
        }
    }
}
