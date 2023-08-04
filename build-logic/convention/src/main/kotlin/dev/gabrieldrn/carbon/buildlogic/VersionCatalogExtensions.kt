package dev.gabrieldrn.carbon.buildlogic

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
