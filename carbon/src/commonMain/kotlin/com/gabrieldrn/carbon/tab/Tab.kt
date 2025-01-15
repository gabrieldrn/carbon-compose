/*
 * Copyright 2025 Jacob Ras
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

package com.gabrieldrn.carbon.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text

@Composable
internal fun Tab(
    item: TabItem,
    selected: Boolean,
    beforeSelected: Boolean,
    isLast: Boolean,
    variant: TabVariant,
    onClick: () -> Unit
) {
    val colors = TabColors.colors(variant)
    val backgroundColor by colors.backgroundColor(isSelected = selected)
    val textColor by colors.tabLabelTextColor(
        isEnabled = item.enabled,
        isSelected = selected
    )

    Box(
        Modifier
            .height(
                when (variant) {
                    TabVariant.Line -> SpacingScale.spacing08
                    TabVariant.Contained -> SpacingScale.spacing09
                }
            )
            .width(IntrinsicSize.Max)
            .background(backgroundColor)
            .clickable(enabled = item.enabled) { onClick() }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = SpacingScale.spacing05),
            text = item.label,
            style = if (selected) {
                Carbon.typography.headingCompact01.copy(textColor)
            } else {
                Carbon.typography.bodyCompact01.copy(textColor)
            }
        )
        if (variant == TabVariant.Line || selected) {
            Spacer(
                Modifier
                    .align(
                        when (variant) {
                            TabVariant.Line -> Alignment.BottomCenter
                            TabVariant.Contained -> Alignment.TopCenter
                        }
                    )
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(
                        if (selected) {
                            Carbon.theme.borderInteractive
                        } else {
                            colors.bottomBorderUnselected
                        }
                    )
            )
        }
        if (variant == TabVariant.Contained && !selected && !beforeSelected && !isLast) {
            Spacer(
                Modifier
                    .align(Alignment.CenterEnd)
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(colors.verticalBorderUnselected)
            )
        }
    }
}