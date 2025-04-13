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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.common.molecules.AnimatedChevronDownIcon
import com.gabrieldrn.carbon.foundation.color.layerHoverColor
import com.gabrieldrn.carbon.foundation.interaction.FocusIndication
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

private val expandShrinkAnimationSpecIntSize = tween<IntSize>(
    durationMillis = Motion.Duration.fast02,
    easing = Motion.Entrance.productiveEasing
)

private val expandAnimationTransition =
    fadeIn(expandShrinkAnimationSpecFloat) + expandVertically(expandShrinkAnimationSpecIntSize)

private val shrinkAnimationTransition = shrinkVertically(expandShrinkAnimationSpecIntSize)

@Composable
private fun Modifier.flushPadding(flushAlignment: Boolean): Modifier =
    if (flushAlignment) padding(horizontal = SpacingScale.spacing05) else this

/**
 * Represents a section within an Accordion component.
 *
 * An Accordion Section consists of a title, a body containing the content to be displayed,
 * and an enabled state indicating whether the section can be interacted with.  The title and body
 * are represented as AnnotatedStrings to allow for rich text formatting within the section.
 *
 * @property title The title of the accordion section, typically displayed as a clickable header.
 * @property body The content of the accordion section, displayed when the section is expanded.
 * @property isEnabled Indicates whether the section is enabled (interactive) or disabled
 * (non-interactive). Defaults to true. A disabled section cannot be expanded or collapsed.
 */
public data class AccordionSection(
    val title: AnnotatedString,
    val body: AnnotatedString,
    val isEnabled: Boolean = true
) {

    /**
     * Alternative constructor of [AccordionSection] that takes plain strings for the title and
     * body.
     */
    public constructor(
        title: String,
        body: String,
        isEnabled: Boolean = true
    ) : this(
        title = AnnotatedString(title),
        body = AnnotatedString(body),
        isEnabled = isEnabled
    )
}

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
 * @param sections A list of [AccordionSection] objects, each representing a section in the
 * accordion.
 * @param size The size of the accordion, determining the overall visual density and padding.
 * Use [AccordionSize].
 * @param modifier Modifier for styling and layout of the accordion.
 * @param flushAlignment Whether to use flush alignment. When `true`, the section title and chevron
 * icon will have no padding, aligning them with the divider lines. Defaults to `false`.
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

        val flushPadding = Modifier.flushPadding(flushAlignment)

        Column {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .then(flushPadding)
                    .background(colors.dividerColor)
                    .testTag(AccordionTestTags.DIVIDER_TOP)
            )

            sections.forEach { section ->
                Section(
                    section = section,
                    size = size,
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
                        .then(flushPadding)
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
    colors: AccordionColors,
    marginRight: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = Carbon.typography
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    var isExpanded by remember { mutableStateOf(false) }

    val textColor by colors.textColor(section.isEnabled)
    val iconColor by colors.iconColor(section.isEnabled)
    val hoverBackgroundColor = Carbon.theme.layerHoverColor(Carbon.layer)

    val textStyle = remember(typography, textColor) {
        typography.body01.copy(color = textColor)
    }

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
                .clickable(
                    interactionSource = interactionSource,
                    indication = FocusIndication(Carbon.theme),
                    enabled = section.isEnabled,
                    onClick = { isExpanded = !isExpanded }
                )
                .drawBehind {
                    drawRect(if (isHovered) hoverBackgroundColor else Color.Transparent)
                }
                .padding(horizontal = SpacingScale.spacing05)
                .testTag(AccordionTestTags.TITLE_CONTAINER)
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
                .padding(horizontal = SpacingScale.spacing05)
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
