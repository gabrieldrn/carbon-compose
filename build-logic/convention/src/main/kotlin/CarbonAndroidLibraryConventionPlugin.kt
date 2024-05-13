import carbon.buildlogic.applyTestOptions
import carbon.buildlogic.configureKotlinAndroidCommon
import carbon.buildlogic.getPlugin
import carbon.buildlogic.kotlinOptions
import carbon.buildlogic.libs
import carbon.buildlogic.setupComposeCompilerOptions
import carbon.buildlogic.setupExplicitApi
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * A plugin used by android libraries modules from Carbon to configure themselves. It
 * provides a convention to keep consistency across those modules.
 */
class CarbonAndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val libs = libs

        with(pluginManager) {
            apply(libs.getPlugin("android-library"))
            apply(libs.getPlugin("kotlin-android"))
            apply(libs.getPlugin("vanniktech-publish-plugin"))
        }

        extensions.configure<LibraryExtension> {
            configureKotlinAndroidCommon()
            afterEvaluate { setupExplicitApi() }
            kotlinOptions {
                options.setupComposeCompilerOptions(this@with)
            }
            applyTestOptions()
        }
    }
}
