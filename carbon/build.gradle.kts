import com.gabrieldrn.carbon.Configuration
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("carbon.kmp.library")
    id("carbon.detekt")
    id("carbon.dokka")
    alias(libs.plugins.vanniktech.publish.plugin)
}

buildscript {
    dependencies {
        classpath(libs.dokka.base)
    }
}

apply(from = "${rootDir}/scripts/publishing.gradle.kts")

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "Carbon"
            isStatic = true
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    useConfigDirectory(
                        project.projectDir.resolve("karma.config.d").resolve("wasm")
                    )
                }
            }
        }
        binaries.executable()
    }

    @OptIn(ExperimentalComposeLibrary::class)
    sourceSets {
        commonMain.dependencies {
            api(project(":carbon:common"))

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.animation)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.touchlab.kermit)
        }
        commonTest.dependencies {
            implementation(project(":carbon:test"))

            implementation(libs.kotlin.test)
            implementation(compose.uiTest)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }

    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.get().compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
                freeCompilerArgs.add("-opt-in=com.gabrieldrn.carbon.api.ExperimentalCarbonApi")
            }
        }
    }
}

compose.resources {
    packageOfResClass = "com.gabrieldrn.carbon"
    generateResClass = always
}

android {

    namespace = "com.gabrieldrn.carbon"

    dependencies {
        debugImplementation(compose.uiTooling)
        debugImplementation(libs.androidx.compose.ui.test.manifest)

        testImplementation(libs.junit)
        testImplementation(libs.kotlin.test)
        testImplementation(libs.kotlin.test.junit)

        androidTestImplementation(libs.kotlin.test)
        androidTestImplementation(libs.androidx.test.ext)
        androidTestImplementation(libs.androidx.test.espresso)
    }
}

dokka {
    moduleName.set("carbon")
}

mavenPublishing {

    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaGenerate"),
            sourcesJar = true,
            androidVariantsToPublish = listOf("release")
        )
    )

    val artifactId = "carbon"

    publishing {
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

    coordinates(
        groupId = Configuration.artifactGroup,
        artifactId = artifactId,
        version = rootProject.extra.get("libVersion").toString()
    )

    pom {
        name.set(artifactId)
    }
}
