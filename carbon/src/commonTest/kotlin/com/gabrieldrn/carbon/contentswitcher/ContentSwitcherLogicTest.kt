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

package com.gabrieldrn.carbon.contentswitcher

import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.contentswitcher.domain.rememberDisplayContentSwitcherButtonDividerState
import kotlin.test.Test
import kotlin.test.assertEquals

class ContentSwitcherLogicTest {

    @Test
    fun dividerState_noHover_noSelection() = runComposeUiTest {
        val hoverStateMap = mutableStateMapOf<Int, HoverInteraction.Enter>()
        val buttonIndex = 1
        val selectedButtonIndex = 2

        setContent {
            val state = rememberDisplayContentSwitcherButtonDividerState(
                buttonIndex = buttonIndex,
                selectedButtonIndex = selectedButtonIndex,
                hoverStateMap = hoverStateMap
            )
            assertEquals(true, state.value)
        }
    }

    @Test
    fun dividerState_hoverNearby() = runComposeUiTest {
        val hoverStateMap = mutableStateMapOf<Int, HoverInteraction.Enter>()
        hoverStateMap[0] = HoverInteraction.Enter()
        val buttonIndex = 1
        val selectedButtonIndex = 2

        setContent {
            val state = rememberDisplayContentSwitcherButtonDividerState(
                buttonIndex = buttonIndex,
                selectedButtonIndex = selectedButtonIndex,
                hoverStateMap = hoverStateMap
            )
            assertEquals(false, state.value)
        }
    }

    @Test
    fun dividerState_buttonSelected() = runComposeUiTest {
        val hoverStateMap = mutableStateMapOf<Int, HoverInteraction.Enter>()
        val buttonIndex = 1
        val selectedButtonIndex = 1

        setContent {
            val state = rememberDisplayContentSwitcherButtonDividerState(
                buttonIndex = buttonIndex,
                selectedButtonIndex = selectedButtonIndex,
                hoverStateMap = hoverStateMap
            )
            assertEquals(false, state.value)
        }
    }

    @Test
    fun dividerState_previousButtonSelected() = runComposeUiTest {
        val hoverStateMap = mutableStateMapOf<Int, HoverInteraction.Enter>()
        val buttonIndex = 2
        val selectedButtonIndex = 1

        setContent {
            val state = rememberDisplayContentSwitcherButtonDividerState(
                buttonIndex = buttonIndex,
                selectedButtonIndex = selectedButtonIndex,
                hoverStateMap = hoverStateMap
            )
            assertEquals(false, state.value)
        }
    }
}
