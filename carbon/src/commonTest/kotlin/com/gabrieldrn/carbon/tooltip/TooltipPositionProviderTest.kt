package com.gabrieldrn.carbon.tooltip

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import com.gabrieldrn.carbon.forEachParameter
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import kotlin.test.Test
import kotlin.test.assertEquals

class TooltipPositionProviderTest {

    private val density = Density(1f)
    private val anchorBounds = IntRect(499, 576, 782, 720)
    private val windowSize = IntSize(1280, 2628)
    private val singleLinePopupContentSize = IntSize(540, 127)
    private val multiLinePopupContentSize = IntSize(80, 90)
    private val caretSize = SpacingScale.spacing02
    private val tooltipContentPaddingValues = PaddingValues(
        horizontal = SpacingScale.spacing05,
        vertical = SpacingScale.spacing01
    )

    private val expectedResults = mapOf(
        Triple(TooltipPlacement.Right, TooltipAlignment.Start, true) to IntOffset(786, 584),
        Triple(TooltipPlacement.Right, TooltipAlignment.Start, false) to IntOffset(786, 634),
        Triple(TooltipPlacement.Right, TooltipAlignment.Center, true) to IntOffset(786, 584),
        Triple(TooltipPlacement.Right, TooltipAlignment.Center, false) to IntOffset(786, 603),
        Triple(TooltipPlacement.Right, TooltipAlignment.End, true) to IntOffset(786, 584),
        Triple(TooltipPlacement.Right, TooltipAlignment.End, false) to IntOffset(786, 572),
        Triple(TooltipPlacement.Left, TooltipAlignment.Start, true) to IntOffset(-45, 584),
        Triple(TooltipPlacement.Left, TooltipAlignment.Start, false) to IntOffset(415, 634),
        Triple(TooltipPlacement.Left, TooltipAlignment.Center, true) to IntOffset(-45, 584),
        Triple(TooltipPlacement.Left, TooltipAlignment.Center, false) to IntOffset(415, 603),
        Triple(TooltipPlacement.Left, TooltipAlignment.End, true) to IntOffset(-45, 584),
        Triple(TooltipPlacement.Left, TooltipAlignment.End, false) to IntOffset(415, 572),
        Triple(TooltipPlacement.Bottom, TooltipAlignment.Start, true) to IntOffset(612, 724),
        Triple(TooltipPlacement.Bottom, TooltipAlignment.Start, false) to IntOffset(612, 724),
        Triple(TooltipPlacement.Bottom, TooltipAlignment.Center, true) to IntOffset(371, 724),
        Triple(TooltipPlacement.Bottom, TooltipAlignment.Center, false) to IntOffset(600, 724),
        Triple(TooltipPlacement.Bottom, TooltipAlignment.End, true) to IntOffset(129, 724),
        Triple(TooltipPlacement.Bottom, TooltipAlignment.End, false) to IntOffset(589, 724),
        Triple(TooltipPlacement.Top, TooltipAlignment.Start, true) to IntOffset(612, 445),
        Triple(TooltipPlacement.Top, TooltipAlignment.Start, false) to IntOffset(612, 482),
        Triple(TooltipPlacement.Top, TooltipAlignment.Center, true) to IntOffset(371, 445),
        Triple(TooltipPlacement.Top, TooltipAlignment.Center, false) to IntOffset(600, 482),
        Triple(TooltipPlacement.Top, TooltipAlignment.End, true) to IntOffset(129, 445),
        Triple(TooltipPlacement.Top, TooltipAlignment.End, false) to IntOffset(589, 482)
    )

    @Test
    fun positionProvider_returnsCorrectPosition() {
        forEachParameter(
            TooltipPlacement.entries.toTypedArray(),
            TooltipAlignment.entries.toTypedArray(),
            arrayOf(true, false),
        ) { placement, alignment, isSingleLine ->
            val provider = TooltipPositionProvider(
                placement,
                alignment,
                caretSize,
                tooltipContentPaddingValues,
                isSingleLine,
                density,
            )
            val expected = expectedResults[Triple(placement, alignment, isSingleLine)]
            val actual = provider.calculatePosition(
                anchorBounds,
                windowSize,
                LayoutDirection.Ltr,
                if (isSingleLine) singleLinePopupContentSize else multiLinePopupContentSize
            )
            assertEquals(expected, actual)
        }
    }
}
