plugins {
    id("carbon.android.application")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "carbon.compose.catalog"

    defaultConfig {
        applicationId = "carbon.compose.catalog"
        versionCode = 1
        versionName = "0.1"
    }
}

dependencies {
    implementation(project(":carbon"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.uiTooling)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.timber)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso)
}
