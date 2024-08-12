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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text
import com.gabrieldrn.carbon.icons.CheckmarkFilledIcon
import com.gabrieldrn.carbon.icons.ErrorFilledIcon

@Composable
internal fun ProgressBarRootLayout(
    labelText: String?,
    helperText: String?,
    inlined: Boolean,
    indented: Boolean,
    modifier: Modifier = Modifier,
    state: ProgressBarState = ProgressBarState.Active,
    colors: ProgressBarColors = ProgressBarColors.colors(),
    trackContent: @Composable () -> Unit
) {
    if (inlined) {
        InlinedProgressBarLayout(
            labelText = labelText,
            state = state,
            colors = colors,
            modifier = modifier,
            trackContent = trackContent
        )
    } else {
        DefaultProgressBarLayout(
            labelText = labelText,
            helperText = helperText,
            indented = indented,
            state = state,
            colors = colors,
            modifier = modifier,
            trackContent = trackContent
        )
    }
}

@Composable
internal fun DefaultProgressBarLayout(
    labelText: String?,
    helperText: String?,
    indented: Boolean,
    state: ProgressBarState,
    colors: ProgressBarColors,
    modifier: Modifier = Modifier,
    trackContent: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = if (indented) {
                Modifier.padding(horizontal = SpacingScale.spacing05)
            } else {
                Modifier
            }
        ) {
            if (labelText != null) {
                LabelText(
                    text = labelText,
                    modifier = Modifier
                        .weight(1f)
                        .testTag(ProgressBarTestTags.LABEL_TEXT)
                )
            } else {
                Box(modifier = Modifier.weight(1f))
            }

            Icon(state = state, colors = colors)
        }

        Spacer(modifier = Modifier.height(SpacingScale.spacing03))

        trackContent()

        if (helperText != null) {
            Text(
                text = helperText,
                style = Carbon.typography.helperText01,
                color = Carbon.theme.textHelper,
                modifier = Modifier
                    .padding(
                        top = SpacingScale.spacing03,
                        start = if (indented) {
                            SpacingScale.spacing05
                        } else {
                            0.dp
                        }
                    )
                    .testTag(ProgressBarTestTags.HELPER_TEXT)
            )
        }
    }
}

@Composable
private fun InlinedProgressBarLayout(
    labelText: String?,
    state: ProgressBarState,
    colors: ProgressBarColors,
    modifier: Modifier = Modifier,
    trackContent: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (labelText != null) {
            LabelText(
                text = labelText,
                modifier = Modifier.testTag(ProgressBarTestTags.LABEL_TEXT)
            )
        }
        Spacer(modifier = Modifier.width(SpacingScale.spacing05))

        if (state == ProgressBarState.Active) {
            trackContent()
        } else {
            Icon(state = state, colors = colors)
        }
    }
}

@Composable
private fun LabelText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = Carbon.typography.label01,
        color = Carbon.theme.textPrimary,
        modifier = modifier
    )
}

@Composable
private fun Icon(
    state: ProgressBarState,
    colors: ProgressBarColors,
    modifier: Modifier = Modifier
) {
    val iconColor by colors.iconColor(state)

    when (state) {
        ProgressBarState.Active -> {}
        ProgressBarState.Success -> CheckmarkFilledIcon(
            tint = iconColor,
            size = 16.dp,
            modifier = modifier
        )
        ProgressBarState.Error -> ErrorFilledIcon(
            tint = iconColor,
            size = 16.dp,
            modifier = modifier
        )
    }
}
