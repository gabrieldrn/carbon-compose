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

package com.gabrieldrn.carbon.uishell

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.gabrieldrn.carbon.Carbon
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.api.ExperimentalCarbonApi
import com.gabrieldrn.carbon.button.ButtonType
import com.gabrieldrn.carbon.button.IconButton
import com.gabrieldrn.carbon.foundation.color.LocalCarbonInlineTheme
import com.gabrieldrn.carbon.foundation.color.LocalCarbonTheme
import com.gabrieldrn.carbon.foundation.color.Theme
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale

@ExperimentalCarbonApi
@Composable
public fun UiShellHeader(
    headerName: String,
    modifier: Modifier = Modifier,
    menuIconPainter: Painter? = null,
    actions: @Composable RowScope.() -> Unit = {},
    onMenuIconPressed: () -> Unit = {},
    windowInsets: WindowInsets = WindowInsets.statusBars.only(WindowInsetsSides.Top),
    inlineTheme: Theme = LocalCarbonInlineTheme.current,
) {
    CarbonDesignSystem(inlineTheme) {
        Box(
            modifier = modifier
                .background(LocalCarbonTheme.current.background)
                .windowInsetsPadding(windowInsets)
                .height(48.dp),
            contentAlignment = Alignment.BottomStart,
        ) {
            Row(
                modifier = Modifier
                    .windowInsetsPadding(
                        WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal)
                    )
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (menuIconPainter != null) {
                    IconButton(
                        onClick = onMenuIconPressed,
                        iconPainter = menuIconPainter,
                        buttonType = ButtonType.Ghost,
                    )
                    Spacer(modifier = Modifier.width(SpacingScale.spacing03))
                } else {
                    Spacer(modifier = Modifier.width(SpacingScale.spacing05))
                }

                BasicText(
                    text = headerName,
                    style = Carbon.typography.headingCompact02.copy(
                        color = Carbon.theme.textPrimary
                    ),
                    modifier = Modifier.weight(1f)
                )

                Row(
                    content = actions,
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(LocalCarbonTheme.current.borderSubtle00)
            )
        }
    }
}

@Composable
private fun MenuButton(
    onMenuIconPressed: () -> Unit,
    menuIconPainter: Painter,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clickable(onClick = onMenuIconPressed),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = menuIconPainter,
            contentDescription = "Back",
            colorFilter = ColorFilter.tint(LocalCarbonTheme.current.textPrimary),
            modifier = Modifier
                .size(24.dp)
        )
    }
}
