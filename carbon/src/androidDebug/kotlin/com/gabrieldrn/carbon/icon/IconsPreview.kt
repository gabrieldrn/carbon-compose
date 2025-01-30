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

package com.gabrieldrn.carbon.icon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.icons.CheckmarkFilledIcon
import com.gabrieldrn.carbon.icons.CloseIcon
import com.gabrieldrn.carbon.icons.ErrorFilledIcon
import com.gabrieldrn.carbon.icons.ViewIcon
import com.gabrieldrn.carbon.icons.ViewOffIcon
import com.gabrieldrn.carbon.icons.WarningAltIcon
import com.gabrieldrn.carbon.icons.WarningFilledIcon

private class IconParameterProvider : PreviewParameterProvider<@Composable () -> Unit> {
    override val values = buildList<@Composable () -> Unit> {
        add { CheckmarkFilledIcon() }
        add { CloseIcon() }
        add { ErrorFilledIcon() }
        add { ViewIcon() }
        add { ViewOffIcon() }
        add { WarningAltIcon() }
        add { WarningFilledIcon() }
    }.asSequence()
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun IconPreview(
    @PreviewParameter(IconParameterProvider::class) icon: @Composable () -> Unit
) {
    Box(modifier = Modifier.padding(SpacingScale.spacing03)) {
        icon()
    }
}
