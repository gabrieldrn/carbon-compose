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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.isNotEnabled
import androidx.compose.ui.test.isNotFocusable
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.gabrieldrn.carbon.forEachParameter
import com.gabrieldrn.carbon.icons.checkmarkFilledIcon
import com.gabrieldrn.carbon.icons.closeIcon
import com.gabrieldrn.carbon.icons.viewIcon
import com.gabrieldrn.carbon.toList
import kotlin.test.Test

class ContentSwitcherTest {

    private val stringOptions = listOf("Option 1", "Optioooooooooooon 2", "Option 3")
    private lateinit var iconOptions: List<Painter>
    private val iconVectorOptions = listOf(checkmarkFilledIcon, closeIcon, viewIcon)

    private var _selectedStringOption by mutableStateOf(stringOptions.first())
    private var _size by mutableStateOf(ContentSwitcherSize.Small)
    private var _isEnabled by mutableStateOf(true)

    private fun ComposeUiTest.setupDefaultContentSwitcher() {
        setContent {
            ContentSwitcher(
                options = stringOptions,
                selectedOption = _selectedStringOption,
                onOptionSelected = { _selectedStringOption = it },
                size = _size,
                isEnabled = _isEnabled
            )
        }
    }

    private fun ComposeUiTest.setupIconContentSwitcher() {
        setContent {
            iconOptions = key(Unit) { iconVectorOptions.map { rememberVectorPainter(it) } }

            var selectedIconOption by remember {
                mutableStateOf(iconOptions.first())
            }

            IconContentSwitcher(
                options = iconOptions,
                selectedOption = selectedIconOption,
                onOptionSelected = { selectedIconOption = it },
                size = _size,
                isEnabled = _isEnabled,
                optionsContentDescriptions = iconOptions
                    .zip(iconVectorOptions.map { it.name })
                    .toMap()
            )
        }
    }

    @Suppress("NestedBlockDepth")
    private fun ComposeUiTest.validateCommonLayout() {
        val expectedHeight = _size.height

        onNodeWithTag(ContentSwitcherTestTags.ROOT)
            .assertIsDisplayed()
            // Check global height
            .assertHeightIsEqualTo(expectedHeight)

        onAllNodesWithTag(ContentSwitcherTestTags.BUTTON_LAYOUT)
            .assertCountEquals(stringOptions.size)
            .toList()
            .run {
                forEach {
                    it.assertIsDisplayed()
                    it.onChildren()
                        .assertCountEquals(2)
                        .assertAny(hasTestTag(ContentSwitcherTestTags.BUTTON_CONTENT_ROOT))
                        .assertAny(hasTestTag(ContentSwitcherTestTags.BUTTON_DIVIDER))
                }

                // Check that all buttons have the same width
                val expectedWidthForEachButton = with(density) {
                    first().fetchSemanticsNode().size.width.toDp()
                }
                forEach {
                    it.assertWidthIsEqualTo(expectedWidthForEachButton)
                        .assertHeightIsEqualTo(expectedHeight)
                }
            }
    }

    @Test
    fun contentSwitcher_default_validateLayout() = runComposeUiTest {
        setupDefaultContentSwitcher()

        forEachParameter(
            ContentSwitcherSize.entries.toTypedArray(),
            arrayOf(true, false)
        ) { size, isEnabled ->
            _size = size
            _isEnabled = isEnabled

            validateCommonLayout()

            onAllNodesWithTag(ContentSwitcherTestTags.BUTTON_CONTENT_ROOT, useUnmergedTree = true)
                .assertCountEquals(stringOptions.size)
                .toList()
                .forEachIndexed { index, node ->
                    node
                        .onChildAt(0)
                        .onChildAt(0)
                        .assertIsDisplayed()
                        .assert(
                            hasTestTag(ContentSwitcherTestTags.BUTTON_TEXT) and
                                hasText(stringOptions[index])
                        )
                }
        }
    }

    @Test
    fun contentSwitcher_icon_validateLayout() = runComposeUiTest {
        setupIconContentSwitcher()

        forEachParameter(
            ContentSwitcherSize.entries.toTypedArray(),
            arrayOf(true, false)
        ) { size, isEnabled ->
            _size = size
            _isEnabled = isEnabled

            validateCommonLayout()

            onAllNodesWithTag(ContentSwitcherTestTags.BUTTON_CONTENT_ROOT, useUnmergedTree = true)
                .assertCountEquals(stringOptions.size)
                .toList()
                .forEachIndexed { index, node ->
                    node
                        .onChildAt(0)
                        .onChildAt(0)
                        .assertIsDisplayed()
                        .assert(
                            hasTestTag(ContentSwitcherTestTags.BUTTON_IMAGE) and
                                hasContentDescription(iconVectorOptions[index].name)
                        )
                }
        }
    }

    @Test
    fun contentSwitcher_all_validateSemantics() {
        fun ComposeUiTest.runGesturesAssertions() {
            onAllNodesWithTag(ContentSwitcherTestTags.BUTTON_CONTENT_ROOT)
                .toList()
                .onEachIndexed { index, node ->
                    with(node) {
                        assert(
                            isSelectable() and
                                hasClickAction() and
                                if (_isEnabled) isEnabled() else isNotEnabled()
                        )

                        val wasSelected = SemanticsMatcher
                            .expectValue(SemanticsProperties.Selected, true)
                            .matches(fetchSemanticsNode())

                        performClick()

                        if (_isEnabled || wasSelected) assertIsSelected()
                        else assertIsNotSelected()
                    }
                }
        }

        fun ComposeUiTest.runAccessibilityAssertions(isIconVariant: Boolean) {
            onAllNodesWithTag(
                ContentSwitcherTestTags.BUTTON_CONTENT_ROOT,
                useUnmergedTree = true
            )
                .toList()
                .onEachIndexed { index, node ->
                    with(node) {
                        assert(
                            if (_isEnabled) {
                                isFocusable()
                            } else {
                                isNotFocusable()
                            }
                        )

                        // TODO Test focus interactions. At the moment of this comment, testing
                        //  those interactions are working as expected but not on Android platform.

                        if (isIconVariant) {
                            onChildAt(0)
                                .onChildAt(0)
                                .assert(hasContentDescription(iconVectorOptions[index].name))
                        }
                    }
                }
        }

        runComposeUiTest {
            setupDefaultContentSwitcher()
            forEachParameter(
                ContentSwitcherSize.entries.toTypedArray(),
                arrayOf(true, false)
            ) { size, isEnabled ->
                _size = size
                _isEnabled = isEnabled

                runGesturesAssertions()
            }
        }
        runComposeUiTest {
            setupDefaultContentSwitcher()
            forEachParameter(
                ContentSwitcherSize.entries.toTypedArray(),
                arrayOf(true, false)
            ) { size, isEnabled ->
                _size = size
                _isEnabled = isEnabled

                runAccessibilityAssertions(isIconVariant = false)
            }
        }
        runComposeUiTest {
            setupIconContentSwitcher()
            forEachParameter(
                ContentSwitcherSize.entries.toTypedArray(),
                arrayOf(true, false)
            ) { size, isEnabled ->
                _size = size
                _isEnabled = isEnabled

                runGesturesAssertions()
            }
        }
        runComposeUiTest {
            setupIconContentSwitcher()
            forEachParameter(
                ContentSwitcherSize.entries.toTypedArray(),
                arrayOf(true, false)
            ) { size, isEnabled ->
                _size = size
                _isEnabled = isEnabled

                runAccessibilityAssertions(isIconVariant = true)
            }
        }
    }
}
