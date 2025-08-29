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

package com.gabrieldrn.carbon.popover

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.R
import com.gabrieldrn.carbon.button.ButtonSize
import com.gabrieldrn.carbon.button.IconButtonWithPopover
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

  private class IconButtonWithPopoverPreviewParameterProvider :
    PreviewParameterProvider<Pair<ButtonSize, PopoverAlignment>> {
    override val values = sequence {
        ButtonSize.entries.forEach { buttonSize ->
            PopoverAlignment.entries.forEach { alignment ->
                yield(buttonSize to alignment)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun IconButtonWithPopoverPreview(
    @PreviewParameter(IconButtonWithPopoverPreviewParameterProvider::class)
    params: Pair<ButtonSize, PopoverAlignment>
) {
    CarbonDesignSystem {
        Box(
            modifier = Modifier.size(400.dp),
            contentAlignment = Alignment.Center
        ) {
            val (buttonSize, alignment) = params

            IconButtonWithPopover(
                iconPainter = painterResource(id = R.drawable.ic_add),
                isPopoverVisible = true,
                onClick = {},
                popoverAlignment = alignment,
                buttonSize = buttonSize,
            ) {
                BasicText(
                    text = "Hi,\nthere!",
                    style = Carbon.typography.body01,
                    modifier = Modifier.padding(SpacingScale.spacing04)
                )
            }
        }
    }
}
