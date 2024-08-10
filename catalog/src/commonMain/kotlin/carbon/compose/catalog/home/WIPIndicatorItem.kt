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

package carbon.compose.catalog.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import carbon.compose.Carbon
import carbon.compose.catalog.Res
import carbon.compose.catalog.ic_arrow_down
import carbon.compose.catalog.ic_arrow_right
import carbon.compose.catalog.pattern_diagonal_stripes
import carbon.compose.foundation.spacing.SpacingScale
import org.jetbrains.compose.resources.painterResource

private const val wipItemVerticalRotation = -90f
private const val wipBackgroundPatternSize = 40f

@Composable
fun WIPIndicatorItem(
    isInVerticalLayout: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .then(
                if (isInVerticalLayout) Modifier
                    .padding(vertical = SpacingScale.spacing05)
                    .fillMaxWidth()
                else Modifier
                    .padding(horizontal = SpacingScale.spacing05)
                    .fillMaxHeight()
            )
            .wipBackground()
            .padding(SpacingScale.spacing05),
        contentAlignment = Alignment.Center
    ) {
        @Composable
        fun content() {
            val arrow = painterResource(
                if (isInVerticalLayout) Res.drawable.ic_arrow_down else Res.drawable.ic_arrow_right
            )
            Image(
                painter = arrow,
                colorFilter = ColorFilter.tint(Carbon.theme.textPrimary),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            BasicText(
                text = "TO BE IMPLEMENTED",
                style = Carbon.typography.code02.copy(color = Carbon.theme.textPrimary),
                modifier = Modifier.then(
                    if (isInVerticalLayout) Modifier else Modifier.rotateVertical()
                )
            )
            Image(
                painter = arrow,
                colorFilter = ColorFilter.tint(Carbon.theme.textPrimary),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }

        if (isInVerticalLayout) {
            Row(
                modifier = Modifier
                    .composed { background(Carbon.theme.background.copy(alpha = .9f)) }
                    .padding(SpacingScale.spacing03),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    SpacingScale.spacing03,
                    Alignment.CenterHorizontally
                )
            ) {
                content()
            }
        } else {
            Column(
                modifier = Modifier
                    .composed { background(Carbon.theme.background.copy(alpha = .9f)) }
                    .padding(SpacingScale.spacing03),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    SpacingScale.spacing03,
                    Alignment.CenterVertically
                )
            ) {
                content()
            }
        }
    }
}

private fun Modifier.rotateVertical(): Modifier = this
    .layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.height, placeable.width) {
            placeable.place(
                x = -(placeable.width / 2 - placeable.height / 2),
                y = -(placeable.height / 2 - placeable.width / 2)
            )
        }
    }
    .rotate(wipItemVerticalRotation)

private fun Modifier.wipBackground(): Modifier = this.composed {
    val backgroundInverse = Carbon.theme.backgroundInverse
    val density = LocalDensity.current
    val pattern = painterResource(resource = Res.drawable.pattern_diagonal_stripes)
    val brush = remember {
        val intSize = with(density) { wipBackgroundPatternSize.dp.toPx() }

        val bitmap = ImageBitmap(intSize.toInt(), intSize.toInt())
        val canvas = Canvas(bitmap)

        CanvasDrawScope().draw(
            density = density,
            layoutDirection = LayoutDirection.Ltr,
            canvas = canvas,
            size = Size(intSize, intSize)
        ) {
            pattern.run {
                draw(this@draw.size, 1f, ColorFilter.tint(backgroundInverse))
            }
        }

        ShaderBrush(ImageShader(bitmap, TileMode.Repeated, TileMode.Repeated))
    }

    background(Carbon.theme.supportWarning).background(brush)
}
