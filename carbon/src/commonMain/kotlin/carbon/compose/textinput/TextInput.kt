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

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import carbon.compose.Carbon
import carbon.compose.foundation.color.Theme
import carbon.compose.foundation.interaction.FocusIndication
import carbon.compose.foundation.spacing.SpacingScale
import carbon.compose.foundation.text.Text
import carbon.compose.icons.WarningAltIcon
import carbon.compose.icons.WarningIcon
import carbon.compose.semantics.readOnly
import carbon.compose.textinput.TextInputState.Companion.isFocusable

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

    TextInputRoot(
        state = state,
        label = label,
        helperText = helperText,
        colors = colors,
        field = {
            TextInputField(
                value = value,
                onValueChange = onValueChange,
                placeholderText = placeholderText,
                helperText = helperText,
                state = state,
                theme = theme,
                colors = colors,
                fieldTextStyle = fieldTextStyle,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = true,
                maxLines = 1,
                minLines = 1,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                modifier = Modifier.sizeIn(maxHeight = TEXT_INPUT_HEIGHT_LARGE_DP.dp)
            )
        },
        modifier = modifier
    )
}

@Composable
internal fun TextInputRoot(
    state: TextInputState,
    label: String,
    helperText: String,
    colors: TextInputColors,
    field: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    counter: Pair<Int, Int>? = null,
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(bottom = SpacingScale.spacing03)) {
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

        field()

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
internal fun TextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    helperText: String,
    state: TextInputState,
    theme: Theme,
    colors: TextInputColors,
    fieldTextStyle: TextStyle,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    singleLine: Boolean,
    maxLines: Int,
    minLines: Int,
    visualTransformation: VisualTransformation,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .indication(
                interactionSource = interactionSource,
                indication = FocusIndication()
            )
            .focusable(
                enabled = state.isFocusable,
                interactionSource = interactionSource
            )
            .background(color = colors.fieldBackgroundColor(state = state).value)
            .then(
                if (state == TextInputState.Error) {
                    Modifier.border(
                        width = SpacingScale.spacing01,
                        color = theme.supportError
                    )
                } else {
                    Modifier
                }
            )
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
            .semantics(mergeDescendants = true) {
                stateDescription = helperText
            }
            .testTag(TextInputTestTags.FIELD)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.align(Alignment.CenterStart),
            enabled = state != TextInputState.Disabled,
            readOnly = state == TextInputState.ReadOnly,
            textStyle = fieldTextStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(colors.fieldTextColor(state = state).value),
            decorationBox = { innerTextField ->
                FieldContent(
                    singleLine = singleLine,
                    value = value,
                    placeholderText = placeholderText,
                    colors = colors,
                    innerTextField = innerTextField,
                    state = state,
                    trailingIcon = trailingIcon,
                )
            }
        )

        if (state != TextInputState.Disabled) {
            Box(
                modifier = Modifier
                    .background(color = colors.fieldBorderColor(state = state).value)
                    .height(1.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .testTag(TextInputTestTags.FIELD_BORDER)
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
            .padding(
                start = SpacingScale.spacing05,
                end = SpacingScale.spacing05.takeIf { trailingIcon == null } ?: 0.dp
            )
            .padding(
                vertical = 11.dp.takeIf { !singleLine } ?: 0.dp
            )
            .then(
                if (singleLine) {
                    Modifier.fillMaxSize()
                } else {
                    Modifier.fillMaxWidth()
                }
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
        TextInputState.Error -> WarningIcon(
            modifier = modifier.testTag(TextInputTestTags.STATE_ICON_ERROR)
        )
        TextInputState.Warning -> WarningAltIcon(
            modifier = modifier.testTag(TextInputTestTags.STATE_ICON_WARNING)
        )
        else -> {}
    }
}
