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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.accordion.Accordion
import com.gabrieldrn.carbon.accordion.AccordionSection
import com.gabrieldrn.carbon.accordion.AccordionSize
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.catalog.common.loremIpsum
import com.gabrieldrn.carbon.dropdown.Dropdown
import com.gabrieldrn.carbon.dropdown.base.DropdownInteractiveState
import com.gabrieldrn.carbon.dropdown.base.toDropdownOptions
import com.gabrieldrn.carbon.toggle.Toggle

private val sections = listOf(
    AccordionSection("Section 1", loremIpsum),
    AccordionSection("Section 2", loremIpsum),
    AccordionSection("Section 3", loremIpsum),
    AccordionSection("Section 4", loremIpsum, isEnabled = false)
)

@Composable
fun AccordionDemoScreen(modifier: Modifier = Modifier) {

    var accordionSize by rememberSaveable { mutableStateOf(AccordionSize.Large) }
    var isFlushed by rememberSaveable { mutableStateOf(false) }

    DemoScreen(
        demoParametersContent = {
            Dropdown(
                placeholder = "Choose accordion size",
                label = "Accordion size",
                options = AccordionSize.entries.toDropdownOptions(),
                state = getSizeSpecification(accordionSize)
                    ?.let(DropdownInteractiveState::Warning)
                    ?: DropdownInteractiveState.Enabled,
                selectedOption = accordionSize,
                onOptionSelected = { accordionSize = it },
            )

            Toggle(
                label = "Flushed alignment",
                isToggled = isFlushed,
                onToggleChange = { isFlushed = it }
            )
        },
        demoContent = {
            Accordion(
                sections = sections,
                size = accordionSize,
                flushAlignment = isFlushed,
            )
        },
        modifier = modifier
    )
}
