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

package com.gabrieldrn.carbon.semantics

import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

/**
 * Carbon design system semantics properties. They follow the same principles as the ones from
 * [androidx.compose.ui.semantics.SemanticsProperties].
 *
 * @see [androidx.compose.ui.semantics.SemanticsProperties]
 */
public object CarbonSemanticsProperties {

    /**
     * @see [readOnly]
     */
    public val ReadOnly: SemanticsPropertyKey<Boolean> =
        SemanticsPropertyKey("ReadOnly")

    /**
     * @see [imageVectorName]
     */
    internal val ImageVectorName: SemanticsPropertyKey<String> =
        SemanticsPropertyKey("ImageVectorName")

}

/**
 * Whether the node is read-only.
 */
public fun SemanticsPropertyReceiver.readOnly() {
    this[CarbonSemanticsProperties.ReadOnly] = true
}

/**
 * Mark semantic node that contains an icon identified by its name.
 */
internal fun SemanticsPropertyReceiver.imageVectorName(name: String) {
    this[CarbonSemanticsProperties.ImageVectorName] = name
}
