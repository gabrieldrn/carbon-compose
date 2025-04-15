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

package com.gabrieldrn.carbon.toggle

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.test.semantics.isReadOnly
import kotlin.test.Test
import kotlin.test.assertTrue

class ToggleTest {

    @Test
    fun toggle_default_validateLayout() = runComposeUiTest {
        setContent {
            Toggle(
                isToggled = false,
                onToggleChange = {},
                modifier = Modifier.testTag("Toggle")
            )
        }

        onNodeWithTag("Toggle")
            .assertExists()
            .assertWidthIsEqualTo(48.dp)
            .assertHeightIsEqualTo(24.dp)
    }

    @Test
    fun toggle_small_validateLayout() = runComposeUiTest {
        setContent {
            SmallToggle(
                isToggled = false,
                onToggleChange = {},
                modifier = Modifier.testTag("Toggle")
            )
        }

        onNodeWithTag("Toggle")
            .assertExists()
            .assertWidthIsEqualTo(32.dp)
            .assertHeightIsEqualTo(16.dp)
    }

    @Test
    fun toggle_validateSemantics() = runComposeUiTest {
        var isToggled by mutableStateOf(false)
        setContent {
            Column {
                Toggle(
                    isToggled = isToggled,
                    onToggleChange = {},
                    modifier = Modifier.testTag("Toggle")
                )
                SmallToggle(
                    isToggled = isToggled,
                    onToggleChange = {},
                    modifier = Modifier.testTag("Toggle")
                )
            }
        }

        onAllNodesWithTag("Toggle").run {
            assertAll(
                hasClickAction() and
                    SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Switch) and
                    isFocusable()
            )

            isToggled = true
            assertAll(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.ToggleableState,
                    ToggleableState.On
                )
            )

            isToggled = false
            assertAll(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.ToggleableState,
                    ToggleableState.Off
                )
            )
        }
    }

    @Test
    fun toggle_readOnly_validateSemantics() = runComposeUiTest {
        var isToggled by mutableStateOf(false)
        setContent {
            Column {
                Toggle(
                    isToggled = isToggled,
                    onToggleChange = {},
                    modifier = Modifier.testTag("Toggle"),
                    isReadOnly = true
                )
                SmallToggle(
                    isToggled = isToggled,
                    onToggleChange = {},
                    modifier = Modifier.testTag("Toggle"),
                    isReadOnly = true
                )
            }
        }

        onAllNodesWithTag("Toggle").run {
            assertAll(
                SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Switch)
                    and isReadOnly()
            )
        }
    }

    @Test
    fun toggle_validateInteraction() = runComposeUiTest {
        var isToggled by mutableStateOf(false)
        setContent {
            Toggle(
                isToggled = isToggled,
                onToggleChange = { isToggled = it },
                modifier = Modifier.testTag("Toggle")
            )
        }

        onNodeWithTag("Toggle").run {
            assert(
                hasClickAction() and
                    SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Switch) and
                    isFocusable()
            )

            performClick()
            assert(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.ToggleableState,
                    ToggleableState.On
                )
            )
            assertTrue(isToggled)
        }
    }
}
