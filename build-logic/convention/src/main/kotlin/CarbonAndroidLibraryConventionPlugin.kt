import carbon.compose.buildlogic.applyTestOptions
import carbon.compose.buildlogic.configureKotlinAndroidCommon
import carbon.compose.buildlogic.getPlugin
import carbon.compose.buildlogic.libs
import carbon.compose.buildlogic.setupComposeCompilerOptions
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

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
            apply(libs.getPlugin("compose-compiler"))
            apply(libs.getPlugin("vanniktech-publish-plugin"))
        }

        tasks.named("compileKotlin", KotlinCompilationTask::class.java) {
            compilerOptions.setupComposeCompilerOptions(this@with)
        }

        extensions.configure<LibraryExtension> {
            configureKotlinAndroidCommon()
//            afterEvaluate { setupExplicitApi() }
            applyTestOptions()
        }
    }
}
