package carbon.buildlogic

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerToolOptions

internal fun KotlinCommonCompilerToolOptions.setupComposeCompilerOptions(project: Project) =
    with(project) {
        freeCompilerArgs.addAll(
            "-P",
            Constants.CompileArgs.COMPOSE_METRICS_PRE + "${buildDir}/compose/metrics",
            "-P",
            Constants.CompileArgs.COMPOSE_REPORT_PRE + "${buildDir}/compose/reports",
        )
        file("${projectDir.absolutePath}/compose_compiler_config.conf")
            .takeIf { it.exists() }
            ?.let {
                freeCompilerArgs.addAll(
                    "-P",
                    Constants.CompileArgs.COMPOSE_STABILITY_CONFIG_PRE + it
                )
            }
    }
