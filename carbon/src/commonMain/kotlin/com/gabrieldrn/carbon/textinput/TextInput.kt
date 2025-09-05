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

import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.common.semantics.readOnly
import com.gabrieldrn.carbon.common.tooling.loremIpsum
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.foundation.interaction.FocusIndication
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text
import com.gabrieldrn.carbon.icons.WarningAltFilledIcon
import com.gabrieldrn.carbon.icons.WarningFilledIcon
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
 * @param label Text that informs the user about the content they need to enter in the field.
 * @param value The input [String] text to be shown in the text input.
 * @param onValueChange The callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param modifier Optional [Modifier] for this text input.
 * @param placeholderText Optionnal text that provides hints or examples of what to enter.
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
        decorationBox = decorator(
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

internal fun Modifier.fieldBackground(
    state: TextInputState,
    colors: TextInputColors,
) = this then composed {
    val backgroundColor by colors.fieldBackgroundColor(state = state)
    val borderColor by colors.fieldBorderColor(state = state)
    drawBehind {
        drawRect(backgroundColor)
        drawRect(borderColor, topLeft = Offset.Zero.copy(y = size.height - 1.dp.toPx()))
    }
}

internal fun Modifier.errorOutline(
    theme: Theme,
    state: TextInputState
) = this then drawBehind {
    if (state == TextInputState.Error) {
        val outlineWidth = SpacingScale.spacing01.toPx()
        val outlineHalfWidth = outlineWidth * .5f
        drawRect(
            color = theme.supportError,
            style = Stroke(outlineWidth),
            topLeft = Offset(outlineHalfWidth, outlineHalfWidth),
            size = Size(
                width = size.width - outlineWidth,
                height = size.height - outlineWidth
            )
        )
    }
}

@Composable
internal fun decorator(
    label: String,
    value: String,
    placeholderText: String,
    helperText: String,
    state: TextInputState,
    theme: Theme,
    colors: TextInputColors,
    singleLine: Boolean,
    interactionSource: MutableInteractionSource,
    counter: Pair<Int, Int>?,
    trailingIcon: @Composable (() -> Unit)?
): @Composable (@Composable () -> Unit) -> Unit = { innerTextField ->
    Column(
        modifier = Modifier.semantics(mergeDescendants = true) {
            stateDescription = helperText
        }
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = SpacingScale.spacing03)
        ) {
            Text(
                text = label,
                style = Carbon.typography.label01,
                color = colors.labelTextColor(state = state).value,
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .testTag(TextInputTestTags.LABEL)
            )

            if (counter != null) {
                Text(
                    text = "${counter.first}/${counter.second}",
                    style = Carbon.typography.label01,
                    color = colors.labelTextColor(state = state).value,
                    maxLines = 1,
                    modifier = Modifier.testTag(TextInputTestTags.COUNTER)
                )
            }
        }

        FieldContent(
            singleLine = singleLine,
            value = value,
            placeholderText = placeholderText,
            colors = colors,
            innerTextField = innerTextField,
            state = state,
            trailingIcon = trailingIcon,
            modifier = Modifier
                .indication(
                    interactionSource = interactionSource,
                    indication = FocusIndication(Carbon.theme)
                )
                .fieldBackground(state, colors)
                .errorOutline(theme, state)
                .then(
                    when (state) {
                        TextInputState.Disabled -> Modifier.semantics { disabled() }
                        TextInputState.ReadOnly -> Modifier.readOnly(
                            role = null,
                            interactionSource = interactionSource,
                            mergeDescendants = true
                        )
                        else -> Modifier
                    }
                )
                .testTag(TextInputTestTags.FIELD)
        )

        if (helperText.isNotEmpty()) {
            Text(
                text = helperText,
                style = Carbon.typography.helperText01,
                color = colors.helperTextColor(state = state).value,
                modifier = Modifier
                    .padding(top = SpacingScale.spacing02)
                    .testTag(TextInputTestTags.HELPER_TEXT)
            )
        }
    }
}

@Composable
private fun FieldContent(
    singleLine: Boolean,
    value: String,
    placeholderText: String,
    colors: TextInputColors,
    state: TextInputState,
    innerTextField: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .sizeIn(minHeight = TEXT_INPUT_HEIGHT_LARGE_DP.dp)
            .padding(
                start = SpacingScale.spacing05,
                end = SpacingScale.spacing05.takeIf { trailingIcon == null } ?: 0.dp
            )
            .padding(
                vertical = 11.dp.takeIf { !singleLine } ?: 0.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            innerTextField()

            if (value.isEmpty()) {
                Text(
                    text = placeholderText,
                    style = Carbon.typography.bodyCompact01,
                    color = colors.placeholderTextColor(state = state).value,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag(TextInputTestTags.PLACEHOLDER)
                )
            }
        }

        StateIcon(
            state = state,
            modifier = Modifier
                .then(
                    if (singleLine) {
                        Modifier.padding(start = SpacingScale.spacing05)
                    } else {
                        Modifier.align(Alignment.Top)
                    }
                )
        )

        trailingIcon?.invoke()
    }
}

@Composable
private fun StateIcon(
    state: TextInputState,
    modifier: Modifier = Modifier
) {
    when (state) {
        TextInputState.Error -> WarningFilledIcon(
            modifier = modifier.testTag(TextInputTestTags.STATE_ICON_ERROR),
            tint = Carbon.theme.supportError
        )
        TextInputState.Warning -> WarningAltFilledIcon(
            modifier = modifier.testTag(TextInputTestTags.STATE_ICON_WARNING),
            tint = Carbon.theme.supportWarning,
            innerTint = Color.Black
        )
        else -> {}
    }
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
