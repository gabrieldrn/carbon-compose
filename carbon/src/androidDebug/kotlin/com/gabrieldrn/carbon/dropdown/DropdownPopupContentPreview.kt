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

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.dropdown.base.DropdownColors
import com.gabrieldrn.carbon.dropdown.base.DropdownOption
import com.gabrieldrn.carbon.dropdown.base.DropdownPopupContent
import com.gabrieldrn.carbon.dropdown.base.DropdownSize
import com.gabrieldrn.carbon.dropdown.base.dpSize

@Preview
@Composable
private fun DropdownPopupContentPreview(
    @PreviewParameter(DropdownStateParameterProvider::class) dropdownSize: DropdownSize,
) {
    val options: Map<Int, DropdownOption> = (0..4)
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

    CarbonDesignSystem {
        DropdownPopupContent(
            options = options,
            selectedOption = 1,
            colors = DropdownColors.colors(),
            componentHeight = dropdownSize.dpSize(),
            onOptionClicked = {},
        )
    }
}
