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

package com.gabrieldrn.carbon.foundation.color

import androidx.compose.ui.graphics.Color

/**
 * Returns the container color based on a provided [layer].
 *
 * @param layer Associated layer. Defaults to layer 00.
 */
public fun Theme.containerColor(layer: Layer = Layer.Layer00): Color = when (layer) {
    Layer.Layer00 -> background
    Layer.Layer01 -> layer01
    Layer.Layer02 -> layer02
    Layer.Layer03 -> layer03
}

/**
 * Returns the contextual `layer` color token corresponding to the [layer] it is placed on.
 * @param layer Containing layer. Defaults to layer 00.
 */
public fun Theme.layerColor(layer: Layer = Layer.Layer00): Color = when (layer) {
    Layer.Layer00 -> layer01
    Layer.Layer01 -> layer02
    else -> layer03
}

/**
 * Returns the contextual `layer-active` color token corresponding to the [layer] it is placed on.
 * @param layer Containing layer. Defaults to layer 00.
 */
public fun Theme.layerActiveColor(layer: Layer = Layer.Layer00): Color = when (layer) {
    Layer.Layer00 -> layerActive01
    Layer.Layer01 -> layerActive02
    else -> layerActive03
}

/**
 * Returns the contextual `layer-hover` color token corresponding to the [layer] it is placed on.
 * @param layer Containing layer. Defaults to layer 00.
 */
public fun Theme.layerHoverColor(layer: Layer = Layer.Layer00): Color = when (layer) {
    Layer.Layer00 -> layerHover01
    Layer.Layer01 -> layerHover02
    else -> layerHover03
}

/**
 * Returns the contextual `layer-selected` color token corresponding to the [layer] it is placed on.
 * @param layer Containing layer. Defaults to layer 00.
 */
public fun Theme.layerSelectedColor(layer: Layer = Layer.Layer00): Color = when (layer) {
    Layer.Layer00 -> layerSelected01
    Layer.Layer01 -> layerSelected02
    else -> layerSelected03
}

/**
 * Returns the contextual `layer-selected-hover` color token corresponding to the [layer] it is
 * placed on.
 * @param layer Containing layer. Defaults to layer 00.
 */
public fun Theme.layerSelectedHoverColor(layer: Layer = Layer.Layer00): Color = when (layer) {
    Layer.Layer00 -> layerSelectedHover01
    Layer.Layer01 -> layerSelectedHover02
    else -> layerSelectedHover03
}

/**
 * Returns the contextual `layer-accent` color token corresponding to the [layer] it is placed on.
 * @param layer Containing layer. Defaults to layer 00.
 */
public fun Theme.layerAccentColor(layer: Layer = Layer.Layer00): Color = when (layer) {
    Layer.Layer00 -> layerAccent01
    Layer.Layer01 -> layerAccent02
    else -> layerAccent03
}

/**
 * Returns the contextual `layer-accent-hover` color token corresponding to the [layer] it is placed
 * on.
 * @param layer Containing layer. Defaults to layer 00.
 */
public fun Theme.layerAccentHoverColor(layer: Layer = Layer.Layer00): Color = when (layer) {
    Layer.Layer00 -> layerAccentHover01
    Layer.Layer01 -> layerAccentHover02
    else -> layerAccentHover03
}

/**
 * Returns the contextual `layer-accent-active` color token corresponding to the [layer] it is
 * placed on.
 * @param layer Containing layer. Defaults to layer 00.
 */
public fun Theme.layerAccentActiveColor(layer: Layer = Layer.Layer00): Color = when (layer) {
    Layer.Layer00 -> layerAccentActive01
    Layer.Layer01 -> layerAccentActive02
    else -> layerAccentActive03
}

/**
 * Returns the contextual `layer-border-subtle` color token corresponding to the [layer] it is
 * placed on.
 * @param layer Containing layer. Defaults to layer 00.
 */
public fun Theme.layerBorderSubtle(layer: Layer = Layer.Layer00): Color = when (layer) {
    Layer.Layer00 -> borderSubtle01
    Layer.Layer01 -> borderSubtle02
    else -> borderSubtle03
}

/**
 * Returns the contextual `layer-border-strong` color token corresponding to the [layer] it is
 * placed on.
 * @param layer Containing layer. Defaults to layer 00.
 */
public fun Theme.layerBorderStrong(layer: Layer = Layer.Layer00): Color = when (layer) {
    Layer.Layer00 -> borderStrong01
    Layer.Layer01 -> borderStrong02
    else -> borderStrong03
}
