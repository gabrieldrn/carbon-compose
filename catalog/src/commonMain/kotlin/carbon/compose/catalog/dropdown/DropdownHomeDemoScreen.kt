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

package carbon.compose.catalog.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import carbon.compose.button.Button
import carbon.compose.catalog.Res
import carbon.compose.catalog.ic_arrow_right
import carbon.compose.foundation.color.LocalCarbonTheme
import carbon.compose.foundation.spacing.SpacingScale
import org.jetbrains.compose.resources.painterResource

@Composable
fun DropdownDemoMenu(
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(LocalCarbonTheme.current.background)
            .padding(SpacingScale.spacing05)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(SpacingScale.spacing03)
    ) {
        Button(
            label = "Default Dropdown",
            iconPainter = painterResource(Res.drawable.ic_arrow_right),
            onClick = { onNavigate(DropdownNavDestination.Default.route) },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            label = "Multiselect Dropdown",
            iconPainter = painterResource(Res.drawable.ic_arrow_right),
            onClick = { onNavigate(DropdownNavDestination.MultiSelect.route) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
