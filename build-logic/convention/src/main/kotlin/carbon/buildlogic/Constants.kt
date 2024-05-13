package carbon.buildlogic

import org.gradle.api.JavaVersion

internal object Constants {

    object Versions {
        const val COMPILE_SDK = 34
        const val MIN_SDK = 23

        val JAVA = JavaVersion.VERSION_17

        const val COMPOSE_COMPILER = "1.5.12"
    }

    object CompileArgs {
        const val STRICT_API = "explicit-api=strict"

        private const val COMPOSE = "plugin:androidx.compose.compiler.plugins.kotlin"
        const val COMPOSE_METRICS_PRE = "$COMPOSE:metricsDestination="
        const val COMPOSE_REPORT_PRE = "$COMPOSE:reportsDestination="
        const val COMPOSE_STABILITY_CONFIG_PRE = "$COMPOSE:stabilityConfigurationPath="
    }
}
