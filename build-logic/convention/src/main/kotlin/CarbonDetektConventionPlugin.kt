import carbon.buildlogic.getPlugin
import carbon.buildlogic.getVersion
import carbon.buildlogic.libs
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
            source.setFrom(
                "src/main/kotlin",
                "src/commonMain/kotlin",
                "src/androidMain/kotlin",
                "src/iosMain/kotlin",
            )
            toolVersion = libs.getVersion("detekt")
            parallel = false
            config.setFrom("$rootDir/config/detekt/detekt.yml")
            allRules = false
            debug = false
            ignoreFailures = true
            basePath = this@with.projectDir.absolutePath
            ignoredBuildTypes = listOf("debug")
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
