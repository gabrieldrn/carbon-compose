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

package com.gabrieldrn.carbon.catalog.home

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.catalog.Destination
import com.gabrieldrn.carbon.catalog.Res
import com.gabrieldrn.carbon.catalog.picto_construction_worker
import com.gabrieldrn.carbon.foundation.color.containerBackground
import com.gabrieldrn.carbon.foundation.motion.Motion
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

private val itemAppearanceAnimationSpec = tween<Float>(
    delayMillis = Motion.Duration.moderate01,
    durationMillis = Motion.Duration.slow01,
    easing = Motion.Entrance.expressiveEasing
)
private val itemInitOffset = -16f

@Composable
fun CarbonComponentGridTile(
    destination: Destination,
    onTileClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var yOffsetDp by remember { mutableFloatStateOf(itemInitOffset) }
    var alpha by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(destination) {
        launch {
            animate(
                initialValue = itemInitOffset,
                targetValue = 0f,
                animationSpec = itemAppearanceAnimationSpec
            ) { value, _ ->
                yOffsetDp = value
            }
        }
        launch {
            animate(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = itemAppearanceAnimationSpec
            ) { value, _ ->
                alpha = value
            }
        }
    }
    Box(
        modifier = modifier
            .clickable(onClick = onTileClicked)
            .containerBackground()
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationY = yOffsetDp.dp.toPx()
                    this.alpha = alpha
                }
        ) {
            if (destination.illustration != null) {
                Image(
                    painter = painterResource(destination.illustration),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                PlaceholderIllustration()
            }
            BasicText(
                text = destination.title,
                style = Carbon.typography.body01.copy(
                    color = Carbon.theme.textPrimary
                ),
                modifier = Modifier.padding(SpacingScale.spacing05)
            )
        }
    }
}

@Composable
fun PlaceholderIllustration(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(SpacingScale.spacing05),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painterResource(Res.drawable.picto_construction_worker),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Carbon.theme.iconPrimary),
            modifier = Modifier.size(48.dp)
        )
    }
}
