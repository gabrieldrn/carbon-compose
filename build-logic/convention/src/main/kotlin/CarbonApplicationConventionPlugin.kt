import carbon.compose.buildlogic.Constants
import carbon.compose.buildlogic.configureKotlinAndroidCommon
import carbon.compose.buildlogic.getPlugin
import carbon.compose.buildlogic.libs
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Plugin to configure android application modules from this project.
 */
class CarbonApplicationConventionPlugin : Plugin<Project> {

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    override fun apply(target: Project) = with(target) {
        val libs = libs

        with(pluginManager) {
            apply(libs.getPlugin("android-application"))
            apply(libs.getPlugin("kotlin-multiplatform"))
            apply(libs.getPlugin("jetbrains-compose"))
            apply(libs.getPlugin("compose-compiler"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            androidTarget {
                compilerOptions {
                    jvmTarget.set(Constants.Versions.JVM)
                }
            }

            jvm("desktop") {
                compilerOptions {
                    jvmTarget.set(Constants.Versions.JVM)
                }
            }
        }

        extensions.configure<BaseAppModuleExtension> {
            sourceSets.getByName("main").apply {
                manifest.srcFile("src/androidMain/AndroidManifest.xml")
                res.srcDirs("src/androidMain/res")
                resources.srcDirs("src/commonMain/resources")
            }

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
