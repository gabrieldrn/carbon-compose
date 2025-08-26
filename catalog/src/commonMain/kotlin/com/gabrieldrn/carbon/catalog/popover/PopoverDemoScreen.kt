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

package com.gabrieldrn.carbon.catalog.popover

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.button.IconButtonWithPopover
import com.gabrieldrn.carbon.catalog.Res
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.catalog.ic_cognitive
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.foundation.color.borderSubtleColor
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.popover.PopoverAlignment
import com.gabrieldrn.carbon.popover.PopoverBox
import com.gabrieldrn.carbon.popover.PopoverPlacement
import com.gabrieldrn.carbon.popover.carettip.PopoverCaretTipAlignment
import com.gabrieldrn.carbon.popover.carettip.PopoverCaretTipBox
import com.gabrieldrn.carbon.popover.carettip.PopoverCaretTipPlacement
import com.gabrieldrn.carbon.tab.TabItem
import com.gabrieldrn.carbon.tag.ReadOnlyTag
import com.gabrieldrn.carbon.tag.TagType
import org.jetbrains.compose.resources.painterResource

private enum class PopoverVariant(val label: String) {
    NoTip("No tip"),
    CaretTip("Caret tip"),
    TabTip("Tab tip");

    companion object {
        fun fromLabel(label: String) = entries.first { it.label == label }
    }
}

private val variants = PopoverVariant.entries.map { TabItem(it.label) }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopoverDemoScreen(modifier: Modifier = Modifier) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    var popoverNoTipPlacement by rememberSaveable {
        mutableStateOf(PopoverPlacement.Bottom)
    }
    var popoverNoTipAlignment by rememberSaveable {
        mutableStateOf(PopoverAlignment.Start)
    }

    var popoverCaretTipPlacement by rememberSaveable {
        mutableStateOf(PopoverCaretTipPlacement.Bottom)
    }
    var popoverCaretTipAlignment by rememberSaveable {
        mutableStateOf(PopoverCaretTipAlignment.Center)
    }

    var popoverTabTipAlignment by rememberSaveable {
        mutableStateOf(PopoverAlignment.Start)
    }

    var isPopoverVisible by remember { mutableStateOf(false) }

    val uiTrigger: @Composable () -> Unit = {
        IconButton(
            iconPainter = painterResource(Res.drawable.ic_cognitive),
            onClick = { isPopoverVisible = true },
            isEnabled = true,
            interactionSource = interactionSource
        )
    }

    DemoScreen(
        modifier = modifier,
        variants = variants,
        demoParametersContent = { variantTab ->
            when (PopoverVariant.fromLabel(variantTab.label)) {
                PopoverVariant.CaretTip -> {
                    Dropdown(
                        placeholder = "Choose option",
                        label = "Tooltip placement",
                        options = PopoverCaretTipPlacement.entries.toDropdownOptions(),
                        selectedOption = popoverCaretTipPlacement,
                        onOptionSelected = { popoverCaretTipPlacement = it }
                    )

                    Dropdown(
                        placeholder = "Choose option",
                        label = "Tooltip alignment",
                        options = PopoverCaretTipAlignment.entries.toDropdownOptions(),
                        selectedOption = popoverCaretTipAlignment,
                        onOptionSelected = { popoverCaretTipAlignment = it }
                    )
                }

                PopoverVariant.NoTip -> {
                    Dropdown(
                        placeholder = "Choose option",
                        label = "Tooltip placement",
                        options = PopoverPlacement.entries.toDropdownOptions(),
                        selectedOption = popoverNoTipPlacement,
                        onOptionSelected = { popoverNoTipPlacement = it }
                    )

                    Dropdown(
                        placeholder = "Choose option",
                        label = "Tooltip alignment",
                        options = PopoverAlignment.entries.toDropdownOptions(),
                        selectedOption = popoverNoTipAlignment,
                        onOptionSelected = { popoverNoTipAlignment = it }
                    )
                }

                PopoverVariant.TabTip ->
                    Dropdown(
                        placeholder = "Choose option",
                        label = "Tooltip alignment",
                        options = PopoverAlignment.entries.toDropdownOptions(),
                        selectedOption = popoverTabTipAlignment,
                        onOptionSelected = { popoverTabTipAlignment = it }
                    )
            }
        },
        demoContent = { variantTab ->

            LaunchedEffect(variantTab) {
                isPopoverVisible = false
            }

            when (PopoverVariant.fromLabel(variantTab.label)) {
                PopoverVariant.CaretTip -> PopoverCaretTipBox(
                    isVisible = isPopoverVisible,
                    placement = popoverCaretTipPlacement,
                    alignment = popoverCaretTipAlignment,
                    popoverMaxWidth = 350.dp,
                    popoverContent = {
                        PopoverContent(
                            smallContent = popoverCaretTipPlacement == PopoverCaretTipPlacement.Top
                        )
                    },
                    onDismissRequest = { isPopoverVisible = false },
                    content = uiTrigger
                )

                PopoverVariant.NoTip -> PopoverBox(
                    isVisible = isPopoverVisible,
                    placement = popoverNoTipPlacement,
                    alignment = popoverNoTipAlignment,
                    popoverContent = {
                        PopoverContent(
                            smallContent = popoverNoTipPlacement == PopoverPlacement.Top
                        )
                    },
                    onDismissRequest = { isPopoverVisible = false },
                    content = uiTrigger
                )

                PopoverVariant.TabTip -> IconButtonWithPopover(
                    iconPainter = painterResource(Res.drawable.ic_cognitive),
                    popoverAlignment = popoverTabTipAlignment,
                    onClick = {}
                ) {
                    PopoverContent(smallContent = false)
                }
            }
        }
    )
}

@Composable
private fun PopoverContent(
    modifier: Modifier = Modifier,
    smallContent: Boolean = false,
) {
    Column(
        modifier = modifier
            .width(300.dp)
            .padding(vertical = SpacingScale.spacing05)
    ) {
        BasicText(
            text = "INBOUND SOLAR PARTICLE EVENT",
            style = Carbon.typography.headingCompact01.copy(
                color = Carbon.theme.textPrimary
            ),
            modifier = Modifier.padding(horizontal = SpacingScale.spacing05)
        )

        BasicText(
            text = "M-SEP-20250801-1",
            style = Carbon.typography.code01.copy(
                color = Carbon.theme.textSecondary
            ),
            modifier = Modifier.padding(horizontal = SpacingScale.spacing05)
        )

        Row(
            modifier = Modifier
                .padding(start = SpacingScale.spacing05, top = SpacingScale.spacing02),
            horizontalArrangement = Arrangement.spacedBy(SpacingScale.spacing02)
        ) {
            ReadOnlyTag(
                "Solar flare",
                type = TagType.Red,
            )
            ReadOnlyTag(
                "SEP",
                type = TagType.Cyan,
            )
            ReadOnlyTag(
                "CME",
                type = TagType.Cyan,
            )
        }

        if (!smallContent) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SpacingScale.spacing05)
                    .height(1.dp)
                    .background(Carbon.theme.borderSubtleColor(Carbon.layer))
            )

            val data = remember {
                mapOf(
                    "Target:" to "Mars",
                    "Source:" to "Solar Flare, Class M8.9 from Active Region 3901.",
                    "Type:" to "Coronal Mass Ejection + Solar Energetic Particle.",
                    "Event time:" to "Flare peak detected 2025-Aug-01 at 09:16 UTC.",
                    "ETA:" to "Shock front estimated for 2025-Aug-03 at 14:00 UTC (± 4 hours)."
                )
            }

            data.forEach {
                Row(
                    modifier = Modifier
                        .padding(top = SpacingScale.spacing02)
                        .padding(horizontal = SpacingScale.spacing05)
                ) {
                    BasicText(
                        text = it.key,
                        style = Carbon.typography.bodyCompact01.copy(
                            color = Carbon.theme.textHelper,
                        ),
                        modifier = Modifier.weight(.8f)
                    )
                    BasicText(
                        text = it.value,
                        style = Carbon.typography.bodyCompact01.copy(
                            color = Carbon.theme.textPrimary,
                        ),
                        modifier = Modifier.weight(1.2f)
                    )
                }
            }
        }
    }
}
