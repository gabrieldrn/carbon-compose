import dev.gabrieldrn.carbon.buildlogic.getPlugin
import dev.gabrieldrn.carbon.buildlogic.javaVersion
import dev.gabrieldrn.carbon.buildlogic.libs
import dev.gabrieldrn.carbon.buildlogic.setupExplicitApi
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure

/**
 * A plugin used by java libraries modules from Carbon to configure themselves. It provides
 * a convention to keep consistency across those modules.
 */
class CarbonJavaLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val libs = libs
        with(pluginManager) {
            apply("java-library")
            apply(libs.getPlugin("kotlin-jvm"))
        }

        extensions.configure<JavaPluginExtension> {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }

        setupExplicitApi()

        // TODO Configure test tasks.
    }
}
