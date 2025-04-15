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

package com.gabrieldrn.carbon.common.molecules

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.motion.Motion
import com.gabrieldrn.carbon.icons.ChevronDownIcon

private const val CHEVRON_ROTATION_ANGLE = 180f

private val defaultTransitionSpec: TweenSpec<Float> = tween(
    durationMillis = Motion.Duration.moderate01,
    easing = Motion.Standard.productiveEasing
)

@Composable
internal fun AnimatedChevronDownIcon(
    rotateToUp: Boolean,
    modifier: Modifier = Modifier,
    tint: Color = Carbon.theme.iconPrimary,
    size: Dp = 16.dp,
    transitionSpec: TweenSpec<Float> = defaultTransitionSpec
) {
    val expandedStates = remember { MutableTransitionState(rotateToUp) }
    val expandTransition = rememberTransition(expandedStates, "Rotating chevron")

    LaunchedEffect(rotateToUp) {
        expandedStates.targetState = rotateToUp
    }

    AnimatedChevronDownIcon(
        animationTransition = expandTransition,
        tint = tint,
        size = size,
        transitionSpec = transitionSpec,
        modifier = modifier,
    )
}

@Composable
internal fun AnimatedChevronDownIcon(
    animationTransition: Transition<Boolean>,
    modifier: Modifier = Modifier,
    tint: Color = Carbon.theme.iconPrimary,
    size: Dp = 16.dp,
    transitionSpec: TweenSpec<Float> = defaultTransitionSpec
) {
    val chevronRotation by animationTransition.animateFloat(
        transitionSpec = { transitionSpec },
        label = "Chevron rotation"
    ) {
        if (it) CHEVRON_ROTATION_ANGLE else 0f
    }

    ChevronDownIcon(
        tint = tint,
        size = size,
        modifier = modifier
            .graphicsLayer {
                rotationZ = chevronRotation
            }
    )
}
