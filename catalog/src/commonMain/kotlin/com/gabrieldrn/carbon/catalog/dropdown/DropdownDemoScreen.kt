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

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.catalog.common.DemoScreenRoot
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.base.DropdownOption
import com.gabrieldrn.carbon.dropdown.base.DropdownSize
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.toggle.Toggle

private val dropdownStates = listOf(
    DropdownInteractiveState.Enabled,
    DropdownInteractiveState.Warning("Warning message goes here"),
    DropdownInteractiveState.Error("Error message goes here"),
    DropdownInteractiveState.Disabled,
    DropdownInteractiveState.ReadOnly,
).associateWith {
    DropdownOption(
        when (it) {
            is DropdownInteractiveState.Enabled -> "Enabled"
            is DropdownInteractiveState.Warning -> "Warning"
            is DropdownInteractiveState.Error -> "Error"
            is DropdownInteractiveState.Disabled -> "Disabled"
            is DropdownInteractiveState.ReadOnly -> "Read-only"
        }
    )
}

private val dropdownSizes = DropdownSize.entries.toDropdownOptions()

private val stateSaver = Saver<DropdownInteractiveState, String>(
    save = { it::class.simpleName },
    restore = {
        when (it) {
            "Enabled" -> DropdownInteractiveState.Enabled
            "Warning" -> DropdownInteractiveState.Warning("Warning message goes here")
            "Error" -> DropdownInteractiveState.Error("Error message goes here")
            "Disabled" -> DropdownInteractiveState.Disabled
            "ReadOnly" -> DropdownInteractiveState.ReadOnly
            else -> throw IllegalArgumentException("Unknown state: $it")
        }
    }
)

@Composable
internal fun DropdownDemoScreen(
    variant: DropdownVariant,
    modifier: Modifier = Modifier
) {
    var dropdownState by rememberSaveable(Unit, stateSaver = stateSaver) {
        mutableStateOf(DropdownInteractiveState.Enabled)
    }

    var dropdownSize by rememberSaveable {
        mutableStateOf(DropdownSize.Large)
    }

    var isInlined by rememberSaveable {
        mutableStateOf(false)
    }

    val demoContent: @Composable ColumnScope.() -> Unit = {

        when (variant) {
            DropdownVariant.Default -> DefaultDemoDropdown(
                state = dropdownState,
                size = dropdownSize,
                isInlined = isInlined
            )
            DropdownVariant.Multiselect -> MultiselectDemoDropdown(
                state = dropdownState,
                size = dropdownSize
            )
        }
    }

    val parametersContent: @Composable ColumnScope.() -> Unit = {
        BasicText(
            text = "Configuration",
            style = Carbon.typography.heading02.copy(color = Carbon.theme.textPrimary)
        )

        Dropdown(
            placeholder = "Choose option",
            label = "Dropdown state",
            options = dropdownStates,
            selectedOption = dropdownState,
            onOptionSelected = { dropdownState = it },
        )

        dropdownSizes.takeIf { it.size > 1 }?.let { sizes ->
            Dropdown(
                placeholder = "Choose option",
                label = "Dropdown size",
                options = sizes,
                selectedOption = dropdownSize,
                onOptionSelected = { dropdownSize = it },
            )
        }

        if (variant == DropdownVariant.Default) {
            Toggle(
                label = "Inlined",
                isToggled = isInlined,
                onToggleChange = { isInlined = it },
            )
        }
    }

    DemoScreenRoot(
        modifier = modifier,
        demoContent = demoContent,
        demoParametersContent = parametersContent
    )
}
