import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import carbon.compose.buildlogic.configureKotlinAndroidCommon
import carbon.compose.buildlogic.getPlugin
import carbon.compose.buildlogic.getVersion
import carbon.compose.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class CarbonApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val libs = libs

        with(pluginManager) {
            apply(libs.getPlugin("android-application"))
            apply(libs.getPlugin("kotlin-android"))
        }

        extensions.configure<BaseAppModuleExtension> {

            configureKotlinAndroidCommon(this)

            defaultConfig {
                targetSdk = libs.getVersion("targetSdk").toInt()
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                }
                debug {
                    isDebuggable = true
                }
            }
        }
    }
}
