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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

public data class Breadcrumb(
    val label: String,
    val isEnabled: Boolean = true,
)

@Composable
public fun Breadcrumb(
    breadcrumbs: List<Breadcrumb>,
    onBreadcrumbClick: (Breadcrumb) -> Unit,
    modifier: Modifier = Modifier,
    displayTrailingSeparator: Boolean = false
) {
    Layout(
        content = {
            if (breadcrumbs.isEmpty()) return@Layout

            breadcrumbs.forEachIndexed { index, element ->
                BreadcrumbItem(
                    breadcrumb = element,
                    modifier = Modifier
                        .clickable { onBreadcrumbClick(element) }
                        .layoutId(BreadcrumbMeasurePolicy.LayoutId.Breadcrumb(index))
                )

                if (index < breadcrumbs.size - 1 ||
                    index == breadcrumbs.size - 1 && displayTrailingSeparator
                ) {
                    Separator(
                        modifier = Modifier
                            .padding(horizontal = SpacingScale.spacing03)
                            .layoutId(BreadcrumbMeasurePolicy.LayoutId.Separator(index))
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
    modifier: Modifier = Modifier
) {
    BasicText(
        text = breadcrumb.label,
        style = Carbon.typography.bodyCompact01.copy(
            color = if (breadcrumb.isEnabled) {
                Carbon.theme.linkPrimary
            } else {
                Carbon.theme.textPrimary
            }
        ),
        modifier = modifier
    )
}

@Composable
private fun Separator(
    modifier: Modifier = Modifier
) {
    BasicText(
        text = "/",
        style = Carbon.typography.bodyCompact01.copy(
            color = Carbon.theme.textPrimary
        ),
        modifier = modifier
    )
}
