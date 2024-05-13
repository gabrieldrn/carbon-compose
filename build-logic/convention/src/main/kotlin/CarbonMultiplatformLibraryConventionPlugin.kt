import carbon.kmp.buildlogic.Constants
import carbon.kmp.buildlogic.applyTestOptions
import carbon.kmp.buildlogic.getPlugin
import carbon.kmp.buildlogic.libs
import carbon.kmp.buildlogic.setupExplicitApi
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
        }

        extensions.configure<KotlinMultiplatformExtension> {
            androidTarget {
                compilations.all {
                    kotlinOptions {
                        jvmTarget = Constants.Versions.JAVA.toString()
                    }
                }
            }

            afterEvaluate { setupExplicitApi() }
        }

        extensions.configure<LibraryExtension> {
            compileSdk = Constants.Versions.COMPILE_SDK
            defaultConfig.minSdk = Constants.Versions.MIN_SDK
            compileOptions {
                sourceCompatibility = Constants.Versions.JAVA
                targetCompatibility = Constants.Versions.JAVA
            }
            applyTestOptions()
        }
    }
}
