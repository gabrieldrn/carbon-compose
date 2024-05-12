package carbon.compose.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Shortcut to configure Kotlin compiler options.
 */
internal fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

/**
 * Setup explicit api for all kotlin compile tasks except tests and debug.
 * @see <a href="https://github.com/Kotlin/KEEP/blob/master/proposals/explicit-api-mode.md">
 *     Explicit API mode
 * </a>
 */
internal fun Project.setupExplicitApi() {
    tasks
        .withType(KotlinCompile::class.java)
        .matching { !it.name.contains("test", ignoreCase = true) }
        .also {
            logger.quiet("Configuring explicit API for tasks ${it.map { t -> t.path }}")
        }
        .matching {
            it.kotlinOptions
                .freeCompilerArgs
                .contains("-X" + Constants.CompileArgs.STRICT_API)
                .not()
        }
        .configureEach {
            kotlinOptions.freeCompilerArgs += listOf("-X" + Constants.CompileArgs.STRICT_API)
        }
}
