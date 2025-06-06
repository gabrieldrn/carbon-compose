package com.gabrieldrn.carbon.tooltip

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import kotlin.test.Test
import kotlin.test.assertEquals

class TooltipPositionProviderTest {

    private val density = Density(1f)
    private val anchorBounds = IntRect(10, 20, 50, 60)
    private val windowSize = IntSize(200, 200)
    private val popupContentSize = IntSize(40, 20)

    @Test
    fun positionProvider_rightPlacement_returnsCorrectPosition() {
        val provider = TooltipPositionProvider(density, TooltipPlacement.Right)
        val expected = IntOffset(x = 54, y = 30)
        val actual = provider.calculatePosition(
            anchorBounds,
            windowSize,
            LayoutDirection.Ltr,
            popupContentSize
        )
        assertEquals(expected, actual)
    }

    @Test
    fun positionProvider_leftPlacement_returnsCorrectPosition() {
        val provider = TooltipPositionProvider(density, TooltipPlacement.Left)
        val expected = IntOffset(x = -34, y = 30)
        val actual = provider.calculatePosition(
            anchorBounds,
            windowSize,
            LayoutDirection.Ltr,
            popupContentSize
        )
        assertEquals(expected, actual)
    }

    @Test
    fun positionProvider_bottomPlacement_returnsCorrectPosition() {
        val provider = TooltipPositionProvider(density, TooltipPlacement.Bottom)
        val expected = IntOffset(x = 10, y = 64)
        val actual = provider.calculatePosition(
            anchorBounds,
            windowSize,
            LayoutDirection.Ltr,
            popupContentSize
        )
        assertEquals(expected, actual)
    }

    @Test
    fun positionProvider_topPlacement_returnsCorrectPosition() {
        val provider = TooltipPositionProvider(density, TooltipPlacement.Top)
        val expected = IntOffset(x = 10, y = -4)
        val actual = provider.calculatePosition(
            anchorBounds,
            windowSize,
            LayoutDirection.Ltr,
            popupContentSize
        )
        assertEquals(expected, actual)
    }
}
