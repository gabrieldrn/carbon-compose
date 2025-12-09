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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.common.semantics.imageVectorName
import com.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.icons.viewIcon
import com.gabrieldrn.carbon.icons.viewOffIcon
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

/**
 * A modified [KeyboardOptions.Default] with autoCorrect set to false and keyboardType set to
 * [KeyboardType.Password].
 */
public val PasswordKeyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
    autoCorrectEnabled = false,
    keyboardType = KeyboardType.Password
)

/**
 * # Password input
 *
 * Password input is a sub-variant of text input. It is used to collect private data and will hide
 * the characters as a user enters them. A user can choose to toggle on the character visibility by
 * clicking the view icon on the far right of the input field. When using a password input be sure
 * to provide detailed helper text listing any requirements related to the data format, such as
 * types of characters allowed or date structure.
 *
 * (From [Password input documentation](https://carbondesignsystem.com/components/text-input/usage/#password-input))
 *
 * @param label Text that informs the user about the content they need to enter in the field.
 * @param value The input [String] text to be shown in the text input.
 * @param passwordHidden Whether the password should be hidden or not.
 * @param onValueChange The callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback.
 * @param onPasswordHiddenChange Callback that is triggered when the user requests to hide the
 * password.
 * @param modifier Optional [Modifier] for this text input.
 * @param placeholderText Optionnal text that provides hints or examples of what to enter.
 * @param helperText Optional helper text is pertinent information that assists the user in
 * correctly completing a field. It is often used to explain the correct data format.
 * @param state The interactive state of the text input.
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction]. Defaults to [PasswordKeyboardOptions].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this TextField. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this TextField in different [Interaction]s.
 */
@Composable
public fun PasswordInput(
    label: String,
    value: String,
    passwordHidden: Boolean,
    onValueChange: (String) -> Unit,
    onPasswordHiddenChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    helperText: String = "",
    state: TextInputState = TextInputState.Enabled,
    keyboardOptions: KeyboardOptions = PasswordKeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val theme = Carbon.theme
    val typography = Carbon.typography
    val colors = TextInputColors.colors()

    val passwordHiddenIcon = remember { viewIcon }
    val passwordVisibleIcon = remember { viewOffIcon }
    val icon = remember(passwordHidden) {
        if (passwordHidden) {
            passwordHiddenIcon
        } else {
            passwordVisibleIcon
        }
    }

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
        visualTransformation = if (passwordHidden) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
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
            trailingIcon = {
                HidePasswordButton(
                    theme = theme,
                    passwordHidden = passwordHidden,
                    icon = icon,
                    state = state,
                    onPasswordHiddenChange = onPasswordHiddenChange
                )
            }
        )
    )
}

@Composable
private fun HidePasswordButton(
    theme: Theme,
    passwordHidden: Boolean,
    icon: ImageVector,
    state: TextInputState,
    onPasswordHiddenChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val hidePasswordButtonTheme by remember(theme) {
        mutableStateOf(
            value = theme.copy(
                linkPrimary = theme.iconPrimary,
                linkPrimaryHover = theme.iconPrimary
            ),
            policy = referentialEqualityPolicy()
        )
    }

    CompositionLocalProvider(LocalCarbonTheme provides hidePasswordButtonTheme) {
        IconButton(
            iconPainter = rememberVectorPainter(icon),
            buttonType = ButtonType.Ghost,
            isEnabled = state != TextInputState.Disabled,
            onClick = { onPasswordHiddenChange(!passwordHidden) },
            modifier = modifier
                .semantics { imageVectorName(icon.name) }
                .testTag(TextInputTestTags.HIDE_PASSWORD_BUTTON)
        )
    }
}

@Preview
@Composable
private fun PasswordInputPreview(
    @PreviewParameter(TextInputStatePreviewParameterProvider::class) state: TextInputState
) {
    var text by remember {
        mutableStateOf("S0mePa55word%")
    }
    var passwordHidden by remember {
        mutableStateOf(false)
    }

    CarbonDesignSystem {
        PasswordInput(
            label = "Label",
            value = text,
            passwordHidden = passwordHidden,
            onValueChange = { text = it },
            onPasswordHiddenChange = { passwordHidden = it },
            modifier = Modifier.padding(SpacingScale.spacing03),
            placeholderText = "Placeholder",
            helperText = state.name,
            state = state,
        )
    }
}
