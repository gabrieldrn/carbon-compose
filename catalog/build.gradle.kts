import carbon.compose.Configuration

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
    }
}

compose.resources {
    packageOfResClass = "carbon.compose.catalog"
    generateResClass = always
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
