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

package com.gabrieldrn.carbon.radiobutton

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.InteractiveStatePreviewParameterProvider
import com.gabrieldrn.carbon.common.selectable.SelectableInteractiveState
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.toLabel

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
    group = "Unselected state",
    device = "spec:width=1080px,height=2340px,dpi=640",
)
@Composable
private fun RadioButtonOffPreview(
    @PreviewParameter(InteractiveStatePreviewParameterProvider::class)
    interactiveState: SelectableInteractiveState
) {
    CarbonDesignSystem {
        RadioButton(
            selected = false,
            interactiveState = interactiveState,
            label = "${interactiveState.toLabel()} unselected",
            onClick = {},
            modifier = Modifier.padding(SpacingScale.spacing03)
        )
    }
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
    group = "Selected state",
    device = "spec:width=1080px,height=2340px,dpi=640",
)
@Composable
private fun RadioButtonOnPreview(
    @PreviewParameter(InteractiveStatePreviewParameterProvider::class)
    interactiveState: SelectableInteractiveState
) {
    CarbonDesignSystem {
        RadioButton(
            selected = true,
            interactiveState = interactiveState,
            label = "${interactiveState.toLabel()} selected",
            onClick = {},
            modifier = Modifier.padding(SpacingScale.spacing03)
        )
    }
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
    group = "Focused state",
    device = "spec:width=1080px,height=2340px,dpi=640",
)
@Composable
private fun RadioButtonFocusPreview() {
    CarbonDesignSystem {
        val focusRequester = FocusRequester()

        SideEffect {
            focusRequester.requestFocus()
        }

        RadioButton(
            selected = false,
            label = "Focused",
            onClick = {},
            modifier = Modifier
                .padding(SpacingScale.spacing03)
                .focusRequester(focusRequester)
        )
    }
}
