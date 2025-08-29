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

package com.gabrieldrn.carbon.popover

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import com.gabrieldrn.carbon.button.ButtonSize
import com.gabrieldrn.carbon.forEachParameter
import com.gabrieldrn.carbon.popover.tabtip.PopoverTabTipPositionProvider
import kotlin.test.Test
import kotlin.test.assertEquals

class PopoverTabTipPositionProviderTest {

    private val density = Density(1f)
    private val anchorBounds = IntRect(499, 576, 782, 720)
    private val windowSize = IntSize(1280, 2628)
    private val smallPopupContentSize = IntSize(540, 127)
    private val bigPopupContentSize = IntSize(80, 90)

    @Suppress("MaxLineLength")
    private val expectedResults = mapOf(
        Triple(ButtonSize.Small, PopoverAlignment.Start, true) to IntOffset(499, 576),
        Triple(ButtonSize.Small, PopoverAlignment.Start, false) to IntOffset(499, 576),
        Triple(ButtonSize.Small, PopoverAlignment.End, true) to IntOffset(242, 576),
        Triple(ButtonSize.Small, PopoverAlignment.End, false) to IntOffset(702, 576),
        Triple(ButtonSize.Medium, PopoverAlignment.Start, true) to IntOffset(499, 576),
        Triple(ButtonSize.Medium, PopoverAlignment.Start, false) to IntOffset(499, 576),
        Triple(ButtonSize.Medium, PopoverAlignment.End, true) to IntOffset(242, 576),
        Triple(ButtonSize.Medium, PopoverAlignment.End, false) to IntOffset(702, 576),
        Triple(ButtonSize.LargeProductive, PopoverAlignment.Start, true) to IntOffset(499, 576),
        Triple(ButtonSize.LargeProductive, PopoverAlignment.Start, false) to IntOffset(499, 576),
        Triple(ButtonSize.LargeProductive, PopoverAlignment.End, true) to IntOffset(242, 576),
        Triple(ButtonSize.LargeProductive, PopoverAlignment.End, false) to IntOffset(702, 576),
        Triple(ButtonSize.LargeExpressive, PopoverAlignment.Start, true) to IntOffset(499, 576),
        Triple(ButtonSize.LargeExpressive, PopoverAlignment.Start, false) to IntOffset(499, 576),
        Triple(ButtonSize.LargeExpressive, PopoverAlignment.End, true) to IntOffset(242, 576),
        Triple(ButtonSize.LargeExpressive, PopoverAlignment.End, false) to IntOffset(702, 576),
        Triple(ButtonSize.ExtraLarge, PopoverAlignment.Start, true) to IntOffset(499, 576),
        Triple(ButtonSize.ExtraLarge, PopoverAlignment.Start, false) to IntOffset(499, 576),
        Triple(ButtonSize.ExtraLarge, PopoverAlignment.End, true) to IntOffset(242, 576),
        Triple(ButtonSize.ExtraLarge, PopoverAlignment.End, false) to IntOffset(702, 576),
        Triple(ButtonSize.TwiceExtraLarge, PopoverAlignment.Start, true) to IntOffset(499, 576),
        Triple(ButtonSize.TwiceExtraLarge, PopoverAlignment.Start, false) to IntOffset(499, 576),
        Triple(ButtonSize.TwiceExtraLarge, PopoverAlignment.End, true) to IntOffset(242, 576),
        Triple(ButtonSize.TwiceExtraLarge, PopoverAlignment.End, false) to IntOffset(702, 576)
    )

    @Test
    fun caretTipPositionProvider_returnsCorrectPosition() {
        forEachParameter(
            ButtonSize.entries.toTypedArray(),
            PopoverAlignment.entries.toTypedArray(),
            arrayOf(true, false),
        ) { buttonSize, alignment, isSmall ->
            val provider = PopoverTabTipPositionProvider(
                buttonSize,
                alignment,
                density,
            )
            val expected = expectedResults[Triple(buttonSize, alignment, isSmall)]!!
            val actual = provider.calculatePosition(
                anchorBounds,
                windowSize,
                LayoutDirection.Ltr,
                if (isSmall) smallPopupContentSize else bigPopupContentSize
            )
            assertEquals(expected, actual)
        }
    }
}
