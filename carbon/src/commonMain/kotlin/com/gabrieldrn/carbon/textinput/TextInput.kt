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

package com.gabrieldrn.carbon.textinput

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.common.tooling.loremIpsum
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

internal const val TEXT_INPUT_HEIGHT_LARGE_DP = 48

/**
 * # Text input
 *
 * Text inputs enable users to enter free-form text data. The type of text field used should reflect
 * the length of the content you expect the user to enter. The default text input is for short,
 * one-line content, whereas text area is for longer, multi-line entries.
 *
 * ## Implementation note:
 * As per Carbon's documentation, the text input can have three different sizes. However, the small
 * and medium sizes does not comply with accessibility on Android, as the minimum touch target size
 * for an interactible component should be at least 48dp. This implementation then provides only the
 * large text input.
 *
 * (From [Text input documentation](https://carbondesignsystem.com/components/text-input/usage/))
 *
 * @param label Text that informs the user about the content they need to type in the field.
 * @param value The input [String] text to be shown in the text input.
 * @param onValueChange The callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param modifier Optional [Modifier] for this text input.
 * @param placeholderText Optional text that provides hints or examples of what to type.
 * @param helperText Optional helper text is pertinent information that assists the user in
 * correctly completing a field. It is often used to explain the correct data format.
 * @param state The interactive state of the text input.
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param visualTransformation The visual transformation filter for changing the visual
 * representation of the input. By default no visual transformation is applied.
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this TextField. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this TextField in different [Interaction]s.
 */
@Composable
public fun TextInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    helperText: String = "",
    state: TextInputState = TextInputState.Enabled,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val theme = Carbon.theme
    val typography = Carbon.typography
    val colors = TextInputColors.colors()

    val fieldTextColor by colors.fieldTextColor(state = state)
    val fieldTextStyle by remember(fieldTextColor) {
        mutableStateOf(typography.bodyCompact01.copy(color = fieldTextColor))
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = state != TextInputState.Disabled,
        readOnly = state == TextInputState.ReadOnly,
        textStyle = fieldTextStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        maxLines = 1,
        minLines = 1,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(colors.fieldTextColor(state = state).value),
        decorationBox = inputDecorator(
            label = label,
            value = value,
            placeholderText = placeholderText,
            helperText = helperText,
            state = state,
            theme = theme,
            colors = colors,
            singleLine = true,
            interactionSource = interactionSource,
            counter = null,
            trailingIcon = null
        )
    )
}

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
