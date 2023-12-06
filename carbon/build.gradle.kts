plugins {
    id("carbon.android.library")
    id("carbon.detekt")
}

android {
    namespace = "carbon.compose"
}

dependencies {
    api(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.uiTooling)

//    implementation(libs.timber)

    debugImplementation(libs.androidx.compose.ui.test.manifest)

    androidTestImplementation(libs.androidx.compose.ui.test)
}
