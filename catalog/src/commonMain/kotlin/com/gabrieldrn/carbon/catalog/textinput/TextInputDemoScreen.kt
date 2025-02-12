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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.button.Button
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.catalog.Res
import com.gabrieldrn.carbon.catalog.common.LayerSelectionDropdown
import com.gabrieldrn.carbon.catalog.ic_delete
import com.gabrieldrn.carbon.catalog.ic_text_long_paragraph
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.dropdownOptionsOf
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.foundation.color.CarbonLayer
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.containerBackground
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.textinput.PasswordInput
import com.gabrieldrn.carbon.textinput.TextArea
import com.gabrieldrn.carbon.textinput.TextInput
import com.gabrieldrn.carbon.textinput.TextInputState
import org.jetbrains.compose.resources.painterResource

private const val TEXT_INPUT_VARIANT = "Text input (single line)"
private const val TEXT_AREA_VARIANT = "Text area"
private const val PASSWORD_INPUT_VARIANT = "Password area"
private val textInputStateOptions = TextInputState.entries.toDropdownOptions()
private val textInputVariantOptions = dropdownOptionsOf(
    TEXT_INPUT_VARIANT, TEXT_AREA_VARIANT, PASSWORD_INPUT_VARIANT
)

private val loremIpsum =
    """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut 
        labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco 
        laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in 
        voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat 
        cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum
    """.trimIndent().replace("\n", "")

@Composable
fun TextInputDemoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .containerBackground()
            .verticalScroll(state = rememberScrollState())
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        var layer by rememberSaveable { mutableStateOf(Layer.Layer00) }
        var textInputState by rememberSaveable { mutableStateOf(TextInputState.Enabled) }
        var variant by rememberSaveable { mutableStateOf(TEXT_INPUT_VARIANT) }
        var text by rememberSaveable { mutableStateOf("") }
        var passwordHidden by rememberSaveable { mutableStateOf(true) }

        LaunchedEffect(variant) {
            if (variant == PASSWORD_INPUT_VARIANT) {
                text = ""
            }
        }

        CarbonLayer(layer = layer) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .containerBackground()
                    .padding(SpacingScale.spacing05)
            ) {
                when (variant) {
                    TEXT_INPUT_VARIANT -> TextInput(
                        label = "Text input",
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.align(Alignment.Center),
                        placeholderText = "Placeholder",
                        helperText = textInputState.name,
                        state = textInputState,
                    )
                    PASSWORD_INPUT_VARIANT -> PasswordInput(
                        label = "Password input",
                        value = text,
                        passwordHidden = passwordHidden,
                        onValueChange = { text = it },
                        onPasswordHiddenChange = { passwordHidden = it },
                        modifier = Modifier.align(Alignment.Center),
                        helperText = textInputState.name,
                        state = textInputState,
                    )
                    TEXT_AREA_VARIANT -> TextArea(
                        label = "Text area",
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.align(Alignment.Center),
                        placeholderText = "Placeholder",
                        helperText = textInputState.name,
                        state = textInputState,
                        maxLines = 5
                    )
                }
            }
        }

        CarbonLayer {
            Column(
                modifier = Modifier
                    .padding(SpacingScale.spacing05)
                    .containerBackground()
                    .padding(SpacingScale.spacing05),
                verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing05)
            ) {
                BasicText(
                    text = "Configuration",
                    style = Carbon.typography.heading02.copy(color = Carbon.theme.textPrimary)
                )

                Dropdown(
                    label = "Variant / modifier",
                    placeholder = "Choose a variant",
                    options = textInputVariantOptions,
                    selectedOption = variant,
                    onOptionSelected = { variant = it },
                )

                Spacer(
                    modifier = Modifier
                        .background(color = Carbon.theme.borderStrong01)
                        .fillMaxWidth()
                        .height(1.dp)
                )

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
                        isEnabled = variant != PASSWORD_INPUT_VARIANT,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        iconPainter = painterResource(Res.drawable.ic_delete),
                        onClick = { text = "" },
                        modifier = Modifier.padding(start = SpacingScale.spacing03)
                    )
                }

                LayerSelectionDropdown(
                    selectedLayer = layer,
                    onLayerSelected = { layer = it },
                )
            }
        }
    }
}
