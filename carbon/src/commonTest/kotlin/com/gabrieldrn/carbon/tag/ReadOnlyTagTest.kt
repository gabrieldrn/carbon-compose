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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.CarbonDesignSystem
import kotlin.test.Test

class ReadOnlyTagTest {

    private var _text by mutableStateOf("")
    private var _icon by mutableStateOf<(@Composable () -> Painter)?>(null)
    private var _type by mutableStateOf(TagType.Gray)
    private var _size by mutableStateOf(TagSize.Medium)

    private val rootTag = "root"

    @Test
    fun readOnlyTag_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                ReadOnlyTag(
                    text = _text,
                    icon = _icon,
                    type = _type,
                    size = _size,
                    modifier = Modifier.testTag(rootTag)
                )
            }
        }

        forEachParameter { text, painter, tagType, tagSize ->
            _text = text
            _icon = painter
            _type = tagType
            _size = tagSize

            onNodeWithTag(rootTag)
                .assertHeightIsEqualTo(tagSize.height)

            onNodeWithTag(TagTestTags.LABEL, useUnmergedTree = false)
                .assertIsDisplayed()

            onNodeWithTag(TagTestTags.ICON, useUnmergedTree = false).run {
                if (painter != null) {
                    assertIsDisplayed()
                } else {
                    assertDoesNotExist()
                }
            }
        }
    }

    @Suppress("NestedBlockDepth")
    private fun forEachParameter(
        testBlock: (String, (@Composable () -> Painter)?, TagType, TagSize) -> Unit
    ) {
        val painterLambda = @Composable { ColorPainter(Color.Black) }

        arrayOf("", "Tag").forEach { text ->
            arrayOf(null, painterLambda).forEach { painter ->
                TagType.entries.forEach { type ->
                    TagSize.entries.forEach { size ->
                        testBlock(text, painter, type, size)
                    }
                }
            }
        }
    }
}
