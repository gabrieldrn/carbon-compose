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

package com.gabrieldrn.carbon.textinput

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.common.semantics.imageVectorName
import com.gabrieldrn.carbon.foundation.color.LocalCarbonTheme

@Composable
internal fun ClickableTrailingIcon(
    icon: ImageVector,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentDescription: String? = null,
) {
    val theme = Carbon.theme
    val buttonTheme by remember(Carbon.theme) {
        mutableStateOf(
            value = theme.copy(
                linkPrimary = theme.iconPrimary,
                linkPrimaryHover = theme.iconPrimary
            ),
            policy = referentialEqualityPolicy()
        )
    }

    CompositionLocalProvider(LocalCarbonTheme provides buttonTheme) {
        IconButton(
            iconPainter = rememberVectorPainter(icon),
            buttonType = ButtonType.Ghost,
            isEnabled = isEnabled,
            onClick = onClick,
            contentDescription = contentDescription,
            interactionSource = interactionSource,
            modifier = modifier
                .semantics { imageVectorName(icon.name) }
                .testTag(TextInputTestTags.CLICKABLE_TRAILING_ICON)
        )
    }
}
