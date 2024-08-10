import carbon.compose.Configuration
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("carbon.android.application")
    id("carbon.detekt")
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Catalog"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(project(":carbon"))

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.animation)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.components.resources)

            implementation(libs.androidx.navigation.compose)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat)
            implementation(libs.timber)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "carbon.compose.catalog"

    defaultConfig {
        applicationId = "carbon.compose.catalog"
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

compose {
    resources {
        packageOfResClass = "carbon.compose.catalog"
        generateResClass = always
    }

    desktop {
        application {
            mainClass = "carbon.compose.catalog.MainKt"

            nativeDistributions {
                targetFormats(
                    // Disabled because major version 0 is not allowed
                    // To compile for macOS, uncomment the following line and comment the one
                    // configuring packageVersion
//                    TargetFormat.Dmg,
                    TargetFormat.Msi,
                    TargetFormat.Deb
                )
                packageName = "Carbon catalog"
                packageVersion = Configuration.versionName

                macOS {
                    iconFile.set(project.file("carbon_catalog_icon.icns"))
                }

                windows {
                    iconFile.set(project.file("carbon_catalog_icon.ico"))
                }

                linux {
                    iconFile.set(project.file("carbon_catalog_icon.png"))
                }
            }
        }
    }
}
