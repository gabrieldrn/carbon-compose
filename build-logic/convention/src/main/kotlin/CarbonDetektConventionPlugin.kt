import carbon.compose.buildlogic.getPlugin
import carbon.compose.buildlogic.getVersion
import carbon.compose.buildlogic.libs
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named

class CarbonDetektConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val libs = libs

        pluginManager.apply(libs.getPlugin("detekt"))

        extensions.configure<DetektExtension> {
            toolVersion = libs.getVersion("detekt")
            parallel = false
            config.setFrom("$rootDir/config/detekt/detekt.yml")
            allRules = false
            debug = false
            ignoreFailures = true
            basePath = this@with.projectDir.absolutePath
        }

        tasks.named<Detekt>("detekt").configure {
            reports {
                xml {
                    required.set(true)
                    outputLocation.set(file("build/reports/detekt/detekt.xml"))
                }
                html {
                    required.set(true)
                    outputLocation.set(file("build/reports/detekt/detekt.html"))
                }
                txt.required.set(false)
                sarif.required.set(false)
            }
        }
    }
}
