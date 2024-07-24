package carbon.compose.buildlogic

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
