import carbon.compose.Configuration

plugins {
    id("carbon.android.application")
    id("carbon.detekt")
}

kotlin {
//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "CarbonCatalog"
//            isStatic = true
//        }
//    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":carbon"))
        }

        androidMain.dependencies {
//            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.compose.foundation)
            implementation(libs.androidx.compose.ui)
            implementation(libs.androidx.compose.uiTooling)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.lifecycle.runtime.ktx)

            implementation(libs.timber)
        }
//        commonMain.dependencies {
//            implementation(compose.runtime)
//            implementation(compose.foundation)
//            implementation(compose.ui)
//            implementation(compose.components.resources)
//            implementation(compose.components.uiToolingPreview)
//        }
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
