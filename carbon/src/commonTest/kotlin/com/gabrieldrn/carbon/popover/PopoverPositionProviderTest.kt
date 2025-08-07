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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import com.gabrieldrn.carbon.forEachParameter
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.popover.carettip.PopoverCaretTipAlignment
import com.gabrieldrn.carbon.popover.carettip.PopoverCaretTipPlacement
import com.gabrieldrn.carbon.popover.carettip.PopoverCaretTipPositionProvider
import kotlin.test.Test
import kotlin.test.assertEquals

class PopoverPositionProviderTest {

    private val density = Density(1f)
    private val anchorBounds = IntRect(499, 576, 782, 720)
    private val windowSize = IntSize(1280, 2628)
    private val smallPopupContentSize = IntSize(540, 127)
    private val bigPopupContentSize = IntSize(80, 90)
    private val caretSize = SpacingScale.spacing02
    private val tooltipContentPaddingValues = PaddingValues(
        horizontal = SpacingScale.spacing05,
        vertical = SpacingScale.spacing01
    )

    @Suppress("MaxLineLength")
    private val expectedResults = mapOf(
        Triple(PopoverCaretTipPlacement.Right, PopoverCaretTipAlignment.Start, true) to IntOffset(786, 584),
        Triple(PopoverCaretTipPlacement.Right, PopoverCaretTipAlignment.Start, false) to IntOffset(786, 634),
        Triple(PopoverCaretTipPlacement.Right, PopoverCaretTipAlignment.Center, true) to IntOffset(786, 584),
        Triple(PopoverCaretTipPlacement.Right, PopoverCaretTipAlignment.Center, false) to IntOffset(786, 603),
        Triple(PopoverCaretTipPlacement.Right, PopoverCaretTipAlignment.End, true) to IntOffset(786, 584),
        Triple(PopoverCaretTipPlacement.Right, PopoverCaretTipAlignment.End, false) to IntOffset(786, 572),
        Triple(PopoverCaretTipPlacement.Left, PopoverCaretTipAlignment.Start, true) to IntOffset(-45, 584),
        Triple(PopoverCaretTipPlacement.Left, PopoverCaretTipAlignment.Start, false) to IntOffset(415, 634),
        Triple(PopoverCaretTipPlacement.Left, PopoverCaretTipAlignment.Center, true) to IntOffset(-45, 584),
        Triple(PopoverCaretTipPlacement.Left, PopoverCaretTipAlignment.Center, false) to IntOffset(415, 603),
        Triple(PopoverCaretTipPlacement.Left, PopoverCaretTipAlignment.End, true) to IntOffset(-45, 584),
        Triple(PopoverCaretTipPlacement.Left, PopoverCaretTipAlignment.End, false) to IntOffset(415, 572),
        Triple(PopoverCaretTipPlacement.Bottom, PopoverCaretTipAlignment.Start, true) to IntOffset(612, 724),
        Triple(PopoverCaretTipPlacement.Bottom, PopoverCaretTipAlignment.Start, false) to IntOffset(612, 724),
        Triple(PopoverCaretTipPlacement.Bottom, PopoverCaretTipAlignment.Center, true) to IntOffset(371, 724),
        Triple(PopoverCaretTipPlacement.Bottom, PopoverCaretTipAlignment.Center, false) to IntOffset(600, 724),
        Triple(PopoverCaretTipPlacement.Bottom, PopoverCaretTipAlignment.End, true) to IntOffset(129, 724),
        Triple(PopoverCaretTipPlacement.Bottom, PopoverCaretTipAlignment.End, false) to IntOffset(589, 724),
        Triple(PopoverCaretTipPlacement.Top, PopoverCaretTipAlignment.Start, true) to IntOffset(612, 445),
        Triple(PopoverCaretTipPlacement.Top, PopoverCaretTipAlignment.Start, false) to IntOffset(612, 482),
        Triple(PopoverCaretTipPlacement.Top, PopoverCaretTipAlignment.Center, true) to IntOffset(371, 445),
        Triple(PopoverCaretTipPlacement.Top, PopoverCaretTipAlignment.Center, false) to IntOffset(600, 482),
        Triple(PopoverCaretTipPlacement.Top, PopoverCaretTipAlignment.End, true) to IntOffset(129, 445),
        Triple(PopoverCaretTipPlacement.Top, PopoverCaretTipAlignment.End, false) to IntOffset(589, 482)
    )

    @Test
    fun positionProvider_returnsCorrectPosition() {
        forEachParameter(
            PopoverCaretTipPlacement.entries.toTypedArray(),
            PopoverCaretTipAlignment.entries.toTypedArray(),
            arrayOf(true, false),
        ) { placement, alignment, isSmall ->
            val provider = PopoverCaretTipPositionProvider(
                placement,
                alignment,
                caretSize,
                tooltipContentPaddingValues,
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
