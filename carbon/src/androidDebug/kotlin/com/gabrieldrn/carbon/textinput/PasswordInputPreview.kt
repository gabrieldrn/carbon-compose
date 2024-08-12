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

package com.gabrieldrn.carbon.textinput

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

@Preview
@Composable
private fun PasswordInputPreview(
    @PreviewParameter(TextInputStatePreviewParameterProvider::class) state: TextInputState
) {
    var text by remember {
        mutableStateOf("S0mePa55word%")
    }
    var passwordHidden by remember {
        mutableStateOf(false)
    }

    CarbonDesignSystem {
        PasswordInput(
            label = "Label",
            value = text,
            passwordHidden = passwordHidden,
            onValueChange = { text = it },
            onPasswordHiddenChange = { passwordHidden = it },
            modifier = Modifier.padding(SpacingScale.spacing03),
            placeholderText = "Placeholder",
            helperText = state.name,
            state = state,
        )
    }
}
