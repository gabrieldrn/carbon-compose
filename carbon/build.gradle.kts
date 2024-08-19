import com.gabrieldrn.carbon.Configuration
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration

plugins {
    id("carbon.kmp.library")
    id("carbon.detekt")
    alias(libs.plugins.vanniktech.publish.plugin)
    alias(libs.plugins.dokka)
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

    @OptIn(ExperimentalComposeLibrary::class)
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.uiTest) {
                exclude(group = "androidx.test.espresso", module = "espresso-core")
            }
            implementation(compose.animation)
            implementation(compose.components.resources)

            implementation(libs.touchlab.kermit)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(compose.uiTest)
        }
        desktopTest.dependencies {
            implementation(compose.desktop.currentOs)
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

tasks.dokkaHtml {
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        customStyleSheets = listOf(
            file("../docs/dokka-custom-styles.css"),
            file("../docs/dokka-custom-logo-styles.css")
        )
        customAssets = listOf(file("../docs/assets/carbon_docs_icon.png"))
    }
    moduleName.set("Carbon Compose")
    moduleVersion.set("v${Configuration.versionName}")
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
