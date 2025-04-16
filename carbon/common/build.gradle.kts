plugins {
    id("carbon.kmp.library")
    id("carbon.detekt")
    id("carbon.dokka")
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

dokka {
    moduleName.set("carbon-common")
}
