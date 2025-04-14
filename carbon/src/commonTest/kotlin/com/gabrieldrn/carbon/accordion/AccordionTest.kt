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

package com.gabrieldrn.carbon.accordion

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.isNotEnabled
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.forEachParameter
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.toList
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AccordionTest {

    private var _sections by mutableStateOf(listOf<AccordionSection>())
    private var _size by mutableStateOf(AccordionSize.Medium)
    private var _flushedAlignment by mutableStateOf(false)

    @BeforeTest
    fun setup() {
        _sections = listOf()
        _size = AccordionSize.Medium
        _flushedAlignment = false
    }

    private fun ComposeUiTest.setupContent() {
        setContent {
            CarbonDesignSystem {
                Accordion(
                    sections = _sections,
                    size = _size,
                    flushAlignment = _flushedAlignment
                )
            }
        }
    }

    @Test
    fun accordion_shrinkedContent_validateLayout() = runComposeUiTest {
        setupContent()

        forEachParameter(
            testSections,
            AccordionSize.entries.toTypedArray(),
            arrayOf(false, true)
        ) { sections, size, flushedAlignment ->

            _sections = sections
            _size = size
            _flushedAlignment = flushedAlignment

            val dividerTopNode = onNodeWithTag(AccordionTestTags.DIVIDER_TOP)

            val dividerTopNodeWidthDp = with(density) {
                dividerTopNode.fetchSemanticsNode().size.width.toDp()
            }

            val rootWidthDp = with(density) {
                onNodeWithTag(AccordionTestTags.ROOT).fetchSemanticsNode().size.width.toDp()
            }

            dividerTopNode.assertIsDisplayed()

            assertEquals(
                expected =
                    if (flushedAlignment) rootWidthDp - SpacingScale.spacing05 * 2
                    else rootWidthDp,
                actual = dividerTopNodeWidthDp
            )

            fun onNodeWithTagAt(tag: String, index: Int, useUnmergedTree: Boolean = false) =
                onAllNodesWithTag(tag, useUnmergedTree = useUnmergedTree)[index]

            sections.forEachIndexed { index, section ->

                onNodeWithTagAt(AccordionTestTags.SECTION_CONTAINER, index).run {
                    assertIsDisplayed()

                    val container = fetchSemanticsNode()
                    assertEquals(
                        expected = size.heightDp(),
                        actual = with(density) { container.size.height.toDp() }
                    )
                }

                val titleContainer =
                    onNodeWithTagAt(
                        AccordionTestTags.TITLE_CONTAINER,
                        index,
                    )

                titleContainer
                    .assertIsDisplayed()
                    .assertHasClickAction()

                onNodeWithTagAt(AccordionTestTags.TITLE, index, true)
                    .run {
                        assertIsDisplayed()
                        assertTextEquals(sections[index].title.toString())
                    }

                onNodeWithTagAt(AccordionTestTags.CHEVRON_ICON, index, true)
                    .assertIsDisplayed()

                onNodeWithTagAt(AccordionTestTags.DIVIDER_BOTTOM, index)
                    .assertIsDisplayed()
            }
        }
    }

    @Test
    fun accordion_expandedContent_validateLayout() = runComposeUiTest {
        setupContent()

        forEachParameter(
            testSections,
            AccordionSize.entries.toTypedArray(),
            arrayOf(false, true)
        ) { sections, size, flushedAlignment ->

            _sections = sections
            _size = size
            _flushedAlignment = flushedAlignment

            sections.forEachIndexed { index, section ->

                val titleContainer = onAllNodesWithTag(AccordionTestTags.TITLE_CONTAINER)[index]

                val bodyContainer = onNodeWithTag(AccordionTestTags.BODY_CONTAINER, true)
                val body = onNodeWithTag(AccordionTestTags.BODY, true)
                val marginRight = onNodeWithTag(AccordionTestTags.MARGIN_RIGHT, true)

                titleContainer.performClick()

                if (section.isEnabled) {
                    bodyContainer.assertIsDisplayed()
                    body
                        .assertIsDisplayed()
                        .assertTextEquals(sections[index].body.toString())
                    marginRight.assertExists()
                } else {
                    bodyContainer.assertDoesNotExist()
                    body.assertDoesNotExist()
                    marginRight.assertDoesNotExist()
                }

                titleContainer.performClick()
            }
        }
    }

    @Test
    fun accordion_validateSemantics() = runComposeUiTest {
        setupContent()

        forEachParameter(
            testSections,
            AccordionSize.entries.toTypedArray(),
            arrayOf(false, true)
        ) { sections, size, flushedAlignment ->

            _sections = sections
            _size = size
            _flushedAlignment = flushedAlignment

            sections.forEachIndexed { index, section ->
                onAllNodesWithTag(AccordionTestTags.TITLE_CONTAINER)[index].run {
                    if (section.isEnabled) {
                        assert(isEnabled())
                        requestFocus()
                        assertIsFocused()
                    } else {
                        assert(isNotEnabled())
                    }
                }
            }
        }
    }

    @Test
    fun accordion_validateMarginRight() = runComposeUiTest {
        var widthConstraint by mutableStateOf(641.dp)

        var containerWidth = 0.dp

        setContent {
            CarbonDesignSystem {
                BoxWithConstraints(
                    modifier = Modifier.sizeIn(maxWidth = widthConstraint)
                ) {
                    containerWidth = maxWidth

                    Accordion(
                        sections = listOf(
                            AccordionSection("Title", "Content"),
                            AccordionSection("Title", "Content"),
                            AccordionSection("Title", "Content", isEnabled = false),
                        ),
                        size = AccordionSize.Medium
                    )
                }
            }
        }

        onAllNodesWithTag(AccordionTestTags.TITLE_CONTAINER)
            .toList()
            .onEach { node -> node.performClick() }

        fun assertMarginWidth(expected: Float, absoluteTolerance: Float = 0f) {
            onAllNodesWithTag(AccordionTestTags.MARGIN_RIGHT)
                .assertCountEquals(2)
                .toList()
                .onEach { node ->
                    val marginWidth = with(density) {
                        node.fetchSemanticsNode().size.width.toDp()
                    }

                    assertEquals(
                        expected = expected,
                        actual = marginWidth.value,
                        absoluteTolerance = absoluteTolerance
                    )
                }
        }

        // This condition can be false on mobile targets if their maximum screen width is smaller.
        // The following assertions will then be ignored for those targets but others must validate
        // them.
        if (containerWidth >= 641.dp) {
            assertMarginWidth(
                expected = (containerWidth * .25f).value,
                absoluteTolerance = .5f
            )
        }

        arrayOf(420.dp, 640.dp).onEach { constrainedWidth ->
            widthConstraint = constrainedWidth

            assertMarginWidth(expected = SpacingScale.spacing10.value)
        }

        widthConstraint = 419.dp

        assertMarginWidth(expected = SpacingScale.spacing05.value)
    }

    private companion object {
        val testSections = arrayOf(
            listOf(),
            listOf(
                AccordionSection("Title", "Content"),
                AccordionSection("Title", "Content"),
                AccordionSection("Title", "Content"),
                AccordionSection("Title", "Content"),
            ),
            listOf(
                AccordionSection("Title", "Content", isEnabled = false),
                AccordionSection("Title", "Content", isEnabled = false),
                AccordionSection("Title", "Content", isEnabled = false),
            )
        )
    }
}
