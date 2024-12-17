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

package com.gabrieldrn.carbon.contentswitcher.domain

import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap

@Composable
internal fun rememberDisplayContentSwitcherButtonDividerState(
    buttonIndex: Int,
    selectedButtonIndex: Int,
    hoverStateMap: SnapshotStateMap<Int, HoverInteraction.Enter>
): MutableState<Boolean> {
    // Hide if the current or the previous button is hovered.
    val hasHoverNearby =
        hoverStateMap[buttonIndex - 1] != null || hoverStateMap[buttonIndex] != null

    return remember(buttonIndex, selectedButtonIndex, hasHoverNearby) {
        mutableStateOf(
            buttonIndex != selectedButtonIndex &&
                buttonIndex - 1 != selectedButtonIndex &&
                !hasHoverNearby
        )
    }
}
