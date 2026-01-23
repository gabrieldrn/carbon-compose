import com.android.build.api.dsl.ApplicationExtension
import com.gabrieldrn.carbon.buildlogic.Constants
import com.gabrieldrn.carbon.buildlogic.getPlugin
import com.gabrieldrn.carbon.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * Plugin to configure android application modules from this project.
 */
class CarbonApplicationConventionPlugin : Plugin<Project> {

    @OptIn(ExperimentalWasmDsl::class)
    override fun apply(target: Project) = with(target) {
        val libs = libs

        with(pluginManager) {
            apply(libs.getPlugin("android-application"))
            apply(libs.getPlugin("jetbrains-compose"))
            apply(libs.getPlugin("compose-compiler"))
        }

        extensions.configure<KotlinAndroidProjectExtension> {
            compilerOptions {
                jvmTarget.set(Constants.Versions.JVM)
            }
        }

        extensions.configure<ApplicationExtension> {
            compileSdk = Constants.Versions.COMPILE_SDK

            defaultConfig {
                targetSdk = Constants.Versions.COMPILE_SDK
                minSdk = Constants.Versions.MIN_SDK
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//                consumerProguardFiles("consumer-rules.pro")
            }

            compileOptions {
                sourceCompatibility = Constants.Versions.JAVA
                targetCompatibility = Constants.Versions.JAVA
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
