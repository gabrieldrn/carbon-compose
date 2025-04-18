import com.android.build.gradle.LibraryExtension
import com.gabrieldrn.carbon.buildlogic.Constants
import com.gabrieldrn.carbon.buildlogic.applyTestOptions
import com.gabrieldrn.carbon.buildlogic.configureKotlinAndroidCommon
import com.gabrieldrn.carbon.buildlogic.getPlugin
import com.gabrieldrn.carbon.buildlogic.libs
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.dokka.gradle.engine.plugins.DokkaHtmlPluginParameters
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

/**
 * A plugin used by kmp libraries modules from Carbon to configure themselves. It
 * provides a convention to keep consistency across those modules.
 */
class CarbonMultiplatformLibraryConventionPlugin : Plugin<Project> {

    @OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)
    override fun apply(target: Project) = with(target) {
        with(libs) {
            with(pluginManager) {
                apply(getPlugin("android-library"))
                apply(getPlugin("kotlin-multiplatform"))
                apply(getPlugin("jetbrains-compose"))
                apply(getPlugin("compose-compiler"))
                apply(getPlugin("dokka"))
                apply(getPlugin("vanniktech-publish-plugin"))
            }
        }

        val extension = extensions.create("carbonLibrary", Extension::class.java)

        // region KMP config

        extensions.configure<KotlinMultiplatformExtension> {
            androidTarget {
                @OptIn(ExperimentalKotlinGradlePluginApi::class)
                instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)

                compilerOptions {
                    jvmTarget.set(Constants.Versions.JVM)
                }
            }

            jvm("desktop") {
                compilerOptions {
                    jvmTarget.set(Constants.Versions.JVM)
                }
            }

            wasmJs { browser() }

            listOf(
                iosX64(),
                iosArm64(),
                iosSimulatorArm64()
            )

            sourceSets.apply {
                all {
                    languageSettings.optIn("androidx.compose.ui.test.ExperimentalTestApi")
                }
            }

            explicitApi()
        }

        // endregion

        // region Android lib config

        extensions.configure<LibraryExtension> {
            defaultConfig {
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            configureKotlinAndroidCommon()

            applyTestOptions()
        }

        // endregion

        // region Compose compiler config

        extensions.configure<ComposeCompilerGradlePluginExtension> {
            reportsDestination.set(layout.buildDirectory.dir("compose_compiler"))
            metricsDestination.set(layout.buildDirectory.dir("compose_compiler"))
            stabilityConfigurationFiles.add {
                project.rootDir.resolve("compose/compiler_config.conf")
            }
        }

        // endregion

        // region Dokka

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
            afterEvaluate {
                moduleName.set(extension.artifactId.get())
            }
        }

        // endregion

        // region Maven Central publication

        extensions.configure<MavenPublishBaseExtension> {
            configure(
                KotlinMultiplatform(
                    javadocJar = JavadocJar.Dokka("dokkaGenerate"),
                    sourcesJar = true,
                    androidVariantsToPublish = listOf("release")
                )
            )

            configure<PublishingExtension> {
                repositories {
                    maven {
                        name = "GitHub"
                        url = uri("https://maven.pkg.github.com/gabrieldrn/carbon-compose")
                        credentials {
                            username = System.getenv("GITHUB_ACTOR")
                                ?: project.findProperty("CARBON_GITHUB_USER").toString()
                            password = System.getenv("GITHUB_TOKEN")
                                ?: project.findProperty("CARBON_GITHUB_TOKEN").toString()
                        }
                    }
                }
            }

            afterEvaluate {
                coordinates(
                    groupId = extension.artifactGroup.get(),
                    artifactId = extension.artifactId.get(),
                    version = extension.version.get()
                )

                pom {
                    name.set(extension.artifactId.get())
                }
            }
        }

        // endregion
    }

    interface Extension {
        val artifactGroup: Property<String>
        val artifactId: Property<String>
        val version: Property<String>
    }
}
