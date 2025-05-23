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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

private val breadcrumbs = buildList {
    repeat(3) {
        add(Breadcrumb(label = "Breadcrumb ${it + 1}", isEnabled = it != 0))
    }
}

@Preview(showBackground = true)
@Composable
private fun MediumBreadcrumbPreview() {
    CarbonDesignSystem {
        Column(modifier = Modifier.fillMaxWidth()) {
            Breadcrumb(
                breadcrumbs = breadcrumbs,
                onBreadcrumbClick = {},
                displayTrailingSeparator = true,
            )
            Breadcrumb(
                breadcrumbs = breadcrumbs,
                onBreadcrumbClick = {},
                displayTrailingSeparator = false,
                modifier = Modifier
                    .padding(top = SpacingScale.spacing05)
                    .width(200.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SmallBreadcrumbPreview() {
    CarbonDesignSystem {
        Column(modifier = Modifier.fillMaxWidth()) {
            Breadcrumb(
                breadcrumbs = breadcrumbs,
                onBreadcrumbClick = {},
                displayTrailingSeparator = true,
                size = BreadcrumbSize.Small
            )
            Breadcrumb(
                breadcrumbs = breadcrumbs,
                onBreadcrumbClick = {},
                displayTrailingSeparator = false,
                modifier = Modifier
                    .padding(top = SpacingScale.spacing05)
                    .width(200.dp),
                size = BreadcrumbSize.Small
            )
        }
    }
}
