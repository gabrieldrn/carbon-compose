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

package com.gabrieldrn.codegen.common.model

/**
 * Marks a `@Transient` color property in a model class as having been removed.
 *
 * The code generator emits a deprecated constructor parameter whose per-theme fallback value is
 * sourced from the theme's `removed-tokens.json` file:
 * ```kotlin
 * @RemovedToken @Transient val removedName: String? = null
 * ```
 *
 * The corresponding entry must be present in each theme's `removed-tokens.json`.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class RemovedToken
