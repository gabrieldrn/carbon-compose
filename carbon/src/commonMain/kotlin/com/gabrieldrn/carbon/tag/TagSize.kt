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

package com.gabrieldrn.carbon.tag

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * There are three different tag sizes — small, medium, and large. Use small tags in condensed or
 * inline spaces. The medium tag size is the default size and is most commonly used. Use large tags
 * when they are used as a primary task of the page or focal point, when you have more screen real
 * estate at your disposal, or if the tag lives near other components that are also 32px in height.
 */
@Immutable
@Suppress("UndocumentedPublicProperty")
public enum class TagSize(
    internal val height: Dp
) {
    Small(18.dp),
    Medium(24.dp),
    Large(32.dp)
}
