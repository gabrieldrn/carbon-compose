import com.gabrieldrn.carbon.Configuration
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    id("carbon.kmp.library")
    id("carbon.detekt")
}

carbonLibrary {
    artifactId.set("carbon")
    artifactGroup.set(Configuration.artifactGroup)
    version.set(Configuration.version)
}

val generatedColorTokensDir = layout.buildDirectory.dir("generated/colorTokens/commonMain")

kotlin {
    androidLibrary {
        namespace = "com.gabrieldrn.carbon"

        compilerOptions {
            enableCoreLibraryDesugaring = true
        }
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            instrumentationRunnerArguments["notAnnotation"] =
                "com.gabrieldrn.carbon.AndroidExcluded"
            execution = "HOST"
        }
    }

    listOf(
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

    sourceSets {
        commonMain {
            kotlin.srcDir(generatedColorTokensDir)

            dependencies {
                api(project(":carbon-common"))

                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.ui)
                implementation(libs.compose.uiToolingPreview)
                implementation(libs.compose.animation)
                implementation(libs.compose.components.resources)

                api(libs.kotlinx.datetime)

                implementation(libs.touchlab.kermit)
            }
        }

        commonTest.dependencies {
            implementation(project(":carbon-test"))

            implementation(libs.kotlin.test)
            implementation(libs.compose.uiTest)
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.compose.ui.testManifest)
            }
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

tasks.withType<KotlinCompilationTask<*>>().configureEach {
    dependsOn(":code-gen:execute")
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
    coreLibraryDesugaring(libs.desugaring.jdkLibs)
}
