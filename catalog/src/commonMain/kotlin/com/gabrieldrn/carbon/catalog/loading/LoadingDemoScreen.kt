/*
 * Copyright 2024-2025 Gabriel Derrien
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

package com.gabrieldrn.carbon.catalog.loading

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gabrieldrn.carbon.catalog.common.DemoScreen
import com.gabrieldrn.carbon.foundation.spacing.SpacingScale
import com.gabrieldrn.carbon.loading.Loading
import com.gabrieldrn.carbon.loading.SmallLoading
import com.gabrieldrn.carbon.tab.TabItem

private enum class LoadingVariant { Large, Small }

private val loadingVariants = LoadingVariant.entries.map { TabItem(it.name) }

@Composable
fun LoadingDemoScreen(modifier: Modifier = Modifier) {
    DemoScreen(
        variants = loadingVariants,
        defaultVariant = loadingVariants.first(),
        demoContent = { variant ->
            when (LoadingVariant.valueOf(variant.label)) {
                LoadingVariant.Large ->
                    Loading(modifier = Modifier.padding(top = SpacingScale.spacing04))
                LoadingVariant.Small ->
                    SmallLoading(modifier = Modifier.padding(top = SpacingScale.spacing04))
            }
        },
        modifier = modifier,
        displayLayerParameter = false
    )
}
