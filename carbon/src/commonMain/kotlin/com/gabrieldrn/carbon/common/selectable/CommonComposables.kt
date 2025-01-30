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

package com.gabrieldrn.carbon.common.selectable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.foundation.text.Text
import com.gabrieldrn.carbon.icons.WarningAltIcon
import com.gabrieldrn.carbon.icons.WarningFilledIcon

@Composable
internal fun ErrorContent(
    colors: SelectableColors,
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        WarningFilledIcon(
            modifier = Modifier.padding(SpacingScale.spacing01),
            tint = Carbon.theme.supportError
        )
        Text(
            text = errorMessage,
            color = colors.errorMessageTextColor,
            modifier = Modifier.padding(start = SpacingScale.spacing03),
            style = Carbon.typography.label01
        )
    }
}

@Composable
internal fun WarningContent(
    colors: SelectableColors,
    warningMessage: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        WarningAltIcon(Modifier.padding(2.dp))
        Text(
            text = warningMessage,
            color = colors.warningMessageTextColor,
            modifier = Modifier.padding(start = SpacingScale.spacing03),
            style = Carbon.typography.label01
        )
    }
}
