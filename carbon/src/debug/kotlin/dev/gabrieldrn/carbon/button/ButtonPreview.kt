package dev.gabrieldrn.carbon.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.gabrieldrn.carbon.R

private class ButtonPreviewParameterProvider :
    PreviewParameterProvider<Pair<CarbonButton, ButtonSize>> {

    override val values: Sequence<Pair<CarbonButton, ButtonSize>>
        get() = CarbonButton.entries.flatMap { type ->
            ButtonSize.entries.map { size -> type to size }
        }.asSequence()
}

private class IconButtonPreviewParameterProvider :
    PreviewParameterProvider<CarbonButton> {

    override val values: Sequence<CarbonButton>
        get() = CarbonButton.entries.asSequence()
}

@Preview(group = "All")
@Composable
private fun ButtonPreview(
    @PreviewParameter(ButtonPreviewParameterProvider::class)
    combination: Pair<CarbonButton, ButtonSize>,
) {
    Box(modifier = Modifier.padding(8.dp)) {
        Button(
            label = "${combination.first.name} - ${combination.second.name}",
            onClick = {},
            buttonType = combination.first,
            buttonSize = combination.second,
            iconPainter = painterResource(id = R.drawable.ic_add),
        )
    }
}

@Preview(group = "Icon button")
@Composable
private fun IconButtonPreview(
    @PreviewParameter(IconButtonPreviewParameterProvider::class)
    buttonType: CarbonButton,
) {
    Box(modifier = Modifier.padding(8.dp)) {
        IconButton(
            onClick = {},
            buttonType = buttonType,
            iconPainter = painterResource(id = R.drawable.ic_add),
        )
    }
}