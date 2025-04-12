plugins {
    id("carbon.kmp.library")
    id("carbon.detekt")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.ui)
        }
    }
}

android {
    namespace = "com.gabrieldrn.carbon.common"
}
