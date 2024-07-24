package carbon.compose.buildlogic

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerToolOptions

internal fun KotlinCommonCompilerToolOptions.setupComposeCompilerOptions(project: Project) =
    with(project) {
        freeCompilerArgs.addAll(
            "-P",
            Constants.CompileArgs.COMPOSE_METRICS_PRE +
                "\"${layout.buildDirectory.get()}/compose/metrics\"",
            "-P",
            Constants.CompileArgs.COMPOSE_REPORT_PRE +
                "\"${layout.buildDirectory.get()}/compose/reports\"",
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
