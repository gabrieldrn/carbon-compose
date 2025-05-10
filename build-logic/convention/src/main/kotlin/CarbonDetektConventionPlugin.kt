import com.gabrieldrn.carbon.buildlogic.getPlugin
import com.gabrieldrn.carbon.buildlogic.getVersion
import com.gabrieldrn.carbon.buildlogic.libs
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
                "src/nativeMain/kotlin",
            )
            toolVersion = libs.getVersion("detekt")
            parallel = false
            config.setFrom("$rootDir/config/detekt/detekt.yml")
            allRules = false
            debug = false
            ignoreFailures = false
            basePath = rootProject.projectDir.absolutePath
            ignoredBuildTypes = listOf("debug")
        }

        tasks.named<Detekt>("detekt").configure {
            reports {
                xml.required.set(true)
                html.required.set(true)
                txt.required.set(false)
                sarif.required.set(true)
            }
        }
    }
}
