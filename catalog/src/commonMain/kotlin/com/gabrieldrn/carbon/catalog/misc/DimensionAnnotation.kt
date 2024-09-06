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

package com.gabrieldrn.carbon.catalog.misc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.getIBMPlexMonoFamily

@Composable
fun BottomDimension(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(top = SpacingScale.spacing03),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(color = Color.Magenta)
                    .height(7.dp)
                    .width(1.dp)
            )
            Box(
                modifier = Modifier
                    .background(color = Color.Magenta)
                    .fillMaxWidth()
                    .height(1.dp)
                    .weight(1f)
            )
            Box(
                modifier = Modifier
                    .background(color = Color.Magenta)
                    .height(7.dp)
                    .width(1.dp)
            )
        }
        BasicText(
            text = text,
            style = Carbon.typography.label01.copy(
                color = Color.Magenta,
                fontFamily = getIBMPlexMonoFamily()
            )
        )
    }
}
