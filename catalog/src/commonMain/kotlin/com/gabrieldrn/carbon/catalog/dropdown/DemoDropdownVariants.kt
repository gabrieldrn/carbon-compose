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

package com.gabrieldrn.carbon.catalog.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.base.DropdownOption
import com.gabrieldrn.carbon.dropdown.base.DropdownSize
import com.gabrieldrn.carbon.dropdown.multiselect.MultiselectDropdown
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.getIBMPlexMonoFamily

private val dropdownOptions: Map<Int, DropdownOption> = (0..9)
    .associateWith { DropdownOption("Option $it") }
    .toMutableMap()
    .apply {
        set(
            1, DropdownOption(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
                    "nisi ut aliquip ex ea commodo consequat."
            )
        )
        set(2, DropdownOption("Disabled", enabled = false))
    }

@Composable
fun DefaultDemoDropdown(
    state: DropdownInteractiveState,
    size: DropdownSize,
    isInlined: Boolean,
    modifier: Modifier = Modifier,
) {
    var selectedOption by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier.width(IntrinsicSize.Max),
    ) {
        Dropdown(
            label = "Dropdown",
            placeholder = "Choose option",
            selectedOption = selectedOption,
            options = dropdownOptions,
            onOptionSelected = { selectedOption = it },
            state = state,
            dropdownSize = size,
            isInlined = isInlined,
            modifier = modifier
                .then(if (isInlined) Modifier.fillMaxWidth() else Modifier.width(400.dp))
        )

        Column(
            modifier = Modifier.padding(top = SpacingScale.spacing03),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(color = Color.Magenta)
                        .height(7.dp)
                        .width(1.dp)
                )
                Box(
                    modifier = Modifier
                        .background(color = Color.Magenta)
                        .fillMaxWidth()
                        .height(1.dp)
                        .weight(1f)
                )
                Box(
                    modifier = Modifier
                        .background(color = Color.Magenta)
                        .height(7.dp)
                        .width(1.dp)
                )
            }
            BasicText(
                text = if (isInlined) {
                    "Wrap"
                } else {
                    "Fixed width"
                },
                style = Carbon.typography.label01.copy(
                    color = Color.Magenta,
                    fontFamily = getIBMPlexMonoFamily()
                )
            )
        }
    }
}

@Composable
fun MultiselectDemoDropdown(
    state: DropdownInteractiveState,
    size: DropdownSize,
    modifier: Modifier = Modifier,
) {
    var selectedOptions by remember {
        mutableStateOf<List<Int>>(
            if (state in arrayOf(
                    DropdownInteractiveState.Disabled,
                    DropdownInteractiveState.ReadOnly
                )
            ) {
                listOf(0)
            } else {
                listOf()
            }
        )
    }
    val placeholder by remember(selectedOptions) {
        mutableStateOf(
            when (selectedOptions.size) {
                0 -> "Choose options"
                1 -> "Option selected"
                else -> "Options selected"
            }
        )
    }

    MultiselectDropdown(
        label = "Multiselect dropdown",
        placeholder = placeholder,
        selectedOptions = selectedOptions,
        options = dropdownOptions,
        onOptionClicked = {
            selectedOptions = if (selectedOptions.contains(it)) {
                selectedOptions - it
            } else {
                selectedOptions + it
            }
        },
        onClearSelection = { selectedOptions = listOf() },
        state = state,
        dropdownSize = size,
        modifier = modifier.widthIn(max = 400.dp),
    )
}
