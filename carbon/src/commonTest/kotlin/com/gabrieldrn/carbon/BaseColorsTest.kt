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

package com.gabrieldrn.carbon

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ComposeUiTest
import com.gabrieldrn.carbon.foundation.color.Layer
import com.gabrieldrn.carbon.foundation.color.WhiteTheme

abstract class BaseColorsTest {

    open val theme = WhiteTheme

    protected fun ComposeUiTest.forAllLayers(block: @Composable (layer: Layer) -> Unit) {
        setContent {
            Layer.entries.forEach { layer ->
                CarbonDesignSystem(theme = theme, layer = layer) {
                    block(layer)
                }
            }
        }
    }

    protected fun <T : Any> ComposeUiTest.forAllLayersAndStates(
        statesUnderTest: Collection<T>,
        block: @Composable (state: T, layer: Layer) -> Unit
    ) {
        setContent {
            statesUnderTest.forEach { state ->
                Layer.entries.forEach { layer ->
                    CarbonDesignSystem(theme = theme, layer = layer) {
                        block(state, layer)
                    }
                }
            }
        }
    }

    protected fun <T : Any, U : Any> ComposeUiTest.forAllLayersAndStates(
        statesUnderTest1: Collection<T>,
        statesUnderTest2: Collection<U>,
        block: @Composable (state1: T, state2: U, layer: Layer) -> Unit
    ) {
        setContent {
            statesUnderTest1.forEach { state1 ->
                statesUnderTest2.forEach { state2 ->
                    Layer.entries.forEach { layer ->
                        CarbonDesignSystem(theme = theme, layer = layer) {
                            block(state1, state2, layer)
                        }
                    }
                }
            }
        }
    }

    protected fun <T : Any, U : Any, V : Any> ComposeUiTest.forAllLayersAndStates(
        statesUnderTest1: Collection<T>,
        statesUnderTest2: Collection<U>,
        statesUnderTest3: Collection<V>,
        block: @Composable (state1: T, state2: U, state3: V, layer: Layer) -> Unit
    ) {
        setContent {
            statesUnderTest1.forEach { state1 ->
                statesUnderTest2.forEach { state2 ->
                    statesUnderTest3.forEach { state3 ->
                        Layer.entries.forEach { layer ->
                            CarbonDesignSystem(theme = theme, layer = layer) {
                                block(state1, state2, state3, layer)
                            }
                        }
                    }
                }
            }
        }
    }
}
