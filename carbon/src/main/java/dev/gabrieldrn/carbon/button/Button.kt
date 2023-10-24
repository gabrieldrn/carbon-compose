package dev.gabrieldrn.carbon.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.gabrieldrn.carbon.color.LocalCarbonTheme
import dev.gabrieldrn.carbon.text.CarbonTypography
import dev.gabrieldrn.carbon.text.Text

/**
 * TODO Documentation
 * TODO Support system font scale?
 */
@Composable
public fun Button(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonType: CarbonButton = CarbonButton.Primary,
    isEnabled: Boolean = true,
    buttonSize: ButtonSize = ButtonSize.Medium,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    // TODO Optimize this to use only the colors that are needed
    val theme = LocalCarbonTheme.current

    val labelTextColor by remember {
        derivedStateOf {
            when {
                !isEnabled -> theme.textOnColorDisabled
                else -> theme.textOnColor
            }
        }
    }

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = ButtonIndication(buttonType),
                onClick = onClick,
            )
            .focusable(interactionSource = interactionSource)
            .hoverable(interactionSource = interactionSource)
            .height(buttonSize.height),
    ) {
        Text(
            text = label,
            color = labelTextColor,
            style = CarbonTypography.bodyCompact01,
            modifier = Modifier
                .padding(buttonSize.getLabelPaddings())
                .fillMaxHeight()
        )
    }
}

private class ButtonSizePreviewParameterProvider : PreviewParameterProvider<ButtonSize> {

    override val values: Sequence<ButtonSize>
        get() = ButtonSize.entries.asSequence()
}

@Preview(group = "Primary")
@Composable
private fun PrimaryButtonPreview(
    @PreviewParameter(ButtonSizePreviewParameterProvider::class) size: ButtonSize,
) {
    Box(modifier = Modifier.padding(8.dp)) {
        Button(
            label = "Primary Button",
            onClick = {},
            buttonSize = size,
        )
    }
}

@Preview(group = "Secondary")
@Composable
private fun SecondaryButtonPreview(
    @PreviewParameter(ButtonSizePreviewParameterProvider::class) size: ButtonSize,
) {
    Box(modifier = Modifier.padding(8.dp)) {
        Button(
            label = "Primary Button",
            onClick = {},
            buttonSize = size,
            buttonType = CarbonButton.Secondary,
        )
    }
}
