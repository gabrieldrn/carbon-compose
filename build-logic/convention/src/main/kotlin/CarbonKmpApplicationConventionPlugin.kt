import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.gabrieldrn.carbon.buildlogic.Constants
import com.gabrieldrn.carbon.buildlogic.getPlugin
import com.gabrieldrn.carbon.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Plugin to configure KMP applications modules from this project.
 */
class CarbonKmpApplicationConventionPlugin : Plugin<Project> {

    @OptIn(ExperimentalWasmDsl::class)
    override fun apply(target: Project) = with(target) {
        val libs = libs

        with(pluginManager) {
            apply(libs.getPlugin("android-library"))
            apply(libs.getPlugin("kotlin-multiplatform"))
            apply(libs.getPlugin("jetbrains-compose"))
            apply(libs.getPlugin("jetbrains-compose-hotReload"))
            apply(libs.getPlugin("compose-compiler"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            configure<KotlinMultiplatformAndroidLibraryTarget> {
                compileSdk = Constants.Versions.COMPILE_SDK
                minSdk = Constants.Versions.MIN_SDK

                androidResources {
                    enable = true
                }
            }

            jvm("desktop") {
                compilerOptions {
                    jvmTarget.set(Constants.Versions.JVM)
                }
            }

            wasmJs {
                browser()
                binaries.executable()
                compilerOptions {
                    freeCompilerArgs.add("-Xwasm-debugger-custom-formatters")
                }
            }
        }
    }
}
