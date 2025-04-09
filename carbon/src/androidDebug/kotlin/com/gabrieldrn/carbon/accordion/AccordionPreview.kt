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

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

private val sections = listOf(
    "Section 1" to "This is the first section",
    "Section 2" to "This is the second section",
    "Section 3" to "This is the third section"
)

private class AccordionSizePreviewParameterProvider : PreviewParameterProvider<AccordionSize> {
    override val values: Sequence<AccordionSize>
        get() = AccordionSize.entries.asSequence()
}

@Preview(showBackground = true)
@Composable
private fun AccordionPreview(
    @PreviewParameter(AccordionSizePreviewParameterProvider::class)
    size: AccordionSize
) {
    CarbonDesignSystem {
        Accordion(
            sections = sections,
            size = size,
            modifier = Modifier.padding(SpacingScale.spacing05)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FlushedAccordionPreview(
    @PreviewParameter(AccordionSizePreviewParameterProvider::class)
    size: AccordionSize
) {
    CarbonDesignSystem {
        Accordion(
            sections = sections,
            size = size,
            flushAlignment = true,
            modifier = Modifier.padding(SpacingScale.spacing05)
        )
    }
}
