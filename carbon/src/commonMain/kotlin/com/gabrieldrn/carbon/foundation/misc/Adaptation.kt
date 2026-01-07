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

package com.gabrieldrn.carbon.foundation.misc

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Describes the UI adaptation mode for Carbon components.
 *
 * This enum indicates how the UI should adapt to the current device or accessibility requirements.
 * Use [LocalCarbonAdaptation] to provide or read the current adaptation from the composition.
 */
public enum class Adaptation {

    /**
     * No adaptation. Default behavior for platforms that don't require special accessibility
     * adaptations.
     */
    None,

    /**
     * Adaptation intended for touch screens like tablets and mobile devices.
     */
    Touchscreens
}

/**
 * Composition local that exposes the current [Adaptation] for Carbon components.
 *
 * Default value is [Adaptation.None]. Provide a different value with:
 * `CompositionLocalProvider(LocalCarbonAdaptation provides Adaptation.Touchscreens) { ... }`
 */
public val LocalCarbonAdaptation: ProvidableCompositionLocal<Adaptation> =
    staticCompositionLocalOf { Adaptation.None }
