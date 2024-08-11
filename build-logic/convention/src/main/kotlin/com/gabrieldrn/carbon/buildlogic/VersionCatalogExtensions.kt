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

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Gets the default version catalog named from the project.
 */
internal val Project.libs get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

/**
 * From this version catalog, returns the version associated with the given alias.
 */
internal fun VersionCatalog.getVersion(alias: String) = findVersion(alias).get().requiredVersion

/**
 * From this version catalog, returns the library associated with the given alias, as a Dependency
 * object.
 */
internal fun VersionCatalog.getLibrary(alias: String) = findLibrary(alias).get().get()

/**
 * From this version catalog, returns the plugin associated with the given alias.
 */
internal fun VersionCatalog.getPlugin(alias: String) = findPlugin(alias).get().get().pluginId
