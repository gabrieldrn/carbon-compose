import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import dev.gabrieldrn.carbon.buildlogic.configureKotlinAndroidCommon
import dev.gabrieldrn.carbon.buildlogic.getPlugin
import dev.gabrieldrn.carbon.buildlogic.getVersion
import dev.gabrieldrn.carbon.buildlogic.libs
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
