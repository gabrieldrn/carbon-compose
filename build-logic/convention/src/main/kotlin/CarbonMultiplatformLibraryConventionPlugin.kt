import com.android.build.gradle.LibraryExtension
import com.gabrieldrn.carbon.buildlogic.Constants
import com.gabrieldrn.carbon.buildlogic.applyTestOptions
import com.gabrieldrn.carbon.buildlogic.configureKotlinAndroidCommon
import com.gabrieldrn.carbon.buildlogic.getPlugin
import com.gabrieldrn.carbon.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

/**
 * A plugin used by kmp libraries modules from Carbon to configure themselves. It
 * provides a convention to keep consistency across those modules.
 */
class CarbonMultiplatformLibraryConventionPlugin : Plugin<Project> {

    @OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)
    override fun apply(target: Project) = with(target) {
        val libs = libs

        with(pluginManager) {
            apply(libs.getPlugin("android-library"))
            apply(libs.getPlugin("kotlin-multiplatform"))
            apply(libs.getPlugin("jetbrains-compose"))
            apply(libs.getPlugin("compose-compiler"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            androidTarget {
                @OptIn(ExperimentalKotlinGradlePluginApi::class)
                instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)

                compilerOptions {
                    jvmTarget.set(Constants.Versions.JVM)
                }
            }

            jvm("desktop") {
                compilerOptions {
                    jvmTarget.set(Constants.Versions.JVM)
                }
            }

            wasmJs { browser() }

            sourceSets.apply {
                all {
                    languageSettings.optIn("androidx.compose.ui.test.ExperimentalTestApi")
                }
            }

            explicitApi()
        }

        extensions.configure<LibraryExtension> {
            defaultConfig {
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            configureKotlinAndroidCommon()

            applyTestOptions()
        }

        extensions.configure<ComposeCompilerGradlePluginExtension> {
            reportsDestination.set(layout.buildDirectory.dir("compose_compiler"))
            metricsDestination.set(layout.buildDirectory.dir("compose_compiler"))
            stabilityConfigurationFiles.add {
                file("${projectDir.absolutePath}/compose_compiler_config.conf")
            }
        }
    }
}
