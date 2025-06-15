package com.gabrieldrn.carbon.tooltip

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import kotlin.test.Test
import kotlin.test.assertEquals

class TooltipPositionProviderTest {

    private val density = Density(1f)
    private val anchorBounds = IntRect(10, 20, 50, 60)
    private val windowSize = IntSize(200, 200)
    private val popupContentSize = IntSize(40, 20)
    private val caretSize = SpacingScale.spacing02
    private val tooltipContentPaddingValues = PaddingValues(
        horizontal = SpacingScale.spacing05,
        vertical = SpacingScale.spacing01
    )

    @Test
    fun positionProvider_rightCenter_returnsCorrectPosition() {
        val provider = TooltipPositionProvider(
            TooltipPlacement.Right,
            TooltipAlignment.Center,
            caretSize,
            tooltipContentPaddingValues,
            density,
        )
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
    fun positionProvider_leftCenter_returnsCorrectPosition() {
        val provider = TooltipPositionProvider(
            TooltipPlacement.Left,
            TooltipAlignment.Center,
            caretSize,
            tooltipContentPaddingValues,
            density,
        )
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
    fun positionProvider_bottomStart_returnsCorrectPosition() {
        val provider = TooltipPositionProvider(
            TooltipPlacement.Bottom,
            TooltipAlignment.Start,
            caretSize,
            tooltipContentPaddingValues,
            density,
        )
        val expected = IntOffset(x = 2, y = 64)
        val actual = provider.calculatePosition(
            anchorBounds,
            windowSize,
            LayoutDirection.Ltr,
            popupContentSize
        )
        assertEquals(expected, actual)
    }

    @Test
    fun positionProvider_bottomCenter_returnsCorrectPosition() {
        val provider = TooltipPositionProvider(
            TooltipPlacement.Bottom,
            TooltipAlignment.Center,
            caretSize,
            tooltipContentPaddingValues,
            density,
        )
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
    fun positionProvider_bottomEnd_returnsCorrectPosition() {
        val provider = TooltipPositionProvider(
            TooltipPlacement.Bottom,
            TooltipAlignment.End,
            caretSize,
            tooltipContentPaddingValues,
            density,
        )
        val expected = IntOffset(x = 18, y = 64)
        val actual = provider.calculatePosition(
            anchorBounds,
            windowSize,
            LayoutDirection.Ltr,
            popupContentSize
        )
        assertEquals(expected, actual)
    }

    @Test
    fun positionProvider_topStart_returnsCorrectPosition() {
        val provider = TooltipPositionProvider(
            TooltipPlacement.Top,
            TooltipAlignment.Start,
            caretSize,
            tooltipContentPaddingValues,
            density
        )
        val expected = IntOffset(x = 2, y = -4)
        val actual = provider.calculatePosition(
            anchorBounds,
            windowSize,
            LayoutDirection.Ltr,
            popupContentSize
        )
        assertEquals(expected, actual)
    }

    @Test
    fun positionProvider_topCenter_returnsCorrectPosition() {
        val provider = TooltipPositionProvider(
            TooltipPlacement.Top,
            TooltipAlignment.Center,
            caretSize,
            tooltipContentPaddingValues,
            density
        )
        val expected = IntOffset(x = 10, y = -4)
        val actual = provider.calculatePosition(
            anchorBounds,
            windowSize,
            LayoutDirection.Ltr,
            popupContentSize
        )
        assertEquals(expected, actual)
    }

    @Test
    fun positionProvider_topEnd_returnsCorrectPosition() {
        val provider = TooltipPositionProvider(
            TooltipPlacement.Top,
            TooltipAlignment.End,
            caretSize,
            tooltipContentPaddingValues,
            density
        )
        val expected = IntOffset(x = 18, y = -4)
        val actual = provider.calculatePosition(
            anchorBounds,
            windowSize,
            LayoutDirection.Ltr,
            popupContentSize
        )
        assertEquals(expected, actual)
    }
}
