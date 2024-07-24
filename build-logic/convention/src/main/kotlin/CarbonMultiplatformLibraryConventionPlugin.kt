import carbon.compose.buildlogic.Constants
import carbon.compose.buildlogic.applyTestOptions
import carbon.compose.buildlogic.configureKotlinAndroidCommon
import carbon.compose.buildlogic.getPlugin
import carbon.compose.buildlogic.libs
import carbon.compose.buildlogic.setupComposeCompilerOptions
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * A plugin used by kmp libraries modules from Carbon to configure themselves. It
 * provides a convention to keep consistency across those modules.
 */
class CarbonMultiplatformLibraryConventionPlugin : Plugin<Project> {

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    override fun apply(target: Project) = with(target) {
        val libs = libs

        with(pluginManager) {
            apply(libs.getPlugin("android-library"))
            apply(libs.getPlugin("kotlin-multiplatform"))
            apply(libs.getPlugin("jetbrains-compose"))
            apply(libs.getPlugin("compose-compiler"))
            apply(libs.getPlugin("vanniktech-publish-plugin"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
//            explicitApi()

            androidTarget {
                compilerOptions {
                    jvmTarget.set(Constants.Versions.JVM)
                }
                compilations.all {
                    compileTaskProvider.configure {
                        compilerOptions {
                            setupComposeCompilerOptions(this@with)
//                            freeCompilerArgs.add("-X${Constants.CompileArgs.STRICT_API}")
                        }
                    }
                }
            }

        }

        extensions.configure<LibraryExtension> {
            configureKotlinAndroidCommon()
            applyTestOptions()
        }
    }
}
