import carbon.compose.buildlogic.Constants
import carbon.compose.buildlogic.configureKotlinAndroidCommon
import carbon.compose.buildlogic.getPlugin
import carbon.compose.buildlogic.kotlinOptions
import carbon.compose.buildlogic.libs
import carbon.compose.buildlogic.setupExplicitApi
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.configure

/**
 * A plugin used by android libraries modules from Carbon to configure themselves. It
 * provides a convention to keep consistency across those modules.
 */
class CarbonAndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val libs = libs

        with(pluginManager) {
            apply(libs.getPlugin("android-library"))
            apply(libs.getPlugin("kotlin-android"))
            apply(libs.getPlugin("vanniktech-publish-plugin"))
        }

        extensions.configure<LibraryExtension> {
            configureKotlinAndroidCommon()
            setupExplicitApi()
            applyKotlinOptions(this@with)
            applyTestOptions()
        }
    }

    private fun LibraryExtension.applyKotlinOptions(project: Project) = with(project) {
        kotlinOptions {
            freeCompilerArgs += listOf(
                "-P",
                Constants.CompileArgs.COMPOSE_METRICS_PRE + "${buildDir}/compose/metrics",
                "-P",
                Constants.CompileArgs.COMPOSE_REPORT_PRE + "${buildDir}/compose/reports",
            )

            file("compose_compiler_config.conf").takeIf { it.exists() }?.let {
                freeCompilerArgs += listOf(
                    "-P",
                    Constants.CompileArgs.COMPOSE_STABILITY_CONFIG_PRE + it
                )
            }
        }
    }

    @Suppress("UnstableApiUsage")
    private fun LibraryExtension.applyTestOptions() {
        testOptions {
            // Somehow this must be specified for instrumentation tests to work. If not, the
            // Android system warns that the generated app is made for a lower API level.
            targetSdk = Constants.Versions.COMPILE_SDK

            unitTests.all {
                it.testLogging {
                    events = setOf(
                        TestLogEvent.PASSED,
                        TestLogEvent.SKIPPED,
                        TestLogEvent.FAILED,
//                        TestLogEvent.STANDARD_OUT,
//                        TestLogEvent.STANDARD_ERROR
                    )
                    exceptionFormat = TestExceptionFormat.FULL
                    showStandardStreams = true
                    showExceptions = true
                    showCauses = true
                    showStackTraces = true
                    showStandardStreams = true
                }
            }
        }
    }
}
