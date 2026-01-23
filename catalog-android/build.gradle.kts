import com.gabrieldrn.carbon.Configuration

plugins {
    id("carbon.android.application")
    id("carbon.detekt")
}

android {
    namespace = "com.gabrieldrn.carbon.android.catalog"

    defaultConfig {
        applicationId = "com.gabrieldrn.carbon.catalog"
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(project(":catalog"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)

    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.animation)
    implementation(libs.compose.components.resources)

    implementation(libs.compose.navigation)

    implementation(libs.koin)
    implementation(libs.koin.compose)

    implementation(libs.timber)

    debugImplementation(libs.compose.uiTooling)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso)

    coreLibraryDesugaring(libs.desugaring.jdkLibs)
}
