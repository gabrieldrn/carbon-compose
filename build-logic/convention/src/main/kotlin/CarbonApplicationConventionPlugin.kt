import carbon.buildlogic.Constants
import carbon.buildlogic.configureKotlinAndroidCommon
import carbon.buildlogic.getPlugin
import carbon.buildlogic.libs
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Plugin to configure android application modules from this project.
 */
class CarbonApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val libs = libs

        with(pluginManager) {
            apply(libs.getPlugin("android-application"))
            apply(libs.getPlugin("kotlin-android"))
        }

        extensions.configure<BaseAppModuleExtension> {

            configureKotlinAndroidCommon()

            defaultConfig {
                targetSdk = Constants.Versions.COMPILE_SDK
            }

            buildTypes {
                release {
                    isMinifyEnabled = true
                    isShrinkResources = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
                debug {
                    isDebuggable = true
                }
            }
        }
    }
}
