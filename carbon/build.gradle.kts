import carbon.compose.Configuration
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    id("carbon.kmp.library")
    id("carbon.detekt")
}

apply(from = "${rootDir}/scripts/publishing.gradle.kts")

kotlin {
    // Disabled until actual iOS support is added
//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach {
//        it.binaries.framework {
//            baseName = "carbon"
//            isStatic = true
//        }
//    }

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }

    @OptIn(ExperimentalComposeLibrary::class)
    sourceSets {
        all {
            languageSettings.optIn("androidx.compose.ui.test.ExperimentalTestApi")
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
//            implementation(compose.uiTooling)
            implementation(compose.uiTest)
            implementation(compose.animation)
            implementation(compose.components.resources)

            implementation(libs.touchlab.kermit)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(compose.uiTest)
            implementation(libs.androidx.compose.ui.test.android)
        }
    }
}

compose.resources {
    packageOfResClass = "carbon.compose"
    generateResClass = always
}

android {

    namespace = "carbon.compose"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    dependencies {
        // Disabled while there's no specific Android implementations.
//        implementation(libs.androidx.lifecycle.runtime.ktx)
//        implementation(libs.androidx.lifecycle.viewModel.compose)
//        implementation(libs.androidx.compose.foundation)
//        implementation(libs.androidx.compose.ui)

        debugImplementation(libs.androidx.compose.uiTooling)
        debugImplementation(libs.androidx.compose.ui.test.manifest)

        testImplementation(libs.junit)
        testImplementation(libs.kotlin.test)
        testImplementation(libs.kotlin.test.junit)

        androidTestImplementation(libs.kotlin.test)
        androidTestImplementation(libs.androidx.test.ext)
        androidTestImplementation(libs.androidx.test.espresso)
        androidTestImplementation(libs.androidx.compose.ui.test)
    }
}

mavenPublishing {
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
