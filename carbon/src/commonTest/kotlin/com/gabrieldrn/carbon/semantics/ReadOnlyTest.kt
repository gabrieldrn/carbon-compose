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

package com.gabrieldrn.carbon.semantics

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.common.semantics.readOnly
import kotlin.test.Test

class ReadOnlyTest {

    @Test
    fun readOnly_validateSemantics() = runComposeUiTest {
        setContent {
            val interactionSource = remember { MutableInteractionSource() }
            Box(
                modifier = Modifier
                    .readOnly(
                        role = Role.Checkbox,
                        state = ToggleableState.Indeterminate,
                        interactionSource = interactionSource,
                        mergeDescendants = true
                    )
                    .testTag("ReadOnly")
            )
        }

        onNodeWithTag("ReadOnly")
            .assertIsReadOnly {
                "The node misses the expected semantics for a read-only component."
            }
    }
}
