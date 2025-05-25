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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.CarbonDesignSystem

private val breadcrumbs = buildList {
    repeat(3) {
        add(Breadcrumb(label = "Breadcrumb ${it + 1}", isEnabled = it != 0))
    }
}

private class BreadcrumbPreviewParameterProvider :
    PreviewParameterProvider<Triple<BreadcrumbSize, Boolean, Dp>> {
    override val values = sequence {
        listOf(true, false).forEach { trailingSeparator ->
            BreadcrumbSize.entries.forEach { size ->
                listOf(Dp.Unspecified, 200.dp).forEach { width ->
                    yield(Triple(size, trailingSeparator, width))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun BreadcrumbPreview(
    @PreviewParameter(BreadcrumbPreviewParameterProvider::class)
    parameters: Triple<BreadcrumbSize, Boolean, Dp>
) {
    val (size, trailingSeparator, width) = parameters

    CarbonDesignSystem {
        Column(modifier = Modifier.fillMaxWidth()) {
            Breadcrumb(
                breadcrumbs = breadcrumbs,
                onBreadcrumbClick = {},
                displayTrailingSeparator = trailingSeparator,
                size = size,
                modifier = Modifier.width(width)
            )
        }
    }
}
