package dev.gabrieldrn.carbon.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import dev.gabrieldrn.carbon.spacing.SpacingScale

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
