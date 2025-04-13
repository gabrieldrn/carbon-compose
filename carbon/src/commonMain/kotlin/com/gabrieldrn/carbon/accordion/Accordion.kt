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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.common.molecules.AnimatedChevronDownIcon
import com.gabrieldrn.carbon.foundation.motion.Motion
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

private val ACCORDION_WIDTH_WIDER = 640.dp
private val ACCORDION_WIDTH_NARROW = 420.dp
private val accordionMediumWidthRange = ACCORDION_WIDTH_NARROW..ACCORDION_WIDTH_WIDER

private const val ACCORDION_WIDER_MARGIN_RATIO = .25f

private val expandShrinkAnimationSpecFloat = tween<Float>(
    durationMillis = Motion.Duration.fast02,
    easing = Motion.Entrance.productiveEasing
)

private val expandShrinkAnimationSpecFInt = tween<IntSize>(
    durationMillis = Motion.Duration.fast02,
    easing = Motion.Entrance.productiveEasing
)

private val expandAnimationTransition =
    fadeIn(expandShrinkAnimationSpecFloat) + expandVertically(expandShrinkAnimationSpecFInt)

private val shrinkAnimationTransition = shrinkVertically(expandShrinkAnimationSpecFInt)

@Composable
private fun Modifier.sectionModifier(flushAlignment: Boolean): Modifier =
    if (flushAlignment) this else padding(horizontal = SpacingScale.spacing05)

/**
 * Represents a section within an Accordion component.
 *
 * An Accordion Section consists of a title, a body containing the content to be displayed,
 * and an enabled state indicating whether the section can be interacted with.
 *
 * @property title The title of the accordion section, typically displayed as a clickable header.
 * @property body The content of the accordion section, displayed when the section is expanded.
 * @property isEnabled Indicates whether the section is enabled (interactive) or disabled
 * (non-interactive).  Defaults to true.
 */
public data class AccordionSection(
    val title: String,
    val body: String,
    val isEnabled: Boolean = true
)

/**
 * # Accordion
 *
 * The accordion component delivers large amounts of content in a small space through progressive
 * disclosure. The header title gives the user a high level overview of the content allowing the
 * user to decide which sections to read.
 *
 * Accordions can make information processing and discovering more effective. However, it does hide
 * content from users and it’s important to account for a user not noticing or reading all of the
 * included content.
 *
 * (From [Accordion documentation](https://carbondesignsystem.com/components/accordion/usage/))
 *
 * @param sections A list of pairs containing the header and body text for each section. The header
 * is the title of the section and the body is the content of the section. The sections are
 * displayed in order. Each pair associates a header (first element) with a body (second element).
 * @param size The size of the accordion.
 * @param modifier The modifier to be applied to the accordion.
 * @param flushAlignment Whether to use the flush alignment modifier or not. Flush alignment places
 * the row title and chevron icons with 0px padding, keeping them flush to the rule dividers
 */
@Composable
public fun Accordion(
    sections: List<AccordionSection>,
    size: AccordionSize,
    modifier: Modifier = Modifier,
    flushAlignment: Boolean = false
) {
    val colors = AccordionColors.colors()

    BoxWithConstraints(modifier = modifier) {
        val marginRight = remember(maxWidth) {
            when {
                maxWidth > ACCORDION_WIDTH_WIDER -> maxWidth * ACCORDION_WIDER_MARGIN_RATIO
                maxWidth in accordionMediumWidthRange -> SpacingScale.spacing10
                else -> SpacingScale.spacing05
            }
        }

        Column {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colors.dividerColor)
                    .testTag(AccordionTestTags.DIVIDER_TOP)
            )

            sections.forEach { section ->
                Section(
                    section = section,
                    size = size,
                    flushAlignment = flushAlignment,
                    colors = colors,
                    marginRight = {
                        Spacer(
                            modifier = Modifier
                                .width(marginRight)
                                .testTag(AccordionTestTags.MARGIN_RIGHT)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colors.dividerColor)
                        .testTag(AccordionTestTags.DIVIDER_BOTTOM)
                )
            }
        }
    }
}

@Composable
private fun Section(
    section: AccordionSection,
    size: AccordionSize,
    flushAlignment: Boolean,
    colors: AccordionColors,
    marginRight: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = Carbon.typography

    val textColor by colors.textColor(section.isEnabled)
    val iconColor by colors.iconColor(section.isEnabled)

    val textStyle = remember(typography, textColor) {
        typography.body01.copy(color = textColor)
    }

    var isExpanded by remember {
        mutableStateOf(false)
    }

    val componentsModifier = Modifier.sectionModifier(flushAlignment)

    LaunchedEffect(section.isEnabled) {
        if (isExpanded && !section.isEnabled) {
            isExpanded = false
        }
    }

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(size.heightDp())
                .fillMaxWidth()
                .clickable(enabled = section.isEnabled) { isExpanded = !isExpanded }
                .then(componentsModifier)
        ) {
            BasicText(
                text = section.title,
                style = textStyle,
                modifier = Modifier
                    .weight(1f)
                    .testTag(AccordionTestTags.TITLE)
            )

            AnimatedChevronDownIcon(
                rotateToUp = isExpanded,
                tint = iconColor,
                transitionSpec = expandShrinkAnimationSpecFloat
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandAnimationTransition,
            exit = shrinkAnimationTransition,
            modifier = Modifier
                .fillMaxWidth()
                .then(componentsModifier)
                .testTag(AccordionTestTags.BODY_CONTAINER)
        ) {
            Row {
                BasicText(
                    text = section.body,
                    style = textStyle,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            top = SpacingScale.spacing03,
                            bottom = SpacingScale.spacing06
                        )
                        .testTag(AccordionTestTags.BODY)
                )

                marginRight()
            }
        }
    }
}
