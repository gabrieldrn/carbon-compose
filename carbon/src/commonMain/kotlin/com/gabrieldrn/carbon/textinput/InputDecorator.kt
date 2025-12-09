/*
 * Copyright 2025 Gabriel Derrien
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

import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.common.semantics.readOnly
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.foundation.interaction.FocusIndication
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text
import com.gabrieldrn.carbon.icons.WarningAltFilledIcon
import com.gabrieldrn.carbon.icons.WarningFilledIcon

@Composable
internal fun inputDecorator(
    label: String,
    value: String,
    placeholderText: String,
    helperText: String,
    state: TextInputState,
    theme: Theme,
    colors: TextInputColors,
    singleLine: Boolean,
    interactionSource: MutableInteractionSource,
    counter: Pair<Int, Int>?,
    trailingIcon: @Composable (() -> Unit)?,
    popup: (@Composable () -> Unit)? = null,
): @Composable (@Composable () -> Unit) -> Unit = { innerTextField ->
    Column(
        modifier = Modifier.semantics(mergeDescendants = true) {
            stateDescription = helperText
        }
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = SpacingScale.spacing03)
        ) {
            Text(
                text = label,
                style = Carbon.typography.label01,
                color = colors.labelTextColor(state = state).value,
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .testTag(TextInputTestTags.LABEL)
            )

            if (counter != null) {
                Text(
                    text = "${counter.first}/${counter.second}",
                    style = Carbon.typography.label01,
                    color = colors.labelTextColor(state = state).value,
                    maxLines = 1,
                    modifier = Modifier.testTag(TextInputTestTags.COUNTER)
                )
            }
        }

        Box {
            FieldContent(
                singleLine = singleLine,
                value = value,
                placeholderText = placeholderText,
                colors = colors,
                innerTextField = innerTextField,
                state = state,
                trailingIcon = trailingIcon,
                modifier = Modifier
                    .indication(
                        interactionSource = interactionSource,
                        indication = FocusIndication(Carbon.theme)
                    )
                    .fieldBackground(state, colors)
                    .errorOutline(theme, state)
                    .then(
                        when (state) {
                            TextInputState.Disabled -> Modifier.semantics { disabled() }
                            TextInputState.ReadOnly -> Modifier.readOnly(
                                role = null,
                                interactionSource = interactionSource,
                                mergeDescendants = true
                            )
                            else -> Modifier
                        }
                    )
                    .testTag(TextInputTestTags.FIELD)
            )

            if (popup != null) {
                popup()
            }
        }


        if (helperText.isNotEmpty()) {
            Text(
                text = helperText,
                style = Carbon.typography.helperText01,
                color = colors.helperTextColor(state = state).value,
                modifier = Modifier
                    .padding(top = SpacingScale.spacing02)
                    .testTag(TextInputTestTags.HELPER_TEXT)
            )
        }
    }
}

internal fun Modifier.fieldBackground(
    state: TextInputState,
    colors: TextInputColors,
) = this then composed {
    val backgroundColor by colors.fieldBackgroundColor(state = state)
    val borderColor by colors.fieldBorderColor(state = state)
    drawBehind {
        drawRect(backgroundColor)
        drawRect(borderColor, topLeft = Offset.Zero.copy(y = size.height - 1.dp.toPx()))
    }
}

internal fun Modifier.errorOutline(
    theme: Theme,
    state: TextInputState
) = this then drawBehind {
    if (state == TextInputState.Error) {
        val outlineWidth = SpacingScale.spacing01.toPx()
        val outlineHalfWidth = outlineWidth * .5f
        drawRect(
            color = theme.supportError,
            style = Stroke(outlineWidth),
            topLeft = Offset(outlineHalfWidth, outlineHalfWidth),
            size = Size(
                width = size.width - outlineWidth,
                height = size.height - outlineWidth
            )
        )
    }
}

@Composable
private fun FieldContent(
    singleLine: Boolean,
    value: String,
    placeholderText: String,
    colors: TextInputColors,
    state: TextInputState,
    innerTextField: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .sizeIn(minHeight = TEXT_INPUT_HEIGHT_LARGE_DP.dp)
            .padding(
                start = SpacingScale.spacing05,
                end = SpacingScale.spacing05.takeIf { trailingIcon == null } ?: 0.dp
            )
            .padding(
                vertical = 11.dp.takeIf { !singleLine } ?: 0.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            innerTextField()

            if (value.isEmpty()) {
                Text(
                    text = placeholderText,
                    style = Carbon.typography.bodyCompact01,
                    color = colors.placeholderTextColor(state = state).value,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag(TextInputTestTags.PLACEHOLDER)
                )
            }
        }

        StateIcon(
            state = state,
            modifier = Modifier
                .then(
                    if (singleLine) {
                        Modifier.padding(start = SpacingScale.spacing05)
                    } else {
                        Modifier.align(Alignment.Top)
                    }
                )
        )

        trailingIcon?.invoke()
    }
}

@Composable
private fun StateIcon(
    state: TextInputState,
    modifier: Modifier = Modifier
) {
    when (state) {
        TextInputState.Error -> WarningFilledIcon(
            modifier = modifier.testTag(TextInputTestTags.STATE_ICON_ERROR),
            tint = Carbon.theme.supportError
        )
        TextInputState.Warning -> WarningAltFilledIcon(
            modifier = modifier.testTag(TextInputTestTags.STATE_ICON_WARNING),
            tint = Carbon.theme.supportWarning,
            innerTint = Color.Black
        )
        else -> {}
    }
}
