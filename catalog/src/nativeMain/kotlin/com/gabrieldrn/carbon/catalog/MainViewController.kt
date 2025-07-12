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

package com.gabrieldrn.carbon.catalog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.window.ComposeUIViewController
import com.gabrieldrn.carbon.CarbonDesignSystem
import com.gabrieldrn.carbon.foundation.color.layerBackground
import platform.UIKit.UIViewController

@OptIn(ExperimentalComposeUiApi::class)
@Suppress("FunctionNaming", "unused", "FunctionName")
fun MainViewController(): UIViewController =
    ComposeUIViewController {
        CarbonDesignSystem {
            val windowContainerSize = LocalWindowInfo.current.containerSize

            val layoutType by remember {
                derivedStateOf {
                    with(windowContainerSize) {
                        if (width < height) {
                            CatalogLayoutType.Vertical
                        } else {
                            CatalogLayoutType.Horizontal
                        }
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxSize().layerBackground(),
                contentAlignment = Alignment.Center
            ) {
                Catalog(layoutType = layoutType)
            }
        }
    }
