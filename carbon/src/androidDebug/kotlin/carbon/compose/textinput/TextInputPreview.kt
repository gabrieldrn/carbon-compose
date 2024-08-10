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

package carbon.compose.textinput

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import carbon.compose.CarbonDesignSystem
import carbon.compose.foundation.spacing.SpacingScale

internal val loremIpsum =
    """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut 
        labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco 
        laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in 
        voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat 
        cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum
    """.trimIndent().replace("\n","")

internal class TextInputStatePreviewParameterProvider : PreviewParameterProvider<TextInputState> {
    override val values: Sequence<TextInputState>
        get() = TextInputState.entries.asSequence()
}

@Preview
@Composable
private fun EmptyTextInputPreview(
) {
    var text by remember {
        mutableStateOf("")
    }

    CarbonDesignSystem {
        TextInput(
            label = "Label",
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.padding(SpacingScale.spacing03),
            placeholderText = "Placeholder",
        )
    }
}

@Preview
@Composable
private fun TextInputPreview(
    @PreviewParameter(TextInputStatePreviewParameterProvider::class) state: TextInputState
) {
    var text by remember {
        mutableStateOf(loremIpsum)
    }

    CarbonDesignSystem {
        TextInput(
            label = "Label",
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.padding(SpacingScale.spacing03),
            placeholderText = "Placeholder",
            helperText = state.name,
            state = state,
        )
    }
}
