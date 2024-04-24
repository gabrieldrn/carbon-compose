import carbon.compose.Configuration

plugins {
    id("carbon.android.application")
    alias(libs.plugins.kotlin.android)
    id("carbon.detekt")
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

dependencies {
    implementation(project(":carbon"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.uiTooling)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.timber)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso)
}
