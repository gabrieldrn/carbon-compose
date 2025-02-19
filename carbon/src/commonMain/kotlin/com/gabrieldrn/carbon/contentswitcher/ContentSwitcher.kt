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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.button.ButtonFocusIndication
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.contentswitcher.domain.rememberDisplayContentSwitcherButtonDividerState
import com.gabrieldrn.carbon.foundation.motion.Motion
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

private fun <T : Any> getBottomContainerTransitionSpec() = tween<T>(
    durationMillis = Motion.Duration.fast01,
    easing = Motion.Entrance.expressiveEasing
)

private fun <T : Any> getUpperContainerTransitionSpec() = tween<T>(
    durationMillis = Motion.Duration.fast02,
    easing = Motion.Entrance.expressiveEasing
)

private data class ButtonState(
    val isEnabled: Boolean,
    val isSelected: Boolean
)

/**
 * # Content switcher - Icon variant
 *
 * Content switchers allow users to toggle between two or more content sections within the same
 * space on the screen.
 *
 * Content switchers are frequently used to let users toggle between different formattings, like a
 * grid view and a table view. They are also often used to narrow large content groups or to sort
 * related content. For example, a messaging tool may use a content switcher to divide messages into
 * three views such as “All,” “Read,” and “Unread.”
 *
 * (From [Content switcher documentation](https://carbondesignsystem.com/components/content-switcher/usage/))
 *
 * @param options A list of [Painter]s representing the options to be displayed. Each painter
 * identifies an option and is used as the displayed icon. At least two options are required,
 * otherwise, an [IllegalArgumentException] will be thrown.
 * @param selectedOption The currently selected option.
 * @param onOptionSelected Callback invoked when an option is selected. The painter of the selected
 * option is passed as a parameter.
 * @param modifier The modifier to be applied to the content switcher.
 * @param size The [ContentSwitcherSize] to be applied to the content switcher. Defaults to
 * [ContentSwitcherSize.Medium].
 * @param isEnabled Whether the content switcher is enabled or disabled.
 * @param optionsContentDescriptions A map of [Painter]s to their content descriptions. This map is
 * used to provide content descriptions for each option.
 */
@Composable
public fun IconContentSwitcher(
    options: List<Painter>,
    selectedOption: Painter,
    onOptionSelected: (Painter) -> Unit,
    modifier: Modifier = Modifier,
    size: ContentSwitcherSize = ContentSwitcherSize.Medium,
    isEnabled: Boolean = true,
    optionsContentDescriptions: Map<Painter, String?> = emptyMap(),
) {
    ContentSwitcherBase(
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
        size = size,
        isEnabled = isEnabled,
        optionsContentDescriptions = optionsContentDescriptions
    )
}

/**
 * # Content switcher
 *
 * Content switchers allow users to toggle between two or more content sections within the same
 * space on the screen.
 *
 * Content switchers are frequently used to let users toggle between different formattings, like a
 * grid view and a table view. They are also often used to narrow large content groups or to sort
 * related content. For example, a messaging tool may use a content switcher to divide messages into
 * three views such as “All,” “Read,” and “Unread.”
 *
 * (From [Content switcher documentation](https://carbondesignsystem.com/components/content-switcher/usage/))
 *
 * @param options A list of strings representing the options to be displayed. Each string is a
 * label for an option. At least two options are required, otherwise, an [IllegalArgumentException]
 * will be thrown.
 * @param selectedOption The currently selected option.
 * @param onOptionSelected Callback invoked when an option is selected. The label of the selected
 * option is passed as a parameter.
 * @param modifier The modifier to be applied to the content switcher.
 * @param size The [ContentSwitcherSize] to be applied to the content switcher. Defaults to
 * [ContentSwitcherSize.Medium].
 * @param isEnabled Whether the content switcher is enabled or disabled.
 */
@Composable
public fun ContentSwitcher(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    size: ContentSwitcherSize = ContentSwitcherSize.Medium,
    isEnabled: Boolean = true,
) {
    ContentSwitcherBase(
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
        size = size,
        isEnabled = isEnabled
    )
}

@Composable
private fun <T : Any> ContentSwitcherBase(
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    size: ContentSwitcherSize,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    optionsContentDescriptions: Map<T, String?> = emptyMap(),
) {
    require(options.size >= 2) { "ContentSwitcher requires at least two options" }

    val colors = ContentSwitcherColors.colors()

    val selectedOptionIndex by remember(options, selectedOption) {
        mutableStateOf(options.indexOf(selectedOption))
    }

    val hoverStateMap = remember {
        mutableStateMapOf<Int, HoverInteraction.Enter>()
    }

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (isEnabled) colors.borderColor else colors.borderDisabledColor,
                shape = RoundedCornerShape(4.dp)
            )
            .clip(shape = RoundedCornerShape(4.dp))
            .height(height = size.height)
            .width(IntrinsicSize.Min) // By default, use only the minimum width needed.
            .testTag(ContentSwitcherTestTags.ROOT)
    ) {
        options.forEachIndexed { index, option ->
            key(index, option) {
                val interactionSource = remember { MutableInteractionSource() }

                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect { interaction ->
                        when (interaction) {
                            is HoverInteraction.Enter -> hoverStateMap[index] = interaction
                            is HoverInteraction.Exit -> hoverStateMap.remove(index)
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        // Make all buttons the same width as the widest button.
                        .width(IntrinsicSize.Max)
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .testTag(ContentSwitcherTestTags.BUTTON_LAYOUT),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Divider(
                        index = index,
                        selectedOptionIndex = selectedOptionIndex,
                        hoverState = hoverStateMap,
                        colors = colors
                    )

                    ContentSwitcherButton(
                        index = index,
                        selectedOptionIndex = selectedOptionIndex,
                        content = option,
                        isEnabled = isEnabled,
                        componentSize = size,
                        colors = colors,
                        interactionSource = interactionSource,
                        onClick = { onOptionSelected(option) },
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = optionsContentDescriptions[option]
                    )
                }
            }
        }
    }
}

@Composable
private fun <T : Any> ContentSwitcherButton(
    index: Int,
    selectedOptionIndex: Int,
    content: T,
    isEnabled: Boolean,
    componentSize: ContentSwitcherSize,
    colors: ContentSwitcherColors,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    val interactions = collectHoverAndFocusStates(interactionSource)

    val buttonState by remember(isEnabled, selectedOptionIndex) {
        mutableStateOf(ButtonState(isEnabled, index == selectedOptionIndex))
    }

    val interactionsTransition = updateTransition(interactions)
    val selectedTransition = updateTransition(buttonState)

    // Due to the selection animation, buttons have two background layers in their container.
    // The first layer is for the resting and hovered states, and the second layer is for the
    // selected state. The selected state is animated from the bottom to the top of the button.

    val bottomContainerColor by interactionsTransition.animateColor(
        transitionSpec = { getBottomContainerTransitionSpec() },
        label = "Bottom container color"
    ) { transitionInteractions ->
        when {
            transitionInteractions.any { it is HoverInteraction.Enter } ->
                colors.containerUnselectedHoverColor
            transitionInteractions.any { it is FocusInteraction.Focus } ->
                colors.containerUnselectedFocusColor
            else ->
                colors.containerUnselectedColor
        }
    }

    val upperContainerHeight by selectedTransition.animateFloat(
        transitionSpec = { getUpperContainerTransitionSpec() },
        label = "Upper container height"
    ) { state ->
        if (state.isSelected) 0f else 1f
    }

    Box(
        modifier = modifier
            .selectable(
                selected = buttonState.isSelected,
                enabled = buttonState.isEnabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = ButtonFocusIndication(Carbon.theme, ButtonType.Primary)
            )
            .fillMaxHeight()
            .drawBehind {
                // Unselected color background
                drawRect(bottomContainerColor)
                // Animated selected color background
                drawLine(
                    color =
                        if (buttonState.isEnabled) colors.containerSelectedColor
                        else colors.containerSelectedDisabledColor,
                    start = Offset(x = size.width / 2, y = size.height),
                    end = Offset(
                        x = size.width / 2,
                        y = size.height * upperContainerHeight
                    ),
                    strokeWidth = this.size.width
                )
            }
            .testTag(ContentSwitcherTestTags.BUTTON_CONTENT_ROOT)
    ) {
        AnimatedContent(
            targetState = buttonState,
            transitionSpec = {
                fadeIn(getUpperContainerTransitionSpec()) togetherWith
                    fadeOut(getUpperContainerTransitionSpec())
            },
            label = "Content (Text/Painter) w/ color",
            content = { buttonState ->
                val contentColor = when {
                    !buttonState.isEnabled -> colors.labelDisabledColor
                    buttonState.isSelected -> colors.labelSelectedColor
                    else -> colors.labelUnselectedColor
                }

                when (content) {
                    is String -> Box(Modifier.fillMaxSize()) {
                        BasicText(
                            text = content,
                            style = Carbon.typography.bodyCompact01,
                            color = { contentColor },
                            maxLines = 1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterStart)
                                .padding(horizontal = SpacingScale.spacing05)
                                .testTag(ContentSwitcherTestTags.BUTTON_TEXT)
                        )
                    }
                    is Painter -> Image(
                        painter = content,
                        contentDescription = contentDescription,
                        colorFilter = ColorFilter.tint(contentColor),
                        modifier = Modifier
                            .padding(componentSize.iconPadding)
                            .size(componentSize.iconSize)
                            .align(Alignment.Center)
                            .testTag(ContentSwitcherTestTags.BUTTON_IMAGE)
                    )
                    else -> {}
                }
            }
        )
    }
}

@Composable
private fun collectHoverAndFocusStates(
    interactionSource: MutableInteractionSource,
): SnapshotStateList<Interaction> {
    val interactions = remember { mutableStateListOf<Interaction>() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is HoverInteraction.Enter -> interactions.add(interaction)
                is HoverInteraction.Exit -> interactions.remove(interaction.enter)
                is FocusInteraction.Focus -> interactions.add(interaction)
                is FocusInteraction.Unfocus -> interactions.remove(interaction.focus)
            }
        }
    }

    return interactions
}

@Composable
private fun Divider(
    index: Int,
    selectedOptionIndex: Int,
    hoverState: SnapshotStateMap<Int, HoverInteraction.Enter>,
    colors: ContentSwitcherColors,
    modifier: Modifier = Modifier
) {
    val displayDivider by rememberDisplayContentSwitcherButtonDividerState(
        buttonIndex = index,
        selectedButtonIndex = selectedOptionIndex,
        hoverStateMap = hoverState
    )

    val transition = updateTransition(displayDivider)

    val dividerColor by transition.animateColor(
        transitionSpec = { getBottomContainerTransitionSpec() },
        label = "Divider color"
    ) {
        if (it) colors.dividerColor else Color.Transparent
    }

    Box(
        modifier = modifier
            .height(SpacingScale.spacing05)
            .width(width = 1.dp)
            .drawBehind {
                drawRect(color = dividerColor)
            }
            .testTag(ContentSwitcherTestTags.BUTTON_DIVIDER)
    )
}
