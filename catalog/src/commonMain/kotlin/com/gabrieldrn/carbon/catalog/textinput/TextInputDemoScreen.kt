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

package com.gabrieldrn.carbon.catalog.textinput

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.button.Button
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.catalog.Res
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.catalog.common.loremIpsum
import com.gabrieldrn.carbon.catalog.ic_delete
import com.gabrieldrn.carbon.catalog.ic_text_long_paragraph
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.tab.TabItem
import com.gabrieldrn.carbon.textinput.PasswordInput
import com.gabrieldrn.carbon.textinput.TextArea
import com.gabrieldrn.carbon.textinput.TextInput
import com.gabrieldrn.carbon.textinput.TextInputState
import org.jetbrains.compose.resources.painterResource

private val textInputStateOptions = TextInputState.entries.toDropdownOptions()
private val textInputVariants = TextInputVariant.entries.map { TabItem(it.label) }

private enum class TextInputVariant(val label: String) {
    TextInput("Text input"),
    TextArea("Text area"),
    PasswordInput("Password input");

    companion object {
        fun fromLabel(label: String) = entries.firstOrNull { it.label == label }
    }
}

@Composable
fun TextInputDemoScreen(modifier: Modifier = Modifier) {

    var textInputState by rememberSaveable { mutableStateOf(TextInputState.Enabled) }
    var text by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    DemoScreen(
        variants = textInputVariants,
        demoContent = { variantTabItem ->
            val variant = TextInputVariant.fromLabel(variantTabItem.label)

            LaunchedEffect(variant) {
                if (variant == TextInputVariant.PasswordInput) {
                    text = ""
                }
            }

            when (variant) {
                TextInputVariant.PasswordInput -> PasswordInput(
                    label = "Password input",
                    value = text,
                    passwordHidden = passwordHidden,
                    onValueChange = { text = it },
                    onPasswordHiddenChange = { passwordHidden = it },
                    helperText = textInputState.name,
                    state = textInputState,
                )
                TextInputVariant.TextArea -> TextArea(
                    label = "Text area",
                    value = text,
                    onValueChange = { text = it },
                    placeholderText = "Placeholder",
                    helperText = textInputState.name,
                    state = textInputState,
                    maxLines = 5
                )
                else -> TextInput(
                    label = "Text input",
                    value = text,
                    onValueChange = { text = it },
                    placeholderText = "Placeholder",
                    helperText = textInputState.name,
                    state = textInputState,
                )
            }
        },
        demoParametersContent = { variantTabItem ->
            val variant = TextInputVariant.fromLabel(variantTabItem.label)

            Dropdown(
                label = "State",
                placeholder = "Choose an option",
                options = textInputStateOptions,
                selectedOption = textInputState,
                onOptionSelected = { textInputState = it },
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    label = "Lorem ipsum",
                    onClick = { text = loremIpsum },
                    iconPainter = painterResource(Res.drawable.ic_text_long_paragraph),
                    isEnabled = variant != TextInputVariant.PasswordInput,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    iconPainter = painterResource(Res.drawable.ic_delete),
                    onClick = { text = "" },
                    modifier = Modifier.padding(start = SpacingScale.spacing03)
                )
            }
        },
        modifier = modifier
    )
}
