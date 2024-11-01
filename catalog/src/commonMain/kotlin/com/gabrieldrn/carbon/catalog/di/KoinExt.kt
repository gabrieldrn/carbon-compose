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

package com.gabrieldrn.carbon.catalog.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.gabrieldrn.carbon.catalog.common.ViewModel
import org.koin.compose.currentKoinScope
import org.koin.compose.koinInject
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

@Composable
inline fun <reified T : ViewModel> injectViewModel(
    qualifier: Qualifier? = null,
    scope: Scope = currentKoinScope(),
    noinline parameters: ParametersDefinition? = null,
): T {
    val viewModel = koinInject<T>(
        qualifier = qualifier,
        scope = scope,
        parameters = parameters,
    )

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clear()
        }
    }

    return viewModel
}
