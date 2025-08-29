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
import com.gabrieldrn.carbon.forEachParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class PopoverNoTipPositionProviderTest {

    private val density = Density(1f)
    private val anchorBounds = IntRect(499, 576, 782, 720)
    private val windowSize = IntSize(1280, 2628)
    private val smallPopupContentSize = IntSize(540, 127)
    private val bigPopupContentSize = IntSize(80, 90)

    @Suppress("MaxLineLength")
    private val expectedResults = mapOf(
        Triple(PopoverPlacement.Right, PopoverAlignment.Start, true) to IntOffset(786, 572),
        Triple(PopoverPlacement.Right, PopoverAlignment.Start, false) to IntOffset(786, 572),
        Triple(PopoverPlacement.Right, PopoverAlignment.End, true) to IntOffset(786, 597),
        Triple(PopoverPlacement.Right, PopoverAlignment.End, false) to IntOffset(786, 634),
        Triple(PopoverPlacement.Left, PopoverAlignment.Start, true) to IntOffset(-45, 572),
        Triple(PopoverPlacement.Left, PopoverAlignment.Start, false) to IntOffset(415, 572),
        Triple(PopoverPlacement.Left, PopoverAlignment.End, true) to IntOffset(-45, 597),
        Triple(PopoverPlacement.Left, PopoverAlignment.End, false) to IntOffset(415, 634),
        Triple(PopoverPlacement.Bottom, PopoverAlignment.Start, true) to IntOffset(495, 724),
        Triple(PopoverPlacement.Bottom, PopoverAlignment.Start, false) to IntOffset(495, 724),
        Triple(PopoverPlacement.Bottom, PopoverAlignment.End, true) to IntOffset(246, 724),
        Triple(PopoverPlacement.Bottom, PopoverAlignment.End, false) to IntOffset(706, 724),
        Triple(PopoverPlacement.Top, PopoverAlignment.Start, true) to IntOffset(495, 445),
        Triple(PopoverPlacement.Top, PopoverAlignment.Start, false) to IntOffset(495, 482),
        Triple(PopoverPlacement.Top, PopoverAlignment.End, true) to IntOffset(246, 445),
        Triple(PopoverPlacement.Top, PopoverAlignment.End, false) to IntOffset(706, 482)
    )

    @Test
    fun caretTipPositionProvider_returnsCorrectPosition() {
        forEachParameter(
            PopoverPlacement.entries.toTypedArray(),
            PopoverAlignment.entries.toTypedArray(),
            arrayOf(true, false),
        ) { placement, alignment, isSmall ->
            val provider = PopoverPositionProvider(
                placement,
                alignment,
                density,
            )
            val expected = expectedResults[Triple(placement, alignment, isSmall)]
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
