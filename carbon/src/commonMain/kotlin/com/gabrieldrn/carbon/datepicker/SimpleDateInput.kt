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
