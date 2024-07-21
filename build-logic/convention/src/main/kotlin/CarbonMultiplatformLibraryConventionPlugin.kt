import carbon.compose.buildlogic.Constants
import carbon.compose.buildlogic.applyTestOptions
import carbon.compose.buildlogic.configureKotlinAndroidCommon
import carbon.compose.buildlogic.getPlugin
import carbon.compose.buildlogic.libs
import carbon.compose.buildlogic.setupComposeCompilerOptions
import carbon.compose.buildlogic.setupExplicitApi
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * A plugin used by kmp libraries modules from Carbon to configure themselves. It
 * provides a convention to keep consistency across those modules.
 */
class CarbonMultiplatformLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val libs = libs

        with(pluginManager) {
            apply(libs.getPlugin("android-library"))
            apply(libs.getPlugin("kotlin-multiplatform"))
            apply(libs.getPlugin("vanniktech-publish-plugin"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            androidTarget {
                compilations.all {
                    kotlinOptions {
                        jvmTarget = Constants.Versions.JAVA.toString()
                    }
                    compileTaskProvider.configure {
                        compilerOptions.setupComposeCompilerOptions(this@with)
                    }
                }
            }

            afterEvaluate { setupExplicitApi() }
        }

        extensions.configure<LibraryExtension> {
            configureKotlinAndroidCommon()
            applyTestOptions()
        }
    }
}
