import carbon.Configuration

plugins {
    id("carbon.kmp")
    id("carbon.detekt")
}

apply(from = "${rootDir}/scripts/publishing.gradle.kts")

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

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "carbon-shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "carbon.android"

    dependencies {
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.lifecycle.viewModel.compose)
        implementation(libs.androidx.compose.foundation)
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.uiTooling)
        implementation(libs.androidx.compose.ui.test) {
            exclude(group = "androidx.test.espresso", module = "espresso-core")
        }

        implementation(libs.timber)

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
