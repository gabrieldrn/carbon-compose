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

package carbon.compose.catalog.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import carbon.compose.button.Button
import carbon.compose.button.ButtonType
import carbon.compose.catalog.Res
import carbon.compose.catalog.ic_carbon
import carbon.compose.catalog.ic_logo_github
import carbon.compose.foundation.spacing.SpacingScale
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeLinksItem(
    isVertical: Boolean,
    onOpenLink: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .then(
                if (isVertical) Modifier.padding(vertical = SpacingScale.spacing05)
                else Modifier.padding(horizontal = SpacingScale.spacing05)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            SpacingScale.spacing05,
            Alignment.CenterVertically
        )
    ) {
        Button(
            label = "Carbon Design System",
            onClick = { onOpenLink("https://carbondesignsystem.com/") },
            buttonType = ButtonType.Primary,
            iconPainter = painterResource(Res.drawable.ic_carbon),
        )

        Button(
            label = "⭐️ Star on GitHub",
            onClick = { onOpenLink("https://github.com/gabrieldrn/carbon-compose") },
            buttonType = ButtonType.Tertiary,
            iconPainter = painterResource(Res.drawable.ic_logo_github),
        )
    }
}
