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

package com.gabrieldrn.carbon.progressbar

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

@Composable
internal fun ProgressBarTrack(
    size: ProgressBarSize,
    value: Float,
    modifier: Modifier = Modifier,
    state: ProgressBarState = ProgressBarState.Active,
    colors: ProgressBarColors = ProgressBarColors.colors()
) {
    val theme = Carbon.theme
    val trackColor by colors.trackColor(state)

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(
                when (size) {
                    ProgressBarSize.Big -> SpacingScale.spacing03
                    ProgressBarSize.Small -> SpacingScale.spacing02
                }
            )
    ) {
        drawRect(color = theme.borderSubtle00)
        drawTrack(
            trackHead = if (state == ProgressBarState.Active) value else 1f,
            trackTail = 0f,
            color = trackColor
        )
    }
}

private const val IndeterminateDurationAnimation = 1400
private val IndeterminateTrackDuration = (IndeterminateDurationAnimation * .75f * .25f).toInt()
private val IndeterminateAnimationDelay = IndeterminateTrackDuration

@Composable
internal fun IndeterminateProgressBarTrack(
    size: ProgressBarSize,
    modifier: Modifier = Modifier
) {
    val theme = Carbon.theme
    val intederminateInfiniteTransition = rememberInfiniteTransition()

    val trackHead by intederminateInfiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = IndeterminateDurationAnimation
                0f at 0 + IndeterminateAnimationDelay
                1f at IndeterminateDurationAnimation - IndeterminateTrackDuration
            }
        ),
        label = "TrackHead"
    )

    val trackTail by intederminateInfiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = IndeterminateDurationAnimation
                0f at IndeterminateAnimationDelay + IndeterminateTrackDuration
                1f at IndeterminateDurationAnimation
            }
        ),
        label = "TrackTail"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(
                when (size) {
                    ProgressBarSize.Big -> SpacingScale.spacing03
                    ProgressBarSize.Small -> SpacingScale.spacing02
                }
            )
    ) {
        drawRect(color = theme.borderSubtle00)
        drawTrack(
            trackHead = trackHead,
            trackTail = trackTail,
            color = theme.borderInteractive
        )
    }
}

private fun DrawScope.drawTrack(
    trackHead: Float,
    trackTail: Float,
    color: Color
) {
    drawLine(
        color = color,
        start = Offset(trackTail * this.size.width, this.size.height / 2),
        end = Offset(trackHead * this.size.width, this.size.height / 2),
        strokeWidth = this.size.height
    )
}
