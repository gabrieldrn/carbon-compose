import com.gabrieldrn.carbon.Configuration
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

            implementation(libs.compose.navigation)

            implementation(libs.koin)
            implementation(libs.koin.compose)

            implementation(libs.settings)
            implementation(libs.settings.noArg)
            implementation(libs.settings.coroutines)
            implementation(libs.settings.makeObservable)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat)
            implementation(libs.timber)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)

            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.gabrieldrn.carbon.catalog"

    defaultConfig {
        applicationId = "com.gabrieldrn.carbon.catalog"
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
        packageOfResClass = "com.gabrieldrn.carbon.catalog"
        generateResClass = always
    }

    desktop {
        application {
            mainClass = "com.gabrieldrn.carbon.catalog.MainKt"

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
