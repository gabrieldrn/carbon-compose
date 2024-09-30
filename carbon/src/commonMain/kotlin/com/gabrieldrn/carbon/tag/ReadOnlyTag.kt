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

package com.gabrieldrn.carbon.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text

@Composable
public fun ReadOnlyTag(
    text: String,
    modifier: Modifier = Modifier,
    type: TagType = TagType.Gray,
    size: TagSize = TagSize.Small
) {
    val theme = Carbon.theme

    Box(
        modifier = modifier
            .height(size.height)
            .background(
                shape = RoundedCornerShape(100),
                color = type.backgroundColor(theme)
            )
            .then(
                if (type == TagType.HighContrast || type == TagType.Outline) {
                    Modifier.border(
                        width = 1.dp,
                        color = type.borderColor(theme),
                        shape = RoundedCornerShape(100)
                    )
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = Carbon.typography.label01,
            color = type.contentColor(theme),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(
                horizontal = when (size) {
                    TagSize.Small -> SpacingScale.spacing03
                    TagSize.Medium -> SpacingScale.spacing03
                    TagSize.Large -> SpacingScale.spacing04
                }
            )
        )
    }
}
