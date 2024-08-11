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

package com.gabrieldrn.carbon.textinput

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag

@Suppress("NestedBlockDepth")
fun ComposeUiTest.runGlobalTextInputLayoutAssertions(
    label: String,
    helperText: String,
    state: TextInputState,
) {
    onNodeWithTag(TextInputTestTags.LABEL, useUnmergedTree = true).run {
        assertIsDisplayed()
        assertTextEquals(label)
    }

    //Field value
    onNodeWithTag(TextInputTestTags.FIELD, useUnmergedTree = true).assertIsDisplayed()

    if (helperText.isNotEmpty()) {
        onNodeWithTag(TextInputTestTags.HELPER_TEXT, useUnmergedTree = true).run {
            assertIsDisplayed()
            assertTextEquals(helperText)
        }
    }

    when (state) {
        TextInputState.Enabled -> {}
        TextInputState.Warning ->
            onNodeWithTag(TextInputTestTags.STATE_ICON_WARNING, useUnmergedTree = true)
                .assertIsDisplayed()
        TextInputState.Error ->
            onNodeWithTag(TextInputTestTags.STATE_ICON_ERROR, useUnmergedTree = true)
                .assertIsDisplayed()
        TextInputState.Disabled -> {}
        TextInputState.ReadOnly -> {}
    }
}
