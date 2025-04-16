import com.gabrieldrn.carbon.buildlogic.getPlugin
import com.gabrieldrn.carbon.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.dokka.gradle.engine.plugins.DokkaHtmlPluginParameters

class CarbonDokkaConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        with(libs) {
            with(pluginManager) {
                apply(getPlugin("dokka"))
            }
        }

        extensions.configure<DokkaExtension> {
            pluginsConfiguration.named<DokkaHtmlPluginParameters>("html") {
                customStyleSheets.from(
                    project.rootDir.resolve("docs/dokka-custom-styles.css"),
                    project.rootDir.resolve("docs/dokka-custom-logo-styles.css")
                )
                customAssets.from(
                    project.rootDir.resolve("docs/assets/carbon_docs_icon.png")
                )
            }
        }
    }
}
