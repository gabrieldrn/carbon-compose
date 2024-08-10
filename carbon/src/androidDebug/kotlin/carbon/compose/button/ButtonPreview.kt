/*
 * Copyright 2024 Gabriel Derrien
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package carbon.compose.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import carbon.compose.R

private class ButtonPreviewParameterProvider :
    PreviewParameterProvider<Pair<ButtonType, ButtonSize>> {

    override val values: Sequence<Pair<ButtonType, ButtonSize>>
        get() = ButtonType.entries.flatMap { type ->
            ButtonSize.entries.map { size -> type to size }
        }.asSequence()
}

private class IconButtonPreviewParameterProvider :
    PreviewParameterProvider<ButtonType> {

    override val values: Sequence<ButtonType>
        get() = ButtonType.entries.asSequence()
}

@Preview(group = "All")
@Composable
private fun ButtonPreview(
    @PreviewParameter(ButtonPreviewParameterProvider::class)
    combination: Pair<ButtonType, ButtonSize>,
) {
    Box(modifier = Modifier.padding(8.dp)) {
        Button(
            label = "${combination.first.name} - ${combination.second::class.simpleName}",
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
    buttonType: ButtonType,
) {
    Box(modifier = Modifier.padding(8.dp)) {
        IconButton(
            onClick = {},
            buttonType = buttonType,
            iconPainter = painterResource(id = R.drawable.ic_add),
        )
    }
}
