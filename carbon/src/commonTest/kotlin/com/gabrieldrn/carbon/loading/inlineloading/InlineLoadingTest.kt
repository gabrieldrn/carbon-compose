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

package com.gabrieldrn.carbon.loading.inlineloading

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.icons.checkmarkFilledIcon
import com.gabrieldrn.carbon.icons.errorFilledIcon
import kotlin.test.Test

class InlineLoadingTest {

    private var _status by mutableStateOf(InlineLoadingStatus.Active)
    private var _label by mutableStateOf<String?>(null)
    private var _contentDescription by mutableStateOf<String?>(null)

    @Test
    fun inlineLoading_validateLayout() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                InlineLoading(
                    status = _status,
                    label = _label,
                    contentDescription = _contentDescription,
                )
            }
        }

        listOf("Loading", null).forEach { label ->
            InlineLoadingStatus.entries.forEach { status ->
                _label = label
                _status = status
                _contentDescription = null

                onNodeWithTag(InlineLoadingTestTags.CONTAINER).run {
                    assertExists()
                    if (label != null || status != InlineLoadingStatus.Inactive) {
                        assertIsDisplayed()
                    }
                }

                onNodeWithTag(InlineLoadingTestTags.TEXT, useUnmergedTree = true).run {
                    if (label != null) {
                        assertIsDisplayed()
                        assertTextEquals(label)
                    } else {
                        assertDoesNotExist()
                    }
                }

                onNodeWithTag(InlineLoadingTestTags.ICON, useUnmergedTree = true).run {
                    if (status == InlineLoadingStatus.Inactive) {
                        assertDoesNotExist()
                    } else {
                        assertIsDisplayed()
                    }
                }

                onNodeWithTag(checkmarkFilledIcon.name, useUnmergedTree = true).run {
                    if (status == InlineLoadingStatus.Finished) {
                        assertIsDisplayed()
                    } else {
                        assertDoesNotExist()
                    }
                }

                onNodeWithTag(errorFilledIcon.name, useUnmergedTree = true).run {
                    if (status == InlineLoadingStatus.Error) {
                        assertIsDisplayed()
                    } else {
                        assertDoesNotExist()
                    }
                }
            }
        }
    }

    @Test
    fun inlineLoading_accessibility_whenLabelOmitted_usesStatusDescription() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                InlineLoading(
                    status = _status,
                    label = _label,
                    contentDescription = _contentDescription,
                )
            }
        }

        _label = null
        _contentDescription = null

        mapOf(
            InlineLoadingStatus.Active to "loading",
            InlineLoadingStatus.Finished to "finished",
            InlineLoadingStatus.Error to "error",
        ).forEach { (status, expectedDescription) ->
            _status = status

            onNodeWithTag(InlineLoadingTestTags.ICON, useUnmergedTree = true)
                .assert(hasContentDescription(expectedDescription))
        }
    }

    @Test
    fun inlineLoading_accessibility_customDescriptionOverridesFallback() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                InlineLoading(
                    status = _status,
                    label = _label,
                    contentDescription = _contentDescription,
                )
            }
        }

        _label = null
        _contentDescription = "Submitting request"

        InlineLoadingStatus.entries
            .filter { it != InlineLoadingStatus.Inactive }
            .forEach { status ->
                _status = status

                onNodeWithTag(InlineLoadingTestTags.ICON, useUnmergedTree = true)
                    .assert(hasContentDescription("Submitting request"))
            }
    }

    @Test
    fun inlineLoading_accessibility_liveRegionTracksStatus() = runComposeUiTest {
        setContent {
            CarbonDesignSystem {
                InlineLoading(
                    status = _status,
                    label = _label,
                )
            }
        }

        _label = "Loading"

        InlineLoadingStatus.entries.forEach { status ->
            _status = status

            val expectedLiveRegion = when (status) {
                InlineLoadingStatus.Inactive,
                InlineLoadingStatus.Active -> LiveRegionMode.Polite
                InlineLoadingStatus.Finished,
                InlineLoadingStatus.Error -> LiveRegionMode.Assertive
            }

            onNodeWithTag(InlineLoadingTestTags.CONTAINER).assert(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.LiveRegion,
                    expectedLiveRegion
                )
            )
        }
    }
}
