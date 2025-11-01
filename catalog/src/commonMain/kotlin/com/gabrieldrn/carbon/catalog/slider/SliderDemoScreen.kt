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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.catalog.common.IntSelector
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.slider.Slider
import com.gabrieldrn.carbon.textinput.TextInput
import com.gabrieldrn.carbon.textinput.TextInputState

private val initialRange = 0..10
private const val initialSteps = 1

@Composable
fun SliderDemoScreen(modifier: Modifier = Modifier) {

    var sliderValue by rememberSaveable {
        mutableFloatStateOf(0f)
    }

    var sliderRangeStart by rememberSaveable {
        mutableIntStateOf(initialRange.first)
    }

    var sliderRangeEnd by rememberSaveable {
        mutableIntStateOf(initialRange.last)
    }

    var sliderRange by remember(sliderRangeStart, sliderRangeEnd) {
        mutableStateOf(sliderRangeStart.toFloat()..sliderRangeEnd.toFloat())
    }

    var stepsStringValue by rememberSaveable {
        mutableStateOf(initialSteps.toString())
    }
    var steps by rememberSaveable {
        mutableFloatStateOf(initialSteps.toFloat())
    }

    DemoScreen(
        demoContent = {
            Column {
                Slider(
                    value = sliderValue,
                    startLabel = sliderRangeStart.toString(),
                    endLabel = sliderRangeEnd.toString(),
                    onValueChange = { sliderValue = it },
                    modifier = Modifier.widthIn(max = 300.dp),
                    label = "Label",
                    sliderRange = sliderRange,
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

            FlowRow(
                modifier = Modifier.padding(bottom = SpacingScale.spacing04),
                horizontalArrangement = Arrangement.spacedBy(SpacingScale.spacing06),
                verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing04)
            ) {
                IntSelector(
                    label = "Range start",
                    value = sliderRangeStart,
                    valueRange = -10..10,
                    onValueChanged = { sliderRangeStart = it },
                )

                IntSelector(
                    label = "Range end",
                    value = sliderRangeEnd,
                    valueRange = 10..20,
                    onValueChanged = { sliderRangeEnd = it },
                )
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
