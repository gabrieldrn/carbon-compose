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

package com.gabrieldrn.carbon.contentswitcher

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text

@Composable
public fun ContentSwitcher(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    require(options.size >= 2) { "ContentSwitcher requires at least two options" }

    val colors = ContentSwitcherColors.colors()

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Carbon.theme.borderInverse,
                shape = RoundedCornerShape(4.dp)
            )
            .clip(shape = RoundedCornerShape(4.dp))
            .height(height = SpacingScale.spacing08)
            .width(IntrinsicSize.Min) // By default, use only the minimum width needed.
    ) {
        options.map { option ->
            ContentSwitcherButton(
                text = option,
                isSelected = option == selectedOption,
                colors = colors,
                onClick = { onOptionSelected(option) },
                modifier = Modifier
                    // Make all buttons the same width as the widest button.
                    .width(IntrinsicSize.Max)
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun ContentSwitcherButton(
    text: String,
    isSelected: Boolean,
    colors: ContentSwitcherColors,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .selectable(selected = isSelected, onClick = onClick)
            .fillMaxHeight()
            .background(color = colors.containerColor(isSelected).value)
            .padding(horizontal = SpacingScale.spacing05)
    ) {
        Text(
            text = text,
            style = Carbon.typography.bodyCompact01,
            color = colors.labelColor(isSelected).value,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
        )
    }
}
