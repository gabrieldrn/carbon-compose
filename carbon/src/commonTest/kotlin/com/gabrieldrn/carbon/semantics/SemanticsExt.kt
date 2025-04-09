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

package com.gabrieldrn.carbon.semantics

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert

/**
 * Returns whether the node is read-only and checks the following:
 * - The component is disabled.
 * - The component is focused.
 * - The component has read-only property.
 *
 * @see CarbonSemanticsProperties.ReadOnly
 */
public fun isReadOnly(): SemanticsMatcher = SemanticsMatcher(
    description = "is read-only: is disabled, is focusable, has read-only property",
    matcher = {
        SemanticsProperties.Disabled in it.config &&
            SemanticsProperties.Focused in it.config &&
            it.config.getOrElseNullable(CarbonSemanticsProperties.ReadOnly) { null } == true
    },
)

/**
 * Asserts that the node is read-only.
 */
public fun SemanticsNodeInteraction.assertIsReadOnly(
    messagePrefixOnError: (() -> String)? = null
): SemanticsNodeInteraction = assert(
    matcher = isReadOnly(),
    messagePrefixOnError = messagePrefixOnError
)

/**
 * Returns whether the node has an icon with the given name.
 */
internal fun SemanticsNodeInteraction.assertHasImageVector(
    iconName: String
): SemanticsNodeInteraction = assert(
    matcher = SemanticsMatcher(
        description = "has icon with name: $iconName",
        matcher = { it.config[CarbonSemanticsProperties.ImageVectorName] == iconName }
    ),
    messagePrefixOnError = { "Has icon with name: $iconName" }
)
