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
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

/**
 * # Progress Bar | Determinate
 *
 * A progress bar provides feedback about the duration and progression of a process, such as a
 * download, file transfer, or installation, to indicate how long a user will be waiting.
 *
 * Determinate progress bars are used when the loading progress can be tracked and translated into a
 * bar indicator which fills the track from 0% to 100% and never decreases in value or resets.
 *
 * (From [Progress bar documentation](https://carbondesignsystem.com/components/progress-bar/usage))
 *
 * @param value The progress value, between 0 and 1.
 * @param modifier The modifier to apply to this composable.
 * @param labelText The text label on top of the progress bar.
 * @param helperText The helper text below the progress bar.
 * @param indented Whether to indent the label and helper texts.
 * @param inlined When true, sets the label text inlined with the progress bar. In this case the
 * helper text is not displayed.
 * @param state The state of the progress bar.
 * @param size The size type of the progress bar.
 */
@Composable
public fun ProgressBar(
    value: Float,
    modifier: Modifier = Modifier,
    labelText: String? = null,
    helperText: String? = null,
    indented: Boolean = false,
    inlined: Boolean = false,
    state: ProgressBarState = ProgressBarState.Active,
    size: ProgressBarSize = ProgressBarSize.Big,
) {
    val actualProgressValue = remember(value) {
        value.coerceIn(0f, 1f)
    }

    ProgressBarRootLayout(
        labelText = labelText,
        helperText = helperText,
        inlined = inlined,
        indented = indented,
        modifier = modifier.progressSemantics(value = actualProgressValue),
        state = state,
        trackContent = {
            ProgressBarTrack(
                size = size,
                value = actualProgressValue,
                state = state,
                modifier = Modifier.testTag(ProgressBarTestTags.TRACK)
            )
        }
    )
}

/**
 * # Progress Bar | Indeterminate
 *
 * A progress bar provides feedback about the duration and progression of a process, such as a
 * download, file transfer, or installation, to indicate how long a user will be waiting.
 *
 * Indeterminate progress bars are used when the loading progress is unknown or the amount of wait
 * time can’t be calculated.
 *
 * (From [Progress bar documentation](https://carbondesignsystem.com/components/progress-bar/usage))
 *
 * @param modifier The modifier to apply to this composable.
 * @param labelText The text label on top of the progress bar.
 * @param helperText The helper text below the progress bar.
 * @param indented Whether to indent the label and helper texts.
 * @param inlined When true, sets the label text inlined with the progress bar. In this case the
 * helper text is not displayed.
 * @param state The state of the progress bar.
 * @param size The size type of the progress bar.
 */
@Composable
public fun IndeterminateProgressBar(
    modifier: Modifier = Modifier,
    labelText: String? = null,
    helperText: String? = null,
    indented: Boolean = false,
    inlined: Boolean = false,
    state: ProgressBarState = ProgressBarState.Active,
    size: ProgressBarSize = ProgressBarSize.Big,
) {
    ProgressBarRootLayout(
        labelText = labelText,
        helperText = helperText,
        inlined = inlined,
        indented = indented,
        modifier = modifier.progressSemantics(),
        state = state,
        trackContent = {
            IndeterminateProgressBarTrack(
                size = size,
                modifier = Modifier.testTag(ProgressBarTestTags.TRACK)
            )
        }
    )
}

// region Previews

private class ProgressBarStateParameterProvider : PreviewParameterProvider<ProgressBarState> {
    override val values = ProgressBarState.entries.asSequence()
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ProgressBarPreview(
    @PreviewParameter(ProgressBarStateParameterProvider::class) state: ProgressBarState
) {
    CarbonDesignSystem {
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
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun InlinedProgressBarPreview(
    @PreviewParameter(ProgressBarStateParameterProvider::class) state: ProgressBarState
) {
    CarbonDesignSystem {
        ProgressBar(
            value = 0.75f,
            labelText = "Loading",
            helperText = "",
            inlined = true,
            state = state,
            modifier = Modifier.padding(SpacingScale.spacing03)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun IndeterminateProgressBarPreview() {
    CarbonDesignSystem {
        IndeterminateProgressBar(
            labelText = "Loading",
            modifier = Modifier.padding(SpacingScale.spacing03),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun IndeterminateInlinedProgressBarPreview() {
    CarbonDesignSystem {
        IndeterminateProgressBar(
            labelText = "Loading",
            inlined = true,
            modifier = Modifier.padding(SpacingScale.spacing03),
        )
    }
}

// endregion
