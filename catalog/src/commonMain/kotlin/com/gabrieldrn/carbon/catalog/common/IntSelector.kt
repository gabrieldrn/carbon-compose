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

package com.gabrieldrn.carbon.catalog.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.catalog.Res
import com.gabrieldrn.carbon.catalog.ic_add
import com.gabrieldrn.carbon.catalog.ic_subtract
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import org.jetbrains.compose.resources.painterResource

@Composable
fun IntSelector(
    label: String,
    value: Int,
    valueRange: IntRange,
    onValueChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        BasicText(
            text = label,
            style = Carbon.typography.label01.copy(color = Carbon.theme.textSecondary),
        )

        Row(
            modifier = Modifier.padding(top = SpacingScale.spacing04),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val lessButtonEnabled by remember(value) {
                mutableStateOf(value > valueRange.first)
            }
            val moreButtonEnabled by remember(value) {
                mutableStateOf(value < valueRange.last)
            }

            IconButton(
                iconPainter = painterResource(Res.drawable.ic_subtract),
                onClick = {
                    onValueChanged(
                        value.minus(1).coerceAtLeast(valueRange.first)
                    )
                },
                isEnabled = lessButtonEnabled,
                contentDescription = "Decrease"
            )

            BasicText(
                text = value.toString(),
                style = Carbon.typography.body01
                    .copy(
                        color = Carbon.theme.textPrimary,
                        textAlign = TextAlign.Center
                    ),
                modifier = Modifier.width(SpacingScale.spacing09)
            )

            IconButton(
                iconPainter = painterResource(Res.drawable.ic_add),
                onClick = {
                    onValueChanged(
                        value.plus(1).coerceAtMost(valueRange.last)
                    )
                },
                isEnabled = moreButtonEnabled,
                contentDescription = "Increase"
            )
        }
    }
}
