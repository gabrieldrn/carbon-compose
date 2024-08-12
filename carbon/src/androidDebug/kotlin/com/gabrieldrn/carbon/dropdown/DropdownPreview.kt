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

package com.gabrieldrn.carbon.dropdown

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.base.DropdownOption

internal class DropdownStateParameterProvider : PreviewParameterProvider<DropdownInteractiveState> {
    override val values: Sequence<DropdownInteractiveState>
        get() = sequenceOf(
            DropdownInteractiveState.Enabled,
            DropdownInteractiveState.Warning("Warning message"),
            DropdownInteractiveState.Error("Error message"),
            DropdownInteractiveState.Disabled,
        )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DropdownPreview(
    @PreviewParameter(DropdownStateParameterProvider::class) state: DropdownInteractiveState,
) {
    var expanded by remember { mutableStateOf(false) }

    CarbonDesignSystem {
        Dropdown(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            onDismissRequest = { expanded = false },
            placeholder = state::class.java.simpleName,
            selectedOption = null,
            options = mapOf(0 to DropdownOption("Option 0")),
            onOptionSelected = {},
            state = state,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
    }
}
