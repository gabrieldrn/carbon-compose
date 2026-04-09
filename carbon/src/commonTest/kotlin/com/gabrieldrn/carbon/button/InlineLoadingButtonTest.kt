/*
 * Copyright 2026 Gabriel Derrien
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

package com.gabrieldrn.carbon.button

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.icons.checkmarkFilledIcon
import com.gabrieldrn.carbon.loading.inlineloading.InlineLoadingStatus
import com.gabrieldrn.carbon.loading.inlineloading.InlineLoadingTestTags
import kotlin.test.Test
import kotlin.test.assertEquals

class InlineLoadingButtonTest {

    @Test
    fun inlineLoadingButton_inactive_replacedByActiveLoading() = runComposeUiTest {
        var status by mutableStateOf(InlineLoadingStatus.Inactive)
        var clickCount by mutableIntStateOf(0)

        setContent {
            CarbonDesignSystem {
                InlineLoadingButton(
                    label = "Submit",
                    onClick = {
                        clickCount++
                        status = InlineLoadingStatus.Active
                    },
                    status = status,
                    inlineLoadingLabel = "Submitting...",
                    modifier = Modifier.testTag("inline-loading-button")
                )
            }
        }

        onNodeWithText("Submit").run {
            assertHasClickAction()
            assert(
                SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button) and
                    isEnabled() and
                    isFocusable()
            )
        }

        onNodeWithTag("carbon_button_label", useUnmergedTree = true)
            .assertTextEquals("Submit")

        onNodeWithText("Submit").performClick()
        assertEquals(1, clickCount)

        onNodeWithTag("inline-loading-button")
            .assertHasNoClickAction()
            .assertHeightIsEqualTo(ButtonSize.LargeProductive.heightDp())

        onNodeWithTag("carbon_button_label", useUnmergedTree = true)
            .assertDoesNotExist()

        onNodeWithTag(InlineLoadingTestTags.TEXT, useUnmergedTree = true)
            .assertIsDisplayed()
            .assertTextEquals("Submitting...")

        onNodeWithTag(InlineLoadingTestTags.ICON, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun inlineLoadingButton_loadingState_preservesExplicitSizeAndShowsFinishedState() = runComposeUiTest {
        var status by mutableStateOf(InlineLoadingStatus.Inactive)

        setContent {
            CarbonDesignSystem {
                InlineLoadingButton(
                    label = "Save",
                    onClick = {},
                    status = status,
                    inlineLoadingLabel = "Saving...",
                    modifier = Modifier
                        .testTag("inline-loading-button")
                        .assertedWidth(),
                    buttonSize = ButtonSize.Medium,
                )
            }
        }

        status = InlineLoadingStatus.Active

        onNodeWithTag("inline-loading-button")
            .assertHasNoClickAction()
            .assertHeightIsEqualTo(ButtonSize.Medium.heightDp())
            .assertWidthIsEqualTo(280.dp)

        status = InlineLoadingStatus.Finished

        onNodeWithTag(InlineLoadingTestTags.TEXT, useUnmergedTree = true)
            .assertTextEquals("Saving...")

        onNodeWithTag(checkmarkFilledIcon.name, useUnmergedTree = true)
            .assertIsDisplayed()
    }
}

private fun Modifier.assertedWidth(): Modifier = width(280.dp)
