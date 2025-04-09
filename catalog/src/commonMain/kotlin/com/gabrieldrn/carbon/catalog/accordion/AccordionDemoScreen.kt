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

package com.gabrieldrn.carbon.catalog.accordion

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.accordion.Accordion
import com.gabrieldrn.carbon.accordion.AccordionSize
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.catalog.common.loremIpsum
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

private val sections = listOf(
    "Section 1" to loremIpsum,
    "Section 2" to loremIpsum,
    "Section 3" to loremIpsum
)

@Composable
fun AccordionDemoScreen(modifier: Modifier = Modifier) {
    DemoScreen(
        demoContent = {
            Accordion(
                sections = sections,
                size = AccordionSize.Large,
                modifier = Modifier.padding(SpacingScale.spacing05)
            )
        },
        modifier = modifier
    )
}
