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

package com.gabrieldrn.carbon.foundation.color

import co.touchlab.kermit.Logger

/**
 * # Layering tokens
 *
 * Layering tokens are explicit tokens used to manually map the layering model onto components.
 * Layering tokens come in predefined sets that coordinate with the different layer levels.
 * For each layer that a component needs to lives on, a separate component variation must be built
 * using the tokens from that layer set.
 *
 * There are four layers within a theme: base layer, layer 01, layer 02, and layer 03.
 * Layers stack one on top of the other in a set order.
 * Each step in UI color (excluding interaction colors) is another layer and will require the use
 * of a different set of layering tokens.
 *
 * Follow link below for more information about layer tokens and how they work.
 *
 * (From [Layering tokens documentation](https://carbondesignsystem.com/elements/color/usage/))
 */
@Suppress("UndocumentedPublicProperty")
public enum class Layer {
    Layer00,
    Layer01,
    Layer02,
    Layer03;

    /**
     * Returns the next layer in the layering model from this layer.
     */
    public fun next(): Layer {
        return when (this) {
            Layer00 -> Layer01
            Layer01 -> Layer02
            Layer02 -> Layer03
            Layer03 -> Layer03
                .also {
                    Logger.w("Current layer ($it) is already the highest layer available.")
                }
        }
    }

    override fun toString(): String = when (this) {
        Layer00 -> "Layer 00"
        Layer01 -> "Layer 01"
        Layer02 -> "Layer 02"
        Layer03 -> "Layer 03"
    }
}
