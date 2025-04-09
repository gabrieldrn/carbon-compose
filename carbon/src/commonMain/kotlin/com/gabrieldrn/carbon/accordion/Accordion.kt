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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon

/**
 * # Accordion
 *
 * The accordion component delivers large amounts of content in a small space through progressive
 * disclosure. The header title gives the user a high level overview of the content allowing the
 * user to decide which sections to read.
 *
 * Accordions can make information processing and discovering more effective. However, it does hide
 * content from users and it’s important to account for a user not noticing or reading all of the
 * included content.
 *
 * (From [Accordion documentation](https://carbondesignsystem.com/components/accordion/usage/))
 *
 * @param sections A list of pairs containing the header and body text for each section. The header
 * is the title of the section and the body is the content of the section. The sections are
 * displayed in order. Each pair associates a header (first element) with a body (second element).
 * @param modifier The modifier to be applied to the accordion.
 */
@Composable
public fun Accordion(
    sections: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    val dividerColor = Carbon.theme.borderSubtle00 // TODO Adjust by layer

    Column(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(dividerColor)
        )

        sections.forEach { (header, body) ->
            Section(
                header = header,
                body = body,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(dividerColor)
            )
        }
    }
}

@Composable
private fun Section(
    header: String,
    body: String,
    modifier: Modifier = Modifier
) {
    val typography = Carbon.typography
    val theme = Carbon.theme

    val textStyle = remember {
        typography.body01.copy(color = theme.textPrimary)
    }

    Column(modifier = modifier.padding(start = 16.dp)) {
        Box(modifier = Modifier.height(40.dp)) {
            BasicText(
                text = header,
                style = textStyle,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
        BasicText(
            text = body,
            style = textStyle,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )
    }
}
