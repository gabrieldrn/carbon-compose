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

package carbon.compose.toggle.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import carbon.compose.toggle.Toggle
import carbon.compose.toggle.ToggleType
import carbon.compose.toggle.toggleCheckmarkIconHeight
import carbon.compose.toggle.toggleCheckmarkIconWidth

/**
 * Values used to draw a [Toggle].
 */
@Immutable
internal data class ToggleDrawValues(
    val handleSize: Float,
    val toggleHeight: Float,
    val toggleWidth: Float,
    val handleCheckmarkOffset: Offset,
    val handleYOffPos: Float,
    val handleXOnPos: Float,
) {
    @Composable
    fun handleXPos(isToggled: Boolean): State<Float> =
        rememberUpdatedState(
            newValue = if (isToggled) handleXOnPos else handleYOffPos
        )

    companion object {

        fun buildValues(toggleType: ToggleType, density: Density) = with(density) {
            val handleSize = toggleType.handleSize.toPx()
            val toggleHeight = toggleType.height.toPx()
            val toggleWidth = toggleType.width.toPx()
            val handleYOffPos = (toggleHeight - handleSize) * .5f
            ToggleDrawValues(
                handleSize = handleSize,
                toggleHeight = toggleHeight,
                toggleWidth = toggleWidth,
                handleCheckmarkOffset = Offset(
                    x = (handleSize - toggleCheckmarkIconWidth.toPx()) * .5f,
                    y = (handleSize - toggleCheckmarkIconHeight.toPx()) * .5f,
                ),
                handleYOffPos = handleYOffPos,
                handleXOnPos = toggleWidth - handleSize - handleYOffPos,
            )
        }
    }
}
