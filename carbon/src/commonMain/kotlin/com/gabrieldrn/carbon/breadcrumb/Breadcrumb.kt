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

package com.gabrieldrn.carbon.breadcrumb

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

/**
 * Represents a breadcrumb.
 *
 * @param label The label of the breadcrumb.
 * @param isEnabled Whether the breadcrumb is enabled or not.
 */
public data class Breadcrumb(
    val label: String,
    val isEnabled: Boolean = true,
)

/**
 * # Breadcrumb
 *
 * Breadcrumbs show users their current location relative to the information architecture and enable
 * them to quickly move up to a parent level or previous step.
 *
 * (From [Breadcrumb documentation](https://carbondesignsystem.com/components/breadcrumb/usage/))
 *
 * @param breadcrumbs The list of breadcrumbs to display.
 * @param onBreadcrumbClick Callback invoked when a breadcrumb is clicked.
 * @param modifier The modifier to be applied to the breadcrumb.
 * @param displayTrailingSeparator Whether to display a trailing separator or not.
 * @param size The size of the breadcrumb.
 */
@Composable
public fun Breadcrumb(
    breadcrumbs: List<Breadcrumb>,
    onBreadcrumbClick: (Breadcrumb) -> Unit,
    modifier: Modifier = Modifier,
    displayTrailingSeparator: Boolean = false,
    size: BreadcrumbSize = BreadcrumbSize.Medium
) {
    val textStyle = when (size) {
        BreadcrumbSize.Medium -> Carbon.typography.bodyCompact01
        BreadcrumbSize.Small -> Carbon.typography.label01
    }

    Layout(
        content = {
            if (breadcrumbs.isEmpty()) return@Layout

            breadcrumbs.forEachIndexed { index, element ->
                BreadcrumbItem(
                    breadcrumb = element,
                    textStyle = textStyle,
                    onClick = onBreadcrumbClick,
                    modifier = Modifier
                        .layoutId(BreadcrumbMeasurePolicy.LayoutId.Breadcrumb)
                        .testTag(BreadcrumbTestTags.ITEM)
                )

                if (index < breadcrumbs.size - 1 ||
                    index == breadcrumbs.size - 1 && displayTrailingSeparator
                ) {
                    Separator(
                        textStyle = textStyle,
                        modifier = Modifier
                            .padding(horizontal = SpacingScale.spacing03)
                            .layoutId(BreadcrumbMeasurePolicy.LayoutId.Separator)
                            .testTag(BreadcrumbTestTags.SEPARATOR)
                    )
                }
            }
        },
        measurePolicy = BreadcrumbMeasurePolicy(),
        modifier = modifier,
    )
}

@Composable
private fun BreadcrumbItem(
    breadcrumb: Breadcrumb,
    textStyle: TextStyle,
    onClick: (Breadcrumb) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val theme = Carbon.theme
    val isHovered by interactionSource.collectIsHoveredAsState()

    val textStyle = remember(theme, textStyle, breadcrumb.isEnabled, isHovered) {
        textStyle.copy(
            textDecoration = if (isHovered) TextDecoration.Underline else TextDecoration.None,
            color = when {
                isHovered && breadcrumb.isEnabled -> theme.linkPrimaryHover
                breadcrumb.isEnabled -> theme.linkPrimary
                else -> theme.textPrimary
            }
        )
    }

    BasicText(
        text = breadcrumb.label,
        style = textStyle,
        modifier = modifier
            .clickable(
                enabled = breadcrumb.isEnabled,
                interactionSource = interactionSource,
                indication = BreadcrumbIndication(Carbon.theme),
                onClick = { onClick(breadcrumb) }
            )
            .pointerHoverIcon(
                icon = if (breadcrumb.isEnabled) PointerIcon.Hand else PointerIcon.Default
            )
    )
}

@Composable
private fun Separator(
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    BasicText(
        text = "/",
        style = textStyle.copy(color = Carbon.theme.textPrimary),
        modifier = modifier.clearAndSetSemantics {}
    )
}
