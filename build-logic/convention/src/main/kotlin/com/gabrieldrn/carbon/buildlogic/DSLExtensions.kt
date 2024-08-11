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

package com.gabrieldrn.carbon.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.UnknownDomainObjectException
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * Shortcut to configure Kotlin compiler options.
 */
internal fun CommonExtension<*, *, *, *, *, *>.kotlin(
    block: KotlinAndroidProjectExtension.() -> Unit
) {
    try {
        (this as ExtensionAware).extensions.configure("kotlin", block)
    } catch (e: UnknownDomainObjectException) {
        // Kotlin options not found in extension, skipping configuration.
    }
}
