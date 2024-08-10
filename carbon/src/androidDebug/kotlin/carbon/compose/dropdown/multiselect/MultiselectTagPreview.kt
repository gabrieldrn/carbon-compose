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

package carbon.compose.dropdown.multiselect

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import carbon.compose.CarbonDesignSystem
import carbon.compose.dropdown.base.DropdownInteractiveState

@Preview(device = "spec:width=1080px,height=2340px,dpi=640")
@Composable
private fun MultiselectTagPreview() {
    CarbonDesignSystem {
        DropdownMultiselectTag(
            state = DropdownInteractiveState.Enabled,
            count = 42,
            onCloseTagClick = {}
        )
    }
}

@Preview(device = "spec:width=1080px,height=2340px,dpi=640")
@Composable
private fun MultiselectTagReadOnlyPreview() {
    CarbonDesignSystem {
        DropdownMultiselectTag(
            state = DropdownInteractiveState.ReadOnly,
            count = 42,
            onCloseTagClick = {}
        )
    }
}
