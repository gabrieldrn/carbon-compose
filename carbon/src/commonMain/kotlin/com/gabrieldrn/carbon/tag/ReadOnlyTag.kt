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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text

private val tagShape = RoundedCornerShape(100)
private val tagIconSize = 16.dp

/**
 * # Tag - Read-only
 *
 * Tags are components that are often used to label different items, create categorization, filter
 * data, select or deselect options, and include functionality to disclose several related tags in
 * another view.
 *
 * Read-only tags have no interactive functionality and are commonly used for categorizing and
 * labeling.
 *
 * Accessibility note: Read-only tags are not interactive and do not receive focus.
 *
 * (From [Tag documentation](https://carbondesignsystem.com/components/tag/usage/))`
 *
 * @param text The text to be displayed in the tag.
 * @param modifier The modifier to be applied to the tag.
 * @param icon The icon to be displayed in the tag. Defaults to null. The lambda signature ensures
 * stability across recompositions.
 * @param type The type of the tag. The tag type can be referred to the tag color. Defaults to
 * [TagType.Gray].
 * @param size The size of the tag. Defaults to [TagSize.Small].
 */
@Composable
public fun ReadOnlyTag(
    text: String,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Painter)? = null,
    type: TagType = TagType.Gray,
    size: TagSize = TagSize.Medium
) {
    val theme = Carbon.theme

    Row(
        modifier = modifier
            .height(size.height)
            .background(
                shape = tagShape,
                color = type.backgroundColor(theme)
            )
            .then(
                if (type == TagType.HighContrast || type == TagType.Outline) {
                    Modifier.border(
                        width = 1.dp,
                        color = type.borderColor(theme),
                        shape = tagShape
                    )
                } else {
                    Modifier
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val contentColor = remember(type, theme) { type.contentColor(theme) }

        TagIcon(
            icon = icon,
            color = contentColor,
            modifier = Modifier.padding(
                horizontal = when (size) {
                    TagSize.Small -> SpacingScale.spacing02
                    TagSize.Medium -> SpacingScale.spacing02
                    TagSize.Large -> SpacingScale.spacing03
                }
            )
        )

        Text(
            text = text,
            style = Carbon.typography.label01,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = if (icon == null) {
                Modifier.padding(
                    horizontal = when (size) {
                        TagSize.Small -> SpacingScale.spacing03
                        TagSize.Medium -> SpacingScale.spacing03
                        TagSize.Large -> SpacingScale.spacing04
                    }
                )
            } else {
                Modifier.padding(
                    end = when (size) {
                        TagSize.Small -> SpacingScale.spacing03
                        TagSize.Medium -> SpacingScale.spacing03
                        TagSize.Large -> SpacingScale.spacing04
                    }
                )
            }
        )
    }
}

@Composable
private fun TagIcon(
    color: Color,
    icon: @Composable (() -> Painter)?,
    modifier: Modifier = Modifier
) {
    icon?.let {
        val painter = it()
        Image(
            painter = painter,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color),
            modifier = modifier.requiredSize(tagIconSize)
        )
    }
}
