plugins {
    id("carbon.android.library")
}

android {
    namespace = "dev.gabrieldrn.carbon"
}

dependencies {
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.uiTooling)
    implementation(libs.androidx.compose.foundation)
}
