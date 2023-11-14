plugins {
    id("carbon.android.library")
    id("carbon.detekt")
}

android {
    namespace = "carbon.compose"
}

dependencies {
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.uiTooling)
    api(libs.androidx.compose.foundation)

//    implementation(libs.timber)
}
