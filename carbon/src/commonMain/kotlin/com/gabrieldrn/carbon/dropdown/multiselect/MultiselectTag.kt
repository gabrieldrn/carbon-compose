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

package com.gabrieldrn.carbon.dropdown.multiselect

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.dropdown.base.DropdownFieldContentId
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.base.DropdownTestTags
import com.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text
import com.gabrieldrn.carbon.icons.CloseIcon

private val tagShape = RoundedCornerShape(100f)

@Composable
internal fun DropdownMultiselectTag(
    count: Int,
    state: DropdownInteractiveState,
    onCloseTagClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val baseModifier = modifier
        .height(SpacingScale.spacing06)
        .then(
            if (state is DropdownInteractiveState.ReadOnly) {
                Modifier
                    .background(
                        color = Color.Transparent,
                        shape = tagShape
                    )
                    .border(
                        width = 1.dp,
                        color = LocalCarbonTheme.current.borderSubtle01,
                        shape = tagShape
                    )
            } else {
                Modifier
                    .background(
                        color = LocalCarbonTheme.current.backgroundInverse,
                        shape = tagShape
                    )
            }
        )
        .clickable(
            enabled = state !in arrayOf(
                DropdownInteractiveState.Disabled,
                DropdownInteractiveState.ReadOnly
            ),
            interactionSource = interactionSource,
            indication = null,
            onClick = onCloseTagClick
        )

    Row(
        modifier = baseModifier
            .testTag(DropdownTestTags.FIELD_MULTISELECT_TAG)
            .layoutId(DropdownFieldContentId.MULTISELECT_TAG),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = count.toString(),
            color = if (state is DropdownInteractiveState.ReadOnly) {
                LocalCarbonTheme.current.textPrimary
            } else {
                LocalCarbonTheme.current.textInverse
            },
            modifier = Modifier.padding(start = SpacingScale.spacing03)
        )
        Spacer(modifier = Modifier.width(SpacingScale.spacing01))
        Box(
            modifier = Modifier
                .size(SpacingScale.spacing06)
                .indication(
                    indication = MultiselectTagIndication(),
                    interactionSource = interactionSource
                )
        ) {
            CloseIcon(
                tint = if (state is DropdownInteractiveState.ReadOnly) {
                    LocalCarbonTheme.current.iconDisabled
                } else {
                    LocalCarbonTheme.current.iconInverse
                },
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

private class MultiselectTagIndication : Indication {

    class MultiselectTagIndicationInstance(private val theme: Theme) : IndicationInstance {

        var displayIndication: Boolean by mutableStateOf(false)

        override fun ContentDrawScope.drawIndication() {
            if (displayIndication) {
                inset(2f.dp.toPx()) {
                    drawCircle(theme.backgroundHover)
                }
            }

            drawContent()
        }
    }

    @Composable
    override fun rememberUpdatedInstance(
        interactionSource: InteractionSource
    ): IndicationInstance {
        val theme = LocalCarbonTheme.current

        val instance = remember(theme) {
            MultiselectTagIndicationInstance(theme)
        }

        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is HoverInteraction.Enter,
                    is PressInteraction.Press -> instance.displayIndication = true
                    is HoverInteraction.Exit,
                    is PressInteraction.Release,
                    is PressInteraction.Cancel -> instance.displayIndication = false
                    else -> {}
                }
            }
        }

        return instance
    }
}
