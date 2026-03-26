/*
 * Copyright 2026 Gabriel Derrien
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

package com.gabrieldrn.carbon.datepicker

import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.common.semantics.readOnly
import com.gabrieldrn.carbon.textinput.TextInputColors
import com.gabrieldrn.carbon.textinput.TextInputState
import com.gabrieldrn.carbon.textinput.inputDecorator

/**
 * # Date picker - Simple
 *
 * Simple date inputs allow users to type a specific date directly into a text field. Unlike the
 * calendar date picker, this variant has no calendar menu popup. The placeholder text should
 * indicate the expected date format to guide the user.
 *
 * ## Compose implementation
 * The component delegates date parsing and formatting to [SimpleDateInputState], which can be
 * created with [rememberSimpleDateInputState]. Use the [inputState] parameter to reflect
 * validation results (e.g. [com.gabrieldrn.carbon.textinput.TextInputState.Error]) after
 * checking the validity callback passed to [rememberSimpleDateInputState].
 *
 * (From [Date picker documentation](https://carbondesignsystem.com/components/date-picker/usage/#simple-date-input))
 *
 * @param state A [SimpleDateInputState] that controls this component, holding the currently
 * selected date and the logic to parse typed input.
 * @param label Text that informs the user about the content they need to type in the field.
 * @param value The input [String] text to be shown in the text field.
 * @param onValueChange Callback triggered when the input service updates the text. The updated
 * text is passed as a parameter.
 * @param modifier Optional [Modifier] for this component.
 * @param placeholderText Optional hint text displayed when the field is empty, typically used to
 * convey the expected date format (e.g. `"mm/dd/yyyy"`).
 * @param helperText Optional helper text displayed below the field to assist the user in entering
 * the correct date format.
 * @param inputState The interactive state of the text field, such as
 * [com.gabrieldrn.carbon.textinput.TextInputState.Enabled],
 * [com.gabrieldrn.carbon.textinput.TextInputState.Error], or
 * [com.gabrieldrn.carbon.textinput.TextInputState.Disabled].
 * @param keyboardOptions Software keyboard options that contain configuration such as
 * [androidx.compose.ui.text.input.KeyboardType] and [androidx.compose.ui.text.input.ImeAction].
 * @param keyboardActions When the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param interactionSource The [MutableInteractionSource] representing the stream of
 * [androidx.compose.foundation.interaction.Interaction]s for this text field. You can create and
 * pass in your own remembered [MutableInteractionSource] if you want to observe interactions and
 * customize the appearance or behavior of this field in different interaction states.
 */
@Composable
public fun SimpleDateInput(
    state: SimpleDateInputState,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    helperText: String = "",
    inputState: TextInputState = TextInputState.Enabled,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val theme = Carbon.theme
    val typography = Carbon.typography
    val colors = TextInputColors.colors()

    state.updateFieldCallback = onValueChange

    val fieldTextColor by colors.fieldTextColor(state = inputState)
    val fieldTextStyle by remember(fieldTextColor) {
        mutableStateOf(typography.bodyCompact01.copy(color = fieldTextColor))
    }

    BasicTextField(
        value = value,
        onValueChange = state::updateFieldValue,
        modifier = modifier
            .then(
                when (inputState) {
                    TextInputState.Disabled -> Modifier.semantics {
                        role = Role.ValuePicker
                        disabled()
                    }
                    TextInputState.ReadOnly -> Modifier.readOnly(
                        role = Role.ValuePicker,
                        interactionSource = interactionSource,
                        mergeDescendants = true
                    )
                    else -> Modifier
                }
            ),
        enabled = inputState != TextInputState.Disabled,
        readOnly = inputState == TextInputState.ReadOnly,
        textStyle = fieldTextStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        maxLines = 1,
        minLines = 1,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(colors.fieldTextColor(state = inputState).value),
        decorationBox = inputDecorator(
            label = label,
            value = value,
            placeholderText = placeholderText,
            helperText = helperText,
            state = inputState,
            theme = theme,
            colors = colors,
            singleLine = true,
            interactionSource = interactionSource,
            counter = null,
            trailingIcon = null,
        )
    )
}

// region Previews

@Preview
@Composable
private fun SimpleDateInputPreview() {
    CarbonDesignSystem {
        var value by remember { mutableStateOf("") }

        SimpleDateInput(
            state = rememberSimpleDateInputState(),
            label = "Date input",
            placeholderText = "mm/yyyy",
            value = value,
            onValueChange = { value = it }
        )
    }
}

// endregion
