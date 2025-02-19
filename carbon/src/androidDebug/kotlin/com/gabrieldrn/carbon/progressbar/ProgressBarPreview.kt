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

package com.gabrieldrn.carbon.progressbar

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

private class ProgressBarStateParameterProvider : PreviewParameterProvider<ProgressBarState> {
    override val values = ProgressBarState.entries.asSequence()
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ProgressBarPreview(
    @PreviewParameter(ProgressBarStateParameterProvider::class) state: ProgressBarState
) {
    ProgressBar(
        value = 0.75f,
        labelText = "Loading",
        helperText = when (state) {
            ProgressBarState.Active -> "75%"
            ProgressBarState.Success -> "Done"
            ProgressBarState.Error -> "Error"
        },
        state = state,
        modifier = Modifier.padding(SpacingScale.spacing03)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun InlinedProgressBarPreview(
    @PreviewParameter(ProgressBarStateParameterProvider::class) state: ProgressBarState
) {
    ProgressBar(
        value = 0.75f,
        labelText = "Loading",
        helperText = "",
        inlined = true,
        state = state,
        modifier = Modifier.padding(SpacingScale.spacing03)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun IndeterminateProgressBarPreview() {
    IndeterminateProgressBar(
        labelText = "Loading",
        modifier = Modifier.padding(SpacingScale.spacing03),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun IndeterminateInlinedProgressBarPreview() {
    IndeterminateProgressBar(
        labelText = "Loading",
        inlined = true,
        modifier = Modifier.padding(SpacingScale.spacing03),
    )
}
