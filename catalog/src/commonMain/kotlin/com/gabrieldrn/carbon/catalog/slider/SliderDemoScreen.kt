/*
 * Copyright 2025 Gabriel Derrien
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

package com.gabrieldrn.carbon.catalog.slider

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.slider.Slider
import com.gabrieldrn.carbon.textinput.TextInput
import com.gabrieldrn.carbon.textinput.TextInputState

@Composable
fun SliderDemoScreen(modifier: Modifier = Modifier) {

    var sliderValue by rememberSaveable {
        mutableFloatStateOf(0f)
    }

    var stepsStringValue by rememberSaveable {
        mutableStateOf("0.1")
    }
    var steps by rememberSaveable {
        mutableFloatStateOf(0.1f)
    }

    DemoScreen(
        demoContent = {
            Column {
                Slider(
                    value = sliderValue,
                    startLabel = "0",
                    endLabel = "1",
                    onValueChange = { sliderValue = it },
                    modifier = Modifier.widthIn(max = 300.dp),
                    label = "Label",
                    steps = steps,
                )
                BasicText(
                    text = sliderValue.toString(),
                    style = Carbon.typography.body01.copy(color = Carbon.theme.textPrimary),
                    modifier = Modifier.padding(top = SpacingScale.spacing03)
                )
            }
        },
        demoParametersContent = {
            var stepsInputState by rememberSaveable {
                mutableStateOf(TextInputState.Enabled)
            }

            TextInput(
                label = "Steps",
                value = stepsStringValue,
                onValueChange = {
                    try {
                        steps = it.toFloat()
                        stepsInputState = TextInputState.Enabled
                    } catch (nfe: NumberFormatException) {
                        stepsInputState = TextInputState.Error
                    }
                    stepsStringValue = it
                },
                state = stepsInputState,
                helperText =
                    if (stepsInputState == TextInputState.Error) {
                        "Invalid number format"
                    } else {
                        ""
                    }
            )
        },
        modifier = modifier
    )
}
