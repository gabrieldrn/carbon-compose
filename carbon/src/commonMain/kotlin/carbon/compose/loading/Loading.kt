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

package carbon.compose.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import carbon.compose.foundation.color.LocalCarbonTheme

private const val SPINNER_FILL = .81f
private const val SPINNER_SMALL_FILL = .75f
private val SPINNER_SIZE = 88.dp
private val SPINNER_SMALL_SIZE = 16.dp
private val SPINNER_STROKE_WIDTH = 10.dp
private val SPINNER_SMALL_STROKE_WIDTH = 3.dp

/**
 * # Loading - Large
 *
 * Loading spinners are used when retrieving data or performing slow computations, and help to
 * notify users that loading is underway.
 *
 * (From [Dropdown documentation](https://carbondesignsystem.com/components/loading/usage))
 */
@Composable
public fun Loading(modifier: Modifier = Modifier) {
    val loadingColor = LocalCarbonTheme.current.interactive

    val infiniteTransition = rememberInfiniteTransition(label = "carbon_loading")
    val rotationZ by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 690, easing = LinearEasing),
        ),
        label = "carbon_loading_rotation"
    )

    Canvas(modifier = modifier.requiredSize(SPINNER_SIZE)) {
        inset(SPINNER_STROKE_WIDTH.toPx() * .5f) {
            rotate(rotationZ) {
                drawArc(
                    color = loadingColor,
                    startAngle = 0f,
                    sweepAngle = 360f * SPINNER_FILL,
                    useCenter = false,
                    style = Stroke(SPINNER_STROKE_WIDTH.toPx())
                )
            }
        }
    }
}

/**
 * # Loading - Small
 *
 * Loading spinners are used when retrieving data or performing slow computations, and help to
 * notify users that loading is underway.
 *
 * (From [Dropdown documentation](https://carbondesignsystem.com/components/loading/usage))
 */
@Composable
public fun SmallLoading(modifier: Modifier = Modifier) {
    val loadingColor = LocalCarbonTheme.current.interactive
    val backgroundColor = LocalCarbonTheme.current.layerAccent01

    val infiniteTransition = rememberInfiniteTransition(label = "carbon_small_loading")
    val rotationZ by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 690, easing = LinearEasing),
        ),
        label = "carbon_small_loading_rotation"
    )

    Canvas(modifier = modifier.requiredSize(SPINNER_SMALL_SIZE)) {
        inset(SPINNER_SMALL_STROKE_WIDTH.toPx() * .5f) {
            drawCircle(
                color = backgroundColor,
                style = Stroke(SPINNER_SMALL_STROKE_WIDTH.toPx())
            )
            rotate(rotationZ) {
                drawArc(
                    color = loadingColor,
                    startAngle = 0f,
                    sweepAngle = 360f * SPINNER_SMALL_FILL,
                    useCenter = false,
                    style = Stroke(SPINNER_SMALL_STROKE_WIDTH.toPx())
                )
            }
        }
    }
}
